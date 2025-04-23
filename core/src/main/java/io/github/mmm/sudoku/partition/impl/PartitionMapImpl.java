/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.partition.impl;

import java.util.HashMap;
import java.util.Map;

import io.github.mmm.sudoku.common.Candidates;
import io.github.mmm.sudoku.field.AggregatedFieldGroup;
import io.github.mmm.sudoku.field.Field;
import io.github.mmm.sudoku.field.FieldGroup;
import io.github.mmm.sudoku.partition.Partition;
import io.github.mmm.sudoku.partition.PartitionMap;

/**
 * Implementation of {@link PartitionMap}.
 */
public class PartitionMapImpl implements PartitionMap {

  private final Partition partition;

  private final int modificationCounter;

  private AggregatedFieldGroupImpl[] counts;

  private TupleIterable[] tuples;

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
  public FieldGroup getNakedTuple(int n) {

    // actually n == size also does not make sense...
    if ((n < 1) || (n > this.partition.getSudoku().getSize())) {
      throw new IndexOutOfBoundsException(n);
    }
    Map<Candidates, FieldGroupImpl> map = null;
    if (n >= 2) {
      map = new HashMap<>();
    }
    for (Field field : this.partition) {
      int remainingCandidates = field.getIncludedCandidateCount();
      if (remainingCandidates == n) {
        if (n == 1) {
          return new FieldGroupImpl(field);
        } else {
          @SuppressWarnings("null")
          FieldGroupImpl group = map.computeIfAbsent(field.getCandidates(), c -> new FieldGroupImpl());
          group.add(field);
          if (group.getFieldCount() == n) {
            return group;
          }
        }
      }
    }
    return null;
  }

  @Override
  public Iterable<AggregatedFieldGroup> getByTuples(int n) {

    if ((n < 1) || (n > this.partition.getSudoku().getSize())) {
      throw new IndexOutOfBoundsException(n);
    }
    if (this.tuples == null) {
      this.tuples = computeTuples();
    }
    return this.tuples[n - 1];
  }

  @Override
  public AggregatedFieldGroup getByCandidate(int candidate) {

    if ((candidate < 1) || (candidate > this.partition.getSudoku().getSize())) {
      throw new IndexOutOfBoundsException(candidate);
    }
    return getCounts()[candidate - 1];
  }

  private TupleIterable[] computeTuples() {

    int size = this.partition.getSudoku().getSize();
    TupleIterable[] result = new TupleIterable[size];
    for (AggregatedFieldGroupImpl group : getCounts()) {
      int fieldCount = group.getFieldCount();
      int i = fieldCount - 1;
      if (i >= 0) {
        if (result[i] == null) {
          result[i] = new TupleIterable(group);
        } else {
          group.next = result[i].first;
          result[i].first = group;
        }
      }
    }
    for (int i = 0; i < result.length; i++) {
      if (result[i] == null) {
        result[i] = TupleIterable.EMPTY;
      }
    }
    return result;
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
    for (Field field : this.partition) {
      if (field.hasValue()) {
        int value = field.getValue();
        result[value - 1] = new AggregatedFieldGroupImpl(value, field);
      } else {
        for (int candidate = 1; candidate <= size; candidate++) {
          if (field.hasCandidate(candidate)) {
            if (result[candidate - 1] == null) {
              result[candidate - 1] = new AggregatedFieldGroupImpl(candidate);
            }
            result[candidate - 1].add(field);
          }
        }
      }
    }
    return result;
  }

}
