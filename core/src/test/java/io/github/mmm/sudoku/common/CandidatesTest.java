package io.github.mmm.sudoku.common;

import java.util.Arrays;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test of {@link Candidates}.
 */
@SuppressWarnings("javadoc")
public class CandidatesTest extends Assertions {

  @Test
  public void testOfNone() {

    // arrange
    Candidates candidates;
    // act
    candidates = Candidates.ofNone();
    // assert
    verify(candidates);
    assertThat(candidates.getInclusionCount()).isEqualTo(0);
    assertThat(candidates.getEncodedBitValue()).isEqualTo(0);
    assertThat(candidates.toIncludedArray()).isEmpty();
    assertThat(candidates.toExcludedArray(9)).containsExactly(1, 2, 3, 4, 5, 6, 7, 8, 9);
    assertThat(candidates.getLowestCandidate()).isEqualTo(-1);
    assertThat(candidates.getCandidate(2)).isEqualTo(-1);
    assertThat(candidates).hasToString("{}");
  }

  @Test
  public void testOdds() {

    // arrage
    Candidates candidates;
    // act
    candidates = Candidates.ofNone().include(9).include(7).include(5).include(3).include(1);
    // assert
    verify(candidates);
    assertThat(candidates.getInclusionCount()).isEqualTo(5);
    assertThat(candidates.toIncludedArray()).containsExactly(1, 3, 5, 7, 9);
    assertThat(candidates.toExcludedArray(9)).containsExactly(2, 4, 6, 8);
    assertThat(candidates.getLowestCandidate()).isEqualTo(1);
    assertThat(candidates.getCandidate(1)).isEqualTo(1);
    assertThat(candidates.getCandidate(2)).isEqualTo(3);
    assertThat(candidates.getCandidate(3)).isEqualTo(5);
    assertThat(candidates.getCandidate(4)).isEqualTo(7);
    assertThat(candidates.getCandidate(5)).isEqualTo(9);
    assertThat(candidates.getCandidate(6)).isEqualTo(-1);
    assertThat(candidates).hasToString("{1,3,5,7,9}");
  }

  @Test
  public void testPooling() {

    // arrange
    Candidates none = Candidates.ofNone();
    // act
    Candidates include1 = none.include(1);
    Candidates include9 = none.include(9);
    Candidates include10 = none.include(10);
    // assert
    verify(include1);
    verify(include9);
    verify(include10);
    assertThat(none.include(1)).isSameAs(include1);
    assertThat(include1.exclude(1)).isSameAs(none);
    assertThat(none.include(9)).isSameAs(include9);
    assertThat(include9.exclude(9)).isSameAs(none);
    assertThat(include9.getLowestCandidate()).isEqualTo(9);
    assertThat(include10.exclude(10)).isSameAs(none);
    assertThat(include10.getLowestCandidate()).isEqualTo(10);
    // testing "anti-feature": to avoid memory waste, we only pool 9 bits (for 9x9 Sudoku)
    assertThat(none.include(10)).isNotSameAs(include10);
  }

  private void verify(Candidates candidates) {

    assertThat(candidates).isNotNull();
    int payload = candidates.getEncodedBitValue();
    Candidates copy = Candidates.of(payload);
    boolean pooled = (payload >= 0) && (payload < 512);
    Candidates flipped1 = copy.flip(1);
    Candidates flipped9 = copy.flip(9);
    assertThat(flipped1).isNotEqualTo(candidates);
    if (pooled) {
      assertThat(copy).isSameAs(candidates);
      assertThat(flipped1.flip(1)).isSameAs(candidates);
      assertThat(flipped9.flip(9)).isSameAs(candidates);
    } else {
      assertThat(copy).isEqualTo(candidates);
      assertThat(flipped1.flip(1)).isEqualTo(candidates);
      assertThat(flipped9.flip(9)).isEqualTo(candidates);
    }
    assertThat(candidates).hasToString(
        Arrays.toString(candidates.toIncludedArray()).replace('[', '{').replace(']', '}').replace(" ", ""));
  }

}
