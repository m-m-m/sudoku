/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.solution.strategy;

import io.github.mmm.sudoku.field.AggregatedFieldGroup;
import io.github.mmm.sudoku.field.Field;
import io.github.mmm.sudoku.partition.Partition;
import io.github.mmm.sudoku.partition.PartitionMap;
import io.github.mmm.sudoku.solution.Hint;

/**
 * {@link SolutionStrategy} to find a hidden single.
 */
public class SolutionStrategyHiddenSingle extends SolutionStrategyByPartition {

  /** The singleton instance. */
  public static final SolutionStrategyHiddenSingle INSTANCE = new SolutionStrategyHiddenSingle();

  private SolutionStrategyHiddenSingle() {

  }

  @Override
  protected Hint findHint(Partition partition) {

    PartitionMap partitionMap = partition.getPartitionMap();
    for (AggregatedFieldGroup tuple : partitionMap.getByTuples(1)) {
      int value = tuple.getCandidate();
      assert (tuple.getFieldCount() == 1);
      for (Field field : tuple) {
        if (!field.hasValue()) {
          assert (field.getSingle() == value);
          return hint(mark(partition), setValue(field, value));
        }
      }
    }
    return null;
  }

  @Override
  public int getDifficulty() {

    return 2;
  }

  @Override
  public String getName() {

    return "hidden single";
  }

}
