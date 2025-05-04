/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.partitioning;

import io.github.mmm.sudoku.Sudoku;
import io.github.mmm.sudoku.common.Candidates;
import io.github.mmm.sudoku.field.Field;
import io.github.mmm.sudoku.partition.Partition;
import io.github.mmm.sudoku.partition.Shape;

/**
 * {@link Partitioning} that is not fixed (build-in) in {@link Sudoku} but can be configured.
 */
public abstract class FlexiblePartitioning extends Partitioning {

  /**
   * The constructor.
   *
   * @param sudoku the {@link #getSudoku() sudoku}.
   * @param index the {@link #getIndex() index}.
   * @param function the {@link PartitionFunction}.
   */
  public FlexiblePartitioning(Sudoku sudoku, int index, PartitionFunction function) {

    super(sudoku, index, function);
  }

  /**
   * @param partitioning the owning {@link Partitioning}.
   * @param shapes
   * @return
   */
  protected static Partition[] createPartitions(Partitioning partitioning, Shape[] shapes) {

    int x = 1;
    int y = 1;
    Sudoku sudoku = partitioning.getSudoku();
    int size = sudoku.getSize();
    Candidates[] rows = new Candidates[size];
    Candidates all = sudoku.getAllCandidates();
    for (int i = 0; i < rows.length; i++) {
      rows[i] = all;
    }
    Partition[] partitions = new Partition[shapes.length];
    for (int i = 0; i < shapes.length; i++) {
      Shape shape = shapes[i];
      Field[] fields = new Field[shape.getFieldCount()];
      Shape current = shape;
      int fieldIndex = 0;
      int yi = 0;
      while (current != null) {
        int count = current.getCount();
        int x0 = x + current.getOffset();
        assert (x0 > 0);
        for (int xi = 0; xi < count; xi++) {
          int fieldX = x0 + xi;
          int fieldY = y + yi;
          fields[fieldIndex++] = sudoku.getField(fieldX, fieldY);
          Candidates candidates = rows[fieldY - 1];
          Candidates newCandidates = candidates.exclude(fieldX);
          assert (newCandidates != candidates); // every field should be used only once
          rows[fieldY - 1] = newCandidates;
        }
        current = current.getNext();
        yi++;
      }
      assert (fieldIndex == fields.length);
      partitions[i] = new Partition(partitioning, i + 1, shape, fields);
      while (y <= size && rows[y - 1].getInclusionCount() == 0) {
        y++;
      }
      if (i == partitions.length) {
        assert (y == size + 1);
      } else {
        x = rows[y - 1].getLowestCandidate();
        assert (x <= size);
      }
    }
    return partitions;
  }

  /**
   * Factory of a specific type of {@link FlexiblePartitioning}.
   */
  @FunctionalInterface
  public interface PartitioningFactory {

    /**
     * @param sudoku the {@link Sudoku}.
     * @param index the {@link #getIndex() index}.
     * @return the new {@link FlexiblePartitioning} for the {@link Sudoku}.
     */
    FlexiblePartitioning create(Sudoku sudoku, int index);

  }

  /**
   * Factory of a specific type of {@link Region}.
   */
  public interface RegionFactory extends PartitioningFactory {

    @Override
    Region create(Sudoku sudoku, int index);
  }

  /**
   * Factory of a specific type of {@link Layer}.
   */
  public interface LayerFactory extends PartitioningFactory {

    @Override
    Layer create(Sudoku sudoku, int index);
  }

}