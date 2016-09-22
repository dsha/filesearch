package com.example.filesearch.token;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class Tokenizer {
  private final Path filePath;
  private final long fileId;

  private static final String REGEX_SPLIT = "[^a-zA-Z0-9]";

  public Tokenizer(Path filePath, long fileId) {
    this.filePath = filePath;
    this.fileId = fileId;
  }

  public static Stream<String> scanLine(String line) {
    return Stream.of(line.split(REGEX_SPLIT)).filter(word -> word.length() != 0);
  }

  public Stream<Token> scan() {
    try {
      return Files
        .lines(filePath)
        .flatMap(Tokenizer::scanLine)
        .map(word -> new Token(word, fileId));
    } catch (IOException e) {
      return Stream.empty(); // not the production error handling
    }
  }
}
