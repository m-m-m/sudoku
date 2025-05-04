package io.github.mmm.sudoku.dimension;

import io.github.mmm.sudoku.Sudoku;
import io.github.mmm.sudoku.partitioning.Box;
import io.github.mmm.sudoku.partitioning.Column;
import io.github.mmm.sudoku.partitioning.Jigsaw;
import io.github.mmm.sudoku.partitioning.Region;
import io.github.mmm.sudoku.partitioning.Row;

/**
 * {@link Enum} with the available types of a {@link Dimension}.
 *
 * @see io.github.mmm.sudoku.common.AttributeDimensionType#getDimensionType()
 */
public enum DimensionType {

  /**
   * A square {@link Dimension} or {@link Sudoku} has a quadratic {@link Dimension#getSize() size}. It is a more
   * specific form of {@link #RECTANGULAR rectangular} with {@link Dimension#getBoxWidth() box-width} ==
   * {@link Dimension#getBoxHeight() box-height}.
   */
  SQUARE,

  /**
   * A rectangular {@link Dimension} or {@link Sudoku} has a non-{@link #PRIME prime} {@link Dimension#getSize() size}.
   * It applies that {@link Dimension#getBoxWidth() box-width} * {@link Dimension#getBoxHeight() box-height} ==
   * {@link Dimension#getSize() size} where both factors are greater than {@code 1}. In case both factors are identical,
   * the type is {@link #SQUARE square}.
   */
  RECTANGULAR,

  /**
   * A prime {@link Dimension} or {@link Sudoku} has prime {@link Dimension#getSize() size} (e.g. {@code 3}, {@code 5},
   * or {@code 7}). Therefore it does not suport {@link Box}es as its size cannot be factorized into
   * {@link Dimension#getBoxWidth() box-width} and {@link Dimension#getBoxHeight() box-height}. If the width or heigth
   * would be {@code 1}, then the {@link Box}es would be identical to {@link Column}s or {@link Row}s. {@link Jigsaw
   * Jigsaw} is an alternative {@link Region} instead of {@link Box}es and allows {@link Sudoku}s with prime
   * {@link io.github.mmm.sudoku.dimension.Dimension#getSize() sizes}.
   */
  PRIME

}
