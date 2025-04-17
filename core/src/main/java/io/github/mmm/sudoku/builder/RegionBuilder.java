package io.github.mmm.sudoku.builder;

import io.github.mmm.sudoku.dimension.AbstractDimension;
import io.github.mmm.sudoku.dimension.RegularDimension;
import io.github.mmm.sudoku.partitioning.FlexiblePartitioning.RegionFactory;
import io.github.mmm.sudoku.partitioning.Region;

/**
 * {@link Builder} for {@link Region}.
 */
public abstract class RegionBuilder extends IntermediateBuilder<LayerBuilder> {

  AbstractDimension dim;

  final RegionFactory regionFactory;

  RegionBuilder(RegionFactory regionFactory) {

    super();
    this.dim = RegularDimension.NORMAL;
    this.regionFactory = regionFactory;
  }

  /**
   * Set {@link RegularDimension#EASY} (4x4).
   *
   * @return the {@link LayerBuilder} for fluent builder API calls.
   */
  public LayerBuilder with4x4() {

    this.dim = RegularDimension.EASY;
    return next();
  }

  /**
   * Set {@link RegularDimension#NORMAL} (9x9).
   *
   * @return the {@link LayerBuilder} for fluent builder API calls.
   */
  public LayerBuilder with9x9() {

    this.dim = RegularDimension.NORMAL; // just for explicitness
    return next();
  }

  /**
   * Set {@link RegularDimension#HEX} (16x16).
   *
   * @return the {@link LayerBuilder} for fluent builder API calls.
   */
  public LayerBuilder with16x16() {

    this.dim = RegularDimension.EASY;
    return next();
  }

  @Override
  protected LayerBuilder next() {

    return new LayerBuilder(this);
  }

}
