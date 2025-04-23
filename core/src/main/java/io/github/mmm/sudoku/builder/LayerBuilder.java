package io.github.mmm.sudoku.builder;

import java.util.ArrayList;
import java.util.List;

import io.github.mmm.sudoku.partitioning.Color;
import io.github.mmm.sudoku.partitioning.FlexiblePartitioning.LayerFactory;
import io.github.mmm.sudoku.partitioning.Layer;
import io.github.mmm.sudoku.partitioning.X;
import io.github.mmm.sudoku.partitioning.X1;
import io.github.mmm.sudoku.partitioning.X2;

/**
 * {@link Builder} for {@link Layer}.
 */
public class LayerBuilder extends IntermediateBuilder<SumBuilder> {

  final RegionBuilder regionBuilder;

  final List<LayerFactory> layerFactories;

  LayerBuilder(RegionBuilder regionBuilder) {

    super();
    this.regionBuilder = regionBuilder;
    this.layerFactories = new ArrayList<>();
  }

  @Override
  protected SumBuilder next() {

    return new SumBuilder(this);
  }

  /**
   * Adds {@link Color}.
   *
   * @return the {@link SumBuilder} for fluent builder API calls.
   */
  public SumBuilder withColor() {

    this.layerFactories.add(Color.FACTORY);
    return next();
  }

  /**
   * Adds {@link X}.
   *
   * @return the {@link SumBuilder} for fluent builder API calls.
   */
  public SumBuilder withX() {

    this.layerFactories.add(X1.FACTORY);
    this.layerFactories.add(X2.FACTORY);
    return next();
  }

  /**
   * Adds only {@link X1}.
   *
   * @return the {@link SumBuilder} for fluent builder API calls.
   */
  public SumBuilder withX1() {

    this.layerFactories.add(X1.FACTORY);
    return next();
  }

  /**
   * Adds only {@link X2}.
   *
   * @return the {@link SumBuilder} for fluent builder API calls.
   */
  public SumBuilder withX2() {

    this.layerFactories.add(X1.FACTORY);
    return next();
  }

  /**
   * @param factory the (custom) {@link LayerFactory} to add.
   * @return the {@link SumBuilder} for fluent builder API calls.
   */
  protected SumBuilder with(LayerFactory factory) {

    this.layerFactories.add(factory);
    return next();
  }

}
