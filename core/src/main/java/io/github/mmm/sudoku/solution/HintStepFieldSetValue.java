package io.github.mmm.sudoku.solution;

import io.github.mmm.sudoku.field.Field;
import io.github.mmm.sudoku.solution.strategy.SolutionStrategy;

/**
 * {@link HintStepField} for {@link Field#setValue(int)}.
 */
public class HintStepFieldSetValue extends HintStepField {

  private final int value;

  /**
   * The constructor.
   *
   * @param name the {@link SolutionStrategy#getName() hint name}.
   * @param field the {@link #getField() field}.
   * @param value the {@link #getValue() value}.
   */
  public HintStepFieldSetValue(String name, Field field, int value) {

    super(name, field);
    this.value = value;
  }

  @Override
  protected void createMessage(StringBuilder sb, boolean first) {

    super.createMessage(sb, first);
    sb.append(" that we can set to value ");
    sb.append(this.value);
    sb.append('.');
  }

  /**
   * @return the {@link Field#getValue() value} to {@link Field#setValue(int) set}.
   */
  public int getValue() {

    return this.value;
  }

  @Override
  public void apply() {

    super.apply();
    this.field.getSudoku().setFieldValue(this.field, this.value);
  }

}
