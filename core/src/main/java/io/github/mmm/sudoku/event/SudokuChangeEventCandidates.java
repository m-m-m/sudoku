/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.event;

import io.github.mmm.sudoku.common.Candidates;
import io.github.mmm.sudoku.field.Field;

/**
 * {@link SudokuChangeEvent} for {@link Field#setCandidates(Candidates)}.
 */
public final class SudokuChangeEventCandidates extends SudokuChangeEvent<SudokuChangeEventCandidates> {

  private final Candidates oldCandidates;

  private final Candidates newCandidates;

  /**
   * The constructor.
   *
   * @param field the {@link #getField() field}.
   * @param oldCandidates the {@link #getOldCandidates() old candidates}.
   * @param newCandidates the {@link #getNewCandidates() new candidates}.
   */
  public SudokuChangeEventCandidates(Field field, Candidates oldCandidates, Candidates newCandidates) {

    super(field);
    this.oldCandidates = oldCandidates;
    this.newCandidates = newCandidates;
  }

  /**
   * @return the old {@link Candidates} before the change.
   */
  public Candidates getOldCandidates() {

    return this.oldCandidates;
  }

  /**
   * @return the new {@link Candidates} after the change.
   */
  public Candidates getNewCandidates() {

    return this.newCandidates;
  }

  @Override
  public void undo() {

    this.field.setCandidates(this.oldCandidates);
  }

  @Override
  public void redo() {

    this.field.setCandidates(this.newCandidates);
  }

}
