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
    int size = partition.getSudoku().getSize();
    for (int value = 1; value <= size; value++) {
      AggregatedFieldGroup group = partitionMap.getByCandidate(value);
      if (group.getFieldCount() == 1) {
        Field field = group.getField(1);
        if (!field.hasValue()) {
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
