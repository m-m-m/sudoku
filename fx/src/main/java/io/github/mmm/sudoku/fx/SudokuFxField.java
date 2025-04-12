/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.fx;

import io.github.mmm.sudoku.Sudoku;
import io.github.mmm.sudoku.child.Field;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

/**
 * JavaFx view for {@link Field}.
 */
public class SudokuFxField extends Region implements SudokuFxView {

  private static final String STYLE_VALUE = "value";

  private static final String STYLE_FIELD = "field";

  private static final String STYLE_GIVEN = "given";

  private static final String STYLE_CANDIDATES = "candidates";

  private static final String STYLE_SELECTED = "selected";

  private static final String STYLE_HIGHLIGHTED = "highlighted";

  private final SudokuFxBoard fxBoard;

  private final Field field;

  private final Label text;

  /**
   * The constructor.
   *
   * @param fxBoard the {@link SudokuFxBoard} owning this field.
   * @param field the {@link Field} to present.
   */
  public SudokuFxField(SudokuFxBoard fxBoard, Field field) {

    super();
    this.fxBoard = fxBoard;
    this.field = field;
    getStyleClass().add(STYLE_FIELD);
    if (this.field.isGiven()) {
      getStyleClass().add(STYLE_GIVEN);
    }
    for (String style : field.getStyles()) {
      getStyleClass().add(style);
    }
    setOnMouseClicked(this::onClick);
    this.text = new Label();
    GridPane.setHalignment(this.text, HPos.CENTER);
    getChildren().add(this.text);
    update();
  }

  @Override
  public boolean isResizable() {

    return true;
  }

  private void onClick(MouseEvent e) {

    this.fxBoard.selectField(this);
  }

  /**
   * @return the {@link Field} presented by this view.
   */
  public Field getField() {

    return this.field;
  }

  @Override
  public Sudoku getSudoku() {

    return this.field.getSudoku();
  }

  /**
   * @param selected the selection flag.
   */
  public void setSelected(boolean selected) {

    if (selected) {
      getStyleClass().add(STYLE_SELECTED);
    } else {
      getStyleClass().remove(STYLE_SELECTED);
    }
  }

  @Override
  public void update() {

    Sudoku sudoku = getSudoku();
    int value = this.field.getValue();
    String styleRemove = STYLE_CANDIDATES;
    String styleAdd = STYLE_VALUE;
    if (value > 0) {
      this.text.setText(sudoku.getSymbol(value));
    } else {
      styleRemove = STYLE_VALUE;
      styleAdd = STYLE_CANDIDATES;
      int size = sudoku.getSize();
      int base = sudoku.getBase();
      int rowCount = 0;
      StringBuilder sb = new StringBuilder(size * 2);
      for (int i = 1; i <= size; i++) {
        if (rowCount == base) {
          sb.append('\n');
          rowCount = 0;
        } else {
          if (i > 1) {
            sb.append(' ');
          }
        }
        rowCount++;
        if (this.field.hasCandidate(i)) {
          sb.append(sudoku.getSymbol(i));
        } else {
          sb.append(' ');
        }
      }
      this.text.setText(sb.toString());
    }
    getStyleClass().remove(styleRemove);
    getStyleClass().add(styleAdd);
  }

  /**
   * @param value the selected value to highlight.
   */
  public void highlight(int value) {

    if (this.field.hasCandidate(value)) {
      getStyleClass().add(STYLE_HIGHLIGHTED);
    } else {
      getStyleClass().remove(STYLE_HIGHLIGHTED);
    }
  }

}
