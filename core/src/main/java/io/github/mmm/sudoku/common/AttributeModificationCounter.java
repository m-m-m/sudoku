/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.common;

import io.github.mmm.sudoku.Sudoku;

/**
 * Interface for {@link #getModificationCounter()}.
 */
public interface AttributeModificationCounter {

  /**
   * @return the current modification counter that reflects a specific state of a {@link Sudoku}. If the {@link Sudoku}
   *         changes, it updates this counter.
   */
  int getModificationCounter();

}
