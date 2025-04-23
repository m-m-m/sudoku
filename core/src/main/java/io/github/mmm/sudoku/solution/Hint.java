package io.github.mmm.sudoku.solution;

import java.util.List;

import io.github.mmm.sudoku.Sudoku;
import io.github.mmm.sudoku.common.Difficulty;

/**
 * A {@link Hint} is a logical next step on the way to the solution of a {@link Sudoku}. It is {@link #getSteps()
 * composed} of multiple {@link HintStep} that allow to explain this {@link Hint} to the end-user. Also, it
 * {@link #getDifficulty() has} a {@link Difficulty} scoring how easy or hard it is to come to the hint.
 */
public class Hint implements AbstractHint {

  private final int difficulty;

  private final List<HintStep> steps;

  /**
   * The constructor.
   *
   * @param difficulty the {@link #getDifficulty}
   * @param steps the {@link #getSteps() steps}.
   */
  public Hint(int difficulty, List<HintStep> steps) {

    super();
    this.difficulty = difficulty;
    this.steps = steps;
    boolean first = true;
    for (HintStep step : steps) {
      step.init(first);
      first = false;
    }
  }

  /**
   * @return the difficulty score in the range from {@code 1} (trivial) to {@code 100} (brute force).
   */
  public int getDifficulty() {

    return this.difficulty;
  }

  /**
   * @return the {@link List} of {@link HintStep}s of this {@link Hint}.
   */
  public List<HintStep> getSteps() {

    return this.steps;
  }

  @Override
  public void apply() {

    for (HintStep step : this.steps) {
      step.apply();
    }
  }

}
