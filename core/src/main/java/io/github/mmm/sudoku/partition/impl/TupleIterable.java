/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.partition.impl;

import java.util.Collections;
import java.util.Iterator;

import io.github.mmm.sudoku.field.AggregatedFieldGroup;

/**
 * Implementation of {@link Iterable} for {@link PartitionMapImpl#getByTuples(int)}.
 */
public class TupleIterable implements Iterable<AggregatedFieldGroup> {

  /** The empty instance. */
  public static final TupleIterable EMPTY = new TupleIterable(null);

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

    if (this.first == null) {
      return Collections.emptyIterator();
    }
    return new AggregatedFieldGroupIterator(this.first);
  }

}
