/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.event;

import io.github.mmm.sudoku.child.Field;

/**
 * {@link SudokuFieldEvent} for the selection of a {@link Field}.
 */
public final class SudokuEventSelectField extends SudokuFieldEvent<SudokuEventSelectField> {

  /**
   * The constructor.
   *
   * @param field the {@link #getField() field}.
   */
  public SudokuEventSelectField(Field field) {

    super(field);
  }

}
