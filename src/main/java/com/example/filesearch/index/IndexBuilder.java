package com.example.filesearch.index;

import com.example.filesearch.token.Token;
import com.example.filesearch.token.Tokenizer;

import java.io.File;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class IndexBuilder {
  private IndexBuilder() {}

  public static WordIndex buildIndex(File[] allFiles) {
    // build the index
    final Map<String, Map<Long, Long>> wordToFileIdToCount;
    if (allFiles != null) {
      wordToFileIdToCount = IntStream
        .range(0, allFiles.length)
        .boxed()
        .parallel()
        .flatMap(fileId -> new Tokenizer(Paths.get(allFiles[fileId].getAbsolutePath()), fileId).scan())
        .collect(
          groupingBy(
            Token::getWord,
            groupingBy(Token::getFileId, counting())));
    } else {
      wordToFileIdToCount = Collections.emptyMap();
    }
    return new WordIndex(wordToFileIdToCount);
  }
}
