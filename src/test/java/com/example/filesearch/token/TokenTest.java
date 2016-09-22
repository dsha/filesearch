package com.example.filesearch.token;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TokenTest {
  @Test
  public void canonical_ignores_case() {
    assertEquals(Token.canonicalForm("wordindifferentcase"), Token.canonicalForm("wOrdInDifferentCase"));
  }

  @Test
  public void canonical_treats_different_words_differently() {
    assertNotEquals(Token.canonicalForm("foo"), Token.canonicalForm("bar"));
  }
}
