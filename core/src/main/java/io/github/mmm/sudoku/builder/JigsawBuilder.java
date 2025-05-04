package io.github.mmm.sudoku.builder;

import io.github.mmm.sudoku.Sudoku;
import io.github.mmm.sudoku.dimension.AbstractDimension;
import io.github.mmm.sudoku.dimension.PrimeDimension;
import io.github.mmm.sudoku.partition.Partition;
import io.github.mmm.sudoku.partition.Shape;
import io.github.mmm.sudoku.partitioning.Jigsaw;
import io.github.mmm.sudoku.partitioning.Region;

/**
 * Builder of a {@link Sudoku} using {@link Jigsaw} as {@link Region}.
 */
public class JigsawBuilder extends RegionBuilder {

  /**
   * The constructor.
   *
   * @param shapes the {@link Shape}s of the {@link Jigsaw} {@link Partition}s.
   */
  public JigsawBuilder(Shape[] shapes) {

    super((s, i) -> new Jigsaw(s, i, shapes));
  }

  /**
   * @param dimension the {@link AbstractDimension} of the {@link Sudoku}.
   * @return the {@link SquareLayerBuilder} for fluent builder API calls.
   */
  public SquareLayerBuilder with(AbstractDimension dimension) {

    this.dim = dimension;
    return next();
  }

  /**
   * Use {@link PrimeDimension#D5 5x5}.
   *
   * @return the {@link SquareLayerBuilder} for fluent builder API calls.
   */
  public SquareLayerBuilder with5x5() {

    this.dim = PrimeDimension.D5;
    return next();
  }

  /**
   * Use {@link PrimeDimension#D7 7x7}.
   *
   * @return the {@link SquareLayerBuilder} for fluent builder API calls.
   */
  public SquareLayerBuilder with7x7() {

    this.dim = PrimeDimension.D7;
    return next();
  }

  /**
   * Use {@link PrimeDimension#D11 11x11}.
   *
   * @return the {@link SquareLayerBuilder} for fluent builder API calls.
   */
  public SquareLayerBuilder with11x11() {

    this.dim = PrimeDimension.D11;
    return next();
  }

}
