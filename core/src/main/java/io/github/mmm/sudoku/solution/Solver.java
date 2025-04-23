/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.solution;

import io.github.mmm.sudoku.Sudoku;
import io.github.mmm.sudoku.solution.strategy.SolutionStrategy;

/**
 * Abstract interface for {@link SudokuSolver} and {@link SolutionStrategy}.
 */
public interface Solver {

  /**
   * @param sudoku the {@link Sudoku} to solve.
   * @return the next {@link Hint} found by this {@link Solver} or {@code null} if nothing found.
   */
  Hint findHint(Sudoku sudoku);

}
