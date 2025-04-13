package io.github.mmm.sudoku.fx;

import io.github.mmm.sudoku.Sudoku;
import io.github.mmm.sudoku.child.Field;
import io.github.mmm.sudoku.event.SudokuChangeEvent;
import io.github.mmm.sudoku.event.SudokuChangeEventSetValue;
import io.github.mmm.sudoku.event.SudokuEvent;
import io.github.mmm.sudoku.event.SudokuEventListener;
import io.github.mmm.sudoku.event.SudokuEventSelectField;
import javafx.scene.layout.BorderPane;

/**
 * Controller and view for a {@link Sudoku} puzzle.
 */
public class SudokuFxPuzzle extends BorderPane implements SudokuFxView, SudokuEventListener {

  private final Sudoku sudoku;

  private final SudokuFxBoard board;

  private final SudokuFxSymbolButtons mainButtons;

  private SudokuFxField selectedFxField;

  private int selectedValue;

  /**
   * The constructor.
   *
   * @param sudoku the {@link #getSudoku() sudoku} to present.
   */
  public SudokuFxPuzzle(Sudoku sudoku) {

    super();
    this.sudoku = sudoku;
    this.board = new SudokuFxBoard(this);
    this.mainButtons = new SudokuFxSymbolButtons(this);
    setCenter(this.board);
    setRight(this.mainButtons);
    this.selectedValue = Field.UNDEFINED;
    sudoku.addListener(this);
  }

  @Override
  public Sudoku getSudoku() {

    return this.sudoku;
  }

  /**
   * @return the {@link SudokuFxBoard}.
   */
  public SudokuFxBoard getBoard() {

    return this.board;
  }

  /**
   * @return the main {@link SudokuFxSymbolButtons}.
   */
  public SudokuFxSymbolButtons getMainButtons() {

    return this.mainButtons;
  }

  @Override
  public void onEvent(SudokuEvent event) {

    if (event instanceof SudokuChangeEvent change) {
      Field field = change.getField();
      if (field == null) {
        return;
      }
      SudokuFxField fxField = this.board.getFxField(field);
      fxField.highlight(getSelectedValue());
      fxField.update();
      if (event instanceof SudokuChangeEventSetValue setValue) {
        int value;
        if (field.hasValue()) {
          value = field.getValue();
        } else {
          value = setValue.getOldValue();
          if (value == Field.UNDEFINED) {
            value = setValue.getNewValue();
          }
        }
        int valueCount = this.sudoku.getValueCount(value);
        boolean valueCompleted = valueCount == this.sudoku.getSize();
        this.mainButtons.onValueCompletion(value, valueCompleted);
      }
    } else if (event instanceof SudokuEventSelectField selectField) {
      Field field = selectField.getField();
      SudokuFxField fxField = this.board.getFxField(field);
      selectField(fxField);
    }
  }

  private void selectField(SudokuFxField fxField) {

    if (this.selectedFxField != null) {
      this.selectedFxField.setSelected(false);
    }
    if (this.selectedFxField == fxField) {
      // deselection
      this.selectedFxField = null;
      this.selectedValue = Field.UNDEFINED;
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
    return true;
  }

  /**
   * @return the currently selected value.
   */
  public int getSelectedValue() {

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
    return value;
  }

  @Override
  public void update() {

    this.board.update();
    // this.mainButtons.update();
  }

}
