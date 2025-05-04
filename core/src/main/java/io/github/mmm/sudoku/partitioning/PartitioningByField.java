/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.partitioning;

import io.github.mmm.sudoku.Sudoku;

/**
 * {@link Partitioning} that is not fixed (build-in) in {@link Sudoku} but can be configured.
 */
public abstract class PartitioningByField extends Partitioning {

  /**
   * The constructor.
   *
   * @param sudoku the {@link #getSudoku() sudoku}.
   * @param index the {@link #getIndex() index}.
   * @param function the {@link PartitionFunctionByField}.
   */
  public PartitioningByField(Sudoku sudoku, int index, PartitionFunctionByField function) {

    super(sudoku, index, function);
  }

}