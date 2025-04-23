/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.solution.strategy;

import java.util.ArrayList;
import java.util.List;

import io.github.mmm.sudoku.Sudoku;
import io.github.mmm.sudoku.common.Candidates;
import io.github.mmm.sudoku.field.Field;
import io.github.mmm.sudoku.field.FieldGroup;
import io.github.mmm.sudoku.partition.Partition;
import io.github.mmm.sudoku.partition.PartitionMap;
import io.github.mmm.sudoku.partitioning.Partitioning;
import io.github.mmm.sudoku.solution.Hint;

/**
 * {@link SolutionStrategy} to find a naked single.
 */
public class SolutionStrategyNakedPair extends SolutionStrategyByPartition {

  /** The singleton instance. */
  public static final SolutionStrategyNakedPair INSTANCE = new SolutionStrategyNakedPair();

  private SolutionStrategyNakedPair() {

  }

  @Override
  protected Hint findHint(Partition partition) {

    PartitionMap partitionMap = partition.getPartitionMap();
    FieldGroup nakedTuple = partitionMap.getNakedTuple(2);
    if (nakedTuple == null) {
      return null;
    }
    assert (nakedTuple.getFieldCount() == 2);
    Field pair1 = nakedTuple.getField(1);
    Field pair2 = nakedTuple.getField(2);
    assert (pair1.getIncludedCandidateCount() == 2);
    assert (pair2.getIncludedCandidateCount() == 2);
    Candidates candidates = pair1.getCandidates();
    assert (pair2.getCandidates() == candidates);
    Partitioning pairPartitioning = partition.getPartitioning();
    Sudoku sudoku = partition.getSudoku();
    int[] pair = candidates.toIncludedArray();
    assert (pair.length == 2);
    int v1 = pair[0];
    int v2 = pair[1];
    // find all fields where the two candidates from the naked pair can be removed
    List<Field> fields = new ArrayList<>();
    addFields(fields, partition, pair1, pair2, v1, v2);
    for (Partitioning partitioning : sudoku) {
      if (partitioning != pairPartitioning) {
        Partition partition1 = pair1.getPartition(partitioning);
        Partition partition2 = pair2.getPartition(partitioning);
        if ((partition1 != null) && (partition2 != null) && (partition1 == partition2)) {
          addFields(fields, partition1, pair1, pair2, v1, v2);
        }
      }
    }
    if (fields.size() > 0) {
      return hint(mark(partition), mark(pair1, pair2), exclude(fields, Candidates.ofValues(v1, v2)));
    }
    return null;
  }

  private void addFields(List<Field> fields, FieldGroup group, Field pair1, Field pair2, int v1, int v2) {

    for (Field field : group) {
      if ((field != pair1) && (field != pair2)) {
        if (field.hasCandidate(v1) || field.hasCandidate(v2)) {
          fields.add(field);
        }
      }
    }
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
