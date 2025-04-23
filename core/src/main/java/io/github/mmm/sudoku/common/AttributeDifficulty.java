/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.common;

/**
 * Interface for {@link #getDifficulty()}.
 */
public interface AttributeDifficulty {

  /**
   * @return the difficulty score in the range from {@code 1} (trivial) to {@code 100} (brute force).
   */
  int getDifficulty();

}
