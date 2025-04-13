package io.github.mmm.sudoku.solution;

import io.github.mmm.sudoku.child.Field;

/**
 * {@link HintStepField} for {@link Field#setValue(int)}.
 */
public class HintStepFieldSetValue extends HintStepField {

  private final int value;

  /**
   * The constructor.
   *
   * @param message the {@link #getMessage() message}.
   * @param field the {@link #getField() field}.
   * @param value the {@link #getValue() value}.
   */
  public HintStepFieldSetValue(String message, Field field, int value) {

    super(message, field);
    this.value = value;
  }

  /**
   * @return the {@link Field#getValue() value} to {@link Field#setValue(int) set}.
   */
  public int getValue() {

    return this.value;
  }

  @Override
  public void apply(boolean quick) {

    this.field.getSudoku().setFieldValue(this.field, this.value, false, !quick);
  }

}
