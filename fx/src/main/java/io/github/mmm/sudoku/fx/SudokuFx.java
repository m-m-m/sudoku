/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.fx;

import io.github.mmm.sudoku.Sudoku;
import io.github.mmm.sudoku.solution.SudokuSolver;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFx GUI for {@link Sudoku} puzzle game.
 */
public class SudokuFx extends Application {

  private SudokuFxPuzzle fxPuzzle;

  /**
   * @param args the main arguments.
   */
  public static void main(String[] args) {

    Application.launch(SudokuFx.class, args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {

    Sudoku sudoku = generateSudoku();
    this.fxPuzzle = new SudokuFxPuzzle(sudoku);

    Scene scene = new Scene(this.fxPuzzle, 500, 500);
    scene.getStylesheets().add("file:src/main/resources/css/application.css");
    primaryStage.setScene(scene);
    primaryStage.setTitle("SudokuFx");
    primaryStage.show();
    primaryStage.setMinHeight(primaryStage.getHeight());
    primaryStage.setMinWidth(primaryStage.getWidth());
  }

  private static Sudoku generateSudoku() {

    Sudoku sudoku = generateHyperSudoku();
    boolean fillValues = false;
    SudokuSolver.get().solve(sudoku, fillValues);
    return sudoku;
  }

  private static Sudoku generateXSudoku() {

    Sudoku sudoku = Sudoku.builder().withBoxes().with9x9().withX().build();
    // TODO implement puzzle generator
    sudoku.setFieldGivenValue(2, 1, 7);
    sudoku.setFieldGivenValue(3, 1, 2);
    sudoku.setFieldGivenValue(4, 1, 4);
    sudoku.setFieldGivenValue(5, 1, 9);
    sudoku.setFieldGivenValue(7, 1, 5);
    sudoku.setFieldGivenValue(2, 2, 9);
    sudoku.setFieldGivenValue(7, 2, 2);
    sudoku.setFieldGivenValue(4, 3, 3);
    sudoku.setFieldGivenValue(8, 3, 1);
    sudoku.setFieldGivenValue(2, 4, 4);
    sudoku.setFieldGivenValue(9, 4, 1);
    sudoku.setFieldGivenValue(2, 5, 1);
    sudoku.setFieldGivenValue(3, 5, 3);
    sudoku.setFieldGivenValue(7, 5, 8);
    sudoku.setFieldGivenValue(8, 5, 5);
    sudoku.setFieldGivenValue(1, 6, 8);
    sudoku.setFieldGivenValue(8, 6, 2);
    sudoku.setFieldGivenValue(2, 7, 3);
    sudoku.setFieldGivenValue(6, 7, 4);
    sudoku.setFieldGivenValue(3, 8, 4);
    sudoku.setFieldGivenValue(8, 8, 6);
    sudoku.setFieldGivenValue(3, 9, 1);
    sudoku.setFieldGivenValue(5, 9, 2);
    sudoku.setFieldGivenValue(6, 9, 6);
    sudoku.setFieldGivenValue(7, 9, 3);
    sudoku.setFieldGivenValue(8, 9, 4);
    return sudoku;
  }

  private static Sudoku generateHyperSudoku() {

    Sudoku sudoku = Sudoku.builder().withBoxes().with9x9().withHyper().build();
    // TODO implement puzzle generator
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

}
