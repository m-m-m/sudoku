/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.dimension;

import java.util.List;

/**
 * {@link DimensionType#RECTANGULAR Rectangular} {@link Dimension}.
 */
public final class RectangularDimension extends AbstractDimension {

  /** {@link RectangularDimension} for with {@link #getSize() size} of {@code 6}. */
  public static final RectangularDimension D6 = new RectangularDimension(3, 2);

  /** {@link RectangularDimension} for with {@link #getSize() size} of {@code 8}. */
  public static final RectangularDimension D8 = new RectangularDimension(4, 2);

  /** {@link RectangularDimension} for with {@link #getSize() size} of {@code 10}. */
  public static final RectangularDimension D10 = new RectangularDimension(5, 2);

  /** {@link RectangularDimension} for with {@link #getSize() size} of {@code 12}. */
  public static final RectangularDimension D12 = new RectangularDimension(4, 3);

  /** {@link RectangularDimension} for with {@link #getSize() size} of {@code 14}. */
  public static final RectangularDimension D14 = new RectangularDimension(7, 2);

  /** {@link RectangularDimension} for with {@link #getSize() size} of {@code 15}. */
  public static final RectangularDimension D15 = new RectangularDimension(5, 3);

  private RectangularDimension(int boxWidth, int boxHeight) {

    this(boxWidth, boxHeight, null);
  }

  private RectangularDimension(int boxWidth, int boxHeight, List<String> alphabet) {

    super(boxWidth, boxHeight, boxWidth * boxHeight, alphabet);
    // super((int) Math.ceil(Math.sqrt(size)), size, alphabet);
  }

  @Override
  public DimensionType getDimensionType() {

    return DimensionType.RECTANGULAR;
  }

  /**
   * @param boxWidth the {@link #getBoxWidth() box-width}.
   * @param boxHeight the {@link #getBoxHeight() box-height}.
   * @param alphabet the custom {@link #getAlphabet() alphabet}.
   * @return the new {@link RectangularDimension}.
   */
  public static RectangularDimension of(int boxWidth, int boxHeight, List<String> alphabet) {

    return new RectangularDimension(boxWidth, boxHeight, alphabet);
  }

}
