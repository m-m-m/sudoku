/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.partition.impl;

import java.util.Collections;
import java.util.Iterator;

import io.github.mmm.sudoku.common.Candidates;
import io.github.mmm.sudoku.field.CandidatesFieldGroup;
import io.github.mmm.sudoku.field.Field;
import io.github.mmm.sudoku.partition.Partition;

/**
 * Implementation of {@link Iterable} for {@link CandidatesFieldGroup}.
 */
public class CandidatesFieldGroupIterable implements Iterable<CandidatesFieldGroup> {

  static final CandidatesFieldGroupIterable EMPTY = new CandidatesFieldGroupIterable(null);

  CandidatesFieldGroupImpl first;

  CandidatesFieldGroupIterable(CandidatesFieldGroupImpl first) {

    super();
    this.first = first;
  }

  @Override
  public Iterator<CandidatesFieldGroup> iterator() {

    if (this.first == null) {
      return Collections.emptyIterator();
    }
    return new CandidatesFieldGroupIterator(this.first);
  }

  /**
   * @param partition the owning {@link Partition}.
   * @param candidates the {@link Field#getCandidates()}.
   * @param fieldIndex the {@link Partition#getField(int) index} of the {@link Field} to add.
   */
  public void add(Partition partition, Candidates candidates, int fieldIndex) {

    CandidatesFieldGroupImpl current = this.first;
    CandidatesFieldGroupImpl last = null;
    while (current != null) {
      if (current.candidates.equals(candidates)) {
        break;
      }
      last = current;
      current = current.next;
    }
    if (current == null) {
      CandidatesFieldGroupImpl nextGroup = new CandidatesFieldGroupImpl(partition, candidates);
      nextGroup.add(fieldIndex);
      if (last != null) {
        last.next = nextGroup;
      } else {
        this.first = nextGroup;
      }
    } else {
      current.add(fieldIndex);
      if (last != null) { // same as (current != first)
        int currentSize = current.getSize();
        if (last.getSize() < currentSize) {
          CandidatesFieldGroupImpl pointer = this.first;
          while (pointer != current) {
            int size = pointer.getSize();
            if (size < currentSize) {
              last.next = current.next;
              current.next = pointer;
              if (pointer == this.first) {
                this.first = current;
              }
              return;
            }
            pointer = pointer.next;
          }
          assert false;
        }
      }
    }
  }

}
