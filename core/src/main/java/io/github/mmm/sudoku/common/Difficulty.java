/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.common;

import io.github.mmm.sudoku.Sudoku;
import io.github.mmm.sudoku.field.Field;
import io.github.mmm.sudoku.solution.Hint;

/**
 * {@link Enum} with the available difficulties of a {@link Hint} or {@link Sudoku}.
 */
public enum Difficulty {

  /** Very easy (e.g. finding {@link Field#getSingle() single}s). */
  VERY_EASY("very easy", 1),

  /** Easy (e.g. finding hidden singles). */
  EASY("easy", 5),

  /** Moderate (e.g. finding naked pairs or intersections). */
  MODERATE("moderate", 15),

  /** Challenging (e.g. finding common victims). */
  CHALLENGING("challenging", 25),

  /** Tricky (e.g. quadruples or hidden triplets). */
  TRICKY("tricky", 35),

  /** Hard (e.g. finding chains). */
  HARD("hard", 45),

  /** Very hard (requires "fishes"). */
  VERY_HARD("very hard", 55),

  /** Extreme (requires brute force). */
  EXTREME("extreme", 65);

  private final String title;

  private final int score;

  private Difficulty(String title, int score) {

    this.title = title;
    this.score = score;
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

  /**
   * @param score the computed score.
   * @return the corresponding {@link Difficulty}.
   */
  public static Difficulty ofScore(double score) {

    Difficulty[] values = values();
    for (int i = values.length - 1; i >= 0; i++) {
      Difficulty difficulty = values[i];
      if (score >= difficulty.score) {
        return difficulty;
      }
    }
    return VERY_EASY; // should never reach here...
  }

}
