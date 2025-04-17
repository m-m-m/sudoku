package io.github.mmm.sudoku.fx;

import io.github.mmm.sudoku.Sudoku;
import io.github.mmm.sudoku.event.SudokuChangeEvent;
import io.github.mmm.sudoku.event.SudokuChangeEventSetValue;
import io.github.mmm.sudoku.event.SudokuEvent;
import io.github.mmm.sudoku.event.SudokuEventListener;
import io.github.mmm.sudoku.event.SudokuEventSelectField;
import io.github.mmm.sudoku.field.Field;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * Controller and view for a {@link Sudoku} puzzle.
 */
public class SudokuFxPuzzle extends BorderPane implements SudokuFxView, SudokuEventListener {

  private final Sudoku sudoku;

  private final SudokuFxBoard board;

  private final SudokuFxHistoryButtons historyButtons;

  private final SudokuFxSymbolButtons mainButtons;

  private final SudokuFxSymbolButtons candidateButtons;

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
    this.historyButtons = new SudokuFxHistoryButtons(this);
    this.mainButtons = new SudokuFxSymbolButtons(this, false);
    this.candidateButtons = new SudokuFxSymbolButtons(this, true);
    setCenter(this.board);
    VBox buttonBox = new VBox();
    buttonBox.setSpacing(10);
    buttonBox.getChildren().add(this.historyButtons);
    buttonBox.getChildren().add(this.mainButtons);
    buttonBox.getChildren().add(this.candidateButtons);
    setRight(buttonBox);
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

  private void selectField(Field field) {

    selectField(this.board.getFxField(field));
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
    this.historyButtons.update();
    return true;
  }

  /**
   * @param candidate the {@link Field#getValue() value} {@link Field#hasCandidate(int) candidate} to toggle.
   * @return {@code true} if the candidate has been successfully toggled, {@code false} otherwise (no field selected,
   *         selected field {@link Field#hasValue() has value}, etc.).
   * @see Field#toggleCandidate(int)
   */
  public boolean toggleCandidate(int candidate) {

    if (this.selectedFxField == null) {
      return false;
    }
    Field field = this.selectedFxField.getField();
    if (field.hasValue()) {
      return false;
    }
    this.sudoku.toggleCandidate(field, candidate);
    this.historyButtons.update();
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

  /**
   * @param value the new value of {@link #getSelectedValue()}.
   */
  public void setSelectedValue(int value) {

    if (this.selectedValue == value) {
      return;
    }
    int size = this.sudoku.getSize();
    Field valueField = null;
    Field singleField = null;
    Field candidateField = null;
    for (int y = 1; y <= size; y++) {
      for (int x = 1; x <= size; x++) {
        Field field = this.sudoku.getField(x, y);
        if (field.hasCandidate(value)) {
          if (field.getValue() == value) {
            if (valueField == null) {
              valueField = field;
            }
          } else if (field.getSingle() == value) {
            if (singleField == null) {
              singleField = field;
            }
          } else {
            if (candidateField == null) {
              candidateField = field;
            }
          }
        }
      }
    }
    if (singleField != null) {
      selectField(singleField);
    } else if (valueField != null) {
      selectField(valueField);
    } else if (candidateField != null) {
      selectField(candidateField);
    } else {
      this.selectedValue = value;
    }
  }

}
