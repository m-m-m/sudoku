package io.github.mmm.sudoku.partition.impl;

import java.util.Iterator;
import java.util.NoSuchElementException;

import io.github.mmm.sudoku.field.AggregatedFieldGroup;

/**
 * Implementation of {@link Iterator} for {@link AggregatedFieldGroupImpl}.
 */
public class AggregatedFieldGroupIterator implements Iterator<AggregatedFieldGroup> {

  private AggregatedFieldGroupImpl next;

  AggregatedFieldGroupIterator(AggregatedFieldGroupImpl next) {

    super();
    this.next = next;
  }

  @Override
  public boolean hasNext() {

    return this.next != null;
  }

  @Override
  public AggregatedFieldGroup next() {

    if (this.next == null) {
      throw new NoSuchElementException();
    }
    AggregatedFieldGroup result = this.next;
    this.next = this.next.next;
    return result;
  }

}
