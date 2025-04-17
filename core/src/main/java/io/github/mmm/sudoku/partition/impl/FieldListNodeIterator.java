package io.github.mmm.sudoku.partition.impl;

import java.util.Iterator;
import java.util.NoSuchElementException;

import io.github.mmm.sudoku.field.Field;

/**
 * {@link Iterator} implementation for {@link Field} (FieldListNode).
 */
public class FieldListNodeIterator implements Iterator<Field> {

  private FieldListNode next;

  FieldListNodeIterator(FieldListNode next) {

    super();
    this.next = next;
  }

  @Override
  public boolean hasNext() {

    return this.next != null;
  }

  @Override
  public Field next() {

    if (this.next == null) {
      throw new NoSuchElementException();
    }
    Field field = this.next.field;
    this.next = this.next.next;
    return field;
  }

}
