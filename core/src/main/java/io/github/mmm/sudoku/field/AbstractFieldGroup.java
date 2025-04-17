package io.github.mmm.sudoku.field;

import java.util.Iterator;

import io.github.mmm.base.collection.ArrayIterator;

/**
 * Abstract base implementation of {@link FieldGroup}.
 */
public abstract class AbstractFieldGroup implements FieldGroup {

  private final Field[] fields;

  /**
   * The constructor.
   *
   * @param fields the {@link #getField(int) fields}.
   */
  public AbstractFieldGroup(Field... fields) {

    super();
    this.fields = fields;
  }

  @Override
  public Field getField(int fieldIndex) {

    if ((fieldIndex < 1) || (fieldIndex > this.fields.length)) {
      throw new IndexOutOfBoundsException(fieldIndex);
    }
    return this.fields[fieldIndex - 1];
  }

  @Override
  public int getFieldCount() {

    return this.fields.length;
  }

  @Override
  public Iterator<Field> iterator() {

    return new ArrayIterator<>(this.fields);
  }

}
