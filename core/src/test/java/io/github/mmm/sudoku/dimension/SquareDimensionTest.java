/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.dimension;

import org.junit.jupiter.api.Test;

/**
 * Test of {@link SquareDimension}.
 */
class SquareDimensionTest extends DimensionTest {

  /** Test of {@link SquareDimension#D4}. */
  @Test
  void testEasy() {

    // arrange
    Dimension dimension = SquareDimension.D4;
    // act + assert
    verify(dimension);
    assertThat(dimension.getSize()).isEqualTo(4);
    assertThat(dimension.getBoxSize()).isEqualTo(2).isEqualTo(dimension.getBoxWidth())
        .isEqualTo(dimension.getBoxHeight());
    assertThat(getAlphabet(dimension)).isEqualTo("1,2,3,4");
  }

  /** Test of {@link SquareDimension#D9}. */
  @Test
  void testNormal() {

    // arrange
    Dimension dimension = SquareDimension.D9;
    // act + assert
    verify(dimension);
    assertThat(dimension.getSize()).isEqualTo(9);
    assertThat(dimension.getBoxSize()).isEqualTo(3).isEqualTo(dimension.getBoxWidth())
        .isEqualTo(dimension.getBoxHeight());
    assertThat(getAlphabet(dimension)).isEqualTo("1,2,3,4,5,6,7,8,9");
  }

  /** Test of {@link SquareDimension#D16}. */
  @Test
  void test16() {

    // arrange
    Dimension dimension = SquareDimension.D16;
    // act + assert
    verify(dimension);
    assertThat(dimension.getSize()).isEqualTo(16);
    assertThat(dimension.getBoxSize()).isEqualTo(4).isEqualTo(dimension.getBoxWidth())
        .isEqualTo(dimension.getBoxHeight());
    assertThat(getAlphabet(dimension)).isEqualTo("1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16");
  }

  /** Test of {@link SquareDimension#of(int, java.util.List)} for HEX 16x16. */
  @Test
  void test16Hex() {

    // arrange
    Dimension dimension = SquareDimension.of(4, AbstractDimension.toAlphabet("0123456789ABCDEF"));
    // act + assert
    verify(dimension);
    assertThat(dimension.getSize()).isEqualTo(16);
    assertThat(dimension.getBoxSize()).isEqualTo(4).isEqualTo(dimension.getBoxWidth())
        .isEqualTo(dimension.getBoxHeight());
    assertThat(getAlphabet(dimension)).isEqualTo("0,1,2,3,4,5,6,7,8,9,A,B,C,D,E,F");
  }

}
