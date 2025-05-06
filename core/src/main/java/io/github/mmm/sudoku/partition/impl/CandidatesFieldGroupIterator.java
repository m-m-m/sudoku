/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.partition.impl;

import java.util.Iterator;
import java.util.NoSuchElementException;

import io.github.mmm.sudoku.field.CandidatesFieldGroup;

/**
 * Implementation of {@link Iterator} for {@link CandidatesFieldGroupImpl}.
 */
public class CandidatesFieldGroupIterator implements Iterator<CandidatesFieldGroup> {

  private CandidatesFieldGroupImpl next;

  CandidatesFieldGroupIterator(CandidatesFieldGroupImpl next) {

    super();
    this.next = next;
  }

  @Override
  public boolean hasNext() {

    return this.next != null;
  }

  @Override
  public CandidatesFieldGroup next() {

    if (this.next == null) {
      throw new NoSuchElementException();
    }
    CandidatesFieldGroup result = this.next;
    this.next = this.next.next;
    return result;
  }

}
