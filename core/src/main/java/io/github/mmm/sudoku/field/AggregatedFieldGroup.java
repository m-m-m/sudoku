/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.field;

/**
 * A {@link FieldGroup} containing the {@link Field}s that all share the same {@link #getCandidate() candidate}.
 *
 * @see io.github.mmm.sudoku.partition.PartitionMap#getByCandidate(int)
 * @see Field#hasCandidate(int)
 */
public interface AggregatedFieldGroup extends IndexedFieldGroup {

  /**
   * @return the {@link Field#hasCandidate(int) candidate} shared by all {@link Field}s in this group.
   */
  int getCandidate();

}
