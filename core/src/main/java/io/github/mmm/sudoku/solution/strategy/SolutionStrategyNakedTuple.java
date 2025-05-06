/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.solution.strategy;

import java.util.ArrayList;
import java.util.List;

import io.github.mmm.sudoku.common.Candidates;
import io.github.mmm.sudoku.field.CandidatesFieldGroup;
import io.github.mmm.sudoku.field.Field;
import io.github.mmm.sudoku.partition.Partition;
import io.github.mmm.sudoku.partition.PartitionMap;
import io.github.mmm.sudoku.solution.Hint;

/**
 * {@link SolutionStrategyTuple} to find a naked tuple.
 */
public abstract class SolutionStrategyNakedTuple extends SolutionStrategyTuple {

  SolutionStrategyNakedTuple(int tupleSize) {

    super(tupleSize);
  }

  @Override
  protected Hint findHint(Partition partition) {

    Hint hint = null;
    PartitionMap partitionMap = partition.getPartitionMap();
    for (int candidateCount = this.tupleSize; candidateCount >= 2; candidateCount--) {
      Iterable<CandidatesFieldGroup> groupIteraable = partitionMap.getByCandidatesCount(candidateCount);
      for (CandidatesFieldGroup group : groupIteraable) {
        Candidates candidates = group.getCandidates();
        Candidates fieldIndexes = group.getFieldIndexes();
        if (fieldIndexes.getInclusionCount() == this.tupleSize) {
          hint = findHintForNakedTuple(partition, candidates, fieldIndexes);
        } else {
          CandidatesFieldGroup nextGroup = null;
          if (candidateCount < this.tupleSize) {
            nextGroup = group.geteNext();
          }
          hint = findHint(partition, candidates, fieldIndexes, nextGroup, candidateCount);
        }
        if (hint != null) {
          return hint;
        }
      }
    }
    return hint;
  }

  private Hint findHint(Partition partition, Candidates candidates, Candidates fieldIndexes, CandidatesFieldGroup group,
      int candidateCount) {

    CandidatesFieldGroup currentGroup = group;
    while (candidateCount >= 2) {
      while (currentGroup != null) {
        Candidates currentCandidates = candidates.union(currentGroup.getCandidates());
        if (currentCandidates.getInclusionCount() <= this.tupleSize) {
          Candidates currentFieldIndexes = fieldIndexes.union(currentGroup.getFieldIndexes());
          int tupleCount = currentFieldIndexes.getInclusionCount();
          if (tupleCount == this.tupleSize) {
            return findHintForNakedTuple(partition, candidates, currentFieldIndexes);
          }
          if (currentCandidates == candidates) {
            assert (tupleCount < this.tupleSize);
            fieldIndexes = currentFieldIndexes;
          } else {
            Hint hint = findHint(partition, currentCandidates, currentFieldIndexes, currentGroup.geteNext(),
                candidateCount);
            if (hint != null) {
              return hint;
            }
          }
        }
        currentGroup = currentGroup.geteNext();
      }
      candidateCount--;
      currentGroup = partition.getPartitionMap().getFirstByCandidatesCount(candidateCount);
    }
    return null;
  }

  private Hint findHintForNakedTuple(Partition partition, Candidates candidates, Candidates fieldIndexes) {

    List<Field> exclusionFields = null;
    int size = partition.getSize();
    for (int fieldIndex = 1; fieldIndex <= size; fieldIndex++) {
      if (!fieldIndexes.has(fieldIndex)) {
        Field field = partition.getField(fieldIndex);
        if (!field.hasValue() && field.hasAtLeastOneCandidateOf(candidates)) {
          if (exclusionFields == null) {
            exclusionFields = new ArrayList<>();
          }
          exclusionFields.add(field);
        }
      }
    }
    if (exclusionFields != null) {
      return hint(mark(partition), mark(getFields(partition, fieldIndexes)), exclude(candidates, exclusionFields));
    }
    return null;
  }

}
