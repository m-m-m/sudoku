/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.fx;

import io.github.mmm.sudoku.Sudoku;
import io.github.mmm.sudoku.child.Field;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

/**
 * Widget to render the {@link Sudoku} board.
 */
public class SudokuFxBoard extends GridPane implements SudokuFxView {

  private final Sudoku sudoku;

  private SudokuFxField selectedFxField;

  /**
   * The constructor.
   *
   * @param sudoku the {@link Sudoku} presented by this view.
   */
  public SudokuFxBoard(Sudoku sudoku) {

    super();
    getStyleClass().add("board");
    this.sudoku = sudoku;
    initBoard();
  }

  @Override
  public boolean isResizable() {

    return true;
  }

  @Override
  public Sudoku getSudoku() {

    return this.sudoku;
  }

  private void initBoard() {

    int size = this.sudoku.getSize();
    for (int y = 1; y <= size; y++) {
      for (int x = 1; x <= size; x++) {
        Field field = this.sudoku.getField(x, y);
        SudokuFxField fxField = new SudokuFxField(this, field);
        add(fxField, x - 1, y - 1);
      }
    }
  }

  /**
   * @param fxField the {@link SudokuFxField} to select.
   */
  public void selectField(SudokuFxField fxField) {

    if (this.selectedFxField != null) {
      this.selectedFxField.setSelected(false);
    }
    this.selectedFxField = fxField;
    this.selectedFxField.setSelected(true);
    highlightFields();
  }

  private void highlightFields() {

    int value = this.selectedFxField.getField().getValue();
    if (value < 0) {
      return;
    }
    for (Node child : getChildren()) {
      if ((child != this.selectedFxField) && (child instanceof SudokuFxField fxField)) {
        fxField.highlight(value);
      }
    }
  }

  /**
   * @param x the {@link Field#getX() x-coordinate}.
   * @param y the {@link Field#getY() y-coordinate}.
   * @return the specified {@link SudokuFxField}.
   */
  public SudokuFxField getFxField(int x, int y) {

    int size = this.sudoku.getSize();
    int i = (x - 1) + ((y - 1) * size);
    return (SudokuFxField) getChildren().get(i);
  }

  @Override
  public void update() {

    for (Node child : getChildren()) {
      if (child instanceof SudokuFxField fxField) {
        fxField.update();
      }
    }
  }

}
