/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.partitioning;

import io.github.mmm.sudoku.Sudoku;
import io.github.mmm.sudoku.style.ColorType;

/**
 * A {@link Layer} is a {@link Partitioning} that can be optionally added on top of the {@link Region}.
 */
public abstract class Layer extends FlexiblePartitioning {

  /**
   * The constructor.
   *
   * @param sudoku the {@link #getSudoku() sudoku}.
   * @param index the {@link #getIndex() index}.
   * @param function the {@link PartitionFunction}.
   */
  public Layer(Sudoku sudoku, int index, PartitionFunction function) {

    super(sudoku, index, function);
    assert (index >= 4);
  }

  @Override
  public ColorType getColorType() {

    return ColorType.SINGLE;
  }
}