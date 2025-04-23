package io.github.mmm.sudoku.builder;

import io.github.mmm.sudoku.dimension.AbstractDimension;
import io.github.mmm.sudoku.dimension.RectangularDimension;
import io.github.mmm.sudoku.dimension.SquareDimension;
import io.github.mmm.sudoku.partitioning.FlexiblePartitioning.RegionFactory;
import io.github.mmm.sudoku.partitioning.Region;

/**
 * {@link Builder} for {@link Region}.
 */
public abstract class RegionBuilder extends IntermediateBuilder<SquareLayerBuilder> {

  AbstractDimension dim;

  final RegionFactory regionFactory;

  RegionBuilder(RegionFactory regionFactory) {

    super();
    this.dim = SquareDimension.D9;
    this.regionFactory = regionFactory;
  }

  /**
   * Set {@link SquareDimension#D4} (4x4).
   *
   * @return the {@link SquareLayerBuilder} for fluent builder API calls.
   */
  public SquareLayerBuilder with4x4() {

    this.dim = SquareDimension.D4;
    return next();
  }

  /**
   * Set {@link RectangularDimension#D6} (6x6).
   *
   * @return the {@link LayerBuilder} for fluent builder API calls.
   */
  public LayerBuilder with6x6() {

    this.dim = RectangularDimension.D6;
    return new LayerBuilder(this);
  }

  /**
   * Use {@link RectangularDimension#D8 8x8}.
   *
   * @return the {@link LayerBuilder} for fluent builder API calls.
   */
  public LayerBuilder with8x8() {

    this.dim = RectangularDimension.D8;
    return new LayerBuilder(this);
  }

  /**
   * Set {@link SquareDimension#D9} (9x9).
   *
   * @return the {@link SquareLayerBuilder} for fluent builder API calls.
   */
  public SquareLayerBuilder with9x9() {

    this.dim = SquareDimension.D9; // just for explicitness
    return next();
  }

  /**
   * Set {@link RectangularDimension#D10} (10x10).
   *
   * @return the {@link LayerBuilder} for fluent builder API calls.
   */
  public LayerBuilder with10x10() {

    this.dim = RectangularDimension.D10; // just for explicitness
    return new LayerBuilder(this);
  }

  /**
   * Set {@link SquareDimension#D16} (16x16).
   *
   * @return the {@link SquareLayerBuilder} for fluent builder API calls.
   */
  public SquareLayerBuilder with16x16() {

    this.dim = SquareDimension.D16;
    return next();
  }

  @Override
  protected SquareLayerBuilder next() {

    return new SquareLayerBuilder(this);
  }

}
