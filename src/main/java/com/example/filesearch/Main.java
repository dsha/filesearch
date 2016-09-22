package com.example.filesearch;

import com.example.filesearch.index.IndexBuilder;
import com.example.filesearch.index.WordIndex;
import com.example.filesearch.query.FileResult;
import com.example.filesearch.query.QueryExecutor;
import com.example.filesearch.token.Token;
import com.example.filesearch.token.Tokenizer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

  public static void main(String[] args) throws FileNotFoundException {
    if (args.length < 1) {
      System.err.println("Usage: java <class-name> <directory-path>");
      return;
    }

    final String directoryPath = args[0];

    final File directoryFile = new File(directoryPath);
    final File[] allFiles = directoryFile.listFiles();

    final WordIndex wordIndex = IndexBuilder.buildIndex(allFiles);

    // accept the queries
    final QueryExecutor queryExecutor = new QueryExecutor(wordIndex);
    final Scanner stdin = new Scanner(System.in);
    while (true) {
      System.out.print("search>");
      final String line = stdin.nextLine();
      final List<String> query = Tokenizer
        .scanLine(line)
        .map(Token::canonicalForm)
        .collect(Collectors.toList());

      final List<FileResult> rankedResults = queryExecutor
        .getRankedResults(query, 10);
      if (rankedResults.isEmpty()) {
        System.out.println("no matches found");
      } else {
        rankedResults
          .forEach(result -> System.out.println(String.format("%s : %.1f%%", allFiles[(int) result.getFileId()].getName(), 100 * result.getPercentageMatch())));
      }
    }
  }

}
