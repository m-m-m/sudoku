/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.partitioning;

import io.github.mmm.sudoku.Sudoku;
import io.github.mmm.sudoku.dimension.DimensionType;
import io.github.mmm.sudoku.partition.Shape;
import io.github.mmm.sudoku.style.ColorType;

/** {@link Jigsaw} {@link Region}. */
public class Jigsaw extends Region {

  /**
   * The constructor.
   *
   * @param sudoku the {@link #getSudoku() sudoku}.
   * @param index the {@link #getIndex() partitioning index}. Should be {@code 3}.
   * @param shapes the {@link Shape}s.
   */
  public Jigsaw(Sudoku sudoku, int index, Shape... shapes) {

    super(sudoku, index, (p) -> createPartitions(p, shapes));
    assert (shapes.length == sudoku.getSize());
  }

  @Override
  public DimensionType getDimensionType() {

    return DimensionType.PRIME;
  }

  @Override
  public ColorType getColorType() {

    return ColorType.MULTIPLE;
  }

}