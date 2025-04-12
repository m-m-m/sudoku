/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.child;

import io.github.mmm.sudoku.Sudoku;

/**
 * Abstract base class for children of a {@link Sudoku} like {@link Field} or {@link Partitioning}.
 */
public abstract class SudokuChildObject {

  /** @see #getSudoku() */
  protected final Sudoku sudoku;

  /**
   * The constructor.
   *
   * @param sudoku the {@link #getSudoku() sudoku}
   */
  public SudokuChildObject(Sudoku sudoku) {

    super();
    this.sudoku = sudoku;
  }

  /**
   * @return the {@link Sudoku} owning this child object.
   */
  public Sudoku getSudoku() {

    return this.sudoku;
  }

}
