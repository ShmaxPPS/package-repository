package ru.cg.webbpm.repository.api;

import java.util.Objects;

/**
 * @author m.popov
 */
public class PackageRequest {
  private String groupId;
  private String artifactId;

  @SuppressWarnings("unused")
  public PackageRequest() {
  }

  public PackageRequest(String groupId, String artifactId) {
    this.groupId = groupId;
    this.artifactId = artifactId;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof PackageRequest)) return false;
    PackageRequest that = (PackageRequest) o;
    return Objects.equals(groupId, that.groupId) &&
           Objects.equals(artifactId, that.artifactId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(groupId, artifactId);
  }
}
