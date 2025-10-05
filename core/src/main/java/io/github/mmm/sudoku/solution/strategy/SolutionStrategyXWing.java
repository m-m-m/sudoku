/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.solution.strategy;

import java.util.ArrayList;
import java.util.List;

import io.github.mmm.sudoku.Sudoku;
import io.github.mmm.sudoku.common.Candidates;
import io.github.mmm.sudoku.field.AggregatedFieldGroup;
import io.github.mmm.sudoku.field.Field;
import io.github.mmm.sudoku.partition.Partition;
import io.github.mmm.sudoku.partition.PartitionMap;
import io.github.mmm.sudoku.partitioning.Partitioning;
import io.github.mmm.sudoku.solution.Hint;

/**
 * {@link SolutionStrategy} to find an intersection with another {@link Partition}.
 */
public class SolutionStrategyXWing extends SolutionStrategy {

  /** The singleton instance. */
  public static final SolutionStrategyXWing INSTANCE = new SolutionStrategyXWing();

  private SolutionStrategyXWing() {

  }

  @Override
  public Hint findHint(Sudoku sudoku) {

    Hint hint = findHint(sudoku, sudoku.getPartitioning(1));
    if (hint == null) {
      hint = findHint(sudoku, sudoku.getPartitioning(2));
    }
    return hint;
  }

  private Hint findHint(Sudoku sudoku, Partitioning partitioning) {

    int size = sudoku.getSize();
    // for (int )
    AggregatedFieldGroup[] pairsByCandidate = new AggregatedFieldGroup[size];
    for (Partition partition : partitioning) {
      PartitionMap partitionMap = partition.getPartitionMap();
      for (int value = 1; value <= size; value++) {
        AggregatedFieldGroup pair = partitionMap.getByCandidate(value);
        if (pair.getFieldCount() == 2) {
          int candidate = pair.getCandidate();
          if (pairsByCandidate[candidate - 1] == null) {
            pairsByCandidate[candidate - 1] = pair;
          } else {
            AggregatedFieldGroup otherPair = pairsByCandidate[candidate - 1];
            Hint hint = findHint(candidate, pair, otherPair, partitioning);
            if (hint != null) {
              return hint;
            }
          }
        }
      }
    }
    return null;
  }

  private Hint findHint(int candidate, AggregatedFieldGroup pair1, AggregatedFieldGroup pair2,
      Partitioning partitioning) {

    Field f11 = pair1.getField(1);
    Field f12 = pair1.getField(2);
    Field f21 = pair2.getField(1);
    Field f22 = pair2.getField(2);
    int x1 = f11.getX();
    int y1 = f11.getY();
    int x2 = f12.getX();
    int y2 = f12.getY();
    List<Field> fields = null;
    if (x1 == x2) {
      x2 = f21.getX();
      assert (x2 != x1);
      assert (x2 == f22.getX());
      assert (y1 != y2);
      int y = f21.getY();
      if (y == y1) {
        if (f22.getY() != y2) {
          return null;
        }
      } else if (y == y2) {
        if (f22.getY() != y1) {
          return null;
        }
        Field swap = f21;
        f21 = f22;
        f22 = swap;
      } else {
        return null;
      }
      fields = addFields(fields, candidate, f11.getPartition(2), f11, f21);
      fields = addFields(fields, candidate, f12.getPartition(2), f12, f22);
    } else {
      assert (y1 == y2);
      y2 = f21.getY();
      assert (y2 == f22.getY());
      int x = f21.getX();
      if (x == x1) {
        if (f22.getX() != x2) {
          return null;
        }
      } else if (x == x2) {
        if (f22.getX() != x1) {
          return null;
        }
        Field swap = f21;
        f21 = f22;
        f22 = swap;
      } else {
        return null;
      }
      fields = addFields(fields, candidate, f11.getPartition(1), f11, f21);
      fields = addFields(fields, candidate, f12.getPartition(1), f12, f22);
    }
    if (fields != null) {
      return hint(mark(f11, f12, f21, f22), exclude(Candidates.ofValue(candidate), fields));
    }
    return null;
  }

  private List<Field> addFields(List<Field> fields, int candidate, Partition partition, Field field1, Field field2) {

    for (Field field : partition) {
      if ((field != field1) && (field != field2)) {
        if (!field.hasValue() && field.hasCandidate(candidate)) {
          if (fields == null) {
            fields = new ArrayList<>();
          }
          fields.add(field);
        }
      }
    }
    return fields;
  }

  @Override
  public int getDifficulty() {

    return 40;
  }

  @Override
  public String getName() {

    return "X-wing";
  }

}
