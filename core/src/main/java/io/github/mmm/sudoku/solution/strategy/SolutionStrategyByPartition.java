/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.solution.strategy;

import io.github.mmm.sudoku.Sudoku;
import io.github.mmm.sudoku.partition.Partition;
import io.github.mmm.sudoku.partitioning.Partitioning;
import io.github.mmm.sudoku.solution.Hint;

/**
 * Strategy to find a {@link Hint}.
 */
public abstract class SolutionStrategyByPartition extends SolutionStrategy {

  @Override
  public Hint findHint(Sudoku sudoku) {

    for (Partitioning partitioning : sudoku) {
      for (Partition partition : partitioning) {
        Hint hint = findHint(partition);
        if (hint != null) {
          return hint;
        }
      }
    }
    return null;
  }

  /**
   * @param partition the {@link Partition} to scan.
   * @return the next {@link Hint} found in the given {@link Partition} by this {@link SolutionStrategy} or {@code null}
   *         if nothing found.
   */
  protected abstract Hint findHint(Partition partition);
}
