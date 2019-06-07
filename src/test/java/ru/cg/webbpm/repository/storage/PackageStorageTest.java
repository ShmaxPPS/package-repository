package ru.cg.webbpm.repository.storage;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ru.cg.webbpm.repository.model.Package;
import ru.cg.webbpm.repository.util.PackageGenerator;
import ru.cg.webbpm.repository.util.PropertyGenerator;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author m.popov
 */
class PackageStorageTest {

  private static final List<String> SHUFFLED_VERSIONS = Arrays.asList(
      "2.1.2", "3.1.1", "1.2.1", "2.1.1", "1.1.1", "3.1.4", "4.3.1", "3.1.2"
  );
  private static final List<String> SORTED_VERSIONS = Arrays.asList(
      "1.1.1", "1.2.1", "2.1.1", "2.1.2", "3.1.1", "3.1.2", "3.1.4", "4.3.1"
  );
  private static final int TAIL_INDEX = 2;
  private static final int HEAD_INDEX = 5;

  private PackageStorage storage;

  @BeforeEach
  void setUp() {
    storage = new PackageStorage();
  }

  @Test
  void testAddingOneAndRangingOne() {
    Package randomPackage = PackageGenerator.randomPackage();
    storage.add(randomPackage);
    List<Package> packages = storage.range(
        randomPackage.getGroupId(),
        randomPackage.getArtifactId()
    );
    assertNotNull(packages);
    assertEquals(1, packages.size());
    assertEquals(randomPackage, packages.get(0));
  }

  @Test
  void testAddingManyAndRangingMany() {
    List<Package> randomPackages = PackageGenerator.randomPackages(5);
    for (Package randomPackage : randomPackages) {
      storage.add(randomPackage);
    }
    for (Package randomPackage : randomPackages) {
      List<Package> packages = storage.range(
          randomPackage.getGroupId(),
          randomPackage.getArtifactId()
      );
      assertNotNull(packages);
      assertEquals(1, packages.size());
      assertEquals(randomPackage, packages.get(0));
    }
  }

  @Test
  void testAddingAllAndRangingMany() {
    List<Package> randomPackages = PackageGenerator.randomPackages(5);
    storage.addAll(randomPackages);
    for (Package randomPackage : randomPackages) {
      List<Package> packages = storage.range(
          randomPackage.getGroupId(),
          randomPackage.getArtifactId()
      );
      assertNotNull(packages);
      assertEquals(1, packages.size());
      assertEquals(randomPackage, packages.get(0));
    }
  }

  @Test
  void testOrdering() {
    List<Package> randomPackages = PackageGenerator.randomPackageWithVersion(SHUFFLED_VERSIONS);
    String groupId = randomPackages.get(0).getGroupId();
    String artifactId = randomPackages.get(0).getArtifactId();
    storage.addAll(randomPackages);
    List<Package> packages = storage.range(groupId, artifactId);
    List<String> observedVersions = packages.stream()
        .map(Package::getVersion)
        .collect(Collectors.toList());
    List<String> expectedVersions = SORTED_VERSIONS;
    assertNotNull(packages);
    assertEquals(expectedVersions.size(), packages.size());
    assertIterableEquals(expectedVersions, observedVersions);
  }

  @Test
  void testTailRanging() {
    List<Package> randomPackages = PackageGenerator.randomPackageWithVersion(SHUFFLED_VERSIONS);
    String groupId = randomPackages.get(0).getGroupId();
    String artifactId = randomPackages.get(0).getArtifactId();
    storage.addAll(randomPackages);
    List<Package> packages = storage.range(groupId, artifactId, SORTED_VERSIONS.get(TAIL_INDEX));
    List<String> observedVersions = packages.stream()
        .map(Package::getVersion)
        .collect(Collectors.toList());
    List<String> expectedVersions = SORTED_VERSIONS.subList(TAIL_INDEX, SORTED_VERSIONS.size());
    assertNotNull(packages);
    assertEquals(expectedVersions.size(), packages.size());
    assertIterableEquals(expectedVersions, observedVersions);
  }

  @Test
  void testHeadRanging() {
    List<Package> randomPackages = PackageGenerator.randomPackageWithVersion(SHUFFLED_VERSIONS);
    String groupId = randomPackages.get(0).getGroupId();
    String artifactId = randomPackages.get(0).getArtifactId();
    storage.addAll(randomPackages);
    List<Package> packages = storage.range(groupId, artifactId,
        null, SORTED_VERSIONS.get(HEAD_INDEX));
    List<String> observedVersions = packages.stream()
        .map(Package::getVersion)
        .collect(Collectors.toList());
    List<String> expectedVersions = SORTED_VERSIONS.subList(0, HEAD_INDEX + 1);
    assertNotNull(packages);
    assertEquals(expectedVersions.size(), packages.size());
    assertIterableEquals(expectedVersions, observedVersions);
  }

  @Test
  void testHeadAndTailRanging() {
    List<Package> randomPackages = PackageGenerator.randomPackageWithVersion(SHUFFLED_VERSIONS);
    String groupId = randomPackages.get(0).getGroupId();
    String artifactId = randomPackages.get(0).getArtifactId();
    storage.addAll(randomPackages);
    List<Package> packages = storage.range(groupId, artifactId,
        SORTED_VERSIONS.get(TAIL_INDEX), SORTED_VERSIONS.get(HEAD_INDEX));
    List<String> observedVersions = packages.stream()
        .map(Package::getVersion)
        .collect(Collectors.toList());
    List<String> expectedVersions = SORTED_VERSIONS.subList(TAIL_INDEX, HEAD_INDEX + 1);
    assertNotNull(packages);
    assertEquals(expectedVersions.size(), packages.size());
    assertIterableEquals(expectedVersions, observedVersions);
  }

  @Test
  void testOverwriting() {
    Package randomPackage = PackageGenerator.randomPackage();
    String groupId = randomPackage.getGroupId();
    String artifactId = randomPackage.getArtifactId();
    String version = randomPackage.getVersion();
    Package overwrittenPackage = new Package(groupId, artifactId,
        version, PropertyGenerator.randomPath());
    storage.add(randomPackage);
    storage.add(overwrittenPackage);
    List<Package> packages = storage.range(groupId, artifactId);
    assertNotNull(packages);
    assertEquals(1, packages.size());
    assertEquals(overwrittenPackage, packages.get(0));
    assertEquals(overwrittenPackage.getPath(), packages.get(0).getPath()); // overwritten path
  }
}