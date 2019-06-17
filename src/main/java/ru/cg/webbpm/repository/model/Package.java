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

  public Package(String groupId, String artifactId, String version, String path) {
    this.groupId = Objects.requireNonNull(groupId);
    this.artifactId = Objects.requireNonNull(artifactId);
    this.version = Objects.requireNonNull(version);
    this.path = Objects.requireNonNull(path);
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
    Package pPackage = (Package) o;
    return Objects.equals(groupId, pPackage.groupId) &&
           Objects.equals(artifactId, pPackage.artifactId) &&
           Objects.equals(version, pPackage.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(groupId, artifactId, version);
  }
}
