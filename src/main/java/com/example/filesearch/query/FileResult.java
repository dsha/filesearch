package com.example.filesearch.query;

import java.util.Comparator;

// Represents the final result for a given document with regards to the query
public class FileResult {
  private final long fileId;
  private final double percentageMatch;
  private final double rankBoost; // additional boost to break ties between the files with the same percentage match, based on term frequency

  public FileResult(long fileId, double percentageMatch, double rankBoost) {
    this.fileId = fileId;
    this.percentageMatch = percentageMatch;
    this.rankBoost = rankBoost;
  }

  public static Comparator<FileResult> getComparator() {
    return Comparator
      .comparing(FileResult::getPercentageMatch)
      .thenComparing(FileResult::getRankBoost)
      .thenComparing(FileResult::getFileId) // this is purely for deterministic test results
      .reversed();
  }

  public long getFileId() {
    return fileId;
  }

  public double getPercentageMatch() {
    return percentageMatch;
  }

  public double getRankBoost() {
    return rankBoost;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    FileResult that = (FileResult) o;

    if (fileId != that.fileId) return false;
    if (Double.compare(that.percentageMatch, percentageMatch) != 0) return false;
    return Double.compare(that.rankBoost, rankBoost) == 0;

  }

  @Override
  public int hashCode() {
    int result;
    long temp;
    result = (int) (fileId ^ (fileId >>> 32));
    temp = Double.doubleToLongBits(percentageMatch);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(rankBoost);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    return result;
  }

  @Override
  public String toString() {
    final StringBuffer sb = new StringBuffer("FileResult{");
    sb.append("fileId=").append(fileId);
    sb.append(", percentageMatch=").append(percentageMatch);
    sb.append(", rankBoost=").append(rankBoost);
    sb.append('}');
    return sb.toString();
  }
}
