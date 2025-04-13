/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.event;

/**
 * Interface for an object that supports {@link #undo()} and {@link #redo()} operations.
 */
public interface ChangeAware {

  /**
   * Reverts this change (undo).
   */
  void undo();

  /**
   * Re-applies this change (redo).
   */
  void redo();

}
