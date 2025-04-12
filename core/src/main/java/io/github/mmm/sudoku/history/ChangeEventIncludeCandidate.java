/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.history;

import io.github.mmm.sudoku.child.Field;

/**
 * Implementation of {@link ChangeEvent} for {@link Field#includeCandidate(int)}.
 *
 * @param field the {@link Field} that was modified.
 * @param candidate the {@link Field#includeCandidate(int) included candidate}.
 * @param next the next {@link ChangeEvent} or {@code null} if this is the last {@link ChangeEvent} of a
 *        {@link ChangeSet}.
 */
public record ChangeEventIncludeCandidate(Field field, int candidate, ChangeEvent next) implements ChangeEvent {
  @Override
  public void undo() {

    this.field.excludeCandidate(this.candidate);
  }

  @Override
  public void redo() {

    this.field.includeCandidate(this.candidate);
  }

}
