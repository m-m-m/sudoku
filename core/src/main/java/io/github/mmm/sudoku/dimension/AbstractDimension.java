/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.dimension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.github.mmm.sudoku.partition.Shape;
import io.github.mmm.sudoku.partitioning.Box;
import io.github.mmm.sudoku.partitioning.Column;
import io.github.mmm.sudoku.partitioning.Row;

/**
 * Abstract base implementation of {@link Dimension}.
 */
public abstract class AbstractDimension implements Dimension {

  /** The simple {@link #getAlphabet() alphabet}. */
  protected static final List<String> ALPHABET = List.of( //
      "1", "2", "3", "4", "5", "6", "7", "8", "9", // 9x9
      "A", "B", "C", "D", "E", "F", "G", // 16x16
      "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V" // 32x32
  );

  private final int boxWidth;

  private final int boxHeigth;

  private final int boxSize;

  private final int size;

  private final List<String> alphabet;

  private final Shape column;

  private final Shape row;

  private final Shape box;

  /**
   * The constructor.
   *
   * @param boxWidth the {@link #getBoxWidth() boxWidth}.
   * @param boxHeight the {@link #getBoxHeight() boxHeight}.
   * @param size the {@link #getSize() size}.
   */
  protected AbstractDimension(int boxWidth, int boxHeight, int size) {

    this(boxWidth, boxHeight, size, null);
  }

  /**
   * The constructor.
   *
   * @param boxWidth the {@link #getBoxWidth() boxWidth}.
   * @param boxHeight the {@link #getBoxHeight() boxHeight}.
   * @param size the {@link #getSize() size}.
   * @param alphabet the {@link #getAlphabet() alphabet}.
   */
  protected AbstractDimension(int boxWidth, int boxHeight, int size, List<String> alphabet) {

    super();
    if (!validate(boxWidth, boxHeight, size, alphabet)) {
      throw new IllegalArgumentException(boxWidth + "x" + boxHeight + "=" + size);
    }
    this.boxWidth = boxWidth;
    this.boxHeigth = boxHeight;
    if (boxWidth == boxHeight) {
      this.boxSize = boxWidth;
    } else {
      this.boxSize = (int) Math.ceil(Math.sqrt(size));
    }
    this.size = size;
    if (alphabet == null) {
      String[] alph = new String[size];
      for (int i = 1; i <= size; i++) {
        alph[i - 1] = Integer.toString(i);
      }
      this.alphabet = List.of(alph);
    } else {
      this.alphabet = alphabet;
    }
    this.row = new Shape(size);
    Shape shape = null;
    for (int i = 1; i <= size; i++) {
      shape = new Shape(1, shape);
    }
    this.column = shape;
    shape = null;
    for (int i = 1; i <= boxHeight; i++) {
      shape = new Shape(boxWidth, shape);
    }
    this.box = shape;
  }

  private boolean validate(int bw, int bh, int s, List<String> alph) {

    if ((s < 3) || (s > 64) || ((alph != null) && (s > alph.size()))) {
      return false;
    }
    if ((bw * bh) != s) {
      return false;
    }
    DimensionType dimensionType = getDimensionType();
    if (dimensionType == DimensionType.SQUARE) {
      return bw == bh;
    } else if (dimensionType == DimensionType.RECTANGULAR) {
      return (bw > 1) || (bh > 1);
    } else {
      return switch (s) {
        case 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61 -> true;
        default -> false;
      };
    }
  }

  @Override
  public int getBoxWidth() {

    return this.boxWidth;
  }

  @Override
  public int getBoxHeight() {

    return this.boxHeigth;
  }

  @Override
  public int getBoxSize() {

    return this.boxSize;
  }

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
    return this.alphabet.get(value - 1);
  }

  /**
   * @return the {@link Shape} of a {@link Column}.
   */
  public Shape getColumn() {

    return this.column;
  }

  /**
   * @return the {@link Shape} of a {@link Row}.
   */
  public Shape getRow() {

    return this.row;
  }

  /**
   * @return the {@link Shape} of a {@link Box}.
   */
  public Shape getBox() {

    return this.box;
  }

  @Override
  public String toString() {

    return this.size + "[" + this.boxWidth + "x" + this.boxHeigth + "]";
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

  /**
   * @param size the {@link #getSize() size} (number of values).
   * @return the sum <code>1+2+...+size</code>.
   */
  public static int gaussianSum(int size) {

    return ((size * size) + size) / 2;
  }

}
