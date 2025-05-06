/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.solution.strategy;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.mmm.sudoku.common.Candidates;
import io.github.mmm.sudoku.field.AggregatedFieldGroup;
import io.github.mmm.sudoku.field.Field;
import io.github.mmm.sudoku.partition.Partition;
import io.github.mmm.sudoku.partition.PartitionMap;
import io.github.mmm.sudoku.solution.Hint;

/**
 * {@link SolutionStrategyTuple} to find a hidden tuple.
 */
public abstract class SolutionStrategyHiddenTuple extends SolutionStrategyTuple {

  private static final Logger LOG = LoggerFactory.getLogger(SolutionStrategyTuple.class);

  // ATTENTION: stateful: not applicable for concurrent computations
  private final List<AggregatedFieldGroup> groups;

  SolutionStrategyHiddenTuple(int tupleSize) {

    super(tupleSize);
    this.groups = new ArrayList<>();
  }

  @Override
  protected Hint findHint(Partition partition) {

    PartitionMap partitionMap = partition.getPartitionMap();
    int size = partition.getSudoku().getSize();
    this.groups.clear();
    for (int candidate = 1; candidate <= size; candidate++) {
      AggregatedFieldGroup group = partitionMap.getByCandidate(candidate);
      int fieldCount = group.getFieldCount();
      if ((fieldCount > 1) && (fieldCount <= this.tupleSize)) {
        this.groups.add(group);
      }
    }
    if (this.groups.size() >= this.tupleSize) {
      return findHiddenTuple(partition, this.groups.toArray(AggregatedFieldGroup[]::new));
    }
    return null;
  }

  /**
   * @param partition the {@link Partition}.
   * @param candidateGroups all {@link AggregatedFieldGroup}s that {@link AggregatedFieldGroup#getFieldCount() have} at
   *        max our {@link #getTupleSize() tuple-size}}.
   * @return the {@link Hint} or {@code null} if none found.
   */
  protected Hint findHiddenTuple(Partition partition, AggregatedFieldGroup[] candidateGroups) {

    int startIndex = candidateGroups.length - this.tupleSize;
    int newEndIndex = startIndex + 1;
    for (int i = startIndex; i >= 0; i--) {
      AggregatedFieldGroup group = candidateGroups[i];
      Candidates candidates = Candidates.ofValue(group.getCandidate());
      Candidates fieldIndexes = group.getFieldIndexes();
      Hint hint = findHintForHiddenTuppleRecursive(partition, candidateGroups, candidates, fieldIndexes,
          this.tupleSize - 1, i + 1, newEndIndex);
      if (hint != null) {
        return hint;
      }
    }
    return null;
  }

  private Hint findHintForHiddenTuppleRecursive(Partition partition, AggregatedFieldGroup[] candidateGroups,
      Candidates candidates, Candidates fieldIndexes, int recursion, int startIndex, int endIndex) {

    recursion--;
    int newEndIndex = endIndex + 1;
    for (int i = startIndex; i <= endIndex; i++) {
      AggregatedFieldGroup group = candidateGroups[i];
      Candidates newCandidates = candidates.include(group.getCandidate());
      Candidates newFieldIndexes = fieldIndexes.union(group.getFieldIndexes());
      if (newFieldIndexes.getInclusionCount() <= this.tupleSize) {
        if (recursion == 0) {
          Field[] fields = new Field[this.tupleSize];
          int j = 0;
          boolean hasHint = false;
          for (int fieldIndex : newFieldIndexes.toIncludedArray()) {
            Field field = partition.getField(fieldIndex);
            if (!hasHint) {
              hasHint = field.hasOtherCandidatesThan(newCandidates);
            }
            fields[j++] = field;
          }
          if (hasHint) {
            return hint(mark(partition), mark(fields), intersect(newCandidates, fields));
          } else {
            LOG.debug("Found {}-tuple with candidates {} in fields {} from partition {} but already naked!",
                this.tupleSize, newCandidates, newFieldIndexes, partition);
          }
        } else {
          Hint hint = findHintForHiddenTuppleRecursive(partition, candidateGroups, newCandidates, newFieldIndexes,
              recursion, i + 1, newEndIndex);
          if (hint != null) {
            return hint;
          }
        }
      }
    }
    return null;
  }

}
