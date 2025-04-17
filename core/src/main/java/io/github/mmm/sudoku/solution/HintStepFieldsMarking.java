package io.github.mmm.sudoku.solution;

import io.github.mmm.sudoku.field.Field;

/**
 * {@link HintStep} applying to multiple {@link Field}s.
 */
public class HintStepFieldsMarking extends HintStepFields {

  /**
   * The constructor.
   *
   * @param message the {@link #getMessage() message}.
   * @param fields the {@link #getFields() fields}.
   */
  public HintStepFieldsMarking(String message, Field... fields) {

    super(message, fields);
  }

}
