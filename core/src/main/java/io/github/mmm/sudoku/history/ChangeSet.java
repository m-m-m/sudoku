/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.history;

import java.time.Instant;
import java.util.Objects;

import io.github.mmm.sudoku.Sudoku;

/**
 * A set of {@link ChangeEvent}s for an operation at a given {@link #getTimestamp() timestamp} to a {@link Sudoku} for
 * {@link #undo()}/{@link #redo()} history.
 */
public class ChangeSet implements ChangeAware {

  private final Instant timestamp;

  private final ChangeSet previous;

  private final ChangeEvent change;

  private ChangeSet next;

  /**
   * The constructor.
   *
   * @param change the {@link #getChange() change}.
   * @param previous the {@link #getPrevious() previous} {@link ChangeSet}.
   */
  public ChangeSet(ChangeEvent change, ChangeSet previous) {

    this(change, previous, Instant.now());
  }

  /**
   * The constructor.
   *
   * @param change the {@link #getChange() change}.
   * @param previous the {@link #getPrevious() previous} {@link ChangeSet}.
   * @param timestamp the explicit {@link #getTimestamp() timestamp}.
   */
  public ChangeSet(ChangeEvent change, ChangeSet previous, Instant timestamp) {

    super();
    Objects.requireNonNull(change);
    Objects.requireNonNull(timestamp);
    this.change = change;
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
   * @return the (first) {@link ChangeEvent}.
   * @see ChangeEventSetValue#next()
   */
  public ChangeEvent getChange() {

    return this.change;
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

    ChangeEvent current = this.change;
    do {
      current.undo();
      current = current.next();
    } while (current != null);
  }

  @Override
  public void redo() {

    ChangeEvent current = this.change;
    do {
      current.redo();
      current = current.next();
    } while (current != null);
  }

}
