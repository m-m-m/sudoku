/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.style;

/**
 * Type to represent a style to mark borders of a {@link io.github.mmm.sudoku.child.Field} as thick. Used to mark the
 * outline of a {@link io.github.mmm.sudoku.child.Region}.
 */
public final class BorderStyle extends Style {

  private static final int BIT_TOP = 0;

  private static final int BIT_RIGHT = 1;

  private static final int BIT_BOTTOM = 2;

  private static final int BIT_LEFT = 3;

  private static final String TRBL = "trbl";

  private static final int[] MASK = { 0b00001, 0b00010, 0b00100, 0b01000 };

  private static final BorderStyle[] MAP = new BorderStyle[4 * 4];

  /** */
  public static BorderStyle EMPTY = of(0);

  private final int trbl;

  private BorderStyle(int trbl) {

    super(createStyle(trbl));
    this.trbl = trbl;
  }

  private static String createStyle(int trbl) {

    if (trbl == 0) {
      return null;
    }
    StringBuilder sb = new StringBuilder("border-");
    for (int i = 0; i < TRBL.length(); i++) {
      if ((trbl & MASK[i]) != 0) {
        sb.append(TRBL.charAt(i));
      }
    }
    return sb.toString();
  }

  /**
   * @return {@code true} if the top border is thick, {@code false} otherwise.
   */
  public boolean isTop() {

    return hasBit(BIT_TOP);
  }

  /**
   * @return this {@link BorderStyle} with {@link #isTop() thick top border}.
   */
  public BorderStyle withTop() {

    return withTop(true);
  }

  /**
   * @return this {@link BorderStyle} without {@link #isTop() thick top border}.
   */
  public BorderStyle withoutTop() {

    return withTop(false);
  }

  /**
   * @param thick the new value of {@link #isTop()}.
   * @return this {@link BorderStyle} with the given value for {@link #isTop()}.
   */
  public BorderStyle withTop(boolean thick) {

    return with(BIT_TOP, thick);
  }

  /**
   * @return {@code true} if the right border is thick, {@code false} otherwise.
   */
  public boolean isRight() {

    return hasBit(BIT_RIGHT);
  }

  /**
   * @return this {@link BorderStyle} with {@link #isRight() thick right border}.
   */
  public BorderStyle withRight() {

    return withRight(true);
  }

  /**
   * @return this {@link BorderStyle} without {@link #isRight() thick right border}.
   */
  public BorderStyle withoutRight() {

    return withRight(false);
  }

  /**
   * @param thick the new value of {@link #isRight()}.
   * @return this {@link BorderStyle} with the given value for {@link #isRight()}.
   */
  public BorderStyle withRight(boolean thick) {

    return with(BIT_RIGHT, thick);
  }

  /**
   * @return {@code true} if the bottom border is thick, {@code false} otherwise.
   */
  public boolean isBottom() {

    return hasBit(BIT_BOTTOM);
  }

  /**
   * @return this {@link BorderStyle} with {@link #isBottom() thick bottom border}.
   */
  public BorderStyle withBottom() {

    return withBottom(true);
  }

  /**
   * @return this {@link BorderStyle} without {@link #isBottom() thick bottom border}.
   */
  public BorderStyle withoutBottom() {

    return withBottom(false);
  }

  /**
   * @param thick the new value of {@link #isBottom()}.
   * @return this {@link BorderStyle} with the given value for {@link #isBottom()}.
   */
  public BorderStyle withBottom(boolean thick) {

    return with(BIT_BOTTOM, thick);
  }

  /**
   * @return {@code true} if the left border is thick, {@code false} otherwise.
   */
  public boolean isLeft() {

    return hasBit(BIT_LEFT);
  }

  /**
   * @return this {@link BorderStyle} with {@link #isLeft() thick left border}.
   */
  public BorderStyle withLeft() {

    return withLeft(true);
  }

  /**
   * @return this {@link BorderStyle} without {@link #isLeft() thick left border}.
   */
  public BorderStyle withoutLeft() {

    return withLeft(false);
  }

  /**
   * @param thick the new value of {@link #isLeft()}.
   * @return this {@link BorderStyle} with the given value for {@link #isLeft()}.
   */
  public BorderStyle withLeft(boolean thick) {

    return with(BIT_LEFT, thick);
  }

  private boolean hasBit(int i) {

    return (this.trbl & MASK[i]) != 0;
  }

  private BorderStyle with(int border, boolean thick) {

    int newTrbl;
    int bit = MASK[border];
    if (thick) {
      newTrbl = this.trbl | bit;
    } else {
      newTrbl = this.trbl & ~bit;
    }
    return of(newTrbl);
  }

  private static BorderStyle of(int trblMask) {

    BorderStyle result = MAP[trblMask];
    if (result == null) {
      result = new BorderStyle(trblMask);
      MAP[trblMask] = result;
    }
    return result;
  }

}
