/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.partition.impl;

import java.util.Iterator;

import io.github.mmm.sudoku.field.Field;
import io.github.mmm.sudoku.field.FieldGroup;

/**
 * Implementation of {@link FieldGroup} using {@code FieldListNode}.
 */
public class FieldGroupImpl implements FieldGroup {

  boolean locked;

  private int count;

  FieldListNode fields;

  FieldGroupImpl() {

    super();
  }

  FieldGroupImpl(Field field) {

    super();
    add(field);
    lock();
  }

  void lock() {

    this.locked = true;
  }

  @Override
  public int getFieldCount() {

    return this.count;
  }

  @Override
  public Field getField(int fieldIndex) {

    if ((fieldIndex < 1) || (fieldIndex > this.count)) {
      throw new IndexOutOfBoundsException(fieldIndex);
    }
    FieldListNode current = this.fields;
    int i = fieldIndex - 1;
    while (i > 0) {
      current = current.next;
      i--;
    }
    return current.field;
  }

  @Override
  public Iterator<Field> iterator() {

    return new FieldListNodeIterator(this.fields);
  }

  void add(Field field) {

    if (this.locked) {
      return;
    }
    this.count++;
    this.fields = new FieldListNode(field, this.fields);
  }

}
