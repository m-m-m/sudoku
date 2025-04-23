package io.github.mmm.sudoku.solution;

import io.github.mmm.sudoku.field.Field;
import io.github.mmm.sudoku.partition.Partition;
import io.github.mmm.sudoku.solution.strategy.SolutionStrategy;

/**
 * {@link HintStepPartition} to mark a specific partition to visualise a {@link Hint}.
 */
public class HintStepPartitionMarking extends HintStepPartition {

  /**
   * The constructor.
   *
   * @param partition the {@link #getPartition() partition}.
   * @param name the {@link SolutionStrategy#getName() hint name}.
   */
  public HintStepPartitionMarking(String name, Partition partition) {

    super(name, partition);
  }

  @Override
  protected void createMessage(StringBuilder sb, boolean first) {

    super.createMessage(sb, first);
    sb.append(" in ");
    sb.append(this.partition.getPartitioning().getName());
    sb.append(' ');
    sb.append(this.partition.getIndex());
    sb.append('.');
  }

  @Override
  public void prepare() {

    for (Field field : this.partition) {
      field.setMarked(true);
    }
  }

}
