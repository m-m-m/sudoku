/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.event;

import java.util.Objects;

import io.github.mmm.sudoku.field.Field;

/**
 * {@link SudokuEvent} for a change typically of a {@link #getField() field}.
 *
 * @param <E> type of this class itself.
 */
public abstract class SudokuFieldEvent<E extends SudokuFieldEvent<E>> extends SudokuEvent<E> {

  /** @see #getField() */
  protected final Field field;

  /**
   * The constructor.
   *
   * @param field the changed {@link #getField() field}.
   */
  public SudokuFieldEvent(Field field) {

    super();
    if (isFieldRequired()) {
      Objects.requireNonNull(field);
    }
    this.field = field;
  }

  /**
   * @return {@code true} if the {@link #getField() field} is required, {@code false} otherwise (optional).
   */
  protected boolean isFieldRequired() {

    return true;
  }

  @Override
  public int hashCode() {

    if (this.field == null) {
      return 0;
    }
    return this.field.hashCode();
  }

  @Override
  protected boolean isEqual(E other) {

    return (this.field == ((SudokuFieldEvent<?>) other).field);
  }

  /**
   * @return the {@link Field} that has been changed or {@code null} if the change is not related to a field.
   */
  public Field getField() {

    return this.field;
  }

  @Override
  protected void toString(StringBuilder sb) {

    sb.append(' ');
    sb.append(this.field);
  }

}
