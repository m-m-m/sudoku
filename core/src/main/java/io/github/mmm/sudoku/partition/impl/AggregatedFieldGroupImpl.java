/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.partition.impl;

import io.github.mmm.sudoku.field.AggregatedFieldGroup;
import io.github.mmm.sudoku.partition.Partition;

/**
 * Implementation of {@link AggregatedFieldGroup}.
 */
public class AggregatedFieldGroupImpl extends IndexedFieldGroupImpl implements AggregatedFieldGroup {

  private final int candidate;

  AggregatedFieldGroupImpl(Partition partition, int candidate) {

    super(partition);
    this.candidate = candidate;
  }

  AggregatedFieldGroupImpl(Partition partition, int candidate, int fieldIndex) {

    super(partition, fieldIndex);
    this.candidate = candidate;
  }

  @Override
  public int getCandidate() {

    return this.candidate;
  }

}
