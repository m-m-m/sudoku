package io.github.mmm.sudoku.solution;

import io.github.mmm.sudoku.child.Field;
import io.github.mmm.sudoku.child.Partitioning;

/**
 * {@link HintStepPartition} to mark a specific partition to visualise a {@link Hint}.
 */
public class HintStepPartitionMarking extends HintStepPartition {

  /**
   * The constructor.
   *
   * @param message the {@link #getMessage() message}.
   * @param partitioning the {@link #getPartitioning() partitioning}.
   * @param partitionIndex the {@link #getPartitionIndex() partition index}.
   */
  public HintStepPartitionMarking(String message, Partitioning partitioning, int partitionIndex) {

    super(message, partitioning, partitionIndex);
  }

  @Override
  public void prepare() {

    int size = this.partitioning.getSudoku().getSize();
    for (int fieldIndex = 1; fieldIndex <= size; fieldIndex++) {
      Field field = this.partitioning.getPartitionField(this.partitionIndex, fieldIndex);
      field.setMarked(true);
    }
  }

}
