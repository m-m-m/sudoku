package io.github.mmm.sudoku.solution;

import java.util.List;

import io.github.mmm.sudoku.Sudoku;
import io.github.mmm.sudoku.child.Field;
import io.github.mmm.sudoku.child.Partitioning;

/**
 * Solver for a {@link Sudoku}.
 */
public class SudokuSolver {

  public Hint findHint(Sudoku sudoku) {

    Hint hint = findSingle(sudoku);
    if (hint == null) {
      hint = findHiddenSingle(sudoku);
    }
    // TODO add more strategies (see ct' Maganzin 2025 7 & 8)
    return hint;
  }

  private Hint findSingle(Sudoku sudoku) {

    int size = sudoku.getSize();
    for (int y = 1; y <= size; y++) {
      for (int x = 1; x <= size; x++) {
        Field field = sudoku.getField(x, y);
        if (!field.hasValue()) {
          int value = field.getSingle();
          if (value != Field.UNDEFINED) {
            HintStepFieldSetValue step = new HintStepFieldSetValue(
                "There is a single in field " + field.getX() + "x" + field.getY(), field, value);
            return new Hint(Difficulty.VERY_EASY, List.of(step));
          }
        }
      }
    }
    return null;
  }

  private Hint findHiddenSingle(Sudoku sudoku) {

    int size = sudoku.getSize();
    int[] valueCounts = new int[size];
    for (Partitioning partitioning : sudoku.getPartitionings()) {
      int partitionCount = partitioning.getPartitionCount();
      for (int partitionIndex = 1; partitionIndex <= partitionCount; partitionIndex++) {
        for (int fieldIndex = 1; fieldIndex <= size; fieldIndex++) {
          Field field = partitioning.getPartitionField(partitionIndex, fieldIndex);
          countCandidates(field, valueCounts);
        }
        for (int value = 1; value <= valueCounts.length; value++) {
          int count = valueCounts[value - 1];
          if (count == 1) {
            Field field = null;
            for (int fieldIndex = 1; fieldIndex <= size; fieldIndex++) {
              field = partitioning.getPartitionField(partitionIndex, fieldIndex);
              if (field.hasCandidate(value)) {
                break;
              }
            }
            HintStepPartitionMarking stepPartition = new HintStepPartitionMarking(
                "There is a hidden single in " + partitioning.getName() + " " + partitionIndex, partitioning,
                partitionIndex);
            HintStepFieldSetValue stepValue = new HintStepFieldSetValue(
                "The hidden single is in field " + field.getX() + "x" + field.getY(), field, value);
            return new Hint(Difficulty.EASY, List.of(stepPartition, stepValue));
          }
        }
      }
    }
    return null;
  }

  private void countCandidates(Field field, int[] valueCounts) {

    if (field.hasValue()) {
      int value = field.getValue();
      valueCounts[value - 1] = Field.UNDEFINED;
      return;
    }
    for (int value = 1; value <= valueCounts.length; value++) {
      int count = valueCounts[value - 1];
      if ((count != Field.UNDEFINED) && (count < 2)) {
        if (field.hasCandidate(value)) {
          valueCounts[value - 1]++;
        }
      }
    }
  }
}
