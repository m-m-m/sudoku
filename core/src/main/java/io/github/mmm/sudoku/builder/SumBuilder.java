package io.github.mmm.sudoku.builder;

import io.github.mmm.sudoku.partition.Partition;
import io.github.mmm.sudoku.partition.SumShape;
import io.github.mmm.sudoku.partitioning.FlexiblePartitioning.LayerFactory;
import io.github.mmm.sudoku.partitioning.Layer;
import io.github.mmm.sudoku.partitioning.Sum;

/**
 * {@link Builder} for {@link Layer}.
 */
public class SumBuilder extends IntermediateBuilder<Builder> {

  final LayerBuilder layerBuilder;

  SumBuilder(LayerBuilder layerBuilder) {

    super();
    this.layerBuilder = layerBuilder;
  }

  /**
   * @param sums the {@link SumShape}s for the {@link Sum} {@link Partition}s.
   * @return the {@link Builder} for fluent-API calls.
   */
  public Builder withSums(SumShape... sums) {

    LayerFactory sumFactory = (s, i) -> new Sum(s, i, sums);
    this.layerBuilder.layerFactories.add(sumFactory);
    return next();
  }

  @Override
  protected Builder next() {

    return new FinalBuilder(this);
  }

}
