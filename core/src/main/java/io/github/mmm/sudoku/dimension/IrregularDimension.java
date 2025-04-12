/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.dimension;

import java.util.List;

/**
 * Regular {@link AbstractDimension} that have a {@link #getBase() base}.
 */
public final class IrregularDimension extends AbstractDimension {

  /** {@link IrregularDimension} for with {@link #getSize() size} of {@code 5}. */
  public static final IrregularDimension D5 = new IrregularDimension(5);

  /** {@link IrregularDimension} for with {@link #getSize() size} of {@code 6}. */
  public static final IrregularDimension D6 = new IrregularDimension(6);

  /** {@link IrregularDimension} for with {@link #getSize() size} of {@code 7}. */
  public static final IrregularDimension D7 = new IrregularDimension(7);

  /** {@link IrregularDimension} for with {@link #getSize() size} of {@code 8}. */
  public static final IrregularDimension D8 = new IrregularDimension(8);

  /** {@link IrregularDimension} for with {@link #getSize() size} of {@code 10}. */
  public static final IrregularDimension D10 = new IrregularDimension(10);

  /** {@link IrregularDimension} for with {@link #getSize() size} of {@code 11}. */
  public static final IrregularDimension D11 = new IrregularDimension(11);

  /** {@link IrregularDimension} for with {@link #getSize() size} of {@code 12}. */
  public static final IrregularDimension D12 = new IrregularDimension(12);

  private IrregularDimension(int size) {

    this(size, ALPHABET);
  }

  private IrregularDimension(int size, List<String> alphabet) {

    super((int) Math.ceil(Math.sqrt(size)), size, alphabet);
  }

  @Override
  public boolean isRegular() {

    return false;
  }

  /**
   * @param base the {@link #getBase() base}.
   * @param alphabet the custom {@link #getAlphabet() alphabet}.
   * @return the new {@link IrregularDimension}.
   */
  public static IrregularDimension of(int base, List<String> alphabet) {

    return new IrregularDimension(base, alphabet);
  }

}
