/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.style;

import io.github.mmm.sudoku.child.Partitioning;

/**
 * {@link Enum} with the available types for the {@link ColorType} of the {@link io.github.mmm.sudoku.child.Field}s of a
 * {@link io.github.mmm.sudoku.child.Partitioning#getPartitionCount() partition}.
 *
 * @see io.github.mmm.sudoku.child.Partitioning#getColorType()
 */
public enum ColorType {

  /** No colors (default). */
  NONE {
    @Override
    public int getOffset(Partitioning partitioning) {

      return 0;
    }

    @Override
    public int getColor(int offset, int partitionIndex, Partitioning partitioning) {

      return 0;
    }
  },

  /**
   * Single color meaning that all {@link io.github.mmm.sudoku.child.Partitioning#getPartitionCount() partitions} of a
   * {@link io.github.mmm.sudoku.child.Partitioning} have the same color. This makes sense for distinct
   * {@link io.github.mmm.sudoku.child.Partitioning#getPartitionCount() partitions} that do not "touch" each other so
   * {@link io.github.mmm.sudoku.child.Field} neighbours never belong to different
   * {@link io.github.mmm.sudoku.child.Partitioning#getPartitionCount() partitions}. Examples are
   * {@link io.github.mmm.sudoku.Sudoku#getType() Sudoku types} like Hyper or Percent.
   */
  SINGLE {
    @Override
    public int getOffset(Partitioning partitioning) {

      return 1;
    }

    @Override
    public int getColor(int offset, int partitionIndex, Partitioning partitioning) {

      return offset + 1;
    }
  },

  /**
   * Alternating colors meaning that only even {@link io.github.mmm.sudoku.child.Partitioning#getPartitionCount()
   * partitions} are colored with a single highlighting color.
   */
  ALTERNATE {
    @Override
    public int getOffset(Partitioning partitioning) {

      return 1;
    }

    @Override
    public int getColor(int offset, int partitionIndex, Partitioning partitioning) {

      if ((partitionIndex % 2) == 1) {
        return 0;
      } else {
        return offset + 1;
      }
    }
  },

  /**
   * Distinct colors means that {@link io.github.mmm.sudoku.child.Partitioning#getPartitionCount() partitions} are
   * colored such that neighbour {@link io.github.mmm.sudoku.child.Field}s from a different
   * {@link io.github.mmm.sudoku.child.Partitioning#getPartitionCount() partition} always have a different color while
   * using as little different colors as possible. According to graph-theory a maximum number of four colors are needed
   * so that all {@link io.github.mmm.sudoku.child.Partitioning#getPartitionCount() partitions} can be uniquely
   * identified by the user via different colors.
   */
  DISTINCT {

    @Override
    public int getOffset(Partitioning partitioning) {

      return 4;
    }

    @Override
    public int getColor(int offset, int partitionIndex, Partitioning partitioning) {

      // TODO
      return offset + 1 + (partitionIndex % 4);
    }
  },

  /**
   * Multiple coloring meaning that each {@link io.github.mmm.sudoku.child.Partitioning#getPartitionCount() partition}
   * has its own unique color. Examples are {@link io.github.mmm.sudoku.Sudoku#getType() Sudoku types} like Color that
   * have a fourth color {@link io.github.mmm.sudoku.child.Partitioning} or jigsaw where the
   * {@link io.github.mmm.sudoku.child.Region regions} are irregular (not square {@link io.github.mmm.sudoku.child.Box
   * boxes}).
   */
  MULTIPLE {

    @Override
    public int getOffset(Partitioning partitioning) {

      return partitioning.getSudoku().getSize();
    }

    @Override
    public int getColor(int offset, int partitionIndex, Partitioning partitioning) {

      return offset + partitionIndex;
    }
  };

  /**
   * @param partitioning the owning {@link Partitioning}.
   * @return the offset as the (mmximum) number of colors reserved for this {@link ColorType}.
   */
  public abstract int getOffset(Partitioning partitioning);

  /**
   * @param offset the {@link #getOffset(Partitioning) offset} from previous {@link Partitioning}s or otherwise
   *        {@code 0}.
   * @param partitionIndex the index of the field in the partition.
   * @param partitioning the owning {@link Partitioning}.
   * @return the index of the color to use or {@code 0} for no explicit highlight color.
   */
  public abstract int getColor(int offset, int partitionIndex, Partitioning partitioning);

}
