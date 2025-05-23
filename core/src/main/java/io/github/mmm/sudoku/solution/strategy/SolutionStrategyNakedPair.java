/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.solution.strategy;

/**
 * {@link SolutionStrategy} to find a naked pair.
 */
public class SolutionStrategyNakedPair extends SolutionStrategyNakedTuple {

  /** The singleton instance. */
  public static final SolutionStrategyNakedPair INSTANCE = new SolutionStrategyNakedPair();

  private SolutionStrategyNakedPair() {

    super(2);
  }

  @Override
  public int getDifficulty() {

    return 10;
  }

  @Override
  public String getName() {

    return "naked pair";
  }

}
