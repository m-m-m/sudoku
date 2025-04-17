/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.event;

import io.github.mmm.sudoku.field.Field;

/**
 * {@link SudokuChangeEventValue} for {@link Field#setSolution(int)}.
 */
public final class SudokuChangeEventSetSolution extends SudokuChangeEventValue {

  /**
   * The constructor.
   *
   * @param field the {@link #getField() field}.
   * @param oldValue the {@link #getOldValue() old value}.
   * @param newValue the {@link #getNewValue() new value}.
   */
  public SudokuChangeEventSetSolution(Field field, int oldValue, int newValue) {

    super(field, oldValue, newValue);
  }

  @Override
  public void undo() {

    this.field.setSolution(this.oldValue);
  }

  @Override
  public void redo() {

    this.field.setSolution(this.newValue);
  }

}
