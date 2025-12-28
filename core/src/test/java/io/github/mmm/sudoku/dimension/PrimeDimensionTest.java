/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.dimension;

import org.junit.jupiter.api.Test;

/**
 * Test of {@link PrimeDimension}.
 */
class PrimeDimensionTest extends DimensionTest {

  /** Test of {@link PrimeDimension#D5}. */
  @Test
  void test5() {

    // arrange
    Dimension dimension = PrimeDimension.D5;

    // act + assert
    verify(dimension);
    assertThat(dimension.getSize()).isEqualTo(5);
    assertThat(dimension.getBoxSize()).isEqualTo(3);
  }

  /** Test of {@link PrimeDimension#D7}. */
  @Test
  void test7() {

    // arrange
    Dimension dimension = PrimeDimension.D7;

    // act + assert
    verify(dimension);
    assertThat(dimension.getSize()).isEqualTo(7);
    assertThat(dimension.getBoxSize()).isEqualTo(3);
  }

  /** Test of {@link PrimeDimension#D11}. */
  @Test
  void test11() {

    // arrange
    Dimension dimension = PrimeDimension.D11;

    // act + assert
    verify(dimension);
    assertThat(dimension.getSize()).isEqualTo(11);
    assertThat(dimension.getBoxSize()).isEqualTo(4);
  }

  /** Test of {@link PrimeDimension#D13}. */
  @Test
  void test13() {

    // arrange
    Dimension dimension = PrimeDimension.D13;

    // act + assert
    verify(dimension);
    assertThat(dimension.getSize()).isEqualTo(13);
    assertThat(dimension.getBoxSize()).isEqualTo(4);
  }

  /** Test of {@link PrimeDimension#D17}. */
  @Test
  void test17() {

    // arrange
    Dimension dimension = PrimeDimension.D17;

    // act + assert
    verify(dimension);
    assertThat(dimension.getSize()).isEqualTo(17);
    assertThat(dimension.getBoxSize()).isEqualTo(5);
  }

  /** Test of {@link PrimeDimension#D19}. */
  @Test
  void test19() {

    // arrange
    Dimension dimension = PrimeDimension.D19;

    // act + assert
    verify(dimension);
    assertThat(dimension.getSize()).isEqualTo(19);
    assertThat(dimension.getBoxSize()).isEqualTo(5);
  }

  /** Test of {@link PrimeDimension#D23}. */
  @Test
  void test23() {

    // arrange
    Dimension dimension = PrimeDimension.D23;

    // act + assert
    verify(dimension);
    assertThat(dimension.getSize()).isEqualTo(23);
    assertThat(dimension.getBoxSize()).isEqualTo(5);
  }

  /** Test of {@link PrimeDimension#D29}. */
  @Test
  void test29() {

    // arrange
    Dimension dimension = PrimeDimension.D29;

    // act + assert
    verify(dimension);
    assertThat(dimension.getSize()).isEqualTo(29);
    assertThat(dimension.getBoxSize()).isEqualTo(6);
  }

  /** Test of {@link PrimeDimension#D31}. */
  @Test
  void test31() {

    // arrange
    Dimension dimension = PrimeDimension.D31;

    // act + assert
    verify(dimension);
    assertThat(dimension.getSize()).isEqualTo(31);
    assertThat(dimension.getBoxSize()).isEqualTo(6);
  }

  /** Test of {@link PrimeDimension#D37}. */
  @Test
  void test37() {

    // arrange
    Dimension dimension = PrimeDimension.D37;

    // act + assert
    verify(dimension);
    assertThat(dimension.getSize()).isEqualTo(37);
    assertThat(dimension.getBoxSize()).isEqualTo(7);
  }

}
