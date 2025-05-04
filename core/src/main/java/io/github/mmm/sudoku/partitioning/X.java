/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.partitioning;

import io.github.mmm.sudoku.Sudoku;
import io.github.mmm.sudoku.dimension.DimensionType;
import io.github.mmm.sudoku.field.Field;
import io.github.mmm.sudoku.partition.Partition;
import io.github.mmm.sudoku.style.ColorType;

/**
 * {@link X} is a {@link Layer} with the two diagonals of the {@link Sudoku} board as colored {@link Partition}s. <br>
 * The result for a 9x9 {@link Sudoku} looks like this:
 * <table border="1">
 * <tr>
 * <td>1&nbsp;</td>
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
 * <td style="border-right: 3px solid">&nbsp;</td>
 * <td>&nbsp;</td>
 * <td>&nbsp;</td>
 * <td style="border-right: 3px solid">&nbsp;</td>
 * <td>&nbsp;</td>
 * <td>2</td>
 * <td>&nbsp;</td>
 * </tr>
 * <tr>
 * <td style="border-bottom:3px solid">&nbsp;</td>
 * <td style="border-bottom:3px solid">&nbsp;</td>
 * <td style="border-bottom:3px solid;border-right: 3px solid">1</td>
 * <td style="border-bottom:3px solid">&nbsp;</td>
 * <td style="border-bottom:3px solid">&nbsp;</td>
 * <td style="border-bottom:3px solid;border-right: 3px solid">&nbsp;</td>
 * <td style="border-bottom:3px solid">2</td>
 * <td style="border-bottom:3px solid">&nbsp;</td>
 * <td style="border-bottom:3px solid">&nbsp;</td>
 * </tr>
 * <tr>
 * <td>&nbsp;</td>
 * <td>&nbsp;</td>
 * <td style="border-right: 3px solid">&nbsp;</td>
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
 * <td>1/2</td>
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
 * <td style="border-bottom:3px solid;border-right: 3px solid">1</td>
 * <td style="border-bottom:3px solid">&nbsp;</td>
 * <td style="border-bottom:3px solid">&nbsp;</td>
 * <td style="border-bottom:3px solid">&nbsp;</td>
 * </tr>
 * <tr>
 * <td>&nbsp;</td>
 * <td>&nbsp;</td>
 * <td style="border-right: 3px solid">2</td>
 * <td>&nbsp;</td>
 * <td>&nbsp;</td>
 * <td style="border-right: 3px solid">&nbsp;</td>
 * <td>1</td>
 * <td>&nbsp;</td>
 * <td>&nbsp;</td>
 * </tr>
 * <tr>
 * <td>&nbsp;</td>
 * <td>2</td>
 * <td style="border-right: 3px solid">&nbsp;</td>
 * <td>&nbsp;</td>
 * <td>&nbsp;</td>
 * <td style="border-right: 3px solid">&nbsp;</td>
 * <td>&nbsp;</td>
 * <td>1</td>
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
 * <td>1</td>
 * </tr>
 * </table>
 * The numbers represent the {@link Partition#getIndex() partition index} and the empty fields are not reachable via
 * this {@link Partitioning} ({@link Field#getPartition(Partitioning)} will return {@code null} for the empty fields).
 */
public abstract class X extends Layer {

  /**
   * The constructor.
   *
   * @param sudoku the {@link #getSudoku() sudoku}.
   * @param index the {@link #getIndex() index}.
   * @param function the {@link SinglePartitionFunction}.
   */
  public X(Sudoku sudoku, int index, SinglePartitionFunction function) {

    super(sudoku, index, function);
  }

  @Override
  public DimensionType getDimensionType() {

    return DimensionType.PRIME;
  }

  @Override
  public ColorType getColorType() {

    return ColorType.SAME;
  }

  @Override
  public boolean isComplete() {

    return false;
  }

}