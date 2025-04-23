package io.github.mmm.sudoku.fx;

import io.github.mmm.sudoku.Sudoku;
import io.github.mmm.sudoku.history.ChangeSet;
import io.github.mmm.sudoku.solution.Hint;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 * View with action buttons for undo, redo, etc.
 */
public class SudokuFxActionButtons extends HBox implements SudokuFxView {

  private final SudokuFxPuzzle fxPuzzle;

  private final Sudoku sudoku;

  private final Button undoButton;

  private final Button redoButton;

  private final Button hintButton;

  /**
   * The constructor.
   *
   * @param fxPuzzle the {@link SudokuFxPuzzle}.
   */
  public SudokuFxActionButtons(SudokuFxPuzzle fxPuzzle) {

    super();
    this.fxPuzzle = fxPuzzle;
    this.sudoku = fxPuzzle.getSudoku();
    getStyleClass().add("history");
    setSpacing(10);
    this.undoButton = new Button("↶");
    this.undoButton.setAccessibleHelp("undo");
    this.undoButton.setOnAction(e -> onUndo());
    getChildren().add(this.undoButton);
    this.redoButton = new Button("↷");
    this.redoButton.setAccessibleHelp("redo");
    this.redoButton.setOnAction(e -> onRedo());
    getChildren().add(this.redoButton);
    this.hintButton = new Button("?");
    this.hintButton.setAccessibleHelp("hint");
    this.hintButton.setOnAction(e -> onHint());
    getChildren().add(this.hintButton);
    update();
  }

  @Override
  public Sudoku getSudoku() {

    return this.sudoku;
  }

  private void onUndo() {

    this.sudoku.undo();
    update();
  }

  private void onRedo() {

    this.sudoku.redo();
    update();
  }

  private void onHint() {

    Hint hint = this.fxPuzzle.getSolver().findHint(this.sudoku);
    if (hint != null) {
      this.sudoku.apply(hint);
      update();
    }
  }

  @Override
  public void update() {

    ChangeSet lastChange = this.sudoku.getLastChange();
    this.undoButton.setDisable(lastChange.getPrevious() == null);
    this.redoButton.setDisable(lastChange.getNext() == null);
  }

}
