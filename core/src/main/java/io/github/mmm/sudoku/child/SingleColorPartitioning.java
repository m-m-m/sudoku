/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.child;

import io.github.mmm.sudoku.Sudoku;
import io.github.mmm.sudoku.style.ColorType;

/**
 * A {@link SingleColorPartitioning} is a {@link Partitioning} of {@link Field}s from a {@link Sudoku} puzzle board. In
 * a classic {@link Sudoku} the columns and rows are partitioned into 3x3 square boxes: So the first
 * {@link SingleColorPartitioning} is from 1,1 to 3x3 the second is 4,1 to 6,3 and the last is 7,7 to 9,9. In order to
 * represent non-classic {@link Sudoku}s like jigsaw that may not even contain connected {@link Field}s the types
 * {@link Partitioning} and {@link SingleColorPartitioning} are used as abstraction.
 */
public abstract class SingleColorPartitioning extends Partitioning {

  /**
   * The constructor.
   *
   * @param sudoku the {@link #getSudoku() sudoku}.
   */
  public SingleColorPartitioning(Sudoku sudoku) {

    super(sudoku);
  }

  @Override
  public int getId() {

    return 3;
  }

  @Override
  public ColorType getColorType() {

    return ColorType.SINGLE;
  }
}