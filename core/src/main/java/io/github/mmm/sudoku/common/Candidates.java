/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.common;

import io.github.mmm.sudoku.partition.Partition;

/**
 * Represents any combination of {@link #has(int) candidates} encoded as bits in a single integer value. Bit encoding of
 * {@link #include(int) included candidate} is {@code 1} and {@link #exclude(int) excluded candidates} are stored as
 * {@code 0}.
 */
public class Candidates {

  private static final Candidates[] CACHE = new Candidates[512];

  private final int bits;

  private Candidates() {

    this(0);
  }

  private Candidates(int bits) {

    super();
    this.bits = bits;
  }

  /**
   * @param candidate the {@link io.github.mmm.sudoku.field.Field#getValue() value} candidate in the range from
   *        {@code 1} to <code>{@link io.github.mmm.sudoku.Sudoku#getSize()}</code>. E.g. a
   *        {@link io.github.mmm.sudoku.field.Field field} may have the two candidates {@code 2} and {@code 4} but all
   *        others are already excluded meaning the the {@link io.github.mmm.sudoku.field.Field#getValue() value} can be
   *        only one of these two options.
   * @return {@code true} if the candidate is possible, {@code false} otherwise (already excluded).
   * @see io.github.mmm.sudoku.field.Field#hasCandidate(int)
   */
  public boolean has(int candidate) {

    if ((candidate < 1) || (candidate > 64)) {
      throw new IndexOutOfBoundsException(candidate);
    }
    return (this.bits & (1L << (candidate - 1))) != 0;
  }

  /**
   * @param candidate the {@link #has(int) candidate} to exclude.
   * @return the new {@link Candidates} with the change or {@code this} if nothing changed.
   */
  public Candidates exclude(int candidate) {

    int bitMask = getBitMask(candidate);
    int newBits = this.bits & ~bitMask;
    if (newBits == this.bits) {
      return this;
    }
    return of(newBits);
  }

  /**
   * @param candidate the {@link #has(int) candidate} to include.
   * @return the new {@link Candidates} with the change or {@code this} if nothing changed.
   */
  public Candidates include(int candidate) {

    int bitMask = getBitMask(candidate);
    int newBits = this.bits | bitMask;
    if (newBits == this.bits) {
      return this;
    }
    return of(newBits);
  }

  /**
   * @param candidate the {@link #has(int) candidate} to flip or toggle.
   * @return the new {@link Candidates} with the change.
   */
  public Candidates flip(int candidate) {

    int bitMask = getBitMask(candidate);
    int newBits = this.bits ^ bitMask;
    return of(newBits);
  }

  /**
   * @return the number of distinct {@link #include(int) included} {@link #has(int) candidates}. In other words the
   *         {@link Integer#bitCount(int) bit count} of the {@link #getEncodedBitValue() encoded bits}.
   */
  public int getInclusionCount() {

    return Integer.bitCount(this.bits);
  }

  /**
   * @param candidates the {@link Candidates} to intersect with.
   * @return the intersection of this {@link Candidates} with the given {@link Candidates} that only {@link #has(int)
   *         has candidates} present in both.
   */
  public Candidates intersect(Candidates candidates) {

    int newBits = this.bits & candidates.bits;
    if (newBits == this.bits) {
      return this;
    } else if (newBits == candidates.bits) {
      return candidates;
    }
    return of(newBits);
  }

  /**
   * @param candidates the {@link Candidates} to unite with.
   * @return the union of this {@link Candidates} with the given {@link Candidates} that {@link #has(int) has all
   *         candidates} present in at least one of them.
   */
  public Candidates union(Candidates candidates) {

    int newBits = this.bits | candidates.bits;
    if (newBits == this.bits) {
      return this;
    } else if (newBits == candidates.bits) {
      return candidates;
    }
    return of(newBits);
  }

  /**
   * @param candidates the {@link Candidates} to exclude.
   * @return this {@link Candidates} with the given {@link Candidates} excluded so it only {@link #has(int) has
   *         candidates} left that where not {@link #has(int) present} in the given {@link Candidates}.
   */
  public Candidates exclude(Candidates candidates) {

    int newBits = this.bits & ~candidates.bits;
    if (newBits == this.bits) {
      return this;
    }
    return of(newBits);
  }

  /**
   * @return the lowest {@link #has(int) candidate} or {@code -1} in case of no candidate.
   */
  public int getLowestCandidate() {

    if (this.bits == 0) {
      return -1;
    }
    int result = 1;
    int myBits = this.bits;
    while ((myBits & 1) == 0) {
      result++;
      myBits = myBits >> 1;
    }
    return result;
  }

  /**
   * @return {@code true} if empty (for {@link #ofNone()} and if {@link #getInclusionCount()} is {@code 0}),
   *         {@code false} otherwise.
   */
  public boolean isEmpty() {

    return this.bits == 0;
  }

  /**
   * @return the internal bit-encoded value of the {@link #include(int) included} {@link #has(int) candidates}.
   */
  public int getEncodedBitValue() {

    return this.bits;
  }

  private int getBitMask(int candidate) {

    if ((candidate < 1) || (candidate > 64)) {
      throw new IndexOutOfBoundsException(candidate);
    }
    return 1 << (candidate - 1);
  }

  /**
   * @return the empty {@link Candidates} instance with all {@link #has(int) candidates} {@link #exclude(int) excluded}.
   */
  public static Candidates ofNone() {

    return of(0);
  }

  /**
   * @param bits the internal {@link #getEncodedBitValue() bit mask}.
   * @return the {@link Candidates} instance for the given bits.
   */
  public static Candidates of(int bits) {

    if (bits < 512) {
      if (CACHE[bits] == null) {
        CACHE[bits] = new Candidates(bits);
      }
      return CACHE[bits];
    }
    return new Candidates(bits);
  }

  /**
   * @param value the single value to {@link #include(int) include}.
   * @return the resulting {@link Candidates}.
   */
  public static Candidates ofValue(int value) {

    return ofNone().include(value);
  }

  /**
   * This method is inefficient and mainly for testing.
   *
   * @param values the values to {@link #include(int) include}.
   * @return the resulting {@link Candidates}.
   */
  public static Candidates ofValues(int... values) {

    Candidates candidates = ofNone();
    for (int value : values) {
      candidates = candidates.include(value);
    }
    return candidates;
  }

  /**
   * @return an {@code int}-array with the {@link #include(int) included candidates}.
   */
  public int[] toIncludedArray() {

    int size = getInclusionCount();
    int[] result = new int[size];
    int data = this.bits;
    int i = 0;
    int value = 1;
    while (data > 0) {
      if ((data & 1) == 1) {
        result[i++] = value;
      }
      value++;
      data = data >> 1;
    }
    return result;
  }

  /**
   * @param size the value {@link io.github.mmm.sudoku.Sudoku#getSize() size}.
   * @return an {@code int}-array with the {@link #exclude(int) excluded candidates}.
   */
  public int[] toExcludedArray(int size) {

    int[] result = new int[size - getInclusionCount()];
    int data = this.bits;
    int i = 0;
    int value = 1;
    while (i < result.length) {
      if ((data & 1) == 0) {
        result[i++] = value;
      }
      value++;
      data = data >> 1;
    }
    return result;
  }

  @Override
  public int hashCode() {

    return this.bits;
  }

  @Override
  public boolean equals(Object obj) {

    if (obj == this) {
      return true;
    } else if ((obj == null) || (obj.getClass() != getClass())) {
      return false;
    }
    Candidates other = (Candidates) obj;
    return this.bits == other.bits;
  }

  @Override
  public String toString() {

    int data = this.bits;
    if (data == 0) {
      return "{}";
    }
    int size = getInclusionCount();
    StringBuilder sb = new StringBuilder(2 + size * 2);
    int value = 1;
    char separator = '{';
    while (data > 0) {
      if ((data & 1) == 1) {
        sb.append(separator);
        sb.append(value);
        separator = ',';
      }
      value++;
      data = data >> 1;
    }
    sb.append('}');
    return sb.toString();
  }

  /**
   * @return the {@link String} representation if this {@link Candidates} are considered as {@link Partition#getSum()
   *         sum} combination.
   */
  public String toSumString() {

    int data = this.bits;
    if (data == 0) {
      return "=0";
    }
    int size = getInclusionCount();
    int sum = 0;
    StringBuilder sb = new StringBuilder(2 + size * 2);
    int value = 1;
    while (data > 0) {
      if ((data & 1) == 1) {
        sb.append(value);
        sum += value;
        if (data > 1) {
          sb.append('+');
        }
      }
      value++;
      data = data >> 1;
    }
    sb.append('=');
    sb.append(sum);
    return sb.toString();
  }

}
