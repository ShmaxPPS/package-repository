package ru.cg.webbpm.repository.util;

import java.util.*;
import java.util.stream.Collectors;

import ru.cg.webbpm.repository.api.PackageResponse;

/**
 * @author m.popov
 */
public final class PackageGenerator {

  private PackageGenerator() {
  }

  public static PackageResponse randomPackage() {
    return new PackageResponse(
        PropertyGenerator.randomGroupId(),
        PropertyGenerator.randomArtifactId(),
        PropertyGenerator.randomVersion(),
        PropertyGenerator.randomPath()
    );
  }

  public static List<PackageResponse> randomPackages(int size) {
    Set<String> ids = new HashSet<>();
    List<PackageResponse> packageResponses = new ArrayList<>();
    while (packageResponses.size() != size) {
      PackageResponse randomPackageResponse = randomPackage();
      String packageId = randomPackageResponse.getPackageId();
      if (ids.contains(packageId)) {
        continue;
      }
      ids.add(packageId);
      packageResponses.add(randomPackageResponse);
    }
    return packageResponses;
  }

  public static List<PackageResponse> randomPackageWithVersion(List<String> versions) {
    String groupId = PropertyGenerator.randomGroupId();
    String artifactId = PropertyGenerator.randomArtifactId();
    String path = PropertyGenerator.randomPath();
    return versions.stream()
        .map(version -> new PackageResponse(groupId, artifactId, version, path))
        .collect(Collectors.toList());
  }
}
