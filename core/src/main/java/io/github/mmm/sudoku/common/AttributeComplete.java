package io.github.mmm.sudoku.common;

import io.github.mmm.sudoku.Sudoku;
import io.github.mmm.sudoku.field.Field;
import io.github.mmm.sudoku.partition.Partition;
import io.github.mmm.sudoku.partitioning.Partitioning;
import io.github.mmm.sudoku.partitioning.Sum;

/**
 * Interface for {@link #isComplete()}.
 */
public interface AttributeComplete {

  /**
   * @return {@code true} if <em>complete</em>, {@code false} otherwise (incomplete). A {@link Partition} is complete if
   *         it has as many {@link Partition#getFieldCount() fields} as the {@link Sudoku#getSize() size} of the
   *         {@link Sudoku} or in other words for every possible {@link Field#getValue() value} there is exactly one
   *         {@link Field} in the partition. An incomplete {@link Partition} is e.g. used in a {@link Sum}Doku that has
   *         {@link Partition}s that may cover only a few {@link Field}s (e.g. 2, 3, ...) and specify the
   *         {@link Partition#getSum() sum} of the covered values. In a
   *         {@link io.github.mmm.sudoku.dimension.RegularDimension#NORMAL normal} 9x9 SumDoku a
   *         {@link Partition#getSum() sum}-{@link Partition} may still be complete if it covers 9 {@link Field}s and
   *         has a {@link Partition#getSum() sum} of {@code 45}.<br>
   *         A {@link Partitioning} is only <em>complete</em> if it covers the entire {@link Sudoku} or in other words
   *         its {@link Partitioning#getPartitionCount() partition count} is equal to the {@link Sudoku#getSize() size}
   *         of the {@link Sudoku} (and its {@link Partition}s are disjunct). Do not confuse this method with
   *         {@link Partitioning#hasAlwaysCompletePartitions()}.
   */
  default boolean isComplete() {

    return true;
  }

}
