package io.github.mmm.sudoku.solution;

import io.github.mmm.sudoku.field.Field;

/**
 * {@link HintStepField} for {@link Field#excludeCandidate(int)}.
 */
public class HintStepFieldExcludeCandidate extends HintStepField {

  private final int candidate;

  /**
   * The constructor.
   *
   * @param message the {@link #getMessage() message}.
   * @param field the {@link #getField() field}.
   * @param candidate the {@link #getCandidate() candidate}.
   */
  public HintStepFieldExcludeCandidate(String message, Field field, int candidate) {

    super(message, field);
    this.candidate = candidate;
  }

  /**
   * @return the {@link Field#hasCandidate(int) candidate} to {@link Field#excludeCandidate(int) exclude}.
   */
  public int getCandidate() {

    return this.candidate;
  }

  @Override
  public void apply(boolean quick) {

    this.field.excludeCandidate(this.candidate);
  }

}
