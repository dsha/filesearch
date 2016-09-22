package com.example.filesearch.query;

import com.example.filesearch.index.WordFileOccurrence;
import com.example.filesearch.index.WordIndex;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.reducing;
import static java.util.stream.Collectors.summingDouble;

public class QueryExecutor {
  private final WordIndex wordIndex;

  public QueryExecutor(WordIndex wordIndex) {
    this.wordIndex = wordIndex;
  }

  public List<FileResult> getRankedResults(final List<String> searchTerms, final int limit) {
    return searchTerms
      .parallelStream()
      .flatMap(wordIndex::lookupWord)
      .collect(
        groupingBy(WordFileOccurrence::getFileId,
          groupingBy(
            WordFileOccurrence::getWord,
            reducing(0L, WordFileOccurrence::getOccurrenceCount, (l1, l2) -> l1 + l2))))
      .entrySet()
      .stream()
      .map(entry -> getFileResult(searchTerms, entry))
      .sorted(FileResult.getComparator())
      .limit(limit)
      .collect(Collectors.toList());
  }

  private FileResult getFileResult(final List<String> searchTerms, final Map.Entry<Long, Map<String, Long>> entry) {
    final Map<String, Long> docWordCounts = entry.getValue();
    final long numSearchTermsDocContains = searchTerms.stream().filter(docWordCounts::containsKey).count();
    final double percentageMatch = (double) numSearchTermsDocContains / searchTerms.size();
    // additional, non-normalized rank boost - for terms occurring more than once, add number-occurrences * 1/number-of-terms
    final double rankBoost = (docWordCounts.values().stream().filter(v -> v > 1).collect(summingDouble(v -> (double) (v - 1)))) / searchTerms.size();
    return new FileResult(entry.getKey(), percentageMatch, rankBoost);
  }
}
