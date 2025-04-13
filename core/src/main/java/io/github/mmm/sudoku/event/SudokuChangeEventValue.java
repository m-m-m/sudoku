/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.event;

import java.util.Objects;

import io.github.mmm.sudoku.child.Field;

/**
 * {@link SudokuChangeEvent} for {@link Field#setValue(int)} or {@link Field#setSolution(int)}.
 */
public abstract class SudokuChangeEventValue extends SudokuChangeEvent<SudokuChangeEventValue> {

  /** @see #getOldValue() */
  protected final int oldValue;

  /** @see #getNewValue() */
  protected final int newValue;

  /**
   * The constructor.
   *
   * @param field the {@link #getField() field}.
   * @param oldValue the {@link #getOldValue() old value}.
   * @param newValue the {@link #getNewValue() new value}.
   */
  public SudokuChangeEventValue(Field field, int oldValue, int newValue) {

    super(field);
    this.oldValue = oldValue;
    this.newValue = newValue;
  }

  /**
   * @return the old value or solution before this change.
   */
  public int getOldValue() {

    return this.oldValue;
  }

  /**
   * @return the new value or solution before this change.
   */
  public int getNewValue() {

    return this.newValue;
  }

  @Override
  public int hashCode() {

    return Objects.hash(super.hashCode(), this.oldValue, this.newValue);
  }

  @Override
  protected void toString(StringBuilder sb) {

    sb.append(" oldValue=");
    sb.append(this.oldValue);
    sb.append(" newValue=");
    sb.append(this.newValue);
    super.toString(sb);
  }

}
