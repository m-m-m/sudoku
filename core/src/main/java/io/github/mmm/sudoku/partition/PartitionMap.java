package io.github.mmm.sudoku.partition;

import io.github.mmm.sudoku.Sudoku;
import io.github.mmm.sudoku.common.AttributeModificationCounter;
import io.github.mmm.sudoku.common.Candidates;
import io.github.mmm.sudoku.field.AggregatedFieldGroup;
import io.github.mmm.sudoku.field.CandidatesFieldGroup;
import io.github.mmm.sudoku.field.Field;
import io.github.mmm.sudoku.solution.Hint;

/**
 * A {@link PartitionMap} is computed from a {@link Partition} to compute a {@link Hint}. It is a temporary index for
 * the current state of the {@link Partition} that becomes obsolete if the {@link Sudoku} is updated.
 */
public interface PartitionMap extends AttributeModificationCounter {

  /**
   * @return the owning {@link Partition}.
   * @see Partition#getPartitionMap()
   */
  Partition getPartition();

  /**
   * @param candidate the {@link Field#hasCandidate(int) candidate}.
   * @return the {@link AggregatedFieldGroup} with all {@link Field}s having this {@link Field#hasCandidate(int)
   *         candidate}.
   */
  AggregatedFieldGroup getByCandidate(int candidate);

  /**
   * @param count the {@link Candidates#getInclusionCount() inclusion count}. Should be in the range from {@code 2} to
   *        <code>{@link Sudoku#getSize()}-1</code>.
   * @return an {@link Iterable} of {@link CandidatesFieldGroup}s per {@link CandidatesFieldGroup#getCandidates()
   *         candidates}.
   */
  Iterable<CandidatesFieldGroup> getByCandidatesCount(int count);

  /**
   * @param count the {@link Candidates#getInclusionCount() inclusion count}. Should be in the range from {@code 2} to
   *        <code>{@link Sudoku#getSize()}-1</code>.
   * @return the first {@link CandidatesFieldGroup}s per {@link CandidatesFieldGroup#getCandidates() candidates} or
   *         {@code null} if none exists.
   */
  CandidatesFieldGroup getFirstByCandidatesCount(int count);

}
