/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.style;

import io.github.mmm.sudoku.Sudoku;
import io.github.mmm.sudoku.field.Field;
import io.github.mmm.sudoku.partition.Partition;
import io.github.mmm.sudoku.partitioning.Hyper;
import io.github.mmm.sudoku.partitioning.Partitioning;
import io.github.mmm.sudoku.partitioning.Percent;
import io.github.mmm.sudoku.partitioning.X2;

/**
 * {@link Enum} with the available types for the {@link ColorType} of the {@link io.github.mmm.sudoku.field.Field}s of a
 * {@link Partition partition}.
 *
 * @see io.github.mmm.sudoku.partitioning.Partitioning#getColorType()
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
   * Single color meaning that all {@link Partition}s of a {@link Partitioning} have the same color. This makes sense
   * for distinct {@link Partitioning#getPartitionCount() partitions} that do not "touch" each other so {@link Field}
   * neighbours never belong to different {@link Partitioning#getPartitionCount() partitions}. Examples are
   * {@link Sudoku#getType() Sudoku types} like {@link Hyper} or {@link Percent}.
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
   * Same color as the last {@link Partition}. E.g. for {@link X2}.
   */
  SAME {
    @Override
    public int getOffset(Partitioning partitioning) {

      return 0;
    }

    @Override
    public int getColor(int offset, int partitionIndex, Partitioning partitioning) {

      return offset + 1;
    }
  },

  /**
   * Alternating colors meaning that only even {@link Partition}s are colored with a single highlighting color.
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
   * Distinct colors means that {@link Partition}s are colored such that neighbour {@link Field}s from a different
   * {@link Partition} always have a different color while using as little different colors as possible. According to
   * graph-theory a maximum number of four colors are needed so that all {@link Partition}s can be uniquely identified
   * by the user via different colors.
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
   * Multiple coloring meaning that each {@link Partition} has its own unique color. E.g. for
   * {@link io.github.mmm.sudoku.Sudoku#getType() Sudoku types} like {@link io.github.mmm.sudoku.partitioning.Color}.
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
