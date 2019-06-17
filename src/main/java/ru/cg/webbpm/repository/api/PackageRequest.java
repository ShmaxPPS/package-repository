package ru.cg.webbpm.repository.api;

/**
 * @author m.popov
 */
public class PackageRequest {
  private String groupId;
  private String artifactId;
  private String fromVersion;
  private boolean fromInclusive;
  private String toVersion;
  private boolean toInclusive;

  @SuppressWarnings("unused")
  public PackageRequest() {
  }

  public String getGroupId() {
    return groupId;
  }

  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }

  public String getArtifactId() {
    return artifactId;
  }

  public void setArtifactId(String artifactId) {
    this.artifactId = artifactId;
  }

  public String getFromVersion() {
    return fromVersion;
  }

  public void setFromVersion(String fromVersion) {
    this.fromVersion = fromVersion;
  }

  public boolean getFromInclusive() {
    return fromInclusive;
  }

  public void setFromInclusive(boolean fromInclusive) {
    this.fromInclusive = fromInclusive;
  }

  public String getToVersion() {
    return toVersion;
  }

  public void setToVersion(String toVersion) {
    this.toVersion = toVersion;
  }

  public boolean getToInclusive() {
    return toInclusive;
  }

  public void setToInclusive(boolean toInclusive) {
    this.toInclusive = toInclusive;
  }
}
