/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.solution.strategy;

import io.github.mmm.sudoku.common.Candidates;
import io.github.mmm.sudoku.field.Field;
import io.github.mmm.sudoku.partition.Partition;

/**
 * {@link SolutionStrategy} to find a triplet.
 */
public abstract class SolutionStrategyTuple extends SolutionStrategyByPartition {

  final int tupleSize;

  /**
   * The constructor.
   *
   * @param tupleSize the {@link #getTupleSize() tuple size}.
   */
  protected SolutionStrategyTuple(int tupleSize) {

    super();
    assert (tupleSize >= 2);
    this.tupleSize = tupleSize;
  }

  /**
   * @return the tuple size ({@code 2} for pair, {@code 3} for triplet, etc.).
   */
  protected int getTupleSize() {

    return this.tupleSize;
  }

  Field[] getFields(Partition partition, Candidates fieldIndexes) {

    return getFields(partition.toArray(), fieldIndexes);
  }

  Field[] getFields(Field[] fields, Candidates fieldIndexes) {

    int size = fieldIndexes.getInclusionCount();
    Field[] result = new Field[size];
    for (int i = 1; i <= size; i++) {
      int fieldIndex = fieldIndexes.getCandidate(i);
      result[i - 1] = fields[fieldIndex - 1];
    }
    return result;
  }

}
