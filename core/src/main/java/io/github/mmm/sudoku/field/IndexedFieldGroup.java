/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.field;

import io.github.mmm.sudoku.common.Candidates;
import io.github.mmm.sudoku.partition.Partition;

/**
 * A {@link FieldGroup} that is a sub-group of a {@link #getPartition() partition} based on the
 * {@link #getFieldIndexes() field indexes}.
 */
public interface IndexedFieldGroup extends FieldGroup {

  /**
   * @return the original {@link Partition} from which this {@link FieldGroup} is a sub-set of.
   */
  Partition getPartition();

  /**
   * @return the {@link Partition#getField(int) field indexes} from the original {@link Partition} of all
   *         {@link #contains(Field) contained} {@link Field}s as {@link Candidates}.
   */
  Candidates getFieldIndexes();

  @Override
  default int getFieldCount() {

    return getFieldIndexes().getInclusionCount();
  }

  @Override
  default Field getField(int fieldIndex) {

    int index = getFieldIndexes().getCandidate(fieldIndex);
    if (index == -1) {
      throw new IndexOutOfBoundsException(fieldIndex);
    }
    return getPartition().getField(index);
  }

}
