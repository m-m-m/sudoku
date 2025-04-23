/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.fx;

import io.github.mmm.sudoku.Sudoku;
import io.github.mmm.sudoku.child.SudokuContainer;
import io.github.mmm.sudoku.dimension.DimensionType;
import io.github.mmm.sudoku.field.Field;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

/**
 * Widget for the buttons with the {@link Sudoku#getSymbol(int) symbols}. Allows to
 * {@link Sudoku#setFieldValue(io.github.mmm.sudoku.field.Field, int) set field values} in the currently selected
 * {@link Field}.
 */
public class SudokuFxSymbolButtons extends GridPane implements SudokuContainer, ValueCompletion {

  private final SudokuFxPuzzle fxPuzzle;

  private final Sudoku sudoku;

  private final boolean candidates;

  /**
   * The constructor.
   */
  public SudokuFxSymbolButtons(SudokuFxPuzzle fxPuzzle, boolean candidates) {

    super();
    this.fxPuzzle = fxPuzzle;
    this.sudoku = fxPuzzle.getSudoku();
    this.candidates = candidates;
    getStyleClass().add("symbols");
    if (candidates) {
      getStyleClass().add("candidates");
    }
    initButtons();
  }

  @Override
  public Sudoku getSudoku() {

    return this.sudoku;
  }

  private void initButtons() {

    int size = this.sudoku.getSize();
    int base = this.sudoku.getBoxSize();
    int width = base;
    int height = base;
    if (this.sudoku.getDimensionType() != DimensionType.SQUARE) {
      height = size / base;
    }
    int i = 1;
    for (int y = 0; y < width; y++) {
      for (int x = 0; x < height; x++) {
        Button button = createButton(i++);
        add(button, x, y);
        if (i > size) {
          break;
        }
      }
    }
  }

  private Button createButton(int value) {

    String symbol;
    if (this.candidates) {
      symbol = this.sudoku.getCandidateMatrix(i -> i == value);
    } else {
      symbol = this.sudoku.getSymbol(value);
    }
    Button button = new Button(symbol);
    button.setOnAction(e -> {
      if (this.candidates) {
        this.fxPuzzle.toggleCandidate(value);
      } else {
        this.fxPuzzle.setValue(value);
      }
    });
    // button.focusedProperty().addListener(e -> {
    // this.fxPuzzle.setSelectedValue(value);
    // });
    return button;
  }

  @Override
  public void onValueCompletion(int value, boolean completed) {

    Button button = (Button) getChildren().get(value - 1);
    button.setDisable(completed);
  }

}
