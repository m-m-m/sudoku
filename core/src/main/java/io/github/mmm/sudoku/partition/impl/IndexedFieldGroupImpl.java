/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.partition.impl;

import java.util.Iterator;

import io.github.mmm.base.collection.AbstractIterator;
import io.github.mmm.sudoku.common.Candidates;
import io.github.mmm.sudoku.field.Field;
import io.github.mmm.sudoku.field.FieldGroup;
import io.github.mmm.sudoku.field.IndexedFieldGroup;
import io.github.mmm.sudoku.partition.Partition;

/**
 * Implementation of {@link FieldGroup} using {@code FieldListNode}.
 */
public class IndexedFieldGroupImpl implements IndexedFieldGroup {

  private final Partition partition;

  private Candidates fieldIndexes;

  private final boolean locked;

  IndexedFieldGroupImpl(Partition partition) {

    super();
    this.partition = partition;
    this.fieldIndexes = Candidates.ofNone();
    this.locked = false;
  }

  IndexedFieldGroupImpl(Partition partition, int fieldIndex) {

    super();
    this.partition = partition;
    this.fieldIndexes = Candidates.ofValue(fieldIndex);
    this.locked = true;
  }

  @Override
  public Partition getPartition() {

    return this.partition;
  }

  @Override
  public Candidates getFieldIndexes() {

    return this.fieldIndexes;
  }

  @Override
  public Iterator<Field> iterator() {

    return new IndexedFieldGroupIterator();
  }

  void add(int fieldIndex) {

    if (this.locked) {
      return;
    }
    this.fieldIndexes = this.fieldIndexes.include(fieldIndex);
  }

  /**
   * Implementation of {@link Iterator} for {@link IndexedFieldGroupImpl#iterator()}.
   */
  private class IndexedFieldGroupIterator extends AbstractIterator<Field> {

    private int i;

    IndexedFieldGroupIterator() {

      super();
      this.i = 1;
      findFirst();
    }

    @Override
    protected Field findNext() {

      int index = IndexedFieldGroupImpl.this.fieldIndexes.getCandidate(this.i++);
      if (index > 0) {
        return IndexedFieldGroupImpl.this.partition.getField(index);
      }
      return null;
    }
  }

}
