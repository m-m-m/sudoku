/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.field;

import java.util.Collections;
import java.util.Iterator;

/**
 * Empty implementation of {@link FieldGroup}.
 */
class EmptyFieldGroup implements FieldGroup {

  static final EmptyFieldGroup INSTANCE = new EmptyFieldGroup();

  private EmptyFieldGroup() {

  }

  @Override
  public int getFieldCount() {

    return 0;
  }

  @Override
  public Field getField(int fieldIndex) {

    throw new IndexOutOfBoundsException(fieldIndex);
  }

  @Override
  public Iterator<Field> iterator() {

    return Collections.emptyIterator();
  }

}
