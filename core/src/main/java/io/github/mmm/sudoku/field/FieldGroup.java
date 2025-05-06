/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.field;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.github.mmm.base.collection.SizedIterable;
import io.github.mmm.sudoku.Sudoku;
import io.github.mmm.sudoku.common.Candidates;

/**
 * Interface for a group of {@link Field}s.
 */
public interface FieldGroup extends SizedIterable<Field> {

  /** The empty {@link FieldGroup}. */
  FieldGroup EMPTY = EmptyFieldGroup.INSTANCE;

  /**
   * @return the number of {@link Field}s contained in this group. For a regular
   *         {@link io.github.mmm.sudoku.partition.Partition} the same as {@link Sudoku#getSize() sudoku size}.
   */
  int getFieldCount();

  @Override
  default int getSize() {

    return getFieldCount();
  }

  /**
   * @param fieldIndex the index of the requested {@link Field} in the range from {@code 1} to
   *        <code>{@link #getFieldCount()}</code>.
   * @return the requested {@link Field}.
   */
  Field getField(int fieldIndex);

  /**
   * @param field the {@link Field} to check.
   * @return {@code true} if the given {@link Field} is contained in this {@link FieldGroup}, {@code false} otherwise.
   */
  default boolean contains(Field field) {

    return indexOf(field) > 0;
  }

  /**
   * @param field the {@link Field} to check.
   * @return the {@link #getField(int) field-index} of the given {@link Field} or {@code -1} if not
   *         {@link #contains(Field) contained}.
   */
  default int indexOf(Field field) {

    int i = 1;
    for (Field f : this) {
      if (f == field) {
        return i;
      }
      i++;
    }
    return -1;
  }

  /**
   * @return the {@link Candidates#union(Candidates) union} of all {@link Candidates} in this group.
   */
  default Candidates uniteCandidates() {

    return uniteCandidates(Candidates.ofNone());
  }

  /**
   * @param candidates the {@link Candidates} to {@link Candidates#union(Candidates) start the union} with.
   * @return {@link Candidates#union(Candidates) union} of all {@link Candidates} in this group and the given
   *         {@link Candidates}.
   */
  default Candidates uniteCandidates(Candidates candidates) {

    Candidates result = candidates;
    for (Field field : this) {
      result = result.union(field.getCandidates());
    }
    return result;
  }

  /**
   * @return this group as {@link Field} array. Do not modify the array.
   */
  default Field[] toArray() {

    Field[] result = new Field[getFieldCount()];
    for (int i = 1; i <= result.length; i++) {
      result[i - 1] = getField(i);
    }
    return result;
  }

  /**
   * @param fields the {@link Iterable} of {@link Field}s.
   * @return the array of {@link Field}s. Do not modify the array.
   */
  static Field[] toArray(Iterable<Field> fields) {

    Field[] fieldArray;
    if (fields instanceof FieldGroup group) {
      fieldArray = group.toArray();
    } else if (fields instanceof Collection<Field> collection) {
      fieldArray = collection.toArray(Field[]::new);
    } else {
      List<Field> list = new ArrayList<>();
      for (Field field : fields) {
        list.add(field);
      }
      fieldArray = list.toArray(Field[]::new);
    }
    return fieldArray;
  }

}
