/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.partitioning;

import io.github.mmm.sudoku.Sudoku;
import io.github.mmm.sudoku.dimension.DimensionType;
import io.github.mmm.sudoku.field.Field;
import io.github.mmm.sudoku.style.ColorType;

/** {@link Jigsaw} {@link Region}. */
public class Jigsaw extends Region {

  /**
   * The constructor.
   *
   * @param sudoku the {@link #getSudoku() sudoku}.
   */
  public Jigsaw(Sudoku sudoku, int index) {

    super(sudoku, index, Jigsaw::getField);
  }

  private static Field getField(Sudoku sudoku, int partitionIndex, int fieldIndex) {

    // TODO: Jigsaw partions are not define in a unique way
    // take an index/seed as additional parameter to identify and compute partitions?
    return null;
  }

  @Override
  public DimensionType getDimensionType() {

    return DimensionType.PRIME;
  }

  @Override
  public ColorType getColorType() {

    return ColorType.MULTIPLE;
  }

}