/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.partition;

import io.github.mmm.sudoku.Sudoku;
import io.github.mmm.sudoku.child.SudokuContainer;
import io.github.mmm.sudoku.common.AttributeComplete;
import io.github.mmm.sudoku.common.AttributeSum;
import io.github.mmm.sudoku.field.AbstractFieldGroup;
import io.github.mmm.sudoku.field.Field;
import io.github.mmm.sudoku.partition.impl.PartitionMapImpl;
import io.github.mmm.sudoku.partitioning.Box;
import io.github.mmm.sudoku.partitioning.Column;
import io.github.mmm.sudoku.partitioning.Hyper;
import io.github.mmm.sudoku.partitioning.Partitioning;
import io.github.mmm.sudoku.partitioning.Row;
import io.github.mmm.sudoku.partitioning.Sum;

/**
 * A {@link Partition} is a group of {@link Field}s from the {@link Sudoku}. Each {@link Field} in a {@link Partition}
 * has a unique {@link Field#getValue() value}. Typically the {@link #getFieldCount() field count} is the same as the
 * {@link Sudoku#getSize() sudoku size}. Some examples for a {@link Partition} are:
 * <ul>
 * <li>{@link Column} #1</li>
 * <li>{@link Row} #2</li>
 * <li>{@link Box} #3</li>
 * <li>{@link Hyper} #4</li>
 * <li>{@link Sum} #5</li>
 * </ul>
 */
public class Partition extends AbstractFieldGroup implements SudokuContainer, AttributeComplete, AttributeSum {

  private final Partitioning partitioning;

  private final int index;

  private final boolean complete;

  private final Shape shape;

  private PartitionMap partitionMap;

  /**
   * The constructor.
   *
   * @param partitioning the {@link #getPartitioning() partitioning}.
   * @param index the {@link #getIndex() index}.
   * @param shape the {@link #getShape() shape}.
   * @param fields the {@link #getField(int) fields}.
   */
  public Partition(Partitioning partitioning, int index, Shape shape, Field... fields) {

    super(fields);
    this.partitioning = partitioning;
    this.index = index;
    this.shape = shape;
    this.complete = (fields.length == partitioning.getSudoku().getSize());
    int sum = -1;
    if (shape != null) {
      sum = shape.getSum();
    }
    if (sum == Field.UNDEFINED) {
      assert this.complete;
    } else {
      assert (fields.length > 0);
    }
  }

  /**
   * @return the owning {@link Partitioning}.
   */
  public Partitioning getPartitioning() {

    return this.partitioning;
  }

  @Override
  public Sudoku getSudoku() {

    return this.partitioning.getSudoku();
  }

  /**
   * @return index the index of this {@link Partition} within {@link Partitioning}. The index is {@code 1}-based so the
   *         first {@link Partition} has index {@code 1} (and NOT {@code 0}).
   */
  public int getIndex() {

    return this.index;
  }

  @Override
  public int getSum() {

    if (this.shape == null) {
      return -1;
    }
    return this.shape.getSum();
  }

  @Override
  public boolean isComplete() {

    return this.complete;
  }

  /**
   * @return the computed {@link PartitionMap}. Is only valid while the {@link Sudoku} remains unchanged. Otherwise this
   *         method will compute a new map.
   */
  public PartitionMap getPartitionMap() {

    if ((this.partitionMap == null)
        || (this.partitionMap.getModificationCounter() != getSudoku().getModificationCounter())) {
      this.partitionMap = new PartitionMapImpl(this);
    }
    return this.partitionMap;
  }

  /**
   * @return the {@link Shape} of this {@link Partition}.
   */
  public Shape getShape() {

    return this.shape;
  }

  @Override
  public String toString() {

    return this.partitioning.getName() + "[" + this.index + "]";
  }

}
