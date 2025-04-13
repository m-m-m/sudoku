/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.event;

import io.github.mmm.sudoku.Sudoku;

/**
 * Interface for an event about a {@link Sudoku}.
 *
 * @param <E> type of this class itself.
 */
public abstract class SudokuEvent<E extends SudokuEvent<E>> {

  @SuppressWarnings("unchecked")
  @Override
  public boolean equals(Object obj) {

    if (obj == this) {
      return true;
    } else if ((obj == null) || (obj.getClass() != getClass())) {
      return false;
    }
    return isEqual((E) obj);
  }

  /**
   * @param other the {@link SudokuEvent} to compare with. Must not be {@code null}.
   * @return {@code true} if {@link #equals(Object) equal}, {@code false} otherwise.
   */
  protected abstract boolean isEqual(E other);

  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder(getClass().getSimpleName());
    sb.append(':');
    toString(sb);
    return sb.toString();
  }

  /**
   * @param sb the {@link StringBuilder} to append to.
   */
  protected abstract void toString(StringBuilder sb);

}
