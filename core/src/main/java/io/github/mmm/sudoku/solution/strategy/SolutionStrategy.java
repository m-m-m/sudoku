/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.solution.strategy;

import java.util.List;

import io.github.mmm.sudoku.common.AttributeDifficulty;
import io.github.mmm.sudoku.common.Candidates;
import io.github.mmm.sudoku.field.Field;
import io.github.mmm.sudoku.field.FieldGroup;
import io.github.mmm.sudoku.partition.Partition;
import io.github.mmm.sudoku.solution.Hint;
import io.github.mmm.sudoku.solution.HintStep;
import io.github.mmm.sudoku.solution.HintStepFieldExcludeCandidate;
import io.github.mmm.sudoku.solution.HintStepFieldSetValue;
import io.github.mmm.sudoku.solution.HintStepFieldsExcludeCandidates;
import io.github.mmm.sudoku.solution.HintStepFieldsMarking;
import io.github.mmm.sudoku.solution.HintStepPartitionMarking;
import io.github.mmm.sudoku.solution.Solver;

/**
 * Strategy to find a {@link Hint}.
 */
public abstract class SolutionStrategy implements Solver, AttributeDifficulty, Comparable<SolutionStrategy> {

  /**
   * @param steps the {@link HintStep}s.
   * @return the {@link Hint}.
   */
  protected Hint hint(HintStep... steps) {

    assert (steps != null);
    assert (steps.length > 0);
    return new Hint(getDifficulty(), List.of(steps));
  }

  /**
   * @param partition the {@link Partition} to mark.
   * @return the {@link HintStepPartitionMarking}.
   */
  protected HintStepPartitionMarking mark(Partition partition) {

    return new HintStepPartitionMarking(getName(), partition);
  }

  /**
   * @param fields the {@link Field}s to mark.
   * @return the {@link HintStepFieldsMarking}.
   */
  protected HintStepFieldsMarking mark(Field... fields) {

    return new HintStepFieldsMarking(getName(), fields);
  }

  /**
   * @param fields the {@link Field}s to mark.
   * @return the {@link HintStepFieldsMarking}.
   */
  protected HintStepFieldsMarking mark(Iterable<Field> fields) {

    return mark(FieldGroup.toArray(fields));
  }

  /**
   * @param field the {@link Field} to {@link Field#setValue(int) set}.
   * @param value the {@link Field#getValue() value} to {@link Field#setValue(int) set}.
   * @return the {@link HintStepFieldSetValue}.
   */
  protected HintStepFieldSetValue setValue(Field field, int value) {

    return new HintStepFieldSetValue(getName(), field, value);
  }

  /**
   * @param field the {@link Field} where to {@link Field#excludeCandidate(int) exclude}.
   * @param candidate the {@link Field#hasCandidate(int) candidate} to {@link Field#excludeCandidate(int) exclude}.
   * @return the {@link HintStepFieldExcludeCandidate}.
   */
  protected HintStepFieldExcludeCandidate exclude(Field field, int candidate) {

    return new HintStepFieldExcludeCandidate(getName(), field, candidate);
  }

  /**
   * @param fields the {@link Field} where to {@link Field#excludeCandidate(int) exclude}.
   * @param candidates the {@link Candidates} to {@link Candidates#exclude(Candidates) exclude}.
   * @return the {@link HintStepFieldsExcludeCandidates}.
   */
  protected HintStepFieldsExcludeCandidates exclude(Iterable<Field> fields, Candidates candidates) {

    return new HintStepFieldsExcludeCandidates(getName(), FieldGroup.toArray(fields), candidates);
  }

  @Override
  public int compareTo(SolutionStrategy other) {

    if (other == null) {
      return -1;
    }
    return getDifficulty() - other.getDifficulty();
  }

  /**
   * @return the name of this {@link SolutionStrategy} and its potential finding (e.g. "hidden single" or "x-wing").
   */
  public abstract String getName();

}
