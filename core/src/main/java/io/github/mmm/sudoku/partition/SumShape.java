package io.github.mmm.sudoku.partition;

import io.github.mmm.sudoku.dimension.AbstractDimension;

/**
 * Represents the shape of a special {@link Partition}.
 *
 * @see io.github.mmm.sudoku.partitioning.Jigsaw
 * @see io.github.mmm.sudoku.partitioning.Sum
 */
public class SumShape extends Shape {

  private final int sum;

  /**
   * The constructor.
   *
   * @param count the {@link #getCount() count}.
   * @param next the {@link #getNext() next}.
   * @param sum the {@link #getSum() sum}.
   */
  public SumShape(int count, Shape next, int sum) {

    super(0, count, next);
    this.sum = sum;
    assert (sum > 0);
    assert (sum >= AbstractDimension.gaussianSum(getFieldCount()));
  }

  @Override
  public int getSum() {

    return this.sum;
  }

}
