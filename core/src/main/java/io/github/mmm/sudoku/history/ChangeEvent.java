/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.history;

import io.github.mmm.sudoku.child.Field;

/**
 * Interface for a single change with its chain of {@link #next() next changes}.
 */
public interface ChangeEvent extends ChangeAware {

  /**
   * @return the {@link Field} that has been changed or {@code null} if the change is not related to a field.
   */
  Field field();

  /**
   * @return the next {@link ChangeEvent} or {@code null} if no more changes are available for this event.
   */
  ChangeEvent next();

  /**
   * Performs {@link #undo()} on this and all {@link #next() next events}.
   */
  default void undoAll() {

    ChangeEvent current = this;
    do {
      current.undo();
      current = current.next();
    } while (current != null);
  }

  /**
   * Performs {@link #redo()} on this and all {@link #next() next events}.
   */
  default void redoAll() {

    ChangeEvent current = this;
    do {
      current.redo();
      current = current.next();
    } while (current != null);
  }

}
