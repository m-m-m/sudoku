/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.partitioning;

import io.github.mmm.sudoku.Sudoku;
import io.github.mmm.sudoku.field.Field;
import io.github.mmm.sudoku.partition.Partition;

/**
 * {@link X1} is first {@link Partition} of {@link X}. <br>
 */
public class X1 extends X {

  /** {@link LayerFactory} for {@link X1}. */
  public static final LayerFactory FACTORY = (s, i) -> new X1(s, i);

  /**
   * The constructor.
   *
   * @param sudoku the {@link #getSudoku() sudoku}.
   * @param index the {@link #getIndex() index}.
   */
  public X1(Sudoku sudoku, int index) {

    super(sudoku, index, X1::getField);
  }

  private static Field getField(Sudoku sudoku, int fieldIndex) {

    return sudoku.getField(fieldIndex, fieldIndex);
  }
}