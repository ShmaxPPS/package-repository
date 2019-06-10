package ru.cg.webbpm.repository.model;

/**
 * @author m.popov
 */
public class VersionRange {
  private Version minVersion;
  private Version maxVersion;

  public VersionRange(Version minVersion, Version maxVersion) {
    this.minVersion = minVersion;
    this.maxVersion = maxVersion;
  }

  public Version getMinVersion() {
    return minVersion;
  }

  public Version getMaxVersion() {
    return maxVersion;
  }

}
