package io.github.mmm.sudoku.solution;

import io.github.mmm.sudoku.field.Field;

/**
 * {@link HintStep} applying to a specific {@link Field}.
 */
public abstract class HintStepField extends HintStep {

  /** @see #getField() */
  protected final Field field;

  /**
   * The constructor.
   *
   * @param message the {@link #getMessage() message}.
   * @param field the {@link #getField() field}.
   */
  public HintStepField(String message, Field field) {

    super(message);
    this.field = field;
  }

  /**
   * @return the {@link Field} affected by this {@link HintStep}.
   */
  public Field getField() {

    return this.field;
  }

  @Override
  protected void createMessage(StringBuilder sb, boolean first) {

    super.createMessage(sb, first);
    sb.append(" in field ");
    this.field.appendCoordinates(sb);
  }

}
