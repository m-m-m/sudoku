/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.child;

import io.github.mmm.sudoku.Sudoku;

/**
 * Implementation of {@link Region} as square box for a normal {@link Sudoku}. In a classic {@link Sudoku} the
 * {@link Field}s are partitioned into 3x3 square boxes:
 * <table border="1">
 * <tr>
 * <td>1,1</td>
 * <td>2,1</td>
 * <td style="border-right: 3px solid">3,1</td>
 * <td>4,1</td>
 * <td>5,1</td>
 * <td style="border-right: 3px solid">6,1</td>
 * <td>7,1</td>
 * <td>8,1</td>
 * <td>9,1</td>
 * </tr>
 * <tr>
 * <td>1,2</td>
 * <td>2,2</td>
 * <td style="border-right: 3px solid">3,2</td>
 * <td>4,2</td>
 * <td>5,2</td>
 * <td style="border-right: 3px solid">6,2</td>
 * <td>7,2</td>
 * <td>8,2</td>
 * <td>9,2</td>
 * </tr>
 * <tr style="border-bottom: 3px solid">
 * <td>1,3</td>
 * <td>2,3</td>
 * <td style="border-right: 3px solid">3,3</td>
 * <td>4,3</td>
 * <td>5,3</td>
 * <td style="border-right: 3px solid">6,3</td>
 * <td>7,3</td>
 * <td>8,3</td>
 * <td>9,3</td>
 * </tr>
 * <tr>
 * <td>1,4</td>
 * <td>2,4</td>
 * <td style="border-right: 3px solid">3,4</td>
 * <td>4,4</td>
 * <td>5,4</td>
 * <td style="border-right: 3px solid">6,4</td>
 * <td>7,4</td>
 * <td>8,4</td>
 * <td>9,4</td>
 * </tr>
 * <tr>
 * <td>1,5</td>
 * <td>2,5</td>
 * <td style="border-right: 3px solid">3,5</td>
 * <td>4,5</td>
 * <td>5,5</td>
 * <td style="border-right: 3px solid">6,5</td>
 * <td>7,5</td>
 * <td>8,5</td>
 * <td>9,5</td>
 * </tr>
 * <tr style="border-bottom: 3px solid">
 * <td>1,6</td>
 * <td>2,6</td>
 * <td style="border-right: 3px solid">3,6</td>
 * <td>4,6</td>
 * <td>5,6</td>
 * <td style="border-right: 3px solid">6,6</td>
 * <td>7,6</td>
 * <td>8,6</td>
 * <td>9,6</td>
 * </tr>
 * <tr>
 * <td>1,7</td>
 * <td>2,7</td>
 * <td style="border-right: 3px solid">3,7</td>
 * <td>4,7</td>
 * <td>5,7</td>
 * <td style="border-right: 3px solid">6,7</td>
 * <td>7,7</td>
 * <td>8,7</td>
 * <td>9,7</td>
 * </tr>
 * <tr>
 * <td>1,8</td>
 * <td>2,8</td>
 * <td style="border-right: 3px solid">3,8</td>
 * <td>4,8</td>
 * <td>5,8</td>
 * <td style="border-right: 3px solid">6,8</td>
 * <td>7,8</td>
 * <td>8,8</td>
 * <td>9,8</td>
 * </tr>
 * <tr>
 * <td>1,9</td>
 * <td>2,9</td>
 * <td style="border-right: 3px solid">3,9</td>
 * <td>4,9</td>
 * <td>5,9</td>
 * <td style="border-right: 3px solid">6,9</td>
 * <td>7,9</td>
 * <td>8,9</td>
 * <td>9,9</td>
 * </tr>
 * </table>
 * So the first {@link Region} is from 1,1 to 3x3 the second is 4,1 to 6,3 and the last is 7,7 to 9,9.
 */
public final class Box extends Region {

  /**
   * The constructor.
   *
   * @param sudoku the {@link #getSudoku() sudoku}.
   */
  public Box(Sudoku sudoku) {

    super(sudoku);
  }

  @Override
  public Field getPartitionField(int partitionIndex, int fieldIndex) {

    int b = this.sudoku.getBase();
    int partition = partitionIndex - 1;
    int x0 = 1 + (partition % b) * b;
    int y0 = 1 + (partition / b) * b;
    int field = fieldIndex - 1;
    int x = x0 + (field % b);
    int y = y0 + (field / b);
    return this.sudoku.getField(x, y);
  }

}