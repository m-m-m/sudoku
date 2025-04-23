/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.dimension;

import org.assertj.core.api.Assertions;

/**
 * Test of {@link SquareDimension}.
 */
public abstract class DimensionTest extends Assertions {

  /**
   * @param dimension the {@link Dimension} to verify.
   */
  protected void verify(Dimension dimension) {

    int boxWidth = dimension.getBoxWidth();
    int boxHeight = dimension.getBoxHeight();
    int boxSize = dimension.getBoxSize();
    int size = dimension.getSize();
    assertThat(size).isEqualTo(boxWidth * boxHeight);
    assertThat(dimension).hasToString(size + "[" + boxWidth + "x" + boxHeight + "]");
    DimensionType type = dimension.getDimensionType();
    switch (type) {
      case PRIME -> {
        assertThat(boxWidth).isEqualTo(1);
        assertThat(boxSize * boxSize).isGreaterThan(size);
        assertThat((boxSize - 1) * (boxSize - 1)).isLessThan(size);
      }
      case RECTANGULAR -> {
        assertThat(boxWidth).isNotEqualTo(boxHeight);
        assertThat(boxWidth).isNotEqualTo(1);
        assertThat(boxHeight).isNotEqualTo(1);
        assertThat(boxSize * boxSize).isGreaterThan(size);
        assertThat((boxSize - 1) * (boxSize - 1)).isLessThan(size);
      }
      case SQUARE -> {
        assertThat(boxWidth).isEqualTo(boxSize);
        assertThat(boxHeight).isEqualTo(boxSize);
      }
      default -> {
        throw new IllegalArgumentException("Unexpected value: " + type);
      }
    }
  }

  /**
   * @param dimension the {@link Dimension}.
   * @return the complete {@link Dimension#getSymbol(int) alphabet} as a single {@link String}.
   */
  protected String getAlphabet(Dimension dimension) {

    int size = dimension.getSize();
    StringBuilder sb = new StringBuilder(size);
    for (int i = 1; i <= size; i++) {
      if (i > 1) {
        sb.append(',');
      }
      sb.append(dimension.getSymbol(i));
    }
    return sb.toString();
  }

}
