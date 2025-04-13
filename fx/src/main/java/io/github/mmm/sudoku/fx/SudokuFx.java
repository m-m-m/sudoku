/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.fx;

import io.github.mmm.sudoku.HyperSudoku;
import io.github.mmm.sudoku.Sudoku;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * JavaFx GUI for {@link Sudoku} puzzle game.
 */
public class SudokuFx extends Application implements ValueCompletion {

  private SudokuFxBoard board;

  private SudokuFxSymbolButtons mainButtons;

  private Sudoku sudoku;

  /**
   * @param args the main arguments.
   */
  public static void main(String[] args) {

    Application.launch(SudokuFx.class, args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {

    this.sudoku = generateSudoku();
    this.board = new SudokuFxBoard(this);
    this.mainButtons = new SudokuFxSymbolButtons(this.board);

    BorderPane root = new BorderPane();
    root.setCenter(this.board);
    root.setRight(this.mainButtons);
    Scene scene = new Scene(root, 500, 500);
    scene.getStylesheets().add("file:src/main/resources/css/application.css");
    primaryStage.setScene(scene);
    primaryStage.setTitle("SudokuFx");
    primaryStage.show();
    primaryStage.setMinHeight(primaryStage.getHeight());
    primaryStage.setMinWidth(primaryStage.getWidth());
  }

  /**
   * @return the {@link Sudoku}.
   */
  public Sudoku getSudoku() {

    return this.sudoku;
  }

  private static Sudoku generateSudoku() {

    Sudoku sudoku = new HyperSudoku();
    // implement puzzle generator
    sudoku.setFieldGivenValue(2, 1, 7);
    sudoku.setFieldGivenValue(4, 1, 9);
    sudoku.setFieldGivenValue(4, 2, 1);
    sudoku.setFieldGivenValue(7, 2, 6);
    sudoku.setFieldGivenValue(1, 3, 5);
    sudoku.setFieldGivenValue(2, 3, 2);
    sudoku.setFieldGivenValue(4, 4, 5);
    sudoku.setFieldGivenValue(5, 4, 4);
    sudoku.setFieldGivenValue(2, 5, 1);
    sudoku.setFieldGivenValue(3, 5, 3);
    sudoku.setFieldGivenValue(7, 5, 7);
    sudoku.setFieldGivenValue(8, 5, 5);
    sudoku.setFieldGivenValue(5, 6, 2);
    sudoku.setFieldGivenValue(6, 6, 3);
    sudoku.setFieldGivenValue(8, 7, 6);
    sudoku.setFieldGivenValue(9, 7, 4);
    sudoku.setFieldGivenValue(3, 8, 1);
    sudoku.setFieldGivenValue(6, 8, 7);
    sudoku.setFieldGivenValue(6, 9, 8);
    sudoku.setFieldGivenValue(8, 9, 9);
    return sudoku;
  }

  @Override
  public void onValueCompletion(int value, boolean completed) {

    this.mainButtons.onValueCompletion(value, completed);
  }

}
