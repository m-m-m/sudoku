package io.github.mmm.sudoku.builder;

import io.github.mmm.sudoku.Sudoku;
import io.github.mmm.sudoku.partitioning.Box;
import io.github.mmm.sudoku.partitioning.Region;

/**
 * Builder of a {@link Sudoku} using {@link Box}es as {@link Region}.
 */
public class BoxBuilder extends RegionBuilder {

  /**
   * The constructor.
   */
  public BoxBuilder() {

    super(Box.FACTORY);
  }

}
