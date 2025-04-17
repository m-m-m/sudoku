package io.github.mmm.sudoku.solution;

import io.github.mmm.sudoku.field.Field;

/**
 * {@link HintStep} applying to multiple {@link Field}s.
 */
public abstract class HintStepFields extends HintStep {

  /** @see #getFields() */
  protected final Field[] fields;

  /**
   * The constructor.
   *
   * @param message the {@link #getMessage() message}.
   * @param fields the {@link #getFields() fields}.
   */
  public HintStepFields(String message, Field... fields) {

    super(message);
    this.fields = fields;
  }

  /**
   * @return the {@link Field}s affected by this {@link HintStep}. Do not modify from the outside.
   */
  public Field[] getFields() {

    return this.fields;
  }

}
