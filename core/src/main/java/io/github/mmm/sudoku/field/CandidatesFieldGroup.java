/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.field;

import io.github.mmm.sudoku.common.Candidates;

/**
 * A {@link IndexedFieldGroup} containing the {@link Field}s that all share the same {@link #getCandidates()
 * candidates}.
 *
 * @see Field#getCandidates()
 */
public interface CandidatesFieldGroup extends IndexedFieldGroup {

  /**
   * @return the {@link Candidates} shared by all {@link Field}s in this group.
   */
  Candidates getCandidates();

  /**
   * @return the next {@link CandidatesFieldGroup} or {@code null} if this is the last node.
   */
  CandidatesFieldGroup geteNext();

}
