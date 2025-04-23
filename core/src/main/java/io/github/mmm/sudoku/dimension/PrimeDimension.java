/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.dimension;

import java.util.List;

/**
 * Regular {@link AbstractDimension} that have a {@link #getBase() base}.
 */
public final class PrimeDimension extends AbstractDimension {

  /** {@link PrimeDimension} for with {@link #getSize() size} of {@code 5}. */
  public static final PrimeDimension D5 = new PrimeDimension(5);

  /** {@link PrimeDimension} for with {@link #getSize() size} of {@code 7}. */
  public static final PrimeDimension D7 = new PrimeDimension(7);

  /** {@link PrimeDimension} for with {@link #getSize() size} of {@code 11}. */
  public static final PrimeDimension D11 = new PrimeDimension(11);

  /** {@link PrimeDimension} for with {@link #getSize() size} of {@code 13}. */
  public static final PrimeDimension D13 = new PrimeDimension(13);

  /** {@link PrimeDimension} for with {@link #getSize() size} of {@code 17}. */
  public static final PrimeDimension D17 = new PrimeDimension(17);

  /** {@link PrimeDimension} for with {@link #getSize() size} of {@code 19}. */
  public static final PrimeDimension D19 = new PrimeDimension(19);

  /** {@link PrimeDimension} for with {@link #getSize() size} of {@code 23}. */
  public static final PrimeDimension D23 = new PrimeDimension(23);

  /** {@link PrimeDimension} for with {@link #getSize() size} of {@code 29}. */
  public static final PrimeDimension D29 = new PrimeDimension(29);

  /** {@link PrimeDimension} for with {@link #getSize() size} of {@code 31}. */
  public static final PrimeDimension D31 = new PrimeDimension(31);

  /** {@link PrimeDimension} for with {@link #getSize() size} of {@code 37}. */
  public static final PrimeDimension D37 = new PrimeDimension(37);

  private PrimeDimension(int size) {

    this(size, null);
  }

  private PrimeDimension(int size, List<String> alphabet) {

    super(1, size, size, alphabet);
  }

  @Override
  public DimensionType getDimensionType() {

    return DimensionType.PRIME;
  }

  /**
   * @param base the {@link #getBase() base}.
   * @param alphabet the custom {@link #getAlphabet() alphabet}.
   * @return the new {@link PrimeDimension}.
   */
  public static PrimeDimension of(int base, List<String> alphabet) {

    return new PrimeDimension(base, alphabet);
  }

}
