/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.dimension;

import org.junit.jupiter.api.Test;

/**
 * Test of {@link RegularDimension}.
 */
public class RegularDimensionTest extends DimensionTest {

  /** Test of {@link RegularDimension#EASY}. */
  @Test
  public void testEasy() {

    // arrange
    RegularDimension dimension = RegularDimension.EASY;
    // act + assert
    assertThat(dimension.getSize()).isEqualTo(4);
    assertThat(dimension.getBase()).isEqualTo(2);
    assertThat(dimension).hasToString("4[2]");
    assertThat(getAlphabet(dimension)).isEqualTo("1234");
  }

  /** Test of {@link RegularDimension#NORMAL}. */
  @Test
  public void testNormal() {

    // arrange
    RegularDimension dimension = RegularDimension.NORMAL;
    // act + assert
    assertThat(dimension.getSize()).isEqualTo(9);
    assertThat(dimension.getBase()).isEqualTo(3);
    assertThat(dimension).hasToString("9[3]");
    assertThat(getAlphabet(dimension)).isEqualTo("123456789");
  }

  /** Test of {@link RegularDimension#HEX}. */
  @Test
  public void testHex() {

    // arrange
    RegularDimension dimension = RegularDimension.HEX;
    // act + assert
    assertThat(dimension.getSize()).isEqualTo(16);
    assertThat(dimension.getBase()).isEqualTo(4);
    assertThat(dimension).hasToString("16[4]");
    assertThat(getAlphabet(dimension)).isEqualTo("0123456789ABCDEF");
  }

}
