/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.partitioning;

import io.github.mmm.sudoku.Sudoku;
import io.github.mmm.sudoku.field.Field;
import io.github.mmm.sudoku.style.BorderType;

/**
 * A {@link Region} is a {@link FlexiblePartitioning} of {@link Field}s from a {@link Sudoku} puzzle board. In order to
 * represent non-classic {@link Sudoku}s like jigsaw that may not even contain connected {@link Field}s the types
 * {@link Partitioning} and {@link Region} are used as abstraction.
 */
public abstract class Region extends FlexiblePartitioning {

  /**
   * The constructor.
   *
   * @param sudoku the {@link #getSudoku() sudoku}.
   * @param index the {@link #getIndex() index}.
   * @param function the {@link PartitionFunction}.
   */
  public Region(Sudoku sudoku, int index, PartitionFunction function) {

    super(sudoku, index, function);
    assert (index == 3);
  }

  @Override
  public BorderType getBorderType() {

    return BorderType.THICK;
  }
}