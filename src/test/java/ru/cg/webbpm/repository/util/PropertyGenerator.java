package ru.cg.webbpm.repository.util;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author m.popov
 */
public final class PropertyGenerator {

  private static final String LOWER_CASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";

  private static final int SHORT_STRING_LENGTH = 3;
  private static final int MEDIUM_STRING_LENGTH = 5;
  private static final int LONG_STRING_LENGTH = 7;

  private static final int SHORT_INT_VALUE = 5;
  private static final int MEDIUM_INT_VALUE = 10;
  private static final int LONG_INT_VALUE = 20;

  private static final String DOT_SEPARATOR = ".";
  private static final String SLASH_SEPARATOR = "/";

  private static final Random RANDOM = new Random();

  private PropertyGenerator() {
  }

  public static String randomGroupId() {
    int[] lengths = {
        SHORT_STRING_LENGTH,
        SHORT_STRING_LENGTH,
        MEDIUM_STRING_LENGTH
    };
    return Arrays.stream(lengths)
        .mapToObj(PropertyGenerator::randomString)
        .collect(Collectors.joining(DOT_SEPARATOR));
  }

  public static String randomArtifactId() {
    return randomString(LONG_STRING_LENGTH);
  }

  public static String randomVersion() {
    int[] maxes = {
        SHORT_INT_VALUE,
        LONG_INT_VALUE,
        MEDIUM_INT_VALUE
    };
    return Arrays.stream(maxes)
        .map(size -> RANDOM.nextInt(size + 1))
        .mapToObj(Integer::toString)
        .collect(Collectors.joining(DOT_SEPARATOR));
  }

  public static String randomPath() {
    int[] lengths = {
        SHORT_STRING_LENGTH,
        SHORT_STRING_LENGTH,
        MEDIUM_STRING_LENGTH,
        MEDIUM_STRING_LENGTH,
        MEDIUM_STRING_LENGTH
    };
    return Arrays.stream(lengths)
        .mapToObj(PropertyGenerator::randomString)
        .collect(Collectors.joining(SLASH_SEPARATOR));
  }

  public static String randomString(int size) {
    return IntStream.range(0, size)
        .map(index -> RANDOM.nextInt(LOWER_CASE_LETTERS.length()))
        .map(LOWER_CASE_LETTERS::charAt)
        .collect(
            StringBuilder::new,
            StringBuilder::appendCodePoint,
            StringBuilder::append
        )
        .toString();
  }
}
