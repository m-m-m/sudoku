package io.github.mmm.sudoku.field;

/**
 * A group of {@link Field}s that all share the same {@link #getCandidate() candidate}.
 *
 * @see io.github.mmm.sudoku.partition.PartitionMap#getByTuples(int)
 */
public interface AggregatedFieldGroup extends FieldGroup {

  /**
   * @return the {@link Field#hasCandidate(int) candidate} shared by all {@link Field}s in this group.
   */
  int getCandidate();

}
