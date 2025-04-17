package io.github.mmm.sudoku.partition.impl;

import io.github.mmm.sudoku.field.AggregatedFieldGroup;
import io.github.mmm.sudoku.field.Field;

/**
 * Implementation of {@link AggregatedFieldGroup} for {@link PartitionMapImpl#getByTuples(int)}.
 */
public class AggregatedFieldGroupImpl extends FieldGroupImpl implements AggregatedFieldGroup {

  private final int candidate;

  AggregatedFieldGroupImpl next;

  AggregatedFieldGroupImpl(int candidate) {

    super();
    this.candidate = candidate;
  }

  AggregatedFieldGroupImpl(int candidate, Field field) {

    super(field);
    assert field.hasValue();
    this.candidate = candidate;
  }

  @Override
  public int getCandidate() {

    return this.candidate;
  }

}
