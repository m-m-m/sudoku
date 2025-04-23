package io.github.mmm.sudoku.solution;

import io.github.mmm.sudoku.field.Field;

/**
 * {@link HintStep} applying to multiple {@link Field}s.
 */
public class HintStepFieldsMarking extends HintStepFields {

  /**
   * The constructor.
   *
   * @param name the {@link #getName() name}.
   * @param fields the {@link #getFields() fields}.
   */
  public HintStepFieldsMarking(String name, Field... fields) {

    super(name, fields);
  }

  @Override
  protected String getAttributedFieldForMessage() {

    return "marked field";
  }

}
