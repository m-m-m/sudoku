/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.field;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.mmm.sudoku.Sudoku;
import io.github.mmm.sudoku.child.SudokuChildObject;
import io.github.mmm.sudoku.common.Candidates;
import io.github.mmm.sudoku.event.SudokuChangeEventCandidates;
import io.github.mmm.sudoku.event.SudokuChangeEventSetError;
import io.github.mmm.sudoku.event.SudokuChangeEventSetMarked;
import io.github.mmm.sudoku.event.SudokuChangeEventSetSolution;
import io.github.mmm.sudoku.event.SudokuChangeEventSetValue;
import io.github.mmm.sudoku.event.SudokuEvent;
import io.github.mmm.sudoku.partition.Partition;
import io.github.mmm.sudoku.partitioning.Hyper;
import io.github.mmm.sudoku.partitioning.Partitioning;
import io.github.mmm.sudoku.partitioning.Percent;
import io.github.mmm.sudoku.partitioning.X;
import io.github.mmm.sudoku.style.BorderStyle;
import io.github.mmm.sudoku.style.BorderType;
import io.github.mmm.sudoku.style.ColorType;

/**
 * Represents a single {@link Field} of the {@link Sudoku} puzzle. It can either have a {@link #getValue() value} filled
 * (if not {@code -1}) or represent a list of {@link #hasCandidate(int) candidates}.
 */
public final class Field extends SudokuChildObject {

  private static final Logger LOG = LoggerFactory.getLogger(Field.class);

  /** {@link #getValue() Value} if undefined. */
  public static final int UNDEFINED = -1;

  private final int x;

  private final int y;

  private Partition[] partitions;

  private boolean given;

  private List<String> styles;

  private int value;

  private int solution;

  private Candidates candidates;

  private boolean error;

  private boolean marked;

  /**
   * The constructor.
   *
   * @param sudoku the {@link #getSudoku() sudoku}.
   * @param x the {@link #getX() x-coordinate}.
   * @param y the {@link #getY() y-coordinate}.
   */
  public Field(Sudoku sudoku, int x, int y) {

    super(sudoku);
    this.x = x;
    this.y = y;
    this.value = UNDEFINED;
    this.solution = UNDEFINED;
    this.candidates = sudoku.getAllCandidates();
  }

  private boolean fireEvent(SudokuEvent<?> event) {

    return this.sudoku.fireEvent(event);
  }

  /**
   * @param partitioning the {@link Partitioning}.
   * @return the the {@link Partition} of the given {@link Partitioning} containing this {@link Field} or {@code null}
   *         if no such {@link Partition} exists (e.g. special {@link Partitioning}s like Hyper, X, Percent, etc. do not
   *         cover all fields).
   */
  public Partition getPartition(Partitioning partitioning) {

    return getPartition(partitioning.getIndex());
  }

  /**
   * @param partitioningIndex the {@link Partitioning}-{@link Partitioning#getIndex() index}.
   * @return the the {@link Partition} of the given {@link Partitioning}-{@link Partitioning#getIndex() index}
   *         containing this {@link Field} or {@code null} if no such {@link Partition} exists (e.g. special
   *         {@link Partitioning}s like {@link Hyper}, {@link X}, {@link Percent}, etc. do not cover all fields).
   */
  public Partition getPartition(int partitioningIndex) {

    if ((partitioningIndex < 1) || (partitioningIndex > this.sudoku.getPartitioningCount())) {
      throw new IndexOutOfBoundsException(partitioningIndex);
    }
    if (this.partitions == null) {
      initPartitions();
    }
    return this.partitions[partitioningIndex - 1];
  }

  private void initPartitions() {

    int partitioningCount = this.sudoku.getPartitioningCount();
    for (Partitioning partitioning : this.sudoku) {
      LOG.debug("Initializing partitions for {}", partitioning.getName());
      int partitioningIndex = partitioning.getIndex();
      for (Partition partition : partitioning) {
        LOG.debug("Initializing partition {}", partition);
        for (Field field : partition) {
          LOG.debug("Initializing {}", field);
          if (field.partitions == null) {
            field.partitions = new Partition[partitioningCount];
          }
          field.partitions[partitioningIndex - 1] = partition;
        }
      }
    }
  }

  /**
   * @return the x-coordinate or column. The value is one based so {@code 1} is the first column.
   */
  public int getX() {

    return this.x;
  }

  /**
   * @return the y-coordinate or row. The value is one based so {@code 1} is the first row.
   */
  public int getY() {

    return this.y;
  }

  /**
   * @return styles
   */
  public List<String> getStyles() {

    if (this.styles == null) {
      initStyles();
    }
    return this.styles;
  }

  private void initStyles() {

    int size = this.sudoku.getSize();
    Partitioning borders = null;
    BorderType borderType = null;
    for (Partitioning partitioning : this.sudoku) {
      BorderType type = partitioning.getBorderType();
      if (type != BorderType.NONE) {
        borderType = type;
        borders = partitioning;
        break;
      }
    }
    for (int x0 = 1; x0 <= size; x0++) {
      for (int y0 = 1; y0 <= size; y0++) {
        Field field = this.sudoku.getField(x0, y0);
        field.initColorStyle();
        if (borderType != null) {
          field.initBorderStyle(borders);
        }
        if (field.styles == null) {
          field.styles = Collections.emptyList();
        } else {
          field.styles = Collections.unmodifiableList(field.styles);
        }
      }
    }
  }

  private void initColorStyle() {

    int color = 0;
    for (Partitioning partitioning : this.sudoku) {
      ColorType colorType = partitioning.getColorType();
      if (colorType != ColorType.NONE) {
        Partition partition = getPartition(partitioning);
        if (partition != null) {
          int partitionIndex = partition.getIndex();
          if (partitionIndex < 0) {
            color = color + colorType.getOffset(partitioning);
          } else {
            color = colorType.getColor(color, partitionIndex, partitioning);
            if (color > 0) {
              addStyle("color" + color);
            }
            return;
          }
        }
      }
    }
  }

  private void addStyle(String style) {

    if (this.styles == null) {
      this.styles = new ArrayList<>();
    }
    this.styles.add(style);
  }

  private void initBorderStyle(Partitioning borders) {

    BorderStyle borderStyle = BorderStyle.EMPTY;
    if (isLeftBorder(borders)) {
      borderStyle = borderStyle.withLeft();
    }
    if (isTopBorder(borders)) {
      borderStyle = borderStyle.withTop();
    }
    if (isRightBorder(borders)) {
      borderStyle = borderStyle.withRight();
    }
    if (isBottomBorder(borders)) {
      borderStyle = borderStyle.withBottom();
    }
    if (borderStyle != BorderStyle.EMPTY) {
      String style = borders.getBorderType().toString().toLowerCase(Locale.ROOT) + "-" + borderStyle;
      addStyle(style);
    }
  }

  private boolean isTopBorder(Partitioning borders) {

    if (this.y == 1) {
      return true;
    }
    return false;
  }

  private boolean isRightBorder(Partitioning borders) {

    if (this.x == this.sudoku.getSize()) {
      return true;
    }
    Field right = this.sudoku.getField(this.x + 1, this.y);
    if (getPartition(borders) != right.getPartition(borders)) {
      return true;
    }
    return false;
  }

  private boolean isBottomBorder(Partitioning borders) {

    if (this.y == this.sudoku.getSize()) {
      return true;
    }
    Field up = this.sudoku.getField(this.x, this.y + 1);
    if (getPartition(borders) != up.getPartition(borders)) {
      return true;
    }
    return false;
  }

  private boolean isLeftBorder(Partitioning borders) {

    if (this.x == 1) {
      return true;
    }
    return false;
  }

  /**
   * @return the {@link Candidates} of this {@link Field}.
   */
  public Candidates getCandidates() {

    return this.candidates;
  }

  /**
   * @param candidates the new value of {@link #getCandidates()}.
   * @return {@code true} if something actually changed, {@code false} otherwise.
   */
  public boolean setCandidates(Candidates candidates) {

    if (this.candidates.getEncodedBitValue() == candidates.getEncodedBitValue()) {
      LOG.trace("No change for setCandidates in {}", this);
      return false;
    }
    SudokuChangeEventCandidates event = new SudokuChangeEventCandidates(this, this.candidates, candidates);
    this.candidates = candidates;
    fireEvent(event);
    return true;
  }

  /**
   * @param candidate the {@link #getValue() value} {@link Candidates#has(int) candidate}.
   * @return {@code true} if the candidate is possible, {@code false} otherwise (already excluded).
   */
  public boolean hasCandidate(int candidate) {

    if (this.value > 0) {
      return (this.value == candidate);
    }
    if (candidate <= 0) {
      return false;
    }
    return this.candidates.has(candidate);
  }

  /**
   * @param other the {@link Candidates} to check.
   * @return {@code true} if this {@link Field} {@link Field#hasCandidate(int) has} at least one of the given
   *         {@link Candidates}.
   */
  public boolean hasAtLeastOneCandidateOf(Candidates other) {

    // candidates = {3,4}, other = {1,2,3} -> exclusion = {4} != candidates
    // candidates = {4,5}, other = {1,2,3} -> exclusion = {4,5} == candidates
    return (this.candidates != this.candidates.exclude(other));
  }

  /**
   * @param other the {@link Candidates} to check.
   * @return {@code true} if this {@link Field} {@link Field#hasCandidate(int) has} at least one other candidate not
   *         from the given {@link Candidates}.
   */
  public boolean hasOtherCandidatesThan(Candidates other) {

    // candidates = {3,4}, other = {1,2,3}, union = {1,2,3,4} != other
    // candidates = {2,3}, other = {1,2,3}, union = {1,2,3} == other

    return (other != this.candidates.union(other));
  }

  /**
   * @param candidate the {@link #hasCandidate(int) candidate} to exclude.
   * @return {@code true} if something actually changed, {@code false} otherwise.
   */
  public boolean excludeCandidate(int candidate) {

    validateValue(candidate);
    return setCandidates(this.candidates.exclude(candidate));
  }

  /**
   * @param candidate the {@link #hasCandidate(int) candidate} to include.
   * @return {@code true} if something actually changed, {@code false} otherwise.
   */
  public boolean includeCandidate(int candidate) {

    validateValue(candidate);
    return setCandidates(this.candidates.include(candidate));
  }

  /**
   * @param candidate the {@link #hasCandidate(int) candidate} to toggle.
   */
  public void toggleCandidate(int candidate) {

    setCandidates(this.candidates.flip(candidate));
  }

  /**
   * @return the number of candidates that have been {@link #excludeCandidate(int) excluded}.
   */
  public int getExcludedCandidateCount() {

    return this.sudoku.getSize() - getIncludedCandidateCount();
  }

  /**
   * @return the number of candidates that are still included and have not been {@link #excludeCandidate(int) excluded}.
   *         If this is {@code 1} then only a {@link #getSingle() single candidate} is left that is supposed to be the
   *         correct {@link #getValue() value}.
   */
  public int getIncludedCandidateCount() {

    return this.candidates.getInclusionCount();
  }

  /**
   * @return the remaining single {@link #getIncludedCandidateCount() included candidate} or {@link #UNDEFINED -1} if
   *         {@link #getIncludedCandidateCount()} is not equal to {@code 1}.
   */
  public int getSingle() {

    if (this.candidates.getInclusionCount() == 1) {
      return this.candidates.getLowestCandidate();
    }
    return UNDEFINED;
  }

  /**
   * @return the value or {@link #UNDEFINED -1} if not (yet) {@link #hasValue() defined}. A defined value is in the
   *         range from {@code 1} to {@link Sudoku#getSize() sudoku size}. It is either a {@link #isGiven() given clue}
   *         or has been {@link #setValue(int) filled} in the process to solve the {@link Sudoku}. Please note that the
   *         value is an internal representation that needs to be mapped to a {@link Sudoku#getSymbol(int) symbol} for
   *         presentation to the end-user. Typically the mapping is trivial and {@code 1} is mapped to "1" but this is
   *         configured and symbols could also be emojis or whatever.
   */
  public int getValue() {

    return this.value;
  }

  /**
   * @return {@code true} if the {@link #getValue() value} is defined, {@code false} otherwise (see {@link #UNDEFINED}).
   */
  public boolean hasValue() {

    return (this.value != UNDEFINED);
  }

  /**
   * Low-level method to set the {@link #getValue() value}.
   *
   * @param value the new {@link #getValue() value}.
   * @return {@code true} if something actually changed, {@code false} otherwise.
   * @see Sudoku#setFieldValue(Field, int)
   */
  public boolean setValue(int value) {

    return setValue(value, false);
  }

  /**
   * Low-level method to set the {@link #getValue() value}.
   *
   * @param value the new {@link #getValue() value}.
   * @param given the {@link #isGiven() given flag}.
   * @return {@code true} if something actually changed, {@code false} otherwise.
   * @see Sudoku#setFieldValue(Field, int, boolean, boolean)
   */
  public boolean setValue(int value, boolean given) {

    if ((this.value == value) && (this.given == given)) {
      LOG.trace("No change for setValue in {}", this);
      return false;
    }
    validateValue(value, !given);
    int oldValue = this.value;
    this.value = value;
    if (given) {
      this.given = true;
      this.solution = value;
    }
    fireEvent(new SudokuChangeEventSetValue(this, oldValue, value));
    return true;
  }

  /**
   * @return the solution (correct {@link #getValue() value}) or {@code -1} if not (yet) defined.
   */
  public int getSolution() {

    return this.solution;
  }

  /**
   * @param solution the new {@link #getSolution() solution}.
   * @return {@code true} if something actually changed, {@code false} otherwise.
   */
  public boolean setSolution(int solution) {

    if (this.solution == solution) {
      LOG.trace("No change for setSolution in {}", this);
      return false;
    }
    validateValue(solution, true);
    int oldSolution = this.solution;
    this.solution = solution;
    fireEvent(new SudokuChangeEventSetSolution(this, oldSolution, solution));
    return true;
  }

  private void validateValue(int v) {

    validateValue(v, false);
  }

  private void validateValue(int v, boolean acceptUndefined) {

    if ((v > this.sudoku.getSize()) || (v <= 0) && (!acceptUndefined || (v != UNDEFINED))) {
      throw new IllegalArgumentException(Integer.toString(v));
    }
  }

  /**
   * @return {@code true} if the {@link #getSolution() solution} is defined, {@code false} otherwise (see
   *         {@link #UNDEFINED}).
   */
  public boolean hasSolution() {

    return (this.solution != UNDEFINED);
  }

  /**
   * @return {@code true} if this {@link Field} has a <em>given</em> {@link #getValue() value}. A given value is also
   *         called <em>clue</em>.
   */
  public boolean isGiven() {

    return this.given;
  }

  /**
   * @return {@code true} if this {@link Field} is valid, {@code false} otherwise (mistake was made).
   */
  public boolean isValid() {

    assert hasSolution();
    if (hasValue()) {
      return (this.value == this.solution);
    } else {
      return this.candidates.has(this.solution);
    }
  }

  /**
   * @return {@code true} if this {@link Field} is currently marked as error (conflicts with other value), {@code false}
   *         otherwise.
   */
  public boolean isError() {

    return this.error;
  }

  /**
   * <b>ATTENTION:</b> Internal method that should not be invoked directly.
   *
   * @param error new value of {@link #isError()}.
   * @return {@code true} if something actually changed, {@code false} otherwise.
   */
  public boolean setError(boolean error) {

    if (this.error == error) {
      LOG.trace("No change for setError in {}", this);
      return false;
    }
    this.error = error;
    fireEvent(new SudokuChangeEventSetError(this));
    return true;
  }

  /**
   * @return {@code true} if this {@link Field} is marked (special visualisation e.g. for hints), {@code false}
   *         otherwise (unmarked).
   */
  public boolean isMarked() {

    return this.marked;
  }

  /**
   * @param marked the new value of {@link #isMarked()}.
   * @return {@code true} if something actually changed, {@code false} otherwise.
   */
  public boolean setMarked(boolean marked) {

    if (this.marked == marked) {
      return false;
    }
    this.marked = marked;
    fireEvent(new SudokuChangeEventSetMarked(this));
    return true;
  }

  /**
   * @param sb the {@link StringBuilder} where to append the coordinates to.
   */
  public void appendCoordinates(StringBuilder sb) {

    sb.append(getX());
    sb.append('x');
    sb.append(getY());
  }

  /**
   * @return a short {@link String} representation of this {@link Field} to append to arbitrary messages.
   */
  public String toShortString() {

    StringBuilder sb = new StringBuilder(11);
    sb.append("field ");
    appendCoordinates(sb);
    return sb.toString();
  }

  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder(96);
    sb.append("Field ");
    appendCoordinates(sb);
    sb.append(" value=");
    appendValue(this.value, sb);
    sb.append(" solution=");
    appendValue(this.solution, sb);
    sb.append(" candidates=");
    sb.append(this.candidates);
    if (this.error) {
      sb.append(" error!");
    }
    return sb.toString();
  }

  private void appendValue(int v, StringBuilder sb) {

    sb.append(v);
    sb.append('[');
    if (v == UNDEFINED) {
      sb.append('?');
    } else {
      sb.append(this.sudoku.getSymbol(v));
    }
    sb.append(']');
  }

  /**
   * @return a {@link String} with the "matrix" of {@link #hasCandidate(int) candidates}.
   */
  public String getCandidateMatrix() {

    return this.sudoku.getCandidateMatrix(this::hasCandidate);
  }

}