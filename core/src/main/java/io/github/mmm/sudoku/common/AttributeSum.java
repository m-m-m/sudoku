/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.common;

import io.github.mmm.sudoku.field.Field;
import io.github.mmm.sudoku.partition.Partition;

/**
 * Interface for {@link #getSum()}.
 */
public interface AttributeSum {

  /**
   * @return the sum of the {@link Field#getValue() values} of the contained {@link Partition#getField(int) fields} in
   *         case this is a "sum-partition" of a "Sumdoku", otherwise {@code -1}.
   */
  int getSum();
}
