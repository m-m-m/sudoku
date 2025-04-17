/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.partitioning;

import io.github.mmm.sudoku.Sudoku;
import io.github.mmm.sudoku.field.Field;
import io.github.mmm.sudoku.partition.Partition;

/**
 * {@link X2} is second {@link Partition} of {@link X}. <br>
 */
public class X2 extends X {

  /** {@link LayerFactory} for {@link X2}. */
  public static final LayerFactory FACTORY = (s, i) -> new X2(s, i);

  /**
   * The constructor.
   *
   * @param sudoku the {@link #getSudoku() sudoku}.
   * @param index the {@link #getIndex() index}.
   */
  public X2(Sudoku sudoku, int index) {

    super(sudoku, index, X2::getField);
  }

  private static Field getField(Sudoku sudoku, int fieldIndex) {

    return sudoku.getField(sudoku.getSize() + 1 - fieldIndex, fieldIndex);
  }
}