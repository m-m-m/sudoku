/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.IntPredicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.mmm.base.collection.ArrayIterator;
import io.github.mmm.event.AbstractEventSender;
import io.github.mmm.sudoku.builder.SudokuBuilder;
import io.github.mmm.sudoku.common.AttributeModificationCounter;
import io.github.mmm.sudoku.common.Candidates;
import io.github.mmm.sudoku.dimension.AbstractDimension;
import io.github.mmm.sudoku.dimension.Dimension;
import io.github.mmm.sudoku.dimension.DimensionType;
import io.github.mmm.sudoku.dimension.SquareDimension;
import io.github.mmm.sudoku.event.ChangeAware;
import io.github.mmm.sudoku.event.SudokuChangeEvent;
import io.github.mmm.sudoku.event.SudokuEvent;
import io.github.mmm.sudoku.event.SudokuEventListener;
import io.github.mmm.sudoku.field.Field;
import io.github.mmm.sudoku.history.ChangeSet;
import io.github.mmm.sudoku.partition.Partition;
import io.github.mmm.sudoku.partitioning.Box;
import io.github.mmm.sudoku.partitioning.Column;
import io.github.mmm.sudoku.partitioning.FlexiblePartitioning.PartitioningFactory;
import io.github.mmm.sudoku.partitioning.FlexiblePartitioning.RegionFactory;
import io.github.mmm.sudoku.partitioning.Partitioning;
import io.github.mmm.sudoku.partitioning.Row;
import io.github.mmm.sudoku.partitioning.Sum;
import io.github.mmm.sudoku.solution.AbstractHint;
import io.github.mmm.sudoku.solution.Hint;
import io.github.mmm.sudoku.solution.HintStep;

/**
 * Represents a Sudoku puzzle.<br>
 * <b>ATTENTION:</b> Indexes of {@link Sudoku} are {@code 1} based. This avoids confusion as otherwise {@code 1} would
 * actually be {@link #getSymbol(int) symbol}/{@link Column column}/{@link Row row} {@code 2}, etc. Hence, you should
 * loop like this:
 *
 * <pre>
 * int size = sudoku.{@link #getSize()};
 * for (int x=1; x<=size; x++) {
 *   for (int y=1; y<=size; y++) {
 *     {@link Field} field = sudoku.{@link #getField(int, int) getField}(x, y);
 *     int value = field.{@link Field#getValue() getValue()};
 *     if (value != -1) {
 *       // field already filled/solved
 *       String valueSymbol = sudoku.{@link #getSymbol(int) getSymbol}(value);
 *       // e.g. value=9 and valueSymbol="9" (or value=16 and valueSymbol="F")
 *       System.out.println("Value at " + x + "x" + y + " is " + valueSymbol);
 *     }
 *   }
 * }
 * </pre>
 */
public class Sudoku extends AbstractEventSender<SudokuEvent<?>, SudokuEventListener>
    implements Dimension, ChangeAware, AttributeModificationCounter, Iterable<Partitioning> {

  private static final Logger LOG = LoggerFactory.getLogger(Sudoku.class);

  /** @see #getSize() */
  protected final AbstractDimension dimension;

  private final Candidates allCandidates;

  private final Field[][] fields;

  private final Partitioning[] partitionings;

  private final String type;

  private int modificationCounter;

  private ChangeSet lastChange;

  private List<SudokuChangeEvent<?>> currentChanges;

  /**
   * The constructor.
   */
  public Sudoku() {

    this(SquareDimension.D9, Box.FACTORY);
  }

  /**
   * The constructor.
   *
   * @param dimension the {@link AbstractDimension}.
   * @param factory the {@link RegionFactory}.
   * @param factories the {@link PartitioningFactory} instances.
   */
  public Sudoku(AbstractDimension dimension, RegionFactory factory, PartitioningFactory... factories) {

    super();
    this.dimension = dimension;
    int bitMask = (2 << (dimension.getSize() - 1)) - 1;
    this.allCandidates = Candidates.of(bitMask);
    this.fields = createFields();
    this.partitionings = new Partitioning[factories.length + 3];
    int i = 0;
    this.partitionings[i++] = new Column(this);
    this.partitionings[i++] = new Row(this);
    this.partitionings[i++] = factory.create(this, i);
    for (PartitioningFactory pFactory : factories) {
      this.partitionings[i++] = pFactory.create(this, i);
    }
    this.lastChange = new ChangeSet(Collections.emptyList(), null);
    this.type = computeType();
  }

  private String computeType() {

    String puzzle = "Sudoku";
    Partitioning region = this.partitionings[2];
    String typeName = "";
    if (!(region instanceof Box)) {
      typeName = region.getName();
    }
    String previous = "";
    for (int i = 3; i < this.partitionings.length; i++) {
      Partitioning p = this.partitionings[i];
      if (p instanceof Sum) {
        puzzle = "Sumdoku";
      } else {
        String name = p.getName();
        if (!name.equals(previous)) {
          typeName = compose(typeName, name);
          previous = name;
        }
      }
    }
    return compose(typeName, puzzle);
  }

  private static String compose(String s1, String s2) {

    if (s1.isEmpty()) {
      return s2;
    } else if (s2.isEmpty()) {
      return s1;
    } else {
      return s1 + "-" + s2;
    }
  }

  @Override
  public int getSize() {

    return this.dimension.getSize();
  }

  @Override
  public int getBoxWidth() {

    return this.dimension.getBoxWidth();
  }

  @Override
  public int getBoxHeight() {

    return this.dimension.getBoxHeight();
  }

  @Override
  public int getBoxSize() {

    return this.dimension.getBoxSize();
  }

  @Override
  public DimensionType getDimensionType() {

    return this.dimension.getDimensionType();
  }

  /**
   * @return the type of this {@link Sudoku} (e.g. just "Sudoku" or "Jigsaw-Hyper-SumDoku").
   */
  public String getType() {

    return this.type;
  }

  /**
   * @param i the index of the requested symbol in the range from {@code 0} to <code>{@link #getSize()}-1</code>.
   * @return the symbol (or number) for the given index. Typically first symbols are '1'-'9' followed letters starting
   *         from 'A'.
   */
  @Override
  public String getSymbol(int i) {

    return this.dimension.getSymbol(i);
  }

  /**
   * @return the {@link Candidates} that {@link Candidates#has(int) has} all possible {@link Field#getValue() values}.
   */
  public Candidates getAllCandidates() {

    return this.allCandidates;
  }

  /**
   * @param x the {@link Field#getX() x-coordinate} in the range from {@code 1} to <code>{@link #getSize()}</code>.
   * @param y the {@link Field#getY() y-coordinate} in the range from {@code 1} to <code>{@link #getSize()}</code>.
   * @return the {@link Field} at the given position.
   */
  public Field getField(int x, int y) {

    if (x - 1 >= this.fields.length) {
      throw new IndexOutOfBoundsException(x);
    }
    if (y - 1 >= this.fields[x - 1].length) {
      throw new IndexOutOfBoundsException(y);
    }
    return this.fields[x - 1][y - 1];
  }

  /**
   * Convenience method for {@link #setFieldValue(Field, int, boolean, boolean)} to set a {@link Field#isGiven() given}
   * {@link Field#getValue() value}.
   *
   * @param x the column number starting from {@code 1}.
   * @param y the row number starting from {@code 1}.
   * @param value the given value starting from {@code 1}.
   */
  public void setFieldGivenValue(int x, int y, int value) {

    setFieldValue(getField(x, y), value, true, false);
  }

  /**
   * Smart variant of {@link Field#setValue(int)} that also updates candidates.
   *
   * @param field the {@link Field} to update.
   * @param value the {@link Field#getValue() field value} to fill in.
   */
  public void setFieldValue(Field field, int value) {

    setFieldValue(field, value, false, true);
  }

  /**
   * Smart variant of {@link Field#setValue(int)} that also updates candidates and allows undo/redo.
   *
   * @param field the {@link Field} to update.
   * @param value the {@link Field#getValue() field value} to fill in.
   * @param given - {@code true} to set the {@link Field#isGiven() given-flag} and consider the value as a <em>clue</em>
   *        during initialisation.
   * @param withHistory - {@code true} to add history events for {@link #undo() undo} support, {@code false} otherwise.
   */
  public void setFieldValue(Field field, int value, boolean given, boolean withHistory) {

    int oldValue = field.getValue();
    if (oldValue == value) {
      return;
    }

    ChangeSet changeSet = null;
    if (withHistory) {
      changeSet = startUndoHistory();
    }
    field.setValue(value, given);
    if (value > 0) {
      for (Partitioning partitioning : this.partitionings) {
        Partition partition = field.getPartition(partitioning);
        if (partition != null) {
          LOG.debug("Updating partition {} of {}", partition.getIndex(), partitioning.getName());
          for (Field neighbour : partition) {
            if ((neighbour != field) && neighbour.hasCandidate(value)) {
              if (neighbour.hasValue()) {
                field.setError(true);
                neighbour.setError(true);
              } else {
                neighbour.excludeCandidate(value);
              }
            }
          }
        }
      }
    }
    endUndoHistory(changeSet);
    if ((changeSet == null) && given) {
      assert (this.lastChange == null) : "Given clues should be set before actual values!";
    }
  }

  /**
   * @param field the {@link Field} to modify.
   * @param candidate the {@link Field#hasCandidate(int) candidate} to {@link Field#toggleCandidate(int) toggle}.
   */
  public void toggleCandidate(Field field, int candidate) {

    ChangeSet changeSet = startUndoHistory();
    field.toggleCandidate(candidate);
    endUndoHistory(changeSet);
  }

  /**
   * @param field the {@link Field} to modify.
   * @param candidates the {@link Candidates} to {@link Candidates#exclude(Candidates) exclude}.
   */
  public void excludeCandidates(Field field, Candidates candidates) {

    ChangeSet changeSet = startUndoHistory();
    field.setCandidates(field.getCandidates().exclude(candidates));
    endUndoHistory(changeSet);
  }

  /**
   * @param hint the {@link Hint} or {@link HintStep} to {@link AbstractHint#apply()}.
   */
  public void apply(AbstractHint hint) {

    apply(hint, true);
  }

  /**
   * @param hint the {@link Hint} or {@link HintStep} to {@link AbstractHint#apply()}.
   * @param withHistory - {@code true} to add history events for {@link #undo() undo} support, {@code false} otherwise.
   */
  public void apply(AbstractHint hint, boolean withHistory) {

    ChangeSet changeSet = null;
    if (withHistory) {
      changeSet = startUndoHistory();
    }
    hint.apply();
    endUndoHistory(changeSet);
  }

  private ChangeSet startUndoHistory() {

    if (this.currentChanges != null) {
      return null; // even history already started (sub-transaction)
    }
    // collect change events in ChangeSet for undo feature
    this.currentChanges = new ArrayList<>();
    return new ChangeSet(this.currentChanges, this.lastChange);
  }

  private void endUndoHistory(ChangeSet changeSet) {

    if (changeSet == null) {
      return; // event history not yet ended (sub-transaction)
    }
    this.currentChanges = null; // end "transaction" to record changes
    this.lastChange = changeSet;
  }

  /**
   * @param value the {@link Field#getValue() value} to count.
   * @return the number of {@link Field}s that {@link Field#getValue() have} the given {@code value}. In case this is
   *         equal to the {@link Sudoku#getSize() sudoku size} the value is completed.
   */
  public int getValueCount(int value) {

    int size = getSize();
    int valueCount = 0;
    for (int x = 1; x <= size; x++) {
      for (int y = 1; y <= size; y++) {
        Field field = getField(x, y);
        if (field.getValue() == value) {
          valueCount++;
        }
      }
    }
    return valueCount;
  }

  /**
   * @return the last {@link ChangeSet}.
   */
  public ChangeSet getLastChange() {

    return this.lastChange;
  }

  @Override
  public int getModificationCounter() {

    return this.modificationCounter;
  }

  @Override
  public void undo() {

    ChangeSet previous = this.lastChange.getPrevious();
    if (previous != null) {
      ChangeSet last = this.lastChange;
      this.lastChange = previous;
      last.undo();
    }
  }

  @Override
  public void redo() {

    ChangeSet next = this.lastChange.getNext();
    if (next != null) {
      this.lastChange = next;
      next.redo();
    }
  }

  /**
   * @return the number of {@link Partitioning}s of this {@link Sudoku}. Will be {@code 3} for a classic {@link Sudoku}
   *         that has {@link Column}s, {@link Row}s, and {@link Box}es.
   */
  public final int getPartitioningCount() {

    return this.partitionings.length;
  }

  /**
   * @param i the index of the requested {@link Partitioning} in the range from {@code 1} to
   *        <code>{@link #getPartitioningCount()}</code>.
   * @return the requested {@link Partitioning}.
   */
  public final Partitioning getPartitioning(int i) {

    if ((i < 1) || (i > this.partitionings.length)) {
      throw new IndexOutOfBoundsException(i);
    }
    return this.partitionings[i - 1];
  }

  @Override
  public Iterator<Partitioning> iterator() {

    return new ArrayIterator<>(this.partitionings);
  }

  private Field[][] createFields() {

    int size = getSize();
    Field[][] result = new Field[size][size];
    for (int x = 1; x <= size; x++) {
      for (int y = 1; y <= size; y++) {
        result[x - 1][y - 1] = new Field(this, x, y);
      }
    }
    return result;
  }

  /**
   * Internal method - use only when you know what you are doing.
   */
  @Override
  public boolean fireEvent(SudokuEvent<?> event) {

    LOG.debug("{}", event);
    if (event instanceof SudokuChangeEvent<?> changeEvent) {
      this.modificationCounter++;
      if (this.currentChanges != null) {
        this.currentChanges.add(changeEvent);
      }
    }
    return super.fireEvent(event);
  }

  /**
   * @param candidateFunction the custom {@link Field#hasCandidate(int) candidate function}.
   * @return a {@link String} with the "matrix" of candidates.
   * @see Field#getCandidateMatrix()
   */
  public String getCandidateMatrix(IntPredicate candidateFunction) {

    int size = getSize();
    int boxSize = getBoxSize();
    int rowCount = 0;
    StringBuilder sb = new StringBuilder(size * 2);
    for (int i = 1; i <= size; i++) {
      if (rowCount == boxSize) {
        sb.append('\n');
        rowCount = 0;
      } else {
        if (i > 1) {
          sb.append(' ');
        }
      }
      rowCount++;
      if (candidateFunction.test(i)) {
        sb.append(getSymbol(i));
      } else {
        sb.append(' ');
      }
    }
    return sb.toString();
  }

  /**
   * @return the {@link SudokuBuilder} to build a {@link Sudoku} with fluent API.
   */
  public static SudokuBuilder builder() {

    return SudokuBuilder.get();
  }

}
