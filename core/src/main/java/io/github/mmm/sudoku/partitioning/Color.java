/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.partitioning;

import io.github.mmm.sudoku.Sudoku;
import io.github.mmm.sudoku.field.Field;
import io.github.mmm.sudoku.partition.Partition;
import io.github.mmm.sudoku.style.ColorType;

/**
 * {@link Color} is a {@link Layer} with the two diagonals of the {@link Sudoku} board as colored {@link Partition}s.
 */
public class Color extends Layer {

  /** {@link LayerFactory} for {@link Color}. */
  public static final LayerFactory FACTORY = (s, i) -> new Color(s, i);

  /**
   * The constructor.
   *
   * @param sudoku the {@link #getSudoku() sudoku}.
   * @param index the {@link #getIndex() index}.
   */
  public Color(Sudoku sudoku, int index) {

    super(sudoku, index, Color::getField);
  }

  private static Field getField(Sudoku sudoku, int partitionIndex, int fieldIndex) {

    int base = sudoku.getBase();
    int field = fieldIndex - 1;
    int x0 = 1 + (field % base) * base;
    int y0 = 1 + (field / base) * base;
    int partition = partitionIndex - 1;
    int x = x0 + (partition % base);
    int y = y0 + (partition / base);
    return sudoku.getField(x, y);
  }

  @Override
  public ColorType getColorType() {

    return ColorType.MULTIPLE;
  }

}