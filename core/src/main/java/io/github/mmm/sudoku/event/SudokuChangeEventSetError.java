/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.event;

import io.github.mmm.sudoku.child.Field;

/**
 * {@link SudokuChangeEvent} for {@link Field#setError(boolean) setError}(true).
 */
public final class SudokuChangeEventSetError extends SudokuChangeEvent<SudokuChangeEventSetError> {

  /**
   * The constructor.
   *
   * @param field the {@link #getField() field}.
   */
  public SudokuChangeEventSetError(Field field) {

    super(field);
  }

  @Override
  public void undo() {

    this.field.setError(false);
  }

  @Override
  public void redo() {

    this.field.setError(true);
  }

}
