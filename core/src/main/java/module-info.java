/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Provides the sudoku puzzles with ability to generated, solve, play, undo, redo, etc.
 */
module io.github.mmm.sudoku {

  requires transitive org.slf4j;

  requires transitive io.github.mmm.event;

  requires io.github.mmm.base;

  exports io.github.mmm.sudoku;

  exports io.github.mmm.sudoku.builder;

  exports io.github.mmm.sudoku.child;

  exports io.github.mmm.sudoku.common;

  exports io.github.mmm.sudoku.dimension;

  exports io.github.mmm.sudoku.event;

  exports io.github.mmm.sudoku.field;

  exports io.github.mmm.sudoku.history;

  exports io.github.mmm.sudoku.partition;

  exports io.github.mmm.sudoku.partitioning;

  exports io.github.mmm.sudoku.solution;

  exports io.github.mmm.sudoku.solution.strategy;

  exports io.github.mmm.sudoku.style;

}
