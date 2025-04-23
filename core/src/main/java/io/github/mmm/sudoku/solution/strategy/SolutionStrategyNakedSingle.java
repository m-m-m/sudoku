/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.solution.strategy;

import io.github.mmm.sudoku.Sudoku;
import io.github.mmm.sudoku.field.Field;
import io.github.mmm.sudoku.solution.Hint;

/**
 * {@link SolutionStrategy} to find a naked single.
 */
public class SolutionStrategyNakedSingle extends SolutionStrategy {

  /** The singleton instance. */
  public static final SolutionStrategyNakedSingle INSTANCE = new SolutionStrategyNakedSingle();

  private SolutionStrategyNakedSingle() {

  }

  @Override
  public Hint findHint(Sudoku sudoku) {

    int size = sudoku.getSize();
    for (int x = 1; x <= size; x++) {
      for (int y = 1; y <= size; y++) {
        Field field = sudoku.getField(x, y);
        if (!field.hasValue()) {
          int value = field.getSingle();
          if (value != Field.UNDEFINED) {
            return hint(setValue(field, value));
          }
        }
      }
    }
    return null;
  }

  @Override
  public int getDifficulty() {

    return 1;
  }

  @Override
  public String getName() {

    return "naked single";
  }

}
