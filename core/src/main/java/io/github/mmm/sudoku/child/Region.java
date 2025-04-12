/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.child;

import io.github.mmm.sudoku.Sudoku;
import io.github.mmm.sudoku.style.BorderType;

/**
 * A {@link Region} is a partition of {@link Field}s from a {@link Sudoku} puzzle board. In order to represent
 * non-classic {@link Sudoku}s like jigsaw that may not even contain connected {@link Field}s the types
 * {@link Partitioning} and {@link Region} are used as abstraction.
 */
public abstract class Region extends Partitioning {

  /**
   * The constructor.
   *
   * @param sudoku the {@link #getSudoku() sudoku}.
   */
  public Region(Sudoku sudoku) {

    super(sudoku);
  }

  @Override
  public int getId() {

    return 2;
  }

  @Override
  public String getName() {

    return "Region";
  }

  @Override
  public BorderType getBorderType() {

    return BorderType.THICK;
  }
}