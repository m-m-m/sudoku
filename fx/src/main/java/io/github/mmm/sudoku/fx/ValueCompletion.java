package io.github.mmm.sudoku.fx;

/**
 *
 */
public interface ValueCompletion {

  /**
   * @param value the {@link io.github.mmm.sudoku.field.Field#getValue() value} that has been (un)completed.
   * @param completed {@code true} if the given {@code value} has been completed, {@code false} if uncompleted (e.g. due
   *        to undo).
   */
  void onValueCompletion(int value, boolean completed);

}
