package io.github.mmm.sudoku.field;

import io.github.mmm.sudoku.Sudoku;

/**
 * Interface for a group of {@link Field}s.
 */
public interface FieldGroup extends Iterable<Field> {

  /**
   * @return the number of {@link Field}s contained in this group. For a regular
   *         {@link io.github.mmm.sudoku.partition.Partition} the same as {@link Sudoku#getSize() sudoku size}.
   */
  int getFieldCount();

  /**
   * @param fieldIndex the index of the requested {@link Field} in the range from {@code 1} to
   *        <code>{@link #getFieldCount()}</code>.
   * @return the requested {@link Field}.
   */
  Field getField(int fieldIndex);

}
