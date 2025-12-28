/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.dimension;

import org.junit.jupiter.api.Test;

/**
 * Test of {@link RectangularDimension}.
 */
class RectangularDimensionTest extends DimensionTest {

  /** Test of {@link RectangularDimension#D6}. */
  @Test
  void test6() {

    // arrange
    Dimension dimension = RectangularDimension.D6;

    // act + assert
    verify(dimension);
    assertThat(dimension.getSize()).isEqualTo(6);
    assertThat(dimension.getBoxWidth()).isEqualTo(3);
    assertThat(dimension.getBoxHeight()).isEqualTo(2);
    assertThat(dimension.getBoxSize()).isEqualTo(3);
  }

  /** Test of {@link RectangularDimension#D8}. */
  @Test
  void test8() {

    // arrange
    Dimension dimension = RectangularDimension.D8;

    // act + assert
    verify(dimension);
    assertThat(dimension.getSize()).isEqualTo(8);
    assertThat(dimension.getBoxWidth()).isEqualTo(4);
    assertThat(dimension.getBoxHeight()).isEqualTo(2);
    assertThat(dimension.getBoxSize()).isEqualTo(3);
  }

  /** Test of {@link RectangularDimension#D10}. */
  @Test
  void test10() {

    // arrange
    Dimension dimension = RectangularDimension.D10;

    // act + assert
    verify(dimension);
    assertThat(dimension.getSize()).isEqualTo(10);
    assertThat(dimension.getBoxWidth()).isEqualTo(5);
    assertThat(dimension.getBoxHeight()).isEqualTo(2);
    assertThat(dimension.getBoxSize()).isEqualTo(4);
  }

  /** Test of {@link RectangularDimension#D12}. */
  @Test
  void test12() {

    // arrange
    Dimension dimension = RectangularDimension.D12;

    // act + assert
    verify(dimension);
    assertThat(dimension.getSize()).isEqualTo(12);
    assertThat(dimension.getBoxWidth()).isEqualTo(4);
    assertThat(dimension.getBoxHeight()).isEqualTo(3);
    assertThat(dimension.getBoxSize()).isEqualTo(4);
  }

  /** Test of {@link RectangularDimension#D14}. */
  @Test
  void test14() {

    // arrange
    Dimension dimension = RectangularDimension.D14;

    // act + assert
    verify(dimension);
    assertThat(dimension.getSize()).isEqualTo(14);
    assertThat(dimension.getBoxWidth()).isEqualTo(7);
    assertThat(dimension.getBoxHeight()).isEqualTo(2);
    assertThat(dimension.getBoxSize()).isEqualTo(4);
  }

  /** Test of {@link RectangularDimension#D15}. */
  @Test
  void test15() {

    // arrange
    Dimension dimension = RectangularDimension.D15;

    // act + assert
    verify(dimension);
    assertThat(dimension.getSize()).isEqualTo(15);
    assertThat(dimension.getBoxWidth()).isEqualTo(5);
    assertThat(dimension.getBoxHeight()).isEqualTo(3);
    assertThat(dimension.getBoxSize()).isEqualTo(4);
  }

}
