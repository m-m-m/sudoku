package io.github.mmm.sudoku.partition;

import io.github.mmm.sudoku.Sudoku;
import io.github.mmm.sudoku.common.AttributeModificationCounter;
import io.github.mmm.sudoku.field.AggregatedFieldGroup;
import io.github.mmm.sudoku.field.Field;
import io.github.mmm.sudoku.field.FieldGroup;
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
   * @param n the tuple size. E.g. {@code 1} to find (hidden) singles, {@code 2} for pairs or {@code 3} for tripplets.
   * @return an {@link Iterable} of {@link AggregatedFieldGroup}s per {@link AggregatedFieldGroup#getCandidate()
   *         candidate} {@link Field#getValue() value}.
   */
  Iterable<AggregatedFieldGroup> getByTuples(int n);

  /**
   * @param candidate the {@link Field#hasCandidate(int) candidate}.
   * @return the {@link AggregatedFieldGroup} with all {@link Field}s having this {@link Field#hasCandidate(int)
   *         candidate}.
   */
  AggregatedFieldGroup getByCandidate(int candidate);

  /**
   * @param n the number of remaining {@link Field#hasCandidate(int) candidates}. E.g. {@code 1} to find naked (obvious)
   *        singles, {@code 2} to find naked pairs, etc.
   * @return the {@link FieldGroup} with the first naked tuple.
   */
  FieldGroup getNakedTuple(int n);

}
