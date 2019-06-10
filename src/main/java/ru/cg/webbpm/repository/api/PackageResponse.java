package ru.cg.webbpm.repository.api;

import java.util.Objects;

/**
 * @author m.popov
 */
public class PackageResponse {
  private String groupId;
  private String artifactId;
  private String version;
  private String path;

  @SuppressWarnings("unused")
  public PackageResponse() {
  }

  public PackageResponse(String groupId, String artifactId, String version, String path) {
    this.groupId = groupId;
    this.artifactId = artifactId;
    this.version = version;
    this.path = path;
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

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getPackageId() {
    return String.format("%s.%s", groupId, artifactId);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof PackageResponse)) return false;
    PackageResponse aPackageResponse = (PackageResponse) o;
    return Objects.equals(groupId, aPackageResponse.groupId) &&
           Objects.equals(artifactId, aPackageResponse.artifactId) &&
           Objects.equals(version, aPackageResponse.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(groupId, artifactId, version);
  }
}
