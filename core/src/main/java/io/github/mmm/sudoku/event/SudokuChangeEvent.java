/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.event;

import io.github.mmm.sudoku.child.Field;

/**
 * {@link SudokuEvent} for a change typically of a {@link #getField() field}.
 *
 * @param <E> type of this class itself.
 */
public abstract class SudokuChangeEvent<E extends SudokuChangeEvent<E>> extends SudokuFieldEvent<E>
    implements ChangeAware {

  /**
   * The constructor.
   *
   * @param field the changed {@link #getField() field}.
   */
  public SudokuChangeEvent(Field field) {

    super(field);
  }

}
