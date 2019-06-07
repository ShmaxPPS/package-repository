package ru.cg.webbpm.repository.model;

/**
 * @author m.popov
 */
public class PackageRequest {
  private String groupId;
  private String artifactId;
  private String minVersion;
  private String maxVersion;

  public PackageRequest() {
  }

  public PackageRequest(String groupId, String artifactId, String minVersion, String maxVersion) {
    this.groupId = groupId;
    this.artifactId = artifactId;
    this.minVersion = minVersion;
    this.maxVersion = maxVersion;
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

  public String getMinVersion() {
    return minVersion;
  }

  public void setMinVersion(String minVersion) {
    this.minVersion = minVersion;
  }

  public String getMaxVersion() {
    return maxVersion;
  }

  public void setMaxVersion(String maxVersion) {
    this.maxVersion = maxVersion;
  }
}
