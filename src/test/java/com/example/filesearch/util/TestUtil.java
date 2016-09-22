package com.example.filesearch.util;

import com.example.filesearch.index.IndexBuilder;
import com.example.filesearch.index.WordIndex;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TestUtil {
  private TestUtil() {}

  public static WordIndex buildIndex(String... fileContents) throws IOException {
    final Path tempDirectory = Files.createTempDirectory("filesearch-test");
    final File[] filesToIndex = new File[fileContents.length];
    for (int i = 0; i < fileContents.length; i++) {
      final Path tempFile = Files.createFile(tempDirectory.resolve("file-" + i));
      filesToIndex[i] = tempFile.toFile();
      try (BufferedWriter writer = Files.newBufferedWriter(tempFile)) {
        writer.write(fileContents[i]);
      }
    }

    return IndexBuilder.buildIndex(filesToIndex);
  }
}
