package io.github.mmm.sudoku.builder;

import io.github.mmm.sudoku.Sudoku;

/**
 * Interface to {@link #build()} a {@link Sudoku}.
 */
public interface Builder {

  /**
   * @return the new {@link Sudoku} instance.
   */
  Sudoku build();

}
