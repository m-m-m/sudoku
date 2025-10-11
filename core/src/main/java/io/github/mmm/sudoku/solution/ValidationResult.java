package io.github.mmm.sudoku.solution;

/**
 * Result of a {@link io.github.mmm.sudoku.Sudoku#validate() validation}.
 */
public class ValidationResult {

  private final int errorCount;

  private final int remainingCount;

  private final double progress;

  /**
   * The constructor.
   *
   * @param errorCount the {@link #getErrorCount() error count}.
   * @param remainingCount the {@link #getRemainingCount() remaining count}.
   * @param progress the {@link #getProgress() progress}.
   */
  public ValidationResult(int errorCount, int remainingCount, double progress) {

    super();
    if (errorCount < 0) {
      throw new IllegalArgumentException("errorCount must not be negative:" + errorCount);
    }
    this.errorCount = errorCount;
    if (remainingCount < 0) {
      throw new IllegalArgumentException("remainingCount must not be negative:" + errorCount);
    }
    this.remainingCount = remainingCount;
    this.progress = progress;
  }

  /**
   * @return the total number of errors ({@link io.github.mmm.sudoku.field.Field fields} with incorrect
   *         {@link io.github.mmm.sudoku.field.Field#getValue() value} or missing
   *         {@link io.github.mmm.sudoku.field.Field#getCandidates() candidate}).
   * @see io.github.mmm.sudoku.field.Field#isError()
   * @see io.github.mmm.sudoku.field.Field#getSolution()
   */
  public int getErrorCount() {

    return this.errorCount;
  }

  /**
   * @return {@code true} if the validation was successful (no {@link #getErrorCount() errors} found), {@code false}
   *         otherwise.
   */
  public boolean isSuccessful() {

    return this.errorCount == 0;
  }

  /**
   * @return {@code true} if at least one {@link #getErrorCount() error} has been found, {@code false} otherwise
   *         ({@link #isSuccessful() successful validation}).
   */
  public boolean hasErrors() {

    return this.errorCount > 0;
  }

  /**
   * @return the number of remaining {@link io.github.mmm.sudoku.field.Field fields} that do not
   *         {@link io.github.mmm.sudoku.field.Field#hasValue() have a value} filled in yet.
   */
  public int getRemainingCount() {

    return this.remainingCount;
  }

  /**
   * @return the progress of your {@link io.github.mmm.sudoku.Sudoku} solution where {@code 0} means you have not solved
   *         anything yet and {@code 1} means everything is solved.
   */
  public double getProgress() {

    return this.progress;
  }

  /**
   * @return the {@link #getProgress() progress} formatted as {@link String} in percent.
   */
  public String getProgressInPercent() {

    return String.format("%.1f", this.progress * 100) + "%";
  }

  @Override
  public String toString() {

    if (this.errorCount == 0) {
      return "Awesome! No errors found, " + getProgressInPercent() + " solved and " + this.remainingCount
          + " field(s) to fill.";
    } else {
      return "Oh no! " + this.errorCount + " error(s) found, " + getProgressInPercent() + "% solved and "
          + this.remainingCount + " field(s) to fill.";

    }
  }

}
