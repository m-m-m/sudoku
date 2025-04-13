/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.history;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

import io.github.mmm.sudoku.Sudoku;
import io.github.mmm.sudoku.event.ChangeAware;
import io.github.mmm.sudoku.event.SudokuChangeEvent;

/**
 * A set of {@link SudokuChangeEvent}s for an operation at a given {@link #getTimestamp() timestamp} to a {@link Sudoku}
 * for {@link #undo()}/{@link #redo()} history.
 */
public class ChangeSet implements ChangeAware {

  private final Instant timestamp;

  private final ChangeSet previous;

  private final List<SudokuChangeEvent<?>> changes;

  private ChangeSet next;

  /**
   * The constructor.
   *
   * @param changes the {@link #getChange(int) changes}.
   * @param previous the {@link #getPrevious() previous} {@link ChangeSet}.
   */
  public ChangeSet(List<SudokuChangeEvent<?>> changes, ChangeSet previous) {

    this(changes, previous, Instant.now());
  }

  /**
   * The constructor.
   *
   * @param changes the {@link #getChange(int) changes}.
   * @param previous the {@link #getPrevious() previous} {@link ChangeSet}.
   * @param timestamp the explicit {@link #getTimestamp() timestamp}.
   */
  public ChangeSet(List<SudokuChangeEvent<?>> changes, ChangeSet previous, Instant timestamp) {

    super();
    Objects.requireNonNull(changes);
    Objects.requireNonNull(timestamp);
    this.changes = changes;
    this.previous = previous;
    this.timestamp = timestamp;
    if (this.previous != null) {
      this.previous.next = this;
    }
  }

  /**
   * @return the {@link Instant} when this {@link ChangeSet} was done.
   */
  public Instant getTimestamp() {

    return this.timestamp;
  }

  /**
   * @param i the index of the requested {@link SudokuChangeEvent} in the range from {@code 0} to
   *        <code>{@link #getChangeCount()}-1</code>.
   * @return the requested {@link SudokuChangeEvent}.
   */
  public SudokuChangeEvent<?> getChange(int i) {

    return this.changes.get(i);
  }

  /**
   * @return the number of {@link #getChange(int) changes}. Should be positive.
   */
  public int getChangeCount() {

    return this.changes.size();
  }

  /**
   * @return the previous {@link ChangeSet} or {@code null} if not available (initial state is reached and no further
   *         undo possible).
   */
  public ChangeSet getPrevious() {

    return this.previous;
  }

  /**
   * @return the next {@link ChangeSet} or {@code null} if not available (final state with no further redo possible).
   */
  public ChangeSet getNext() {

    return this.next;
  }

  @Override
  public void undo() {

    for (SudokuChangeEvent<?> event : this.changes) {
      event.undo();
    }
  }

  @Override
  public void redo() {

    for (SudokuChangeEvent<?> event : this.changes) {
      event.redo();
    }
  }

}
