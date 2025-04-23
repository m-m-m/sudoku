/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.common;

import java.util.Arrays;
import java.util.Set;

import io.github.mmm.sudoku.dimension.AbstractDimension;
import io.github.mmm.sudoku.dimension.Dimension;
import io.github.mmm.sudoku.partition.Partition;

/**
 * Container for all possible sum-combinations for a given {@link Dimension#getSize() size}.
 */
public class SumCombinations {

  private final int size;

  private final int halfSize;

  private final Combinations[] combinations;

  private final int[] sumMin;

  private final int[] sumMax;

  private final CombinationCollector collector;

  /**
   * The constructor.
   *
   * @param dimension the {@link Dimension}.
   */
  public SumCombinations(Dimension dimension) {

    this(dimension.getSize());
  }

  /**
   * The constructor.
   *
   * @param size the {@link Dimension#getSize() size}.
   */
  public SumCombinations(int size) {

    super();
    this.size = size;
    this.halfSize = size / 2;
    this.combinations = new Combinations[AbstractDimension.gaussianSum(size) - 2];
    this.collector = new CombinationCollector(size);
    this.sumMin = new int[size - 1];
    this.sumMax = new int[this.sumMin.length];
    int min = 1;
    int max = size;
    for (int i = 0; i < this.sumMin.length; i++) {
      min += i + 2;
      max += size - i - 1;
      this.sumMin[i] = min;
      this.sumMax[i] = max;
    }
  }

  /**
   * @param sum the {@link Partition#getSum() sum} of the values.
   * @param valueCount the number of distinct values that result in the {@code sum}.
   * @return the {@link Set} with all {@link Candidates} as combinations to build the given {@code sum}.
   */
  public Set<Candidates> getCombinations(int sum, int valueCount) {

    if ((valueCount < 2) || (valueCount > this.size)) {
      throw new IllegalArgumentException("valueCount=" + valueCount + "(size=" + this.size + ")");
    }
    if ((sum < 3) || (sum > (this.combinations.length + 3))) {
      throw new IllegalArgumentException("sum=" + sum + "(size=" + this.size + ")");
    }
    Combinations combination = this.combinations[sum - 3];
    if (combination == null) {
      combination = computeCombinations(sum);
      this.combinations[sum - 3] = combination;
    }
    while (combination != null) {
      if (combination.valueCount == valueCount) {
        return combination.combinations;
      } else if (valueCount < combination.valueCount) {
        combination = combination.next;
      } else {
        combination = null;
      }
    }
    return Set.of();
  }

  private Combinations computeCombinations(int sum) {

    Combinations current = null;
    int valueCount = 2;
    while ((valueCount <= this.size) && (sum >= this.sumMin[valueCount - 2])) {
      if (sum <= this.sumMax[valueCount - 2]) {
        current = computeCombinations(sum, current, valueCount);
      }
      valueCount++;
    }
    return current;
  }

  private Combinations computeCombinations(int sum, Combinations current, int valueCount) {

    int avg = sum / valueCount;
    if (avg <= this.halfSize) {
      computeCombinationsAsc(Candidates.ofNone(), 1, sum, valueCount);
    } else {
      computeCombinationsDsc(Candidates.ofNone(), this.size, sum, valueCount);
    }
    Set<Candidates> combinationSet = this.collector.toSet();
    if (!combinationSet.isEmpty()) {
      current = new Combinations(valueCount, combinationSet, current);
      this.collector.reset();
    }
    return current;
  }

  private void computeCombinationsAsc(Candidates combination, int value, int rest, int remainingValues) {

    if (remainingValues == 1) {
      if (rest <= this.size) {
        this.collector.add(combination.include(rest));
      }
    } else {
      int recursiveRemaining = remainingValues - 1;
      int i = value;
      while (i <= this.size) {
        int newRest = rest - i;
        if (newRest <= i) {
          break;
        }
        Candidates newCombination = combination.include(i++);
        computeCombinationsAsc(newCombination, i, newRest, recursiveRemaining);
      }
    }
  }

  private void computeCombinationsDsc(Candidates combination, int value, int rest, int remainingValues) {

    if (remainingValues == 1) {
      if (rest >= 1) {
        this.collector.add(combination.include(rest));
      }
    } else {
      int recursiveRemaining = remainingValues - 1;
      int i = value;
      while (i >= 1) {
        int newRest = rest - i;
        if ((recursiveRemaining == 1) && (newRest >= i)) {
          break;
        }
        Candidates newCombination = combination.include(i--);
        computeCombinationsDsc(newCombination, i, newRest, recursiveRemaining);
      }
    }
  }

  private static class Combinations {

    private final int valueCount;

    private final Set<Candidates> combinations;

    private final Combinations next;

    private Combinations(int valueCount, Set<Candidates> combinations, Combinations next) {

      super();
      this.valueCount = valueCount;
      this.combinations = combinations;
      this.next = next;
    }
  }

  private static class CombinationCollector {

    private static final int[] MAX_SUM_COMBINATIONS = { 2, 2, 3, 5, 8, 12, 20, 32, 58, 94, 169, 289, 526, 910, 1667,
    2934, 5448, 9686, 18084, 32540, 61108, 110780, 208960, 381676, 723354 };

    private final Candidates[] combinations;

    private int i;

    private CombinationCollector(int size) {

      super();
      // nobody will seriously want to play SumDoku larger than 16x16 so array out of bounds exception will not harm
      int maxLength = MAX_SUM_COMBINATIONS[size - 4];
      this.combinations = new Candidates[maxLength];
    }

    public void add(Candidates combination) {

      this.combinations[this.i++] = combination;
    }

    public void reset() {

      this.i = 0;
    }

    public Set<Candidates> toSet() {

      if (this.i == 0) {
        return Set.of();
      } else if (this.i == 1) {
        return Set.of(this.combinations[0]);
      } else if (this.i == 2) {
        return Set.of(this.combinations[0], this.combinations[1]);
      } else if (this.i == 3) {
        return Set.of(this.combinations[0], this.combinations[1], this.combinations[2]);
      } else if (this.i == this.combinations.length) {
        return Set.of(this.combinations);
      } else {
        return Set.of(Arrays.copyOf(this.combinations, this.i));
      }
    }
  }

}
