/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.common;

import io.github.mmm.sudoku.dimension.Dimension;
import io.github.mmm.sudoku.dimension.DimensionType;
import io.github.mmm.sudoku.partition.Partition;

/**
 * Interface for {@link #getDimensionType()}.
 */
public interface AttributeDimensionType {

  /**
   * @return the {@link DimensionType}. In case of a {@link Partition} this is the requirement so
   *         {@link DimensionType#PRIME} accepts any {@link Dimension}, {@link DimensionType#RECTANGULAR} does not
   *         accept {@link DimensionType#PRIME} accepts prime {@link Dimension}s, and {@link DimensionType#SQUARE} only
   *         accepts square {@link Dimension}s.
   */
  DimensionType getDimensionType();

}
