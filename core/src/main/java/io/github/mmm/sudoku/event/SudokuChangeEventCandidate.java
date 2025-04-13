/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.event;

import io.github.mmm.sudoku.child.Field;

/**
 * {@link SudokuChangeEvent} for the change of a {@link Field#hasCandidate(int) candidate}.
 */
public abstract class SudokuChangeEventCandidate extends SudokuChangeEvent<SudokuChangeEventCandidate> {

  /** @see #getCandidate() */
  protected final int candidate;

  /**
   * The constructor.
   *
   * @param field the {@link #getField() field}.
   * @param candidate the {@link #getCandidate() candidate}.
   */
  public SudokuChangeEventCandidate(Field field, int candidate) {

    super(field);
    this.candidate = candidate;
  }

  /**
   * @return the changed {@link Field#hasCandidate(int) candidate}.
   */
  public int getCandidate() {

    return this.candidate;
  }

  @Override
  public void undo() {

    this.field.includeCandidate(this.candidate);
  }

  @Override
  public void redo() {

    this.field.excludeCandidate(this.candidate);
  }

  @Override
  public int hashCode() {

    return super.hashCode() * 31 + this.candidate;
  }

  @Override
  protected boolean isEqual(SudokuChangeEventCandidate other) {

    if (this.candidate != other.candidate) {
      return false;
    }
    return super.isEqual(other);
  }

  @Override
  protected void toString(StringBuilder sb) {

    sb.append(" candidate=");
    sb.append(this.candidate);
    super.toString(sb);
  }

}
