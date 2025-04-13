/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.IntPredicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.mmm.event.AbstractEventSender;
import io.github.mmm.sudoku.child.Box;
import io.github.mmm.sudoku.child.Column;
import io.github.mmm.sudoku.child.Field;
import io.github.mmm.sudoku.child.Partitioning;
import io.github.mmm.sudoku.child.Row;
import io.github.mmm.sudoku.dimension.AbstractDimension;
import io.github.mmm.sudoku.dimension.Dimension;
import io.github.mmm.sudoku.dimension.RegularDimension;
import io.github.mmm.sudoku.event.ChangeAware;
import io.github.mmm.sudoku.event.SudokuChangeEvent;
import io.github.mmm.sudoku.event.SudokuEvent;
import io.github.mmm.sudoku.event.SudokuEventListener;
import io.github.mmm.sudoku.history.ChangeSet;

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
 *       // e.g. value=9 and valueSymbol="9" or value=16 and valueSymbol="F"
 *       System.out.println("Value at " + x + "x" + y + " is " + valueSymbol);
 *     }
 *   }
 * }
 * </pre>
 */
public abstract class Sudoku extends AbstractEventSender<SudokuEvent<?>, SudokuEventListener>
    implements Dimension, ChangeAware {

  private static final Logger LOG = LoggerFactory.getLogger(Sudoku.class);

  /** @see #getSize() */
  protected final AbstractDimension dimension;

  private final Field[][] fields;

  private final List<Partitioning> partitionings;

  private ChangeSet lastChange;

  private List<SudokuChangeEvent<?>> currentChanges;

  /**
   * The constructor.
   */
  public Sudoku() {

    this(RegularDimension.NORMAL);
  }

  /**
   * The constructor.
   *
   * @param dimension the {@link AbstractDimension}.
   */
  public Sudoku(AbstractDimension dimension) {

    super();
    this.dimension = dimension;
    this.partitionings = createPartitions();
    this.fields = createFields();
    this.lastChange = new ChangeSet(Collections.emptyList(), null);
  }

  private List<Partitioning> createPartitions() {

    List<Partitioning> mutablePartitionings = new ArrayList<>();
    registerPartitions(mutablePartitionings);
    return Collections.unmodifiableList(mutablePartitionings);
  }

  /**
   * @param p the {@link List} where to register the {@link Partitioning}s.
   */
  protected void registerPartitions(List<Partitioning> p) {

    p.add(new Column(this));
    p.add(new Row(this));
    p.add(new Box(this));
  }

  @Override
  public int getBase() {

    return this.dimension.getBase();
  }

  @Override
  public int getSize() {

    return this.dimension.getSize();
  }

  @Override
  public boolean isRegular() {

    return this.dimension.isRegular();
  }

  /**
   * @return the type of this {@link Sudoku} (e.g. "Standard" for {@link StandardSudoku} or "Hyper").
   */
  public abstract String getType();

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
   * @param x the {@link Field#getX() x-coordinate} in the range from {@code 1} to <code>{@link #getSize()}</code>.
   * @param y the {@link Field#getY() y-coordinate} in the range from {@code 1} to <code>{@link #getSize()}</code>.
   * @return the {@link Field} at the given position.
   */
  public Field getField(int x, int y) {

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

    int size = getSize();
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
        int partitionIndex = field.getPartitionIndex(partitioning);
        if (partitionIndex >= 0) {
          LOG.debug("Updating partition {} of {}", partitionIndex, partitioning.getName());
          for (int fieldIndex = 1; fieldIndex <= size; fieldIndex++) {
            Field neighbour = partitioning.getPartitionField(partitionIndex, fieldIndex);
            if (neighbour != field) {
              if (neighbour.hasCandidate(value)) {
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
    }
    if (withHistory) {
      endUndoHistory(changeSet);
    } else {
      assert (changeSet == null);
      assert (this.currentChanges == null);
      if (given) {
        assert (this.lastChange == null) : "Given clues should be set before actual values!";
      }
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

  private ChangeSet startUndoHistory() {

    // collect change events in ChangeSet for undo feature
    this.currentChanges = new ArrayList<>();
    return new ChangeSet(this.currentChanges, this.lastChange);
  }

  private void endUndoHistory(ChangeSet changeSet) {

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
   * @return the {@link List} of {@link Partitioning}s used by this Sudoku. Regular Sudokus always use the
   *         {@link Partitioning}s {@link Column}, {@link Row}, and {@link Box}.
   */
  public final List<Partitioning> getPartitionings() {

    return this.partitionings;
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
   *
   * @param event the {@link SudokuEvent} to send.
   * @return {@code true} if dispatched, {@code false} otherwise (no listeners).
   */
  @Override
  public boolean fireEvent(SudokuEvent<?> event) {

    LOG.debug("{}", event);
    if ((this.currentChanges != null) && (event instanceof SudokuChangeEvent<?> changeEvent)) {
      this.currentChanges.add(changeEvent);
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
    int base = getBase();
    int rowCount = 0;
    StringBuilder sb = new StringBuilder(size * 2);
    for (int i = 1; i <= size; i++) {
      if (rowCount == base) {
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

}
