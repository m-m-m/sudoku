package io.github.mmm.sudoku.fx;

import io.github.mmm.sudoku.Sudoku;
import io.github.mmm.sudoku.history.ChangeSet;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 * View with undo and redo buttons.
 */
public class SudokuFxHistoryButtons extends HBox implements SudokuFxView {

  private final SudokuFxPuzzle fxPuzzle;

  private final Sudoku sudoku;

  private final Button undoButton;

  private final Button redoButton;

  /**
   * The constructor.
   *
   * @param fxPuzzle the {@link SudokuFxPuzzle}.
   */
  public SudokuFxHistoryButtons(SudokuFxPuzzle fxPuzzle) {

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

  @Override
  public void update() {

    ChangeSet lastChange = this.sudoku.getLastChange();
    this.undoButton.setDisable(lastChange.getPrevious() == null);
    this.redoButton.setDisable(lastChange.getNext() == null);
  }

}
