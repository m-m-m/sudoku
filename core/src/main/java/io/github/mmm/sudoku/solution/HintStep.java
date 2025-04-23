package io.github.mmm.sudoku.solution;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.mmm.sudoku.solution.strategy.SolutionStrategy;

/**
 * A Step of a {@link Hint}.
 */
public abstract class HintStep implements AbstractHint {

  private static final Logger LOG = LoggerFactory.getLogger(HintStep.class);

  private final String name;

  private String message;

  /**
   * The constructor.
   *
   * @param name the {@link #getName() name}.
   */
  public HintStep(String name) {

    super();
    this.name = name;
  }

  /**
   * @return the composed {@link #getMessage() message}.
   */
  private String createMessage(boolean first) {

    StringBuilder sb = new StringBuilder(32);
    createMessage(sb, first);
    return sb.toString();
  }

  /**
   * @param sb the {@link StringBuilder} to append to.
   * @param first - {@code true} if this is the first {@link HintStep}, {@code false} otherwise.
   */
  protected void createMessage(StringBuilder sb, boolean first) {

    if (first) {
      sb.append("There is a ");
      sb.append(this.name);
    } else {
      sb.append("The ");
      sb.append(this.name);
      sb.append(" is");
    }
  }

  void init(boolean first) {

    assert (this.message == null);
    this.message = createMessage(first);
  }

  /**
   * @return the {@link SolutionStrategy#getName() hint name}.
   */
  public String getName() {

    return this.name;
  }

  /**
   * @return the end-user message to explain this {@link HintStep}.
   */
  public String getMessage() {

    if (this.message == null) {
      return createMessage(true);
    }
    return this.message;
  }

  /**
   * Prepares this step (e.g. add marks in the view to explain the {@link Hint}).
   */
  public void prepare() {

  }

  @Override
  public void apply() {

    LOG.debug("{}", getMessage());
  }

  @Override
  public String toString() {

    return getMessage();
  }

}
