/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.partition.impl;

import io.github.mmm.sudoku.field.Field;

/**
 * Node of a "list" of {@link Field}s.
 */
class FieldListNode {

  final Field field;

  final FieldListNode next;

  FieldListNode(Field field, FieldListNode next) {

    super();
    this.field = field;
    this.next = next;
  }

}
