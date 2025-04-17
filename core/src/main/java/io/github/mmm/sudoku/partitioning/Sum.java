/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.partitioning;

import io.github.mmm.sudoku.Sudoku;
import io.github.mmm.sudoku.partition.Partition;
import io.github.mmm.sudoku.style.BorderType;

/** {@link Sum} {@link Partitioning}. */
public class Sum extends FlexiblePartitioning {

  /**
   * The constructor.
   *
   * @param sudoku the {@link #getSudoku() sudoku}.
   * @param index the {@link #getIndex() index}.
   * @param partitions the {@link Partition#getSum() sum}-{@link Partition}s.
   */
  public Sum(Sudoku sudoku, int index, Partition... partitions) {

    super(sudoku, index, partitions);
  }

  @Override
  public boolean isRegular() {

    return false;
  }

  @Override
  public boolean hasAlwaysCompletePartitions() {

    return false;
  }

  @Override
  public BorderType getBorderType() {

    return BorderType.THICK;
  }

}