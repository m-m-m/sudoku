/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.dimension;

import java.util.List;

import io.github.mmm.sudoku.Sudoku;

/**
 * {@link DimensionType#SQUARE Square} {@link Dimension}.
 */
public final class SquareDimension extends AbstractDimension {

  /**
   * {@link SquareDimension} for with {@link #getBoxSize() box-size} of {@code 2} for a easy 4x4 {@link Sudoku} (Kids).
   */
  public static final SquareDimension D4 = new SquareDimension(2);

  /** {@link SquareDimension} for with {@link #getBoxSize() box-size} of {@code 3} for a normal 9x9 {@link Sudoku}. */
  public static final SquareDimension D9 = new SquareDimension(3);

  /** {@link SquareDimension} for with {@link #getBoxSize() box-size} of {@code 4} for a 16x16 {@link Sudoku}. */
  public static final SquareDimension D16 = new SquareDimension(4);

  private SquareDimension(int boxSize) {

    this(boxSize, null);
  }

  private SquareDimension(int boxSize, List<String> alphabet) {

    super(boxSize, boxSize, boxSize * boxSize, alphabet);
  }

  @Override
  public DimensionType getDimensionType() {

    return DimensionType.SQUARE;
  }

  /**
   * @param boxSize the {@link #getBoxSize() box-size}.
   * @param alphabet the custom {@link #getAlphabet() alphabet}.
   * @return the new {@link SquareDimension}.
   */
  public static SquareDimension of(int boxSize, List<String> alphabet) {

    return new SquareDimension(boxSize, alphabet);
  }

}
