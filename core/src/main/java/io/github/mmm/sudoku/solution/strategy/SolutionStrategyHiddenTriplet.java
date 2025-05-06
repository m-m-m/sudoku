/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.solution.strategy;

/**
 * {@link SolutionStrategy} to find a hidden pair.
 */
public class SolutionStrategyHiddenTriplet extends SolutionStrategyHiddenTuple {

  /** The singleton instance. */
  public static final SolutionStrategyHiddenTriplet INSTANCE = new SolutionStrategyHiddenTriplet();

  private SolutionStrategyHiddenTriplet() {

    super(3);
  }

  @Override
  public int getDifficulty() {

    return 26;
  }

  @Override
  public String getName() {

    return "hidden triplet";
  }

}
