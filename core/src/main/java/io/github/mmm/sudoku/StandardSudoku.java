/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku;

import io.github.mmm.sudoku.dimension.RegularDimension;

/**
 * A standard {@link Sudoku} using Box as regional {@link #getPartitionings() partition}.
 */
public class StandardSudoku extends Sudoku {

  /**
   * The constructor.
   */
  public StandardSudoku() {

    super();
  }

  /**
   * The constructor.
   *
   * @param base the {@link #getBase() base}.
   */
  public StandardSudoku(RegularDimension base) {

    super(base);
  }

  @Override
  public String getType() {

    return "Standard";
  }

}
