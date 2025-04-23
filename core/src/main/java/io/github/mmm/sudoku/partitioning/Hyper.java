/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.partitioning;

import io.github.mmm.sudoku.Sudoku;
import io.github.mmm.sudoku.dimension.Dimension;
import io.github.mmm.sudoku.dimension.DimensionType;
import io.github.mmm.sudoku.field.Field;
import io.github.mmm.sudoku.partition.Partition;

/**
 * {@link Hyper} is a {@link Layer} with a kind of colored {@link Box}es shifted by one right and down with a margin of
 * one. The result for a 9x9 {@link Sudoku} looks like this:
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
 * <td>&nbsp;&nbsp;&nbsp;</td>
 * </tr>
 * <tr>
 * <td>&nbsp;</td>
 * <td>1</td>
 * <td style="border-right: 3px solid">1</td>
 * <td>1</td>
 * <td>&nbsp;</td>
 * <td style="border-right: 3px solid">2</td>
 * <td>2</td>
 * <td>2</td>
 * <td>&nbsp;</td>
 * </tr>
 * <tr>
 * <td style="border-bottom:3px solid">&nbsp;</td>
 * <td style="border-bottom:3px solid">1</td>
 * <td style="border-bottom:3px solid;border-right: 3px solid">1</td>
 * <td style="border-bottom:3px solid">1</td>
 * <td style="border-bottom:3px solid">&nbsp;</td>
 * <td style="border-bottom:3px solid;border-right: 3px solid">2</td>
 * <td style="border-bottom:3px solid">2</td>
 * <td style="border-bottom:3px solid">2</td>
 * <td style="border-bottom:3px solid">&nbsp;</td>
 * </tr>
 * <tr>
 * <td>&nbsp;</td>
 * <td>1</td>
 * <td style="border-right: 3px solid">1</td>
 * <td>1</td>
 * <td>&nbsp;</td>
 * <td style="border-right: 3px solid">2</td>
 * <td>2</td>
 * <td>2</td>
 * <td>&nbsp;</td>
 * </tr>
 * <tr>
 * <td>&nbsp;</td>
 * <td>&nbsp;</td>
 * <td style="border-right: 3px solid">&nbsp;</td>
 * <td>&nbsp;</td>
 * <td>&nbsp;</td>
 * <td style="border-right:3px solid">&nbsp;</td>
 * <td>&nbsp;</td>
 * <td>&nbsp;</td>
 * <td>&nbsp;</td>
 * </tr>
 * <tr>
 * <td style="border-bottom:3px solid">&nbsp;</td>
 * <td style="border-bottom:3px solid">3</td>
 * <td style="border-bottom:3px solid;border-right: 3px solid">3</td>
 * <td style="border-bottom:3px solid">3</td>
 * <td style="border-bottom:3px solid">&nbsp;</td>
 * <td style="border-bottom:3px solid;border-right: 3px solid">4</td>
 * <td style="border-bottom:3px solid">4</td>
 * <td style="border-bottom:3px solid">4</td>
 * <td style="border-bottom:3px solid">&nbsp;</td>
 * </tr>
 * <tr>
 * <td>&nbsp;</td>
 * <td>3</td>
 * <td style="border-right: 3px solid">3</td>
 * <td>3</td>
 * <td>&nbsp;</td>
 * <td style="border-right: 3px solid">4</td>
 * <td>4</td>
 * <td>4</td>
 * <td>&nbsp;</td>
 * </tr>
 * <tr>
 * <td>&nbsp;</td>
 * <td>3</td>
 * <td style="border-right: 3px solid">3</td>
 * <td>3</td>
 * <td>&nbsp;</td>
 * <td style="border-right: 3px solid">4</td>
 * <td>4</td>
 * <td>4</td>
 * <td>&nbsp;</td>
 * </tr>
 * <tr>
 * <td>&nbsp;</td>
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
public class Hyper extends Layer {

  /** {@link LayerFactory} for {@link Hyper}. */
  public static final LayerFactory FACTORY = (s, i) -> new Hyper(s, i);

  /**
   * The constructor.
   *
   * @param sudoku the {@link #getSudoku() sudoku}.
   * @param index the {@link #getIndex() index}.
   */
  public Hyper(Sudoku sudoku, int index) {

    super(sudoku, index, new PartitionFunction() {

      @Override
      public int getPartitionCount(Dimension dimension) {

        int b0 = dimension.getBoxSize() - 1;
        return b0 * b0;
      }

      @Override
      public Field getField(Sudoku s, int partitionIndex, int fieldIndex) {

        int b = sudoku.getBoxSize();
        int b0 = b - 1;
        int b1 = b + 1;
        int partition = partitionIndex - 1;
        int x0 = 2 + (partition % b0) * b1;
        int y0 = 2 + (partition / b0) * b1;
        int field = fieldIndex - 1;
        int x = x0 + (field % b);
        int y = y0 + (field / b);
        return s.getField(x, y);
      }
    });
  }

  @Override
  public DimensionType getDimensionType() {

    return DimensionType.SQUARE;
  }
}