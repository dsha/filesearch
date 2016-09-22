package com.example.filesearch.query;

import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class FileResultTest {

  @Test
  public void comparator_compares_percentage_correctly() {
    final FileResult betterMatch = new FileResult(1, 0.8, 1000);
    final FileResult worseMatch = new FileResult(1, 0.7, 100000);
    final List<FileResult> sorted = Stream.of(worseMatch, betterMatch)
      .sorted(FileResult.getComparator())
      .collect(Collectors.toList());
    assertEquals(Stream.of(betterMatch, worseMatch).collect(Collectors.toList()), sorted);
  }

  @Test
  public void comparator_compares_rank_boost_correctly() {
    final FileResult betterMatch = new FileResult(1, 0.8, 1001);
    final FileResult worseMatch = new FileResult(1, 0.8, 1000);
    final List<FileResult> sorted = Stream.of(worseMatch, betterMatch)
      .sorted(FileResult.getComparator())
      .collect(Collectors.toList());
    assertEquals(Stream.of(betterMatch, worseMatch).collect(Collectors.toList()), sorted);
  }

  @Test
  public void comparator_compares_file_id_if_everything_else_is_equal() {
    final FileResult betterMatch = new FileResult(1, 0.8, 1001);
    final FileResult worseMatch = new FileResult(0, 0.8, 1001);
    final List<FileResult> sorted = Stream.of(worseMatch, betterMatch)
      .sorted(FileResult.getComparator())
      .collect(Collectors.toList());
    assertEquals(Stream.of(betterMatch, worseMatch).collect(Collectors.toList()), sorted);
  }

}
