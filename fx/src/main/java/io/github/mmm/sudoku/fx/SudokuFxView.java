/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.fx;

import io.github.mmm.sudoku.Sudoku;
import io.github.mmm.sudoku.child.SudokuContainer;

/**
 * Interface for a view of a sudoku component in JavaFx.
 */
public interface SudokuFxView extends SudokuContainer {

  /**
   * Uppdates this view to its state is refreshed and shows to recent data from the underlying {@link #getSudoku()
   * sudoku}. Please note that only values, candidates, etc. are updated but the structure and partitions of the
   * {@link Sudoku} itself cannot change. To change the {@link Sudoku#getType() sudoku type} you need to create a new
   * {@link Sudoku} and new {@link SudokuFxView}s for it.
   */
  public void update();

}