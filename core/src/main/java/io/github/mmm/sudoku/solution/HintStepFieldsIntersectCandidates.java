package io.github.mmm.sudoku.solution;

import io.github.mmm.sudoku.common.Candidates;
import io.github.mmm.sudoku.field.Field;

/**
 * {@link HintStep} to {@link Candidates#intersect(Candidates) intersect} with the given {@link Candidates} in the
 * {@link Field}s.
 */
public final class HintStepFieldsIntersectCandidates extends HintStepFields {

  private Candidates candidates;

  /**
   * The constructor.
   *
   * @param name the {@link #getName() name}.
   * @param fields the {@link #getFields() fields}.
   * @param candidates the {@link #getCandidates()}.
   */
  public HintStepFieldsIntersectCandidates(String name, Field[] fields, Candidates candidates) {

    super(name, fields);
    assert (candidates != null);
    this.candidates = candidates;
  }

  @Override
  protected void createMessage(StringBuilder sb, boolean first) {

    sb.append("We can exclude all candidate");
    if (this.candidates.getInclusionCount() > 1) {
      sb.append('s');
    }
    sb.append(" except ");
    sb.append(this.candidates);

    sb.append(" from ");
    appendFieldsToMessage(sb);
    sb.append('.');
  }

  /**
   * @return the {@link Candidates} to exclude.
   */
  public Candidates getCandidates() {

    return this.candidates;
  }

  @Override
  public void apply() {

    super.apply();
    for (Field field : this.fields) {
      field.setCandidates(field.getCandidates().intersect(this.candidates));
    }
  }

}
