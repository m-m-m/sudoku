/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.child;

import io.github.mmm.sudoku.Sudoku;

/**
 * Interface for a container {@link #getSudoku() holding} a {@link Sudoku}.
 */
public interface SudokuContainer {

  /**
   * @return the {@link Sudoku}.
   */
  Sudoku getSudoku();

}
