/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.style;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test of {@link BorderStyle}.
 */
class BorderStyleTest extends Assertions {

  /** Test the main features of {@link BorderStyle}. */
  @Test
  void testMain() {

    BorderStyle empty = BorderStyle.EMPTY;
    assertThat(empty.getStyle()).isNull();
    BorderStyle all = empty.withRight().withLeft().withTop().withBottom();
    // order is trbl
    assertThat(all.getStyle()).isEqualTo("border-trbl");
    assertThat(empty.withBottom()).hasToString("border-b").isSameAs(all.withoutLeft().withoutRight().withoutTop());
    BorderStyle tl = empty.withTop().withLeft();
    assertThat(tl).hasToString("border-tl").isSameAs(all.withoutBottom().withoutRight());
    BorderStyle rl = empty.withLeft().withRight();
    assertThat(rl).hasToString("border-rl").isSameAs(all.withoutBottom().withoutTop());
  }

}
