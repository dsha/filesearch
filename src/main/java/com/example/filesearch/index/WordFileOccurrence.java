package com.example.filesearch.index;

public class WordFileOccurrence {
  private final String word;
  private final long fileId;
  private final long occurrenceCount;

  public WordFileOccurrence(String word, long fileId, long occurrenceCount) {
    this.word = word;
    this.fileId = fileId;
    this.occurrenceCount = occurrenceCount;
  }

  public String getWord() {
    return word;
  }

  public long getFileId() {
    return fileId;
  }

  public long getOccurrenceCount() {
    return occurrenceCount;
  }

  @Override
  public String toString() {
    final StringBuffer sb = new StringBuffer("WordFileOccurrence{");
    sb.append("word='").append(word).append('\'');
    sb.append(", fileId=").append(fileId);
    sb.append(", occurrenceCount=").append(occurrenceCount);
    sb.append('}');
    return sb.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    WordFileOccurrence that = (WordFileOccurrence) o;

    if (fileId != that.fileId) return false;
    if (occurrenceCount != that.occurrenceCount) return false;
    return word != null ? word.equals(that.word) : that.word == null;

  }

  @Override
  public int hashCode() {
    int result = word != null ? word.hashCode() : 0;
    result = 31 * result + (int) (fileId ^ (fileId >>> 32));
    result = 31 * result + (int) (occurrenceCount ^ (occurrenceCount >>> 32));
    return result;
  }
}
