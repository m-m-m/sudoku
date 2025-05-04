/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.partitioning;

import io.github.mmm.sudoku.Sudoku;
import io.github.mmm.sudoku.dimension.DimensionType;
import io.github.mmm.sudoku.partition.Partition;
import io.github.mmm.sudoku.partition.SumShape;
import io.github.mmm.sudoku.style.BorderType;
import io.github.mmm.sudoku.style.ColorType;

/** {@link Sum} {@link Partitioning}. */
public class Sum extends Layer {

  /**
   * The constructor.
   *
   * @param sudoku the {@link #getSudoku() sudoku}.
   * @param index the {@link #getIndex() index}.
   * @param sums the {@link SumShape}s to build the {@link Partition}s from.
   */
  public Sum(Sudoku sudoku, int index, SumShape... sums) {

    super(sudoku, index, p -> createPartitions(p, sums));
  }

  @Override
  public DimensionType getDimensionType() {

    return DimensionType.PRIME;
  }

  @Override
  public boolean hasAlwaysCompletePartitions() {

    return false;
  }

  @Override
  public ColorType getColorType() {

    return ColorType.NONE;
  }

  @Override
  public BorderType getBorderType() {

    // TODO
    return BorderType.THICK;
  }

}