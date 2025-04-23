/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.partitioning;

import io.github.mmm.sudoku.Sudoku;
import io.github.mmm.sudoku.dimension.Dimension;
import io.github.mmm.sudoku.dimension.DimensionType;
import io.github.mmm.sudoku.field.Field;
import io.github.mmm.sudoku.partition.Partition;

/**
 * {@link Percent} is a {@link Partitioning} as a hybrid form of {@link Hyper} and {@link X} resulting in a shape of a %
 * sign.<br>
 * The result for a 9x9 {@link Sudoku} looks like this:
 * <table border="1">
 * <tr>
 * <td>&nbsp;&nbsp;&nbsp;</td>
 * <td>&nbsp;&nbsp;&nbsp;</td>
 * <td style="border-right: 3px solid">&nbsp;&nbsp;&nbsp;</td>
 * <td>&nbsp;&nbsp;&nbsp;</td>
 * <td>&nbsp;&nbsp;&nbsp;</td>
 * <td style="border-right: 3px solid">&nbsp;&nbsp;&nbsp;</td>
 * <td>&nbsp;&nbsp;&nbsp;</td>
 * <td>&nbsp;&nbsp;&nbsp;</td>
 * <td>2&nbsp;</td>
 * </tr>
 * <tr>
 * <td>&nbsp;</td>
 * <td>1</td>
 * <td style="border-right: 3px solid">1</td>
 * <td>1</td>
 * <td>&nbsp;</td>
 * <td style="border-right: 3px solid">&nbsp;</td>
 * <td>&nbsp;</td>
 * <td>2</td>
 * <td>&nbsp;</td>
 * </tr>
 * <tr>
 * <td style="border-bottom:3px solid">&nbsp;</td>
 * <td style="border-bottom:3px solid">1</td>
 * <td style="border-bottom:3px solid;border-right: 3px solid">1</td>
 * <td style="border-bottom:3px solid">1</td>
 * <td style="border-bottom:3px solid">&nbsp;</td>
 * <td style="border-bottom:3px solid;border-right: 3px solid">&nbsp;</td>
 * <td style="border-bottom:3px solid">2</td>
 * <td style="border-bottom:3px solid">&nbsp;</td>
 * <td style="border-bottom:3px solid">&nbsp;</td>
 * </tr>
 * <tr>
 * <td>&nbsp;</td>
 * <td>1</td>
 * <td style="border-right: 3px solid">1</td>
 * <td>1</td>
 * <td>&nbsp;</td>
 * <td style="border-right: 3px solid">2</td>
 * <td>&nbsp;</td>
 * <td>&nbsp;</td>
 * <td>&nbsp;</td>
 * </tr>
 * <tr>
 * <td>&nbsp;</td>
 * <td>&nbsp;</td>
 * <td style="border-right: 3px solid">&nbsp;</td>
 * <td>&nbsp;</td>
 * <td>2</td>
 * <td style="border-right:3px solid">&nbsp;</td>
 * <td>&nbsp;</td>
 * <td>&nbsp;</td>
 * <td>&nbsp;</td>
 * </tr>
 * <tr>
 * <td style="border-bottom:3px solid">&nbsp;</td>
 * <td style="border-bottom:3px solid">&nbsp;</td>
 * <td style="border-bottom:3px solid;border-right: 3px solid">&nbsp;</td>
 * <td style="border-bottom:3px solid">2</td>
 * <td style="border-bottom:3px solid">&nbsp;</td>
 * <td style="border-bottom:3px solid;border-right: 3px solid">3</td>
 * <td style="border-bottom:3px solid">3</td>
 * <td style="border-bottom:3px solid">3</td>
 * <td style="border-bottom:3px solid">&nbsp;</td>
 * </tr>
 * <tr>
 * <td>&nbsp;</td>
 * <td>&nbsp;</td>
 * <td style="border-right: 3px solid">2</td>
 * <td>&nbsp;</td>
 * <td>&nbsp;</td>
 * <td style="border-right: 3px solid">3</td>
 * <td>3</td>
 * <td>3</td>
 * <td>&nbsp;</td>
 * </tr>
 * <tr>
 * <td>&nbsp;</td>
 * <td>2</td>
 * <td style="border-right: 3px solid">&nbsp;</td>
 * <td>&nbsp;</td>
 * <td>&nbsp;</td>
 * <td style="border-right: 3px solid">3</td>
 * <td>3</td>
 * <td>3</td>
 * <td>&nbsp;</td>
 * </tr>
 * <tr>
 * <td>2</td>
 * <td>&nbsp;</td>
 * <td style="border-right: 3px solid">&nbsp;</td>
 * <td>&nbsp;</td>
 * <td>&nbsp;</td>
 * <td style="border-right: 3px solid">&nbsp;</td>
 * <td>&nbsp;</td>
 * <td>&nbsp;</td>
 * <td>&nbsp;</td>
 * </tr>
 * </table>
 * The numbers represent the {@link Partition#getIndex() partition index} and the empty fields are not reachable via
 * this {@link Partitioning} ({@link Field#getPartition(Partitioning)} will return {@code null} for the empty fields).
 */
public class Percent extends Layer {

  /** {@link LayerFactory} for {@link Percent}. */
  public static final LayerFactory FACTORY = (s, i) -> new Percent(s, i);

  /**
   * The constructor.
   *
   * @param sudoku the {@link #getSudoku() sudoku}.
   * @param index the {@link #getIndex() index}.
   */
  public Percent(Sudoku sudoku, int index) {

    super(sudoku, index, new PartitionFunction() {

      @Override
      public int getPartitionCount(Dimension dimension) {

        return 3;
      }

      @Override
      public Field getField(Sudoku s, int partitionIndex, int fieldIndex) {

        int size = s.getSize();
        if (size < 9) {
          throw new IllegalStateException("Percent requires minimum size 9.");
        }
        if (partitionIndex == 2) {
          // "/"
          return s.getField(size + 1 - fieldIndex, fieldIndex);
        } else if ((partitionIndex == 1) || (partitionIndex == 3)) {
          int base = s.getBoxSize();
          int offset = 2;
          if (partitionIndex == 3) {
            offset = size - base;
          }
          // boxes that make "/" to "%"
          int field = fieldIndex - 1;
          int x = offset + (field % base);
          int y = offset + (field / base);
          return s.getField(x, y);
        } else {
          throw new IndexOutOfBoundsException(partitionIndex);
        }
      }
    });
  }

  @Override
  public DimensionType getDimensionType() {

    return DimensionType.SQUARE;
  }
}