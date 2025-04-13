package io.github.mmm.sudoku.solution;

import io.github.mmm.sudoku.child.Partitioning;

/**
 * {@link HintStep} applying to a specific {@link Partitioning#getPartitionCount() partition}.
 */
public abstract class HintStepPartition extends HintStep {

  /** @see #getPartitioning() */
  protected final Partitioning partitioning;

  /** @see #getPartitionIndex() */
  protected final int partitionIndex;

  /**
   * The constructor.
   *
   * @param message the {@link #getMessage() message}.
   * @param partitioning the {@link #getPartitioning() partitioning}.
   * @param partitionIndex the {@link #getPartitionIndex() partition index}.
   */
  public HintStepPartition(String message, Partitioning partitioning, int partitionIndex) {

    super(message);
    this.partitioning = partitioning;
    this.partitionIndex = partitionIndex;
  }

  /**
   * @return the {@link Partitioning} affected by this {@link HintStep}.
   */
  public Partitioning getPartitioning() {

    return this.partitioning;
  }

  /**
   * @return the {@link Partitioning#getPartitionField(int, int) partition index}.
   * @see io.github.mmm.sudoku.child.Field#getPartitionIndex(Partitioning)
   */
  public int getPartitionIndex() {

    return this.partitionIndex;
  }
}
