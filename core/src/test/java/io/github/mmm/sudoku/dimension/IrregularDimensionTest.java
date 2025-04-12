/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.dimension;

import org.junit.jupiter.api.Test;

/**
 * Test of {@link IrregularDimension}.
 */
public class IrregularDimensionTest extends DimensionTest {

  /** Test of {@link IrregularDimension#D5}. */
  @Test
  public void test5() {

    // arrange
    IrregularDimension dimension = IrregularDimension.D5;

    // act + assert
    assertThat(dimension.getSize()).isEqualTo(5);
    assertThat(dimension.getBase()).isEqualTo(3);
    assertThat(dimension).hasToString("5[3]");
    assertThat(getAlphabet(dimension)).isEqualTo("12345");
  }

  /** Test of {@link IrregularDimension#D6}. */
  @Test
  public void test6() {

    // arrange
    IrregularDimension dimension = IrregularDimension.D6;

    // act + assert
    assertThat(dimension.getSize()).isEqualTo(6);
    assertThat(dimension.getBase()).isEqualTo(3);
    assertThat(dimension).hasToString("6[3]");
    assertThat(getAlphabet(dimension)).isEqualTo("123456");
  }

  /** Test of {@link IrregularDimension#D7}. */
  @Test
  public void test7() {

    // arrange
    IrregularDimension dimension = IrregularDimension.D7;

    // act + assert
    assertThat(dimension.getSize()).isEqualTo(7);
    assertThat(dimension.getBase()).isEqualTo(3);
    assertThat(dimension).hasToString("7[3]");
    assertThat(getAlphabet(dimension)).isEqualTo("1234567");
  }

  /** Test of {@link IrregularDimension#D8}. */
  @Test
  public void test8() {

    // arrange
    IrregularDimension dimension = IrregularDimension.D8;

    // act + assert
    assertThat(dimension.getSize()).isEqualTo(8);
    assertThat(dimension.getBase()).isEqualTo(3);
    assertThat(dimension).hasToString("8[3]");
    assertThat(getAlphabet(dimension)).isEqualTo("12345678");
  }

  /** Test of {@link IrregularDimension#D10}. */
  @Test
  public void test10() {

    // arrange
    IrregularDimension dimension = IrregularDimension.D10;

    // act + assert
    assertThat(dimension.getSize()).isEqualTo(10);
    assertThat(dimension.getBase()).isEqualTo(4);
    assertThat(dimension).hasToString("10[4]");
    assertThat(getAlphabet(dimension)).isEqualTo("0123456789");
  }

  /** Test of {@link IrregularDimension#D11}. */
  @Test
  public void test11() {

    // arrange
    IrregularDimension dimension = IrregularDimension.D11;

    // act + assert
    assertThat(dimension.getSize()).isEqualTo(11);
    assertThat(dimension.getBase()).isEqualTo(4);
    assertThat(dimension).hasToString("11[4]");
    assertThat(getAlphabet(dimension)).isEqualTo("0123456789A");
  }

  /** Test of {@link IrregularDimension#D12}. */
  @Test
  public void test12() {

    // arrange
    IrregularDimension dimension = IrregularDimension.D12;

    // act + assert
    assertThat(dimension.getSize()).isEqualTo(12);
    assertThat(dimension.getBase()).isEqualTo(4);
    assertThat(dimension).hasToString("12[4]");
    assertThat(getAlphabet(dimension)).isEqualTo("0123456789AB");
  }

}
