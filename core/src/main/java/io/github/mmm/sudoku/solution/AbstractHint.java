/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.solution;

/**
 * Abstract interface of {@link Hint} or {@link HintStep}.
 */
public interface AbstractHint {

  /**
   * Applies this {@link Hint} or {@link HintStep}.
   */
  void apply();

}
