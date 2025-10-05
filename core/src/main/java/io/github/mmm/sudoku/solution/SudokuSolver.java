package io.github.mmm.sudoku.solution;

import io.github.mmm.sudoku.Sudoku;
import io.github.mmm.sudoku.common.Candidates;
import io.github.mmm.sudoku.field.Field;
import io.github.mmm.sudoku.history.ChangeSet;
import io.github.mmm.sudoku.solution.strategy.SolutionStrategy;
import io.github.mmm.sudoku.solution.strategy.SolutionStrategyHiddenPair;
import io.github.mmm.sudoku.solution.strategy.SolutionStrategyHiddenSingle;
import io.github.mmm.sudoku.solution.strategy.SolutionStrategyHiddenTriplet;
import io.github.mmm.sudoku.solution.strategy.SolutionStrategyNakedPair;
import io.github.mmm.sudoku.solution.strategy.SolutionStrategyNakedSingle;
import io.github.mmm.sudoku.solution.strategy.SolutionStrategyNakedTriplet;
import io.github.mmm.sudoku.solution.strategy.SolutionStrategyPartitionIntersection;
import io.github.mmm.sudoku.solution.strategy.SolutionStrategyXWing;

/**
 * Solver for a {@link Sudoku}.
 */
public class SudokuSolver implements Solver {

  /** @see #get() */
  private static final SudokuSolver INSTANCE = of();

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
   * @param sudoku the {@link Sudoku} to solve.
   * @param fillValues - {@code true} to also {@link Field#setValue(int) fill in the values}, {@code false} to only
   *        compute and {@link Field#setSolution(int) set the solutions}.
   */
  public void solve(Sudoku sudoku, boolean fillValues) {

    Sudoku copy = sudoku.copy();
    doSolve(copy);
    int size = sudoku.getSize();
    int max = size * size;
    for (int i = 1; i <= max; i++) {
      Field field = sudoku.getField(i);
      if (!field.hasSolution()) {
        field.setSolution(copy.getField(i).getValue());
      }
      if (fillValues && !field.hasValue()) {
        sudoku.setFieldValue(field, field.getSolution());
      }
    }
  }

  private void doSolve(Sudoku sudoku) {

    int size = sudoku.getSize();
    int max = size * size;
    for (int i = 1; i <= max; i++) {
      Field field = sudoku.getField(i);
      if (!field.hasValue()) {
        if (field.hasSolution()) {
          field.setValue(field.getSolution());
        } else {
          doSolve(sudoku, i, max, false);
        }
      }
    }
  }

  private boolean doSolve(Sudoku sudoku, int i, int max, boolean recursion) {

    Field field = sudoku.getField(i);
    boolean error = false;
    if (field.isGiven() || field.hasSolution()) {
      if (i < max) {
        error = doSolve(sudoku, i + 1, max, true);
      }
    } else {
      Candidates candidates = field.getCandidates();
      int count = candidates.getInclusionCount();
      ChangeSet checkpoint = sudoku.getLastChange();
      if (count > 0) {
        for (int candidateIndex = 1; candidateIndex <= count; candidateIndex++) {
          int candidate = candidates.getCandidate(candidateIndex);
          error = sudoku.setFieldValue(field, candidate);
          System.out.println(field + " set to value " + candidate);
          if (!error) {
            if (i < max) {
              error = doSolve(sudoku, i + 1, max, true);
            }
          }
          if (error) {
            boolean undoSuccess = sudoku.undo(checkpoint);
            assert undoSuccess;
          } else {
            break;
          }
        }
      } else {
        error = true;
      }
    }
    return error;
  }

  /**
   * @return a new {@link SudokuSolver} with all build-in {@link SolutionStrategy strategies}.
   */
  public static SudokuSolver of() {

    return new SudokuSolver(SolutionStrategyNakedSingle.INSTANCE, SolutionStrategyHiddenSingle.INSTANCE,
        SolutionStrategyNakedPair.INSTANCE, SolutionStrategyHiddenPair.INSTANCE, SolutionStrategyNakedTriplet.INSTANCE,
        SolutionStrategyPartitionIntersection.INSTANCE, SolutionStrategyHiddenTriplet.INSTANCE,
        SolutionStrategyXWing.INSTANCE);
  }

  /**
   * @return the singleton instance of {@link SudokuSolver}. May not be thread-safe. To ensure no concurrency problems
   *         occur, use {@link #of()} instead.
   */
  public static SudokuSolver get() {

    return INSTANCE;
  }

}
