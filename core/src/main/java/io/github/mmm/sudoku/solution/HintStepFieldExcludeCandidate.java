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
   * @param name the {@link #getName() name}.
   * @param field the {@link #getField() field}.
   * @param candidate the {@link #getCandidate() candidate}.
   */
  public HintStepFieldExcludeCandidate(String name, Field field, int candidate) {

    super(name, field);
    this.candidate = candidate;
  }

  @Override
  protected void createMessage(StringBuilder sb, boolean first) {

    sb.append("We can exclude the candidate ");
    sb.append(this.candidate);
    sb.append(" from field ");
    this.field.appendCoordinates(sb);
    sb.append('.');
  }

  /**
   * @return the {@link Field#hasCandidate(int) candidate} to {@link Field#excludeCandidate(int) exclude}.
   */
  public int getCandidate() {

    return this.candidate;
  }

  @Override
  public void apply() {

    super.apply();
    this.field.excludeCandidate(this.candidate);
  }

}
