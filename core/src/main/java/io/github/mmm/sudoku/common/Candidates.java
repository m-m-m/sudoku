package io.github.mmm.sudoku.common;

/**
 * Represents any combination of {@link #has(int) candidates} encoded as bits in a single integer value. Bit encoding of
 * {@link #exclude(int) excluded candidate} is {@code 1} and for {@link #include(int) included candidate} it is stored
 * as {@code 0}.
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
    return (this.bits & (1L << (candidate - 1))) == 0;
  }

  /**
   * @param candidate the {@link #has(int) candidate} to exclude.
   * @return the new {@link Candidates} with the change or {@code this} if nothing changed.
   */
  public Candidates exclude(int candidate) {

    int bitMask = getBitMask(candidate);
    int newBits = this.bits | bitMask;
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
    int newBits = this.bits & ~bitMask;
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
   * @return the number of distinct {@link #exclude(int) excluded} {@link #has(int) candidates}. In other words the
   *         cardinality or {@link Integer#bitCount(int) bit count} of the internal {@link #getEncodedBitValue() bits}.
   */
  public int getExclusionCount() {

    return Integer.bitCount(this.bits);
  }

  /**
   * @return the internal bit-encoded value of the {@link #exclude(int) excluded} {@link #has(int) candidates}.
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
   * @return the empty {@link Candidates} instance with all {@link #has(int) candidates} possible.
   */
  public static Candidates ofAll() {

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
   * @return an {@code int}-array with the {@link #exclude(int) excluded candidates}.
   */
  public int[] toExcludedArray() {

    int size = getExclusionCount();
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
  public int[] toIncludedArray(int size) {

    int[] result = new int[size - getExclusionCount()];
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
    int size = getExclusionCount();
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

}
