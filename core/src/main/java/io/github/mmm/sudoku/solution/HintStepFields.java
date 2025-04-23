package io.github.mmm.sudoku.solution;

import java.util.Objects;

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
   * @param name the {@link #getName() name}.
   * @param fields the {@link #getFields() fields}.
   */
  public HintStepFields(String name, Field... fields) {

    super(name);
    Objects.requireNonNull(fields);
    assert (fields.length > 0);
    this.fields = fields;
  }

  /**
   * @return the {@link Field}s affected by this {@link HintStep}. Do not modify from the outside.
   */
  public Field[] getFields() {

    return this.fields;
  }

  @Override
  protected void createMessage(StringBuilder sb, boolean first) {

    super.createMessage(sb, first);
    sb.append(" in the ");
    appendFieldsToMessage(sb);
  }

  /**
   * @param sb the {@link StringBuilder} where to append the {@link #getFields() fields}.
   * @see #createMessage(StringBuilder, boolean)
   */
  protected void appendFieldsToMessage(StringBuilder sb) {

    sb.append(getAttributedFieldForMessage());
    int length = this.fields.length;
    if (length > 1) {
      sb.append('s');
    }
    int max = length - 1;
    for (int i = 0; i < length; i++) {
      if (i == 0) {
        sb.append(" ");
      } else if (i == max) {
        sb.append(", and ");
      } else {
        sb.append(", ");
      }
      this.fields[i].appendCoordinates(sb);
    }
  }

  /**
   * @return "field" or an attributed variant.
   * @see #createMessage(StringBuilder, boolean)
   */
  protected String getAttributedFieldForMessage() {

    return "field";
  }

}
