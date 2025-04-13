package io.github.mmm.sudoku.solution;

import io.github.mmm.sudoku.Sudoku;
import io.github.mmm.sudoku.child.Field;

/**
 * {@link Enum} with the available difficulties of a {@link Hint} or {@link Sudoku}.
 */
public enum Difficulty {

  /** Very easy (e.g. finding {@link Field#getSingle() single}s). */
  VERY_EASY("very easy"),

  /** Easy (e.g. finding hidden singles). */
  EASY("easy"),

  /** Moderate (e.g. finding naked pairs or intersections). */
  MODERATE("moderate"),

  /** Challenging (e.g. finding common victims). */
  CHALLENGING("challenging"),

  /** Tricky (e.g. quadruples or hidden triplets). */
  TRICKY("tricky"),

  /** Hard (e.g. finding chains). */
  HARD("hard"),

  /** Very hard (requires "fishes"). */
  VERY_HARD("very hard"),

  /** Extreme (requires brute force). */
  EXTREME("extreme");

  private final String title;

  private Difficulty(String title) {

    this.title = title;
  }

  /**
   * @return the display title.
   */
  public String getTitle() {

    return this.title;
  }

  @Override
  public String toString() {

    return this.title;
  }

}
