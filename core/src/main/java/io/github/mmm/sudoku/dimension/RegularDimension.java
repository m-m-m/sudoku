/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.dimension;

import java.util.List;

import io.github.mmm.sudoku.Sudoku;

/**
 * Regular {@link AbstractDimension} that have a {@link #getBase() base}.
 */
public final class RegularDimension extends AbstractDimension {

  /** {@link RegularDimension} for with {@link #getBase() base} of {@code 2} for a easy 2x2 {@link Sudoku} (Kids). */
  public static final RegularDimension EASY = new RegularDimension(2);

  /** {@link RegularDimension} for with {@link #getBase() base} of {@code 3} for a normal 9x9 {@link Sudoku}. */
  public static final RegularDimension NORMAL = new RegularDimension(3);

  /** {@link RegularDimension} for with {@link #getBase() base} of {@code 4} for a 16x16 {@link Sudoku}. */
  public static final RegularDimension HEX = new RegularDimension(4);

  private RegularDimension(int base) {

    this(base, ALPHABET);
  }

  private RegularDimension(int base, List<String> alphabet) {

    super(base, base * base, alphabet);
  }

  @Override
  public boolean isRegular() {

    return true;
  }

  /**
   * @param base the {@link #getBase() base}.
   * @param alphabet the custom {@link #getAlphabet() alphabet}.
   * @return the new {@link RegularDimension}.
   */
  public static RegularDimension of(int base, List<String> alphabet) {

    return new RegularDimension(base, alphabet);
  }

}
