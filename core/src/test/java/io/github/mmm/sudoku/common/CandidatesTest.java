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
  public void testOfAll() {

    // arrange
    Candidates candidates;
    // act
    candidates = Candidates.ofAll();
    // assert
    verify(candidates);
    assertThat(candidates.getExclusionCount()).isEqualTo(0);
    assertThat(candidates.getEncodedBitValue()).isEqualTo(0);
    assertThat(candidates.toExcludedArray()).isEmpty();
    assertThat(candidates.toIncludedArray(9)).containsExactly(1, 2, 3, 4, 5, 6, 7, 8, 9);
    assertThat(candidates).hasToString("{}");
  }

  @Test
  public void testOdds() {

    // arrage
    Candidates candidates;
    // act
    candidates = Candidates.ofAll().exclude(9).exclude(7).exclude(5).exclude(3).exclude(1);
    // assert
    verify(candidates);
    assertThat(candidates.getExclusionCount()).isEqualTo(5);
    assertThat(candidates.toExcludedArray()).containsExactly(1, 3, 5, 7, 9);
    assertThat(candidates.toIncludedArray(9)).containsExactly(2, 4, 6, 8);
    assertThat(candidates).hasToString("{1,3,5,7,9}");
  }

  @Test
  public void testPooling() {

    // arrange
    Candidates all = Candidates.ofAll();
    // act
    Candidates exclude1 = all.exclude(1);
    Candidates exclude9 = all.exclude(9);
    Candidates exclude10 = all.exclude(10);
    // assert
    verify(exclude1);
    verify(exclude9);
    verify(exclude10);
    assertThat(all.exclude(1)).isSameAs(exclude1);
    assertThat(exclude1.include(1)).isSameAs(all);
    assertThat(all.exclude(9)).isSameAs(exclude9);
    assertThat(exclude9.include(9)).isSameAs(all);
    assertThat(exclude10.include(10)).isSameAs(all);
    // testing "anti-feature": to avoid memory waste, we only pool 9 bits (for 9x9 Sudoku)
    assertThat(all.exclude(10)).isNotSameAs(exclude10);
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
        Arrays.toString(candidates.toExcludedArray()).replace('[', '{').replace(']', '}').replace(" ", ""));
  }

}
