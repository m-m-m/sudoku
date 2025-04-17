package io.github.mmm.sudoku.solution;

import java.util.ArrayList;
import java.util.List;

import io.github.mmm.sudoku.Sudoku;
import io.github.mmm.sudoku.common.Candidates;
import io.github.mmm.sudoku.field.AggregatedFieldGroup;
import io.github.mmm.sudoku.field.Field;
import io.github.mmm.sudoku.field.FieldGroup;
import io.github.mmm.sudoku.partition.Partition;
import io.github.mmm.sudoku.partition.PartitionMap;
import io.github.mmm.sudoku.partitioning.Partitioning;

/**
 * Solver for a {@link Sudoku}.
 */
public class SudokuSolver {

  public Hint findHint(Sudoku sudoku) {

    Hint hint = findSingle(sudoku);
    if (hint == null) {
      hint = findByPartitionMap(sudoku);
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
            return new Hint(1, List.of(step));
          }
        }
      }
    }
    return null;
  }

  private Hint findByPartitionMap(Sudoku sudoku) {

    int size = sudoku.getSize();
    Hint easiestHint = null;
    for (Partitioning partitioning : sudoku) {
      for (Partition partition : partitioning) {
        PartitionMap partitionMap = partition.computeMap();
        Hint hint = findHiddenSingle(partitionMap);
        if (hint != null) {
          return hint;
        }
        easiestHint = findNakedPair(partitionMap, easiestHint);
      }
    }
    return null;
  }

  private Hint findHiddenSingle(PartitionMap partitionMap) {

    for (AggregatedFieldGroup tuple : partitionMap.getByTuples(1)) {
      int value = tuple.getCandidate();
      assert (tuple.getFieldCount() == 1);
      Field field = tuple.getField(1);
      assert (field.getSingle() == value);
      Partition partition = partitionMap.getPartition();
      HintStepPartitionMarking stepPartition = new HintStepPartitionMarking(
          "There is a hidden single in " + partition.getPartitioning().getName() + " " + partition.getIndex(),
          partition);
      HintStepFieldSetValue stepValue = new HintStepFieldSetValue(
          "The hidden single is in field " + field.getX() + "x" + field.getY(), field, value);
      return new Hint(2, List.of(stepPartition, stepValue));
    }
    return null;
  }

  private Hint findNakedPair(PartitionMap partitionMap, Hint easiestHint) {

    int difficulty = 10;
    if ((easiestHint != null) && (easiestHint.getDifficulty() <= difficulty)) {
      return easiestHint;
    }
    FieldGroup nakedTuple = partitionMap.getNakedTuple(2);
    assert (nakedTuple.getFieldCount() == 2);
    Field pair1 = nakedTuple.getField(1);
    Field pair2 = nakedTuple.getField(2);
    assert (pair1.getIncludedCandidateCount() == 2);
    assert (pair2.getIncludedCandidateCount() == 2);
    Candidates candidates = pair1.getCandidates();
    assert (pair2.getCandidates() == candidates);
    Partition partition = partitionMap.getPartition();
    Partitioning pairPartitioning = partition.getPartitioning();
    Sudoku sudoku = partition.getSudoku();
    int[] pair = candidates.toIncludedArray(sudoku.getSize());
    assert (pair.length == 2);
    int v1 = pair[0];
    int v2 = pair[1];
    // find all fields where the two candidates from the naked pair can be removed
    List<Field> fields = new ArrayList<>();
    addFields(fields, partition, pair1, pair2, v1, v2);
    for (Partitioning partitioning : sudoku) {
      if (partitioning != pairPartitioning) {
        Partition partition1 = pair1.getPartition(partitioning);
        Partition partition2 = pair2.getPartition(partitioning);
        if (partition1 == partition2) {
          addFields(fields, partition1, pair1, pair2, v1, v2);
        }
      }
    }
    if (fields.size() > 0) {
      HintStepPartitionMarking stepPartition = new HintStepPartitionMarking(
          "There is a naked pair in " + partition.getPartitioning().getName() + " " + partition.getIndex(), partition);
      HintStepFieldsMarking stepMarkPairs = new HintStepFieldsMarking("The naked pair is in these fields", pair1,
          pair2);
      HintStepFieldsExcludeCandidates stepExcludeCandidates = new HintStepFieldsExcludeCandidates(
          "The values from the pair can be excluded from all these fields.", fields.toArray(Field[]::new), v1, v2);
      return new Hint(difficulty, List.of(stepPartition, stepMarkPairs, stepExcludeCandidates));
    }
    return null;
  }

  private void addFields(List<Field> fields, FieldGroup group, Field pair1, Field pair2, int v1, int v2) {

    for (Field field : group) {
      if ((field != pair1) && (field != pair2)) {
        if (field.hasCandidate(v1) || field.hasCandidate(v2)) {
          fields.add(field);
        }
      }
    }
  }

}
