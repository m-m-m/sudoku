package io.github.mmm.sudoku.partition.impl;

import java.util.Iterator;

import io.github.mmm.sudoku.field.AggregatedFieldGroup;

/**
 * Implementation of {@link Iterable} for {@link PartitionMapImpl#getByTuples(int)}.
 */
public class TupleIterable implements Iterable<AggregatedFieldGroup> {

  AggregatedFieldGroupImpl first;

  /**
   * The constructor.
   *
   * @param groups the first {@link AggregatedFieldGroupImpl} to iterate.
   */
  public TupleIterable(AggregatedFieldGroupImpl groups) {

    super();
    this.first = groups;
  }

  @Override
  public Iterator<AggregatedFieldGroup> iterator() {

    return new AggregatedFieldGroupIterator(this.first);
  }

}
