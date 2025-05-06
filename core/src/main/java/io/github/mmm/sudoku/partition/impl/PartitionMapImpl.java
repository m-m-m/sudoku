/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.partition.impl;

import java.util.Collections;

import io.github.mmm.sudoku.common.Candidates;
import io.github.mmm.sudoku.field.AggregatedFieldGroup;
import io.github.mmm.sudoku.field.CandidatesFieldGroup;
import io.github.mmm.sudoku.field.Field;
import io.github.mmm.sudoku.partition.Partition;
import io.github.mmm.sudoku.partition.PartitionMap;

/**
 * Implementation of {@link PartitionMap}.
 */
public class PartitionMapImpl implements PartitionMap {

  private final Partition partition;

  private final int modificationCounter;

  private AggregatedFieldGroupImpl[] counts;

  private CandidatesFieldGroupIterable[] tuples;

  /**
   * The constructor.
   *
   * @param partition the {@link Partition} from which to compute this map.
   */
  public PartitionMapImpl(Partition partition) {

    super();
    this.partition = partition;
    this.modificationCounter = partition.getSudoku().getModificationCounter();
  }

  @Override
  public Partition getPartition() {

    return this.partition;
  }

  @Override
  public int getModificationCounter() {

    return this.modificationCounter;
  }

  @Override
  public AggregatedFieldGroup getByCandidate(int candidate) {

    if ((candidate < 1) || (candidate > this.partition.getSudoku().getSize())) {
      throw new IndexOutOfBoundsException(candidate);
    }
    return getCounts()[candidate - 1];
  }

  private AggregatedFieldGroupImpl[] getCounts() {

    if (this.counts == null) {
      this.counts = computeCounts();
    }
    return this.counts;
  }

  private AggregatedFieldGroupImpl[] computeCounts() {

    int size = this.partition.getSudoku().getSize();
    AggregatedFieldGroupImpl[] result = new AggregatedFieldGroupImpl[size];
    int fieldCount = this.partition.getFieldCount();
    for (int fieldIndex = 1; fieldIndex <= fieldCount; fieldIndex++) {
      Field field = this.partition.getField(fieldIndex);
      if (field.hasValue()) {
        int value = field.getValue();
        result[value - 1] = new AggregatedFieldGroupImpl(this.partition, value, fieldIndex);
      } else {
        for (int candidate = 1; candidate <= size; candidate++) {
          if (field.hasCandidate(candidate)) {
            if (result[candidate - 1] == null) {
              result[candidate - 1] = new AggregatedFieldGroupImpl(this.partition, candidate);
            }
            result[candidate - 1].add(fieldIndex);
          }
        }
      }
    }
    return result;
  }

  @Override
  public Iterable<CandidatesFieldGroup> getByCandidatesCount(int count) {

    if ((count < 2) || (count >= this.partition.getSudoku().getSize())) {
      return Collections.emptySet();
    }
    return getTuples()[count - 2];
  }

  @Override
  public CandidatesFieldGroup getFirstByCandidatesCount(int count) {

    if ((count < 2) || (count >= this.partition.getSudoku().getSize())) {
      return null;
    }
    CandidatesFieldGroupIterable iterable = getTuples()[count - 2];
    return iterable.first;
  }

  private CandidatesFieldGroupIterable[] getTuples() {

    if (this.tuples == null) {
      this.tuples = computeTuples();
    }
    return this.tuples;
  }

  private CandidatesFieldGroupIterable[] computeTuples() {

    int size = this.partition.getSudoku().getSize();
    CandidatesFieldGroupIterable[] result = new CandidatesFieldGroupIterable[size - 2];
    for (int fieldIndex = 1; fieldIndex <= size; fieldIndex++) {
      Field field = this.partition.getField(fieldIndex);
      if (!field.hasValue()) {
        Candidates candidates = field.getCandidates();
        int count = candidates.getInclusionCount();
        if ((count >= 2) && (count < size)) {
          CandidatesFieldGroupIterable iterable = result[count - 2];
          if (iterable == null) {
            iterable = new CandidatesFieldGroupIterable(null);
            result[count - 2] = iterable;
          }
          iterable.add(this.partition, candidates, fieldIndex);
        }
      }
    }
    for (int i = 0; i < result.length; i++) {
      if (result[i] == null) {
        result[i] = CandidatesFieldGroupIterable.EMPTY;
      }
    }
    return result;
  }
}
