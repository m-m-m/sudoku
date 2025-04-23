/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.dimension;

import io.github.mmm.sudoku.Sudoku;
import io.github.mmm.sudoku.common.AttributeDimensionType;
import io.github.mmm.sudoku.partitioning.Box;
import io.github.mmm.sudoku.partitioning.Column;
import io.github.mmm.sudoku.partitioning.Region;
import io.github.mmm.sudoku.partitioning.Row;

/**
 * Interface for the dimension of a {@link Sudoku}.
 */
public interface Dimension extends AttributeDimensionType {

  /**
   * @return the size of this {@link Sudoku} as its width and height. So the size represents the number of
   *         {@link Column}s and {@link Row}s as well as {@link Region}s. Regular dimensions are
   *         {@link DimensionType#SQUARE square}. So a {@link SquareDimension#D9 normal} {@link Sudoku} has a
   *         {@link #getBoxWidth() box-width} and {@link #getBoxHeight() box-heigth} of {@code 3} and therefore a
   *         {@link #getSize() size} of {@code 9} with {@link #getSymbol(int) symbols} from 1 to 9. A
   *         {@link SquareDimension#D4 "Kids-Sudoku"} has a {@link #getBoxWidth() box-width} and {@link #getBoxHeight()
   *         box-heigth} of {@code 2} and therefore a {@link #getSize() size} of {@code 4} with {@link #getSymbol(int)
   *         symbols} from 1 to 4. A {@link SquareDimension#D16 hex} or 16x16 {@link Sudoku} has a {@link #getBoxWidth()
   *         box-width} and {@link #getBoxHeight() box-heigth} of {@code 4} and therefore a {@link #getSize() size} of
   *         {@code 16} with {@link #getSymbol(int) symbols} from 1 to 16.
   */
  int getSize();

  /**
   * @return the square-root of the {@link #getSize() size}. In case of a {@link DimensionType#SQUARE square}
   *         {@link Dimension} the size (width and height) of the (potential) {@link Box}es of the {@link Sudoku}.
   *         Otherwise it is the {@link Math#ceil(double) up-rounded} square-root.
   */
  int getBoxSize();

  /**
   *
   * @return the width of the (potential) {@link Box}es of the {@link Sudoku} or {@code -1} in case of
   *         {@link DimensionType#PRIME prime} {@link Dimension}s.
   */
  int getBoxWidth();

  /**
   *
   * @return the width of the (potential) {@link Box}es of the {@link Sudoku} or {@link #getSize() size} in case of
   *         {@link DimensionType#PRIME prime} {@link Dimension}s.
   */
  int getBoxHeight();

  /**
   * @param i the index of the requested symbol in the range from {@code 1} to <code>{@link #getSize() size}</code>.
   * @return the symbol (or number) for the given index. Typically first symbols are '1'-'9' followed letters starting
   *         from 'A'.
   */
  String getSymbol(int i);
}
