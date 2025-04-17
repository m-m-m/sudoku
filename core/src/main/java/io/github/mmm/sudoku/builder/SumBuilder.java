package io.github.mmm.sudoku.builder;

import java.util.ArrayList;
import java.util.List;

import io.github.mmm.sudoku.partitioning.FlexiblePartitioning.LayerFactory;
import io.github.mmm.sudoku.partitioning.Layer;

/**
 * {@link Builder} for {@link Layer}.
 */
public class SumBuilder extends IntermediateBuilder<Builder> {

  final LayerBuilder layerBuilder;

  final List<LayerFactory> sums;

  SumBuilder(LayerBuilder layerBuilder) {

    super();
    this.layerBuilder = layerBuilder;
    this.sums = new ArrayList<>();
  }

  @Override
  protected Builder next() {

    return new FinalBuilder(this);
  }

}
