/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.solution.strategy;

import java.util.ArrayList;
import java.util.List;

import io.github.mmm.sudoku.Sudoku;
import io.github.mmm.sudoku.common.Candidates;
import io.github.mmm.sudoku.field.AggregatedFieldGroup;
import io.github.mmm.sudoku.field.Field;
import io.github.mmm.sudoku.field.FieldGroup;
import io.github.mmm.sudoku.partition.Partition;
import io.github.mmm.sudoku.partition.PartitionMap;
import io.github.mmm.sudoku.partitioning.Partitioning;
import io.github.mmm.sudoku.solution.Hint;

/**
 * {@link SolutionStrategy} to find an intersection with another {@link Partition}.
 */
public class SolutionStrategyPartitionIntersection extends SolutionStrategyByPartition {

  /** The singleton instance. */
  public static final SolutionStrategyPartitionIntersection INSTANCE = new SolutionStrategyPartitionIntersection();

  private SolutionStrategyPartitionIntersection() {

  }

  @Override
  protected Hint findHint(Partition partition) {

    Sudoku sudoku = partition.getSudoku();
    int size = sudoku.getSize();
    PartitionMap partitionMap = partition.getPartitionMap();
    Partitioning myPartitioning = partition.getPartitioning();
    int myPartitioningIndex = myPartitioning.getIndex();
    int partitioningCount = sudoku.getPartitioningCount();
    for (int value = 1; value <= size; value++) {
      AggregatedFieldGroup group = partitionMap.getByCandidate(value);
      for (int partitioningIndex = 1; partitioningIndex <= partitioningCount; partitioningIndex++) {
        if (partitioningIndex != myPartitioningIndex) {
          int intersectionIndex = 0;
          Partition intersection = null;
          for (Field field : group) {
            Partition fieldPartition = field.getPartition(partitioningIndex);
            if (fieldPartition == null) {
              intersectionIndex = -1;
              intersection = null;
              break;
            } else {
              int fieldPartitionIndex = fieldPartition.getIndex();
              if (intersectionIndex == 0) {
                intersectionIndex = fieldPartitionIndex;
                intersection = fieldPartition;
              } else if (intersectionIndex != fieldPartitionIndex) {
                intersectionIndex = -1;
                intersection = null;
                break;
              }
            }
          }
          if (intersection != null) {
            List<Field> fields = new ArrayList<>();
            addFields(fields, intersection, partition, value);
            if (fields.size() > 0) {
              return hint(mark(partition), mark(group), exclude(Candidates.ofValue(value), fields));
            }
          }
        }
      }
    }
    return null;
  }

  private void addFields(List<Field> fields, FieldGroup group, FieldGroup exclusion, int value) {

    for (Field field : group) {
      if (!field.hasValue() && field.getCandidates().has(value) && !exclusion.contains(field)) {
        fields.add(field);
      }
    }
  }

  @Override
  public int getDifficulty() {

    return 25;
  }

  @Override
  public String getName() {

    return "partition intersection";
  }

}
