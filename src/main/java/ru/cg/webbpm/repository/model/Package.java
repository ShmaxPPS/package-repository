package ru.cg.webbpm.repository.model;

import java.util.Objects;

/**
 * @author m.popov
 */
public class Package {
  private String groupId;
  private String artifactId;
  private String version;
  private String path;

  @SuppressWarnings("unused")
  public Package() {
  }

  public Package(String groupId, String artifactId, String version, String path) {
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
    if (!(o instanceof Package)) return false;
    Package aPackage = (Package) o;
    return Objects.equals(groupId, aPackage.groupId) &&
           Objects.equals(artifactId, aPackage.artifactId) &&
           Objects.equals(version, aPackage.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(groupId, artifactId, version);
  }
}
