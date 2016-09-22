package com.example.filesearch.index;

import com.example.filesearch.util.TestUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class IndexBuilderTest {

  public static void assertContains(WordIndex wordIndex, String word, WordFileOccurrence... wordFileOccurrences) {
    assertEquals(Arrays.stream(wordFileOccurrences).collect(Collectors.toSet()), wordIndex.lookupWord(word).collect(Collectors.toSet()));
  }

  @Test
  public void index_two_files() throws IOException {
    final WordIndex wordIndex = TestUtil.buildIndex("This is the first file", "This is the second file");
    assertContains(wordIndex, "file", new WordFileOccurrence("file", 0, 1), new WordFileOccurrence("file", 1, 1));
    assertContains(wordIndex, "first", new WordFileOccurrence("first", 0, 1));
    assertContains(wordIndex, "second", new WordFileOccurrence("second", 1, 1));
  }

  @Test
  public void index_two_files_with_multiple_occurrences() throws IOException {
    final WordIndex wordIndex = TestUtil.buildIndex("This is the first file, and the first occurrence", "This is the second file, and it's diferent from the previous file.");
    assertContains(wordIndex, "first", new WordFileOccurrence("first", 0, 2));
    assertContains(wordIndex, "file", new WordFileOccurrence("file", 0, 1), new WordFileOccurrence("file", 1, 2));
  }
}
