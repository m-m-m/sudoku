package io.github.mmm.sudoku.common;

import io.github.mmm.sudoku.Sudoku;
import io.github.mmm.sudoku.dimension.Dimension;
import io.github.mmm.sudoku.partitioning.Box;
import io.github.mmm.sudoku.partitioning.Jigsaw;
import io.github.mmm.sudoku.partitioning.Partitioning;
import io.github.mmm.sudoku.partitioning.Region;

/**
 * Interface for {@link #isRegular()}.
 */
public interface AttributeRegular {

  /**
   * @return {@code true} if regular, {@code false} otherwise (irregular). A regular {@link Dimension} or {@link Sudoku}
   *         has a quadratic {@link Dimension#getSize() size} ({@link Dimension#getBase() base} *
   *         {@link Dimension#getBase() base} == {@link Dimension#getSize() size}). If a {@link Partitioning} is
   *         regular, it requires a regular {@link Sudoku}. E.g. {@link Box}es use the {@link Dimension#getBase() base}
   *         as width and height and therefore must be regular. However, {@link Jigsaw Jigsaw} is an alternative
   *         {@link Region} instead of {@link Box}es and allows {@link Sudoku}s with irregular
   *         {@link io.github.mmm.sudoku.dimension.Dimension#getSize() sizes} such as {@code 5} or {@code 7}.
   */
  boolean isRegular();

}
