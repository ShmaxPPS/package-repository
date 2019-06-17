package ru.cg.webbpm.repository.util;

import java.util.*;
import java.util.stream.Collectors;

import ru.cg.webbpm.repository.model.Package;

/**
 * @author m.popov
 */
public final class PackageGenerator {

  private PackageGenerator() {
  }

  public static Package randomPackage() {
    return new Package(
        PropertyGenerator.randomGroupId(),
        PropertyGenerator.randomArtifactId(),
        PropertyGenerator.randomVersion(),
        PropertyGenerator.randomPath()
    );
  }

  public static List<Package> randomDifferentPackages(int size) {
    Set<String> ids = new HashSet<>();
    List<Package> packages = new ArrayList<>();
    while (packages.size() != size) {
      Package randomPackage = randomPackage();
      String packageId = randomPackage.getPackageId();
      if (ids.contains(packageId)) {
        continue;
      }
      ids.add(packageId);
      packages.add(randomPackage);
    }
    return packages;
  }

  public static List<Package> randomPackageWithVersion(List<String> versions) {
    String groupId = PropertyGenerator.randomGroupId();
    String artifactId = PropertyGenerator.randomArtifactId();
    String path = PropertyGenerator.randomPath();
    return versions.stream()
        .map(version -> new Package(groupId, artifactId, version, path))
        .collect(Collectors.toList());
  }
}
