package io.github.mmm.sudoku.builder;

import io.github.mmm.sudoku.Sudoku;
import io.github.mmm.sudoku.partitioning.Box;
import io.github.mmm.sudoku.partitioning.Jigsaw;

/**
 * Builder for a {@link Sudoku}.
 */
public class SudokuBuilder implements Builder {

  private static final SudokuBuilder INSTANCE = new SudokuBuilder();

  private SudokuBuilder() {

    super();
  }

  /**
   * Create a normal {@link Sudoku} with {@link Box}es.
   *
   * @return the {@link BoxBuilder} for fluent builder API calls.
   */
  public BoxBuilder withBoxes() {

    return new BoxBuilder();
  }

  /**
   * Create a {@link Jigsaw}-{@link Sudoku}.
   *
   * @param seed the {@link Jigsaw} seed.
   * @return the {@link JigsawBuilder} for fluent builder API calls.
   */
  public JigsawBuilder withJigsaw(int seed) {

    return new JigsawBuilder(seed);
  }

  @Override
  public Sudoku build() {

    return new Sudoku();
  }

  /**
   * @return the instance of {@link SudokuBuilder}.
   */
  public static SudokuBuilder get() {

    return INSTANCE;
  }

}
