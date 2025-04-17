package io.github.mmm.sudoku.builder;

import io.github.mmm.sudoku.Sudoku;
import io.github.mmm.sudoku.partitioning.FlexiblePartitioning.LayerFactory;

/**
 * TODO joerg This type ...
 *
 */
public class FinalBuilder implements Builder {

  private final SumBuilder sumBuilder;

  /**
   * The constructor.
   */
  public FinalBuilder(SumBuilder sumBuilder) {

    super();
    this.sumBuilder = sumBuilder;
  }

  @Override
  public Sudoku build() {

    LayerBuilder layerBuilder = this.sumBuilder.layerBuilder;
    RegionBuilder regionBuilder = layerBuilder.regionBuilder;
    return new Sudoku(regionBuilder.dim, regionBuilder.regionFactory,
        layerBuilder.layerFactories.toArray(LayerFactory[]::new));
  }

}
