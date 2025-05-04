package io.github.mmm.sudoku.partition;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test of {@link Shape}.
 */
public class ShapeTest extends Assertions {

  @Test
  public void testBox() {

    // arrange
    Shape shape = new Shape(0, 3, null);
    shape = new Shape(0, 3, shape);
    shape = new Shape(0, 3, shape);
    // act
    String toString = shape.toString();
    // assert
    assertThat(toString).isEqualTo("XXX\nXXX\nXXX\n");
    assertThat(shape.getFieldCount()).isEqualTo(9);
  }

  @Test
  public void testRow() {

    // arrange
    Shape shape = new Shape(0, 9, null);
    // act
    String toString = shape.toString();
    // assert
    assertThat(toString).isEqualTo("XXXXXXXXX\n");
    assertThat(shape.getFieldCount()).isEqualTo(9);
  }

  @Test
  public void testColumn() {

    // arrange
    Shape shape = new Shape(0, 1, null);
    shape = new Shape(0, 1, shape);
    shape = new Shape(0, 1, shape);
    shape = new Shape(0, 1, shape);
    shape = new Shape(0, 1, shape);
    shape = new Shape(0, 1, shape);
    shape = new Shape(0, 1, shape);
    shape = new Shape(0, 1, shape);
    shape = new Shape(0, 1, shape);
    // act
    String toString = shape.toString();
    // assert
    assertThat(toString).isEqualTo("X\nX\nX\nX\nX\nX\nX\nX\nX\n");
    assertThat(shape.getFieldCount()).isEqualTo(9);
  }

  @Test
  public void testPlus() {

    // arrange
    Shape shape = new Shape(0, 1, null);
    shape = new Shape(-1, 3, shape);
    shape = new Shape(0, 1, shape);
    // act
    String toString = shape.toString();
    // assert
    assertThat(toString).isEqualTo(" X\nXXX\n X\n");
    assertThat(shape.getFieldCount()).isEqualTo(5);
  }

  @Test
  public void testRightBottom() {

    // arrange
    Shape shape = new Shape(-2, 3, null);
    shape = new Shape(0, 1, shape);
    shape = new Shape(0, 1, shape);
    // act
    String toString = shape.toString();
    // assert
    assertThat(toString).isEqualTo("  X\n  X\nXXX\n");
    assertThat(shape.getFieldCount()).isEqualTo(5);
  }

}
