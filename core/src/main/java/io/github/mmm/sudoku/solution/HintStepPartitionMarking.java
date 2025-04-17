package io.github.mmm.sudoku.solution;

import io.github.mmm.sudoku.field.Field;
import io.github.mmm.sudoku.partition.Partition;

/**
 * {@link HintStepPartition} to mark a specific partition to visualise a {@link Hint}.
 */
public class HintStepPartitionMarking extends HintStepPartition {

  /**
   * The constructor.
   *
   * @param message the {@link #getMessage() message}.
   * @param partition the {@link #getPartition() partition}.
   */
  public HintStepPartitionMarking(String message, Partition partition) {

    super(message, partition);
  }

  @Override
  public void prepare() {

    for (Field field : this.partition) {
      field.setMarked(true);
    }
  }

}
