/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.partitioning;

import io.github.mmm.sudoku.Sudoku;
import io.github.mmm.sudoku.partition.Partition;

/**
 * {@link Partitioning} that is not fixed (build-in) in {@link Sudoku} but can be configured.
 */
public abstract class FlexiblePartitioning extends Partitioning {

  /**
   * The constructor.
   *
   * @param sudoku the {@link #getSudoku() sudoku}.
   * @param index the {@link #getIndex() index}.
   * @param function the {@link PartitionFunction}.
   */
  public FlexiblePartitioning(Sudoku sudoku, int index, PartitionFunction function) {

    super(sudoku, index, function);
  }

  /**
   * The constructor.
   *
   * @param sudoku the {@link #getSudoku() sudoku}.
   * @param index the {@link #getIndex() index}.
   * @param partitions the pre-defined {@link #getPartition(int) partitions}.
   */
  protected FlexiblePartitioning(Sudoku sudoku, int index, Partition[] partitions) {

    super(sudoku, index, partitions);
  }

  /**
   * Factory of a specific type of {@link FlexiblePartitioning}.
   */
  @FunctionalInterface
  public interface PartitioningFactory {

    /**
     * @param sudoku the {@link Sudoku}.
     * @param index the {@link #getIndex() index}.
     * @return the new {@link FlexiblePartitioning} for the {@link Sudoku}.
     */
    FlexiblePartitioning create(Sudoku sudoku, int index);

  }

  /**
   * Factory of a specific type of {@link Region}.
   */
  public interface RegionFactory extends PartitioningFactory {

    @Override
    Region create(Sudoku sudoku, int index);
  }

  /**
   * Factory of a specific type of {@link Layer}.
   */
  public interface LayerFactory extends PartitioningFactory {

    @Override
    Layer create(Sudoku sudoku, int index);
  }

}