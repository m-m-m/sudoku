package io.github.mmm.sudoku.solution;

import io.github.mmm.sudoku.partition.Partition;
import io.github.mmm.sudoku.partitioning.Partitioning;

/**
 * {@link HintStep} applying to a specific {@link Partitioning#getPartitionCount() partition}.
 */
public abstract class HintStepPartition extends HintStep {

  /** @see #getPartition() */
  protected final Partition partition;

  /**
   * The constructor.
   *
   * @param message the {@link #getMessage() message}.
   * @param partition the {@link #getPartition() partition}.
   */
  public HintStepPartition(String message, Partition partition) {

    super(message);
    this.partition = partition;
  }

  /**
   * @return the {@link Partitioning} affected by this {@link HintStep}.
   */
  public Partition getPartition() {

    return this.partition;
  }
}
