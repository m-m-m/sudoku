/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.fx;

import io.github.mmm.sudoku.Sudoku;
import io.github.mmm.sudoku.child.Field;
import io.github.mmm.sudoku.child.SudokuContainer;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

/**
 * Widget for the buttons with the {@link Sudoku#getSymbol(int) symbols}. Allows to
 * {@link Sudoku#setFieldValue(io.github.mmm.sudoku.child.Field, int) set field values} in the currently selected
 * {@link Field}.
 */
public class SudokuFxSymbolButtons extends GridPane implements SudokuContainer {

  private final SudokuFxBoard board;

  private final Sudoku sudoku;

  /**
   * The constructor.
   */
  public SudokuFxSymbolButtons(SudokuFxBoard board) {

    super();
    this.board = board;
    this.sudoku = board.getSudoku();
    getStyleClass().add("symbols");
    initButtons();
  }

  @Override
  public Sudoku getSudoku() {

    return this.sudoku;
  }

  private void initButtons() {

    int size = this.sudoku.getSize();
    int base = this.sudoku.getBase();
    int width = base;
    int height = base;
    if (this.sudoku.isRegular()) {
      height = size / base;
    }
    int i = 1;
    for (int y = 0; y < width; y++) {
      for (int x = 0; x < height; x++) {
        String symbol = this.sudoku.getSymbol(i++);
        Button button = new Button(symbol);
        add(button, x, y);
        if (i > size) {
          break;
        }
      }
    }
  }

}
