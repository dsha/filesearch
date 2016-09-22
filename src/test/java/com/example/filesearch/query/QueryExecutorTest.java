package com.example.filesearch.query;

import com.example.filesearch.index.WordIndex;
import com.example.filesearch.util.TestUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class QueryExecutorTest {

  @Test
  public void basic_word_lookup() throws IOException {
    final WordIndex wordIndex = TestUtil.buildIndex("This is the first file", "This is the second file");
    final QueryExecutor queryExecutor = new QueryExecutor(wordIndex);
    final List<FileResult> results = queryExecutor.getRankedResults(Collections.singletonList("file"), 10);
    assertEquals(Arrays.asList(new FileResult(1, 1.0, 0.0), new FileResult(0, 1.0, 0.0)), results);
  }

  @Test
  public void multiple_words_lookup() throws IOException {
    final WordIndex wordIndex = TestUtil.buildIndex("This is the first file", "This is the second file");
    final QueryExecutor queryExecutor = new QueryExecutor(wordIndex);
    final List<FileResult> results = queryExecutor.getRankedResults(Arrays.asList("file", "first"), 10);
    assertEquals(Arrays.asList(new FileResult(0, 1.0, 0.0), new FileResult(1, 0.5, 0.0)), results);
  }

  @Test
  public void multiple_words_lookup_with_boost() throws IOException {
    final WordIndex wordIndex = TestUtil.buildIndex("This is the first file.", "This is the second file. It has another occurrence of the word file");
    final QueryExecutor queryExecutor = new QueryExecutor(wordIndex);
    final List<FileResult> results = queryExecutor.getRankedResults(Arrays.asList("file", "the"), 10);
    assertEquals(Arrays.asList(new FileResult(1, 1.0, 1.0), new FileResult(0, 1.0, 0.0)), results);
  }

  @Test
  public void multiple_words_incomplete_match() throws IOException {
    final WordIndex wordIndex = TestUtil.buildIndex("This is the first file.", "This is the second file. It has another occurrence of the word file");
    final QueryExecutor queryExecutor = new QueryExecutor(wordIndex);
    final List<FileResult> results = queryExecutor.getRankedResults(Arrays.asList("file", "foo", "first"), 10);
    assertEquals(Arrays.asList(new FileResult(0, (double)2/3, 0.0), new FileResult(1, (double)1/3, (double)1/3)), results);
  }
}
