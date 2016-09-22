package com.example.filesearch.index;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Stream;

// just wrap a long type signature and hide it's a map
public class WordIndex {
  private final Map<String, Map<Long, Long>> wordToFileIdToOccurrenceCount;

  public WordIndex(Map<String, Map<Long, Long>> wordToFileIdToOccurrenceCount) {
    this.wordToFileIdToOccurrenceCount = wordToFileIdToOccurrenceCount;
  }

  public Stream<WordFileOccurrence> lookupWord(String word) {
    return wordToFileIdToOccurrenceCount
      .getOrDefault(word, Collections.emptyMap())
      .entrySet()
      .stream()
      .map(entry -> new WordFileOccurrence(word, entry.getKey(), entry.getValue()));
  }
}
