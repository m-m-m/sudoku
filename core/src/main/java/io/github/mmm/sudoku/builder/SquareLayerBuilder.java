package io.github.mmm.sudoku.builder;

import io.github.mmm.sudoku.partitioning.FlexiblePartitioning.LayerFactory;
import io.github.mmm.sudoku.partitioning.Hyper;
import io.github.mmm.sudoku.partitioning.Layer;
import io.github.mmm.sudoku.partitioning.Percent;

/**
 * {@link Builder} for {@link Layer}.
 */
public class SquareLayerBuilder extends LayerBuilder {

  SquareLayerBuilder(RegionBuilder regionBuilder) {

    super(regionBuilder);
  }

  /**
   * Adds {@link Hyper}.
   *
   * @return the {@link SumBuilder} for fluent builder API calls.
   */
  public SumBuilder withHyper() {

    this.layerFactories.add(Hyper.FACTORY);
    return next();
  }

  /**
   * Adds {@link Percent}.
   *
   * @return the {@link SumBuilder} for fluent builder API calls.
   */
  public SumBuilder withPercent() {

    this.layerFactories.add(Percent.FACTORY);
    return next();
  }

  @Override
  public SumBuilder with(LayerFactory factory) {

    this.layerFactories.add(factory);
    return next();
  }

}
