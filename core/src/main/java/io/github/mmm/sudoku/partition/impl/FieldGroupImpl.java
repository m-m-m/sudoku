package io.github.mmm.sudoku.partition.impl;

import java.util.Iterator;

import io.github.mmm.sudoku.field.Field;
import io.github.mmm.sudoku.field.FieldGroup;

/**
 * Implementation of {@link FieldGroup} using {@code FieldListNode}.
 */
public class FieldGroupImpl implements FieldGroup {

  private final boolean locked;

  private int count;

  private FieldListNode fields;

  FieldGroupImpl() {

    super();
    this.locked = false;
  }

  FieldGroupImpl(Field field) {

    super();
    add(field);
    this.locked = false;
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
