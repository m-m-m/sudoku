/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.event;

import io.github.mmm.sudoku.child.Field;

/**
 * {@link SudokuChangeEvent} for {@link Field#setMarked(boolean) setMarked}(true).
 */
public final class SudokuChangeEventSetMarked extends SudokuChangeEvent<SudokuChangeEventSetMarked> {

  /**
   * The constructor.
   *
   * @param field the {@link #getField() field}.
   */
  public SudokuChangeEventSetMarked(Field field) {

    super(field);
  }

  @Override
  public void undo() {

    this.field.setMarked(false);
  }

  @Override
  public void redo() {

    this.field.setMarked(true);
  }

}
