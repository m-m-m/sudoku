/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.partitioning;

import io.github.mmm.sudoku.Sudoku;

/**
 * {@link Row} as {@link Partitioning}.
 */
public class Row extends Partitioning {

  /**
   * The constructor.
   *
   * @param sudoku the {@link #getSudoku() sudoku}.
   */
  public Row(Sudoku sudoku) {

    super(sudoku, 2, (s, pi, fi) -> s.getField(fi, pi));
  }

  @Override
  public boolean isRegular() {

    return false;
  }
}