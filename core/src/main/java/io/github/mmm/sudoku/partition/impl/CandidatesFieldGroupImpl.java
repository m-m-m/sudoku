/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.partition.impl;

import io.github.mmm.sudoku.common.Candidates;
import io.github.mmm.sudoku.field.AggregatedFieldGroup;
import io.github.mmm.sudoku.field.CandidatesFieldGroup;
import io.github.mmm.sudoku.partition.Partition;

/**
 * Implementation of {@link AggregatedFieldGroup} for {@link PartitionMapImpl#getByCandidatesCount(int)}.
 */
public class CandidatesFieldGroupImpl extends IndexedFieldGroupImpl implements CandidatesFieldGroup {

  final Candidates candidates;

  CandidatesFieldGroupImpl next;

  CandidatesFieldGroupImpl(Partition partition, Candidates candidates) {

    super(partition);
    this.candidates = candidates;
  }

  @Override
  public Candidates getCandidates() {

    return this.candidates;
  }

  @Override
  public CandidatesFieldGroup geteNext() {

    return this.next;
  }

}
