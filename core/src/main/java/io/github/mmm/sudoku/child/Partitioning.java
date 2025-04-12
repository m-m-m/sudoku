/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.child;

import io.github.mmm.sudoku.Sudoku;
import io.github.mmm.sudoku.style.BorderType;
import io.github.mmm.sudoku.style.ColorType;

/**
 * Each {@link Partitioning} splits the {@link Sudoku} board into a specific {@link #getPartitionCount() number of
 * partitions}. Each partition contains one {@link Field} for each available {@link Sudoku#getSymbol(int) symbol}.
 * Typically the {@link #getPartitionCount() number of partitions} is also the {@link Sudoku#getSize() sudoku size} but
 * there are excuses where this is not the case (e.g. X, Hyper or Percent).<br>
 * Every {@link Sudoku} has {@link Column}s as the first partitioning and {@link Row}s as the second partitioning. For
 * ease of use, the method {@link Sudoku#getField(int, int)} allows direct access to a {@link Field} using its x- and
 * y-coordinates. Via {@link Partitioning} the same is also available via {@link Column} using the following code:
 *
 * <pre>
 * {@link Sudoku sudoku}.{@link Sudoku#getPartitionings() getPartitionings}().get(0).{@link #getPartitionField(int, int) getPartitionField}(y, x);
 * </pre>
 *
 * Or via {@link Row}:
 *
 * <pre>
 * {@link Sudoku sudoku}.{@link Sudoku#getPartitionings() getPartitionings}().get(1).{@link #getPartitionField(int, int) getPartitionField}(x, y);
 * </pre>
 *
 * Every {@link Sudoku} has one or more additional {@link Partitioning}s. A normal {@link Sudoku} has {@link Box} as
 * additional {@link Partitioning}. Special {@link Sudoku#getType() types} like {@link io.github.mmm.sudoku.HyperSudoku}
 * add a fourth {@link Partitioning} typically visualised by {@link Partitioning#getColorType() color}. However, there
 * are also {@link Sudoku#getType() types} like {@link io.github.mmm.sudoku.JigsawSudoku} that use irregular shapes
 * instead of {@link Box boxes} and this also allows to have no {@link Sudoku#getBase() base} such as e.g. a 5x5
 * {@link Sudoku} board that can not be split into boxes since 5 is not a square number.<br>
 * As you can see {@link Partitioning} is a very generic but powerful concept that allows to implement many
 * {@link Sudoku#getType() types} of {@link Sudoku}s easily without the need to write much extra code.<br>
 * Also a Sumdoku is possible where no {@link Field#isGiven() clues are given} but only the sum of {@link Field}s for
 * specific {@link #getPartitionCount() partitions}.
 *
 * @see Sudoku#getPartitionings()
 */
public abstract class Partitioning extends SudokuChildObject {

  /**
   * The constructor.
   *
   * @param sudoku the {@link #getSudoku() sudoku}.
   */
  public Partitioning(Sudoku sudoku) {

    super(sudoku);
  }

  /**
   * @return the ID (unique index) of this {@link Partitioning}.
   */
  public abstract int getId();

  /**
   * @return the name of this {@link Partitioning}.
   */
  public abstract String getName();

  /**
   * @return the number of actual partitions available for this {@link Partitioning}. Typically the
   *         {@link Sudoku#getSize() size} of the {@link Sudoku} but may differ for special {@link Partitioning}s.
   */
  public int getPartitionCount() {

    return this.sudoku.getSize();
  }

  /**
   * @param partitionIndex the index of partition in the range from {@code 0} to
   *        <code>{@link #getPartitionCount()}-1</code>.
   * @param fieldIndex the index of the {@link Field} within the sub-partition in the range from {@code 0} to
   *        <code>{@link Sudoku#getSize()}-1</code>.
   * @return the requested {@link Field}.
   */
  public abstract Field getPartitionField(int partitionIndex, int fieldIndex);

  /**
   * @return the {@link ColorType} of the {@link #getPartitionCount() partitions}.
   */
  public ColorType getColorType() {

    return ColorType.NONE;
  }

  /**
   * @return the {@link BorderType} of the {@link #getPartitionCount() partitions}.
   */
  public BorderType getBorderType() {

    return BorderType.NONE;
  }

  @Override
  public String toString() {

    return '#' + getId() + ":" + getName() + "[" + getPartitionCount() + "]";
  }
}