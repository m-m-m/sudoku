package io.github.mmm.sudoku.solution;

import io.github.mmm.sudoku.Sudoku;
import io.github.mmm.sudoku.solution.strategy.SolutionStrategy;
import io.github.mmm.sudoku.solution.strategy.SolutionStrategyHiddenSingle;
import io.github.mmm.sudoku.solution.strategy.SolutionStrategyNakedPair;
import io.github.mmm.sudoku.solution.strategy.SolutionStrategyNakedSingle;
import io.github.mmm.sudoku.solution.strategy.SolutionStrategyPartitionIntersection;

/**
 * Solver for a {@link Sudoku}.
 */
public class SudokuSolver implements Solver {

  private final SolutionStrategy[] strategies;

  /**
   * The constructor.
   *
   * @param strategies the {@link SolutionStrategy strategies}.
   */
  public SudokuSolver(SolutionStrategy... strategies) {

    super();
    int difficulty = 0;
    for (SolutionStrategy strategy : strategies) {
      int nextDifficulty = strategy.getDifficulty();
      if (nextDifficulty < difficulty) {
        throw new IllegalStateException("Please provide solution strategies ordered ascending by difficulty.");
      }
      difficulty = nextDifficulty;
    }
    this.strategies = strategies;
  }

  @Override
  public Hint findHint(Sudoku sudoku) {

    for (SolutionStrategy strategy : this.strategies) {
      Hint hint = strategy.findHint(sudoku);
      if (hint != null) {
        return hint;
      }
    }
    return null;
  }

  /**
   * @return a new {@link SudokuSolver} with all build-in {@link SolutionStrategy strategies}.
   */
  public static SudokuSolver of() {

    return new SudokuSolver(SolutionStrategyNakedSingle.INSTANCE, SolutionStrategyHiddenSingle.INSTANCE,
        SolutionStrategyNakedPair.INSTANCE, SolutionStrategyPartitionIntersection.INSTANCE);
  }

}
