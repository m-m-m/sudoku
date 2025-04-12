/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.sudoku.style;

/**
 * Abstract base class for a style.
 */
public abstract class Style {

  /** @see #getStyle() */
  protected final String style;

  /**
   * The constructor.
   *
   * @param style the {@link #getStyle() style}.
   */
  public Style(String style) {

    super();
    this.style = style;
  }

  /**
   * @return the style name. May be {@code null} for none.
   */
  public String getStyle() {

    return this.style;
  }

  @Override
  public String toString() {

    return this.style;
  }

}
