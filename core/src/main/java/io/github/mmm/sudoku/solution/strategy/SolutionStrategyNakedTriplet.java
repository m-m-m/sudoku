/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.solution.strategy;

/**
 * {@link SolutionStrategy} to find a naked triplet.
 */
public class SolutionStrategyNakedTriplet extends SolutionStrategyNakedTuple {

  /** The singleton instance. */
  public static final SolutionStrategyNakedTriplet INSTANCE = new SolutionStrategyNakedTriplet();

  private SolutionStrategyNakedTriplet() {

    super(3);
  }

  @Override
  public int getDifficulty() {

    return 20;
  }

  @Override
  public String getName() {

    return "naked triplet";
  }

}
