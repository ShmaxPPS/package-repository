package ru.cg.webbpm.repository.model;

import java.util.Comparator;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author m.popov
 */
public class Version implements Comparable<Version> {
  private static final Pattern MAVEN_PATTERN =
      Pattern.compile("(?<major>\\d+)(?:\\.(?<minor>\\d+))?(?:\\.(?<patch>\\d+))");

  private static final Comparator<Version> COMPARATOR = Comparator
      .comparing(Version::getMajor)
      .thenComparing(Version::getMinor)
      .thenComparing(Version::getPatch);

  public static final Version NEGATIVE_INFINITY = new Version(
      Integer.MIN_VALUE,
      Integer.MIN_VALUE,
      Integer.MIN_VALUE
  );

  public static final Version POSITIVE_INFINITY = new Version(
      Integer.MAX_VALUE,
      Integer.MAX_VALUE,
      Integer.MAX_VALUE
  );

  private final int major;
  private final int minor;
  private final int patch;

  private Version(int major, int minor, int patch) {
    this.major = major;
    this.minor = minor;
    this.patch = patch;
  }

  public static Version parseMaven(String versionString) {
    Matcher m = MAVEN_PATTERN.matcher(versionString);
    if (!m.matches()) {
      throw new IllegalArgumentException("Invalid version format");
    }
    int major = Integer.valueOf(m.group("major"));
    int minor = m.group("minor") == null ? 0 : Integer.valueOf(m.group("minor"));
    int patch = m.group("patch") == null ? 0 : Integer.valueOf(m.group("patch"));
    return new Version(major, minor, patch);
  }

  public int getMajor() {
    return major;
  }

  public int getMinor() {
    return minor;
  }

  public int getPatch() {
    return patch;
  }

  @Override
  public int compareTo(Version o) {
    return COMPARATOR.compare(this, o);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Version version = (Version) o;
    return major == version.major &&
           minor == version.minor &&
           patch == version.patch;
  }

  @Override
  public int hashCode() {
    return Objects.hash(major, minor, patch);
  }

  @Override
  public String toString() {
    return String.format("%d.%d.%d", major, minor, patch);
  }
}
