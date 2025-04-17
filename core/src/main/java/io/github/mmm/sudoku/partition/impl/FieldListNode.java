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
