package io.github.mmm.sudoku.common;

import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test of {@link SumCombinations}.
 */
class SumCombinationsTest extends Assertions {

  @Test
  void test9x9Max() {

    SumCombinations sumCombinations = new SumCombinations(9);
    Set<Candidates> combinations = sumCombinations.getCombinations(45, 9);
    assertThat(combinations).hasSize(1).containsExactly(Candidates.of(511));
  }

  @Test
  void test9x9Min() {

    SumCombinations sumCombinations = new SumCombinations(9);
    Set<Candidates> combinations = sumCombinations.getCombinations(3, 2);
    assertThat(combinations).hasSize(1).containsExactly(Candidates.of(3));
  }

  @Test
  void test9x9Mid() {

    SumCombinations sumCombinations = new SumCombinations(9);
    Set<Candidates> combinations = sumCombinations.getCombinations(20, 4);
    assertThat(combinations).hasSize(12).containsExactlyInAnyOrder(Candidates.ofValues(1, 2, 8, 9),
        Candidates.ofValues(1, 3, 7, 9), Candidates.ofValues(1, 4, 6, 9), Candidates.ofValues(1, 4, 7, 8),
        Candidates.ofValues(1, 5, 6, 8), Candidates.ofValues(2, 3, 6, 9), Candidates.ofValues(2, 3, 7, 8),
        Candidates.ofValues(2, 4, 5, 9), Candidates.ofValues(2, 4, 6, 8), Candidates.ofValues(2, 5, 6, 7),
        Candidates.ofValues(3, 4, 5, 8), Candidates.ofValues(3, 4, 6, 7));
  }

}
