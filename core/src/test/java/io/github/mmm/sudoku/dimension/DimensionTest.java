/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.dimension;

import org.assertj.core.api.Assertions;

/**
 * Test of {@link RegularDimension}.
 */
public abstract class DimensionTest extends Assertions {

  /**
   * @param dimension the {@link Dimension}.
   * @return the complete {@link Dimension#getSymbol(int) alphabet} as a single {@link String}.
   */
  protected String getAlphabet(Dimension dimension) {

    int size = dimension.getSize();
    StringBuilder sb = new StringBuilder(size);
    for (int i = 1; i <= size; i++) {
      sb.append(dimension.getSymbol(i));
    }
    return sb.toString();
  }

}
