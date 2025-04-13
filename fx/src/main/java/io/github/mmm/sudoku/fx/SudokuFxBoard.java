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

  private final SudokuFx fxSudoku;

  private final Sudoku sudoku;

  private SudokuFxField selectedFxField;

  private int selectedValue;

  /**
   * The constructor.
   *
   * @param sudoku the {@link Sudoku} presented by this view.
   */
  public SudokuFxBoard(SudokuFx fxSudoku) {

    super();
    this.fxSudoku = fxSudoku;
    this.sudoku = fxSudoku.getSudoku();
    getStyleClass().add("board");
    initBoard();
    this.selectedValue = Field.UNDEFINED;
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
    if (this.selectedFxField == fxField) {
      // deselection
      this.selectedFxField = null;
    } else {
      this.selectedFxField = fxField;
      this.selectedFxField.setSelected(true);
    }
    update();
  }

  /**
   * @param value the new {@link Field#getValue() field value} to set in the selected field.
   * @return {@code true} if the value has been successfully set, {@code false} otherwise (no field selected, selected
   *         field has {@link Field#isGiven() given clue}, etc.).
   * @see Sudoku#setFieldValue(Field, int)
   */
  public boolean setValue(int value) {

    if (this.selectedFxField == null) {
      return false;
    }
    Field field = this.selectedFxField.getField();
    if (field.isGiven()) {
      return false;
    }
    this.sudoku.setFieldValue(field, value);
    int valueCount = this.sudoku.getValueCount(value);
    if (valueCount == this.sudoku.getSize()) {
      this.fxSudoku.onValueCompletion(value, true);
      // this.buttons.disable();
    }
    update();
    return true;
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

    int value = Field.UNDEFINED;
    if (this.selectedFxField != null) {
      Field field = this.selectedFxField.getField();
      if (field.hasValue()) {
        value = field.getValue();
      } else {
        value = field.getSingle();
      }
      if (value == Field.UNDEFINED) {
        value = this.selectedValue;
      } else {
        this.selectedValue = value;
      }
    }
    for (Node child : getChildren()) {
      if (child instanceof SudokuFxField fxField) {
        fxField.highlight(value);
        fxField.update();
      }
    }
  }

}
