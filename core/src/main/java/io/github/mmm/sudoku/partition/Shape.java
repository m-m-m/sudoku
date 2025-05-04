package io.github.mmm.sudoku.partition;

import io.github.mmm.sudoku.common.AttributeSum;
import io.github.mmm.sudoku.field.Field;

/**
 * Represents the shape of a special {@link Partition}.
 *
 * @see io.github.mmm.sudoku.partitioning.Jigsaw
 * @see io.github.mmm.sudoku.partitioning.Sum
 */
public class Shape implements AttributeSum {

  private final int offset;

  private final int count;

  private final Shape next;

  /**
   * The constructor.
   *
   * @param offset the {@link #getOffset() offset}.
   * @param count the {@link #getCount() count}.
   * @param next the {@link #getNext() next}.
   */
  public Shape(int offset, int count, Shape next) {

    super();
    if (count <= 0) {
      throw new IllegalArgumentException("count:" + count);
    }
    this.offset = offset;
    this.count = count;
    this.next = next;
  }

  /**
   * The constructor.
   *
   * @param count the {@link #getCount() count}.
   * @param next the {@link #getNext() next}.
   */
  public Shape(int count, Shape next) {

    this(0, count, next);
  }

  /**
   * The constructor.
   *
   * @param count the {@link #getCount() count}.
   */
  public Shape(int count) {

    this(0, count, null);
  }

  /**
   * @return the offset to add (shift) to the right (if negative to the left) relative to the initial position (start
   *         column). Initial offset must be {@code 0}.
   */
  public int getOffset() {

    return this.offset;
  }

  /**
   * @return the number of {@link Field}s to to the right ({@code 1} means only the current field, {@code 2} means the
   *         current and the next right field). Must be positive.
   */
  public int getCount() {

    return this.count;
  }

  /**
   * @return the next {@link Shape} info to add at the bottom.
   */
  public Shape getNext() {

    return this.next;
  }

  @Override
  public int getSum() {

    return -1;
  }

  /**
   * @return the number of {@link Field}s covered by this {@link Shape}.
   */
  public int getFieldCount() {

    int result = 0;
    Shape current = this;
    while (current != null) {
      result += current.count;
      current = current.next;
    }
    return result;
  }

  @Override
  public String toString() {

    int maxWidth = 1;
    int maxWidthOffset = 0;
    int minOffset = 0;
    int rows = 0;
    Shape current = this;
    while (current != null) {
      if (current.offset < minOffset) {
        minOffset = current.offset;
      }
      int width = current.count;
      if (width > maxWidth) {
        maxWidth = width;
        maxWidthOffset = current.offset;
      }
      current = current.next;
      rows++;
    }
    maxWidth = maxWidth - minOffset + maxWidthOffset;
    StringBuilder sb = new StringBuilder((maxWidth + 1) * rows);
    current = this;
    while (current != null) {
      int indent = current.offset - minOffset;
      while (indent > 0) {
        sb.append(' ');
        indent--;
      }
      int c = current.count;
      while (c > 0) {
        sb.append('X');
        c--;
      }
      sb.append('\n');
      current = current.next;
    }
    return sb.toString();
  }

}
