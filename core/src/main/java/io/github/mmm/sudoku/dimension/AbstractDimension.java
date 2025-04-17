/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.dimension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.github.mmm.sudoku.Sudoku;
import io.github.mmm.sudoku.partitioning.Box;
import io.github.mmm.sudoku.partitioning.Column;
import io.github.mmm.sudoku.partitioning.Jigsaw;
import io.github.mmm.sudoku.partitioning.Region;
import io.github.mmm.sudoku.partitioning.Row;

/**
 * Abstract base implementation of {@link Dimension}.
 */
public abstract class AbstractDimension implements Dimension {

  /** The default {@link #getAlphabet() alphabet}. */
  protected static final List<String> ALPHABET = List.of("0", //
      "1", "2", "3", "4", "5", "6", "7", "8", "9", // 9x9
      "A", "B", "C", "D", "E", "F", // 16x16
      "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V" // 32x32
  );

  private final int base;

  private final int size;

  private final List<String> alphabet;

  /**
   * The constructor.
   *
   * @param base the {@link #getBase() base}.
   * @param size the {@link #getSize() size}.
   */
  protected AbstractDimension(int base, int size) {

    this(base, size, ALPHABET);
  }

  /**
   * The constructor.
   *
   * @param base the {@link #getBase() base}.
   * @param size the {@link #getSize() size}.
   * @param alphabet the {@link #getAlphabet() alphabet}.
   */
  protected AbstractDimension(int base, int size, List<String> alphabet) {

    super();
    if (!validate(base, size, alphabet)) {
      throw new IllegalArgumentException("base:" + base + ", size:" + size);
    }
    this.base = base;
    this.size = size;
    this.alphabet = alphabet;
  }

  private boolean validate(int b, int s, List<String> alph) {

    if ((s < 3) || (s > 64) || (s > alph.size())) {
      return false;
    }
    if (isRegular()) {
      return ((b > 1) && ((b * b) == s));
    } else {
      return ((s != 4) && (s != 9) && (s != 16) && (s != 25) && (s != 36) && (s != 49) && (s != 64));
    }
  }

  /**
   * @return the base of the {@link Sudoku} as the square-root of its {@link #getSize() size}. Or {@code -1} if not a
   *         regular dimension (e.g. a {@link Jigsaw}-{@link Sudoku} can have an irregular dimension such as a
   *         {@link #getSize() size} of {@code 5}). A regular {@link Sudoku} has {@link Box}es usesing the base as width
   *         and height and therefore must have a regular dimension.
   */
  @Override
  public int getBase() {

    return this.base;
  }

  /**
   * @return the size of this {@link Sudoku} as its width and height. So the size represents the number or
   *         {@link Column}s and {@link Row}s as well as {@link Region}s. Regular dimensions have a {@link #getBase()
   *         base} defined and then the size is the square of that base. So a classical {@link Sudoku} has a
   *         {@link #getBase() base} of {@code 3} and therefore a {@link #getSize() size} of {@code 9} with
   *         {@link #getSymbol(int) symbols} from 1 to 9. A "Kids-Sudoku" has a {@link #getBase() base} of {@code 2} and
   *         therefore a {@link #getSize() size} of {@code 4} with {@link #getSymbol(int) symbols} from 1 to 4. A hex or
   *         16x16 {@link Sudoku} has a {@link #getBase() basis} of {@code 4} and therefore a {@link #getSize() size} of
   *         {@code 16} with {@link #getSymbol(int) symbols} from 1 to F.
   */
  @Override
  public int getSize() {

    return this.size;
  }

  /**
   * @return the alphabet for {@link #getSymbol(int)}.
   */
  protected final List<String> getAlphabet() {

    return this.alphabet;
  }

  @Override
  public String getSymbol(int value) {

    if ((value < 1) || (value > this.size)) {
      throw new IndexOutOfBoundsException(value);
    }
    int i = value;
    if (this.size > 9) {
      i--;
    }
    return this.alphabet.get(i);
  }

  @Override
  public String toString() {

    return this.size + "[" + this.base + "]";
  }

  /**
   * @param alphabet the alphabet as single {@link String}.
   * @return the alphabet as {@link List} with each character as {@link String} element.
   */
  protected static List<String> toAlphabet(String alphabet) {

    List<String> result = new ArrayList<>(alphabet.length());
    for (int i = 0; i < alphabet.length(); i++) {
      result.add(Character.toString(alphabet.charAt(i)));
    }
    return Collections.unmodifiableList(result);
  }

}
