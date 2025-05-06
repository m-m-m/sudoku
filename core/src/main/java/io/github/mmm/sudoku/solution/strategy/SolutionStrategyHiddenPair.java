/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.solution.strategy;

/**
 * {@link SolutionStrategy} to find a hidden pair.
 */
public class SolutionStrategyHiddenPair extends SolutionStrategyHiddenTuple {

  /** The singleton instance. */
  public static final SolutionStrategyHiddenPair INSTANCE = new SolutionStrategyHiddenPair();

  private SolutionStrategyHiddenPair() {

    super(2);
  }

  @Override
  public int getDifficulty() {

    return 15;
  }

  @Override
  public String getName() {

    return "hidden pair";
  }

}
