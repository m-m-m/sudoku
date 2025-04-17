package io.github.mmm.sudoku.solution;

import io.github.mmm.sudoku.field.Field;

/**
 * {@link HintStep} applying to multiple {@link Field}s.
 */
public final class HintStepFieldsExcludeCandidates extends HintStepFields {

  private int[] candidates;

  /**
   * The constructor.
   *
   * @param message the {@link #getMessage() message}.
   * @param fields the {@link #getFields() fields}.
   * @param candidates the {@link #getCandidates()}.
   */
  public HintStepFieldsExcludeCandidates(String message, Field[] fields, int... candidates) {

    super(message, fields);
    assert (fields.length > 0);
    assert (candidates.length > 0);
    this.candidates = candidates;
  }

  /**
   * @return the candidates to exclude. Do not modify from the outside.
   */
  public int[] getCandidates() {

    return this.candidates;
  }

  @Override
  public void apply(boolean quick) {

    for (Field field : this.fields) {
      for (int candidate : this.candidates) {
        field.excludeCandidate(candidate);
      }
    }
  }

}
