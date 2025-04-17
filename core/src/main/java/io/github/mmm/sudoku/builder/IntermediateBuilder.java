package io.github.mmm.sudoku.builder;

import io.github.mmm.sudoku.Sudoku;

/**
 * Abstract base class for an intermediate {@link Sudoku} {@link Builder}.
 *
 * @param <N> type of the {@link #next()} {@link Builder}.
 */
public abstract class IntermediateBuilder<N extends Builder> implements Builder {

  /**
   * @return the next {@link Builder}.
   */
  protected abstract N next();

  @Override
  public Sudoku build() {

    return next().build();
  }

}
