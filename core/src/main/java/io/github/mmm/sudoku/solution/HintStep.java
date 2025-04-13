package io.github.mmm.sudoku.solution;

/**
 * A Step of a {@link Hint}.
 */
public abstract class HintStep {

  /** @see #getMessage() */
  protected final String message;

  /**
   * The constructor.
   *
   * @param message the {@link #getMessage() message}.
   */
  public HintStep(String message) {

    super();
    this.message = message;
  }

  /**
   * @return the end-user message to explain this {@link HintStep}.
   */
  public String getMessage() {

    return this.message;
  }

  /**
   * Prepares this step (e.g. add marks in the view to explain the {@link Hint}).
   */
  public void prepare() {

  }

  /**
   * Applies this step.
   *
   * @param quick - {@code true} to apply the solution quickly (e.g. to solve the entire
   *        {@link io.github.mmm.sudoku.Sudoku} without {@link io.github.mmm.sudoku.Sudoku#undo() undo} history and
   *        explanations), {@code false} otherwise (detailed steps).
   */
  public void apply(boolean quick) {

  }

}
