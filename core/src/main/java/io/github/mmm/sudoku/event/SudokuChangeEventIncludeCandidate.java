/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.event;

import io.github.mmm.sudoku.child.Field;

/**
 * {@link SudokuChangeEventCandidate} for {@link Field#includeCandidate(int)}.
 */
public final class SudokuChangeEventIncludeCandidate extends SudokuChangeEventCandidate {

  /**
   * The constructor.
   *
   * @param field the {@link #getField() field}.
   * @param candidate the {@link #getCandidate() candidate}.
   */
  public SudokuChangeEventIncludeCandidate(Field field, int candidate) {

    super(field, candidate);
  }

  @Override
  public void undo() {

    this.field.excludeCandidate(this.candidate);
  }

  @Override
  public void redo() {

    this.field.includeCandidate(this.candidate);
  }

}
