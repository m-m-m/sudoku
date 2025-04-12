/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.child;

import io.github.mmm.sudoku.Sudoku;

/**
 * {@link Column} as {@link Partitioning}.
 */
public class Column extends Partitioning {

  /**
   * The constructor.
   *
   * @param sudoku the {@link #getSudoku() sudoku}.
   */
  public Column(Sudoku sudoku) {

    super(sudoku);
  }

  @Override
  public int getId() {

    return 0;
  }

  @Override
  public String getName() {

    return "Column";
  }

  @Override
  public Field getPartitionField(int partitionIndex, int fieldIndex) {

    return this.sudoku.getField(partitionIndex, fieldIndex);
  }
}