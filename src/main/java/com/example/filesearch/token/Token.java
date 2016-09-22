package com.example.filesearch.token;

public class Token {
  private final String word;
  private long fileId;

  public static String canonicalForm(String word) {
    // here we would also perform stemming and other, language-dependent transformation that make words identical from the index perspective
    return word.toLowerCase();
  }

  public Token(String word, long fileId) {
    this.word = canonicalForm(word);
    this.fileId = fileId;
  }

  public String getWord() {
    return word;
  }

  public long getFileId() {
    return fileId;
  }

}
