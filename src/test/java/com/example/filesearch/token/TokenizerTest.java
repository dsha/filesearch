package com.example.filesearch.token;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class TokenizerTest {
  private void doTest(String input, String...expectedOutput) {
    final List<String> actualOutput = Tokenizer.scanLine(input).collect(Collectors.toList());
    assertEquals(Arrays.asList(expectedOutput), actualOutput);
  }

  @Test
  public void handles_empty_line() {
    doTest("");
  }

  @Test
  public void handles_one_token() {
    doTest("foo", "foo");
  }

  @Test
  public void handles_sentence() {
    doTest("Quick brown fox jumped over a lazy dog.", "Quick", "brown", "fox", "jumped", "over", "a", "lazy", "dog");
  }

  @Test
  public void handles_hyphens() {
    doTest("foo-bar", "foo","bar");
  }
}
