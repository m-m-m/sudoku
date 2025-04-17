/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.fx;

import io.github.mmm.sudoku.Sudoku;
import io.github.mmm.sudoku.field.Field;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

/**
 * Widget to render the {@link Sudoku} board.
 */
public class SudokuFxBoard extends GridPane implements SudokuFxView {

  private final SudokuFxPuzzle fxPuzzle;

  private final Sudoku sudoku;

  /**
   * The constructor.
   *
   * @param fxPuzzle the {@link SudokuFxPuzzle} owning this board.
   */
  public SudokuFxBoard(SudokuFxPuzzle fxPuzzle) {

    super();
    this.fxPuzzle = fxPuzzle;
    this.sudoku = fxPuzzle.getSudoku();
    getStyleClass().add("board");
    initBoard();
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
   * @param field the {@link Field} (model).
   * @return the {@link SudokuFxField} (view).
   */
  public SudokuFxField getFxField(Field field) {

    return getFxField(field.getX(), field.getY());
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

    int value = this.fxPuzzle.getSelectedValue();
    for (Node child : getChildren()) {
      if (child instanceof SudokuFxField fxField) {
        fxField.highlight(value);
        fxField.update();
      }
    }
  }

}
