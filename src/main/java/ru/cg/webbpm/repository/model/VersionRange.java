package ru.cg.webbpm.repository.model;

import java.util.Objects;

/**
 * @author m.popov
 */
public class VersionRange {
  private Version fromVersion;
  private Version toVersion;

  public VersionRange(Version fromVersion, Version toVersion) {
    this.fromVersion = Objects.requireNonNull(fromVersion);
    this.toVersion = Objects.requireNonNull(toVersion);
  }

  public Version getFromVersion() {
    return fromVersion;
  }

  public Version getToVersion() {
    return toVersion;
  }
}
