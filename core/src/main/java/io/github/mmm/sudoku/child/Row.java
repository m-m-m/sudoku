/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.child;

import io.github.mmm.sudoku.Sudoku;

/**
 * {@link Row} as {@link Partitioning}.
 */
public class Row extends Partitioning {

  /**
   * The constructor.
   *
   * @param sudoku the {@link #getSudoku() sudoku}.
   */
  public Row(Sudoku sudoku) {

    super(sudoku);
  }

  @Override
  public int getId() {

    return 1;
  }

  @Override
  public String getName() {

    return "Row";
  }

  @Override
  public Field getPartitionField(int partitionIndex, int fieldIndex) {

    return this.sudoku.getField(fieldIndex, partitionIndex);
  }
}