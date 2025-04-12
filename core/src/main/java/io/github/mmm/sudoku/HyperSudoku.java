/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku;

import java.util.List;

import io.github.mmm.sudoku.child.Field;
import io.github.mmm.sudoku.child.Partitioning;
import io.github.mmm.sudoku.child.SingleColorPartitioning;
import io.github.mmm.sudoku.dimension.RegularDimension;

/**
 * A standard {@link Sudoku} using Box as regional {@link #getPartitionings() partition}.
 */
public class HyperSudoku extends Sudoku {

  /**
   * The constructor.
   */
  public HyperSudoku() {

    super();
  }

  /**
   * The constructor.
   *
   * @param dimension the {@link RegularDimension}.
   */
  public HyperSudoku(RegularDimension dimension) {

    super(dimension);
  }

  @Override
  public String getType() {

    return "Hyper";
  }

  @Override
  protected void registerPartitions(List<Partitioning> p) {

    super.registerPartitions(p);
    p.add(new Hyper(this));
  }

  /** {@link Hyper} {@link Partitioning}. */
  public static class Hyper extends SingleColorPartitioning {

    private final int b0;

    private final int count;

    /**
     * The constructor.
     *
     * @param sudoku the {@link #getSudoku() sudoku}.
     */
    public Hyper(HyperSudoku sudoku) {

      super(sudoku);
      this.b0 = sudoku.getBase() - 1;
      this.count = this.b0 * this.b0;
    }

    @Override
    public String getName() {

      return "Hyper";
    }

    @Override
    public int getPartitionCount() {

      return this.count;
    }

    @Override
    public Field getPartitionField(int partitionIndex, int fieldIndex) {

      int b = this.sudoku.getBase();
      int b1 = b + 1;
      int partition = partitionIndex - 1;
      int x0 = 2 + (partition % this.b0) * b1;
      int y0 = 2 + (partition / this.b0) * b1;
      int field = fieldIndex - 1;
      int x = x0 + (field % b);
      int y = y0 + (field / b);
      return getSudoku().getField(x, y);
    }
  }
}
