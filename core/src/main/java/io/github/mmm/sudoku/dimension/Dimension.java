/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.dimension;

import io.github.mmm.sudoku.Sudoku;
import io.github.mmm.sudoku.common.AttributeRegular;
import io.github.mmm.sudoku.partitioning.Column;
import io.github.mmm.sudoku.partitioning.Region;
import io.github.mmm.sudoku.partitioning.Row;

/**
 * Interface for the dimension of a {@link Sudoku}.
 */
public interface Dimension extends AttributeRegular {

  /**
   * @return the size of this {@link Sudoku} as its width and height. So the size represents the number of
   *         {@link Column}s and {@link Row}s as well as {@link Region}s. Regular dimensions have a {@link #getBase()
   *         base} defined and then the size is the square of that base. So a {@link RegularDimension#NORMAL normal}
   *         {@link Sudoku} has a {@link #getBase() base} of {@code 3} and therefore a {@link #getSize() size} of
   *         {@code 9} with {@link #getSymbol(int) symbols} from 1 to 9. A {@link RegularDimension#EASY "Kids-Sudoku"}
   *         has a {@link #getBase() base} of {@code 2} and therefore a {@link #getSize() size} of {@code 4} with
   *         {@link #getSymbol(int) symbols} from 1 to 4. A {@link RegularDimension#HEX hex} or 16x16 {@link Sudoku} has
   *         a {@link #getBase() basis} of {@code 4} and therefore a {@link #getSize() size} of {@code 16} with
   *         {@link #getSymbol(int) symbols} from 0 to F.
   */
  int getSize();

  /**
   * @return the base of the {@link Sudoku} as the square-root of its {@link #getSize() size}. This is only exact for a
   *         {@link #isRegular() regular} {@link Dimension}. Otherwise it is the {@link Math#ceil(double) up-rounded}
   *         square-root.
   */
  int getBase();

  /**
   * @param i the index of the requested symbol in the range from {@code 1} to <code>{@link #getSize() size}</code>.
   * @return the symbol (or number) for the given index. Typically first symbols are '1'-'9' followed letters starting
   *         from 'A'.
   */
  String getSymbol(int i);
}
