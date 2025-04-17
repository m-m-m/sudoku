/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.style;

/**
 * {@link Enum} with the available types for the borders of a
 * {@link io.github.mmm.sudoku.partitioning.Partitioning#getPartitionCount() partition}.
 */
public enum BorderType {

  /** No bordering (default). */
  NONE,

  /**
   * Thick bordering meaning that each {@link io.github.mmm.sudoku.child.Partitioning#getPartitionCount() partition} has
   * a thick border to be visualised.
   */
  THICK

}
