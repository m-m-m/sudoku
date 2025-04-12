/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku;

import java.util.List;

import io.github.mmm.sudoku.child.Column;
import io.github.mmm.sudoku.child.Field;
import io.github.mmm.sudoku.child.Partitioning;
import io.github.mmm.sudoku.child.Region;
import io.github.mmm.sudoku.child.Row;
import io.github.mmm.sudoku.dimension.AbstractDimension;
import io.github.mmm.sudoku.style.BorderType;
import io.github.mmm.sudoku.style.ColorType;

/**
 * A jigsaw or squiggly {@link Sudoku}.
 */
public class JigsawSudoku extends Sudoku {

  /**
   * The constructor.
   */
  public JigsawSudoku() {

    super();
  }

  /**
   * The constructor.
   *
   * @param dimension the {@link AbstractDimension}.
   */
  public JigsawSudoku(AbstractDimension dimension) {

    super(dimension);
  }

  @Override
  public String getType() {

    return "Jigsaw";
  }

  @Override
  protected void registerPartitions(List<Partitioning> p) {

    p.add(new Column(this));
    p.add(new Row(this));
    p.add(new Jigsaw(this));
  }

  /** {@link Jigsaw} {@link Region}. */
  public static class Jigsaw extends Region {

    /**
     * The constructor.
     *
     * @param sudoku the {@link #getSudoku() sudoku}.
     */
    public Jigsaw(JigsawSudoku sudoku) {

      super(sudoku);
    }

    @Override
    public Field getPartitionField(int partitionIndex, int fieldIndex) {

      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public ColorType getColorType() {

      return ColorType.MULTIPLE;
    }

    @Override
    public BorderType getBorderType() {

      return BorderType.THICK;
    }

  }
}
