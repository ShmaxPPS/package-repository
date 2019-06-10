package ru.cg.webbpm.repository.storage;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ru.cg.webbpm.repository.api.PackageResponse;
import ru.cg.webbpm.repository.model.Version;
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
    PackageResponse randomPackageResponse = PackageGenerator.randomPackage();
    storage.add(randomPackageResponse);
    List<PackageResponse> packageResponses = storage.range(
        randomPackageResponse.getGroupId(),
        randomPackageResponse.getArtifactId()
    );
    assertNotNull(packageResponses);
    assertEquals(1, packageResponses.size());
    assertEquals(randomPackageResponse, packageResponses.get(0));
  }

  @Test
  void testAddingManyAndRangingMany() {
    List<PackageResponse> randomPackageResponses = PackageGenerator.randomPackages(5);
    for (PackageResponse randomPackageResponse : randomPackageResponses) {
      storage.add(randomPackageResponse);
    }
    for (PackageResponse randomPackageResponse : randomPackageResponses) {
      List<PackageResponse> packageResponses = storage.range(
          randomPackageResponse.getGroupId(),
          randomPackageResponse.getArtifactId()
      );
      assertNotNull(packageResponses);
      assertEquals(1, packageResponses.size());
      assertEquals(randomPackageResponse, packageResponses.get(0));
    }
  }

  @Test
  void testAddingAllAndRangingMany() {
    List<PackageResponse> randomPackageResponses = PackageGenerator.randomPackages(5);
    storage.addAll(randomPackageResponses);
    for (PackageResponse randomPackageResponse : randomPackageResponses) {
      List<PackageResponse> packageResponses = storage.range(
          randomPackageResponse.getGroupId(),
          randomPackageResponse.getArtifactId()
      );
      assertNotNull(packageResponses);
      assertEquals(1, packageResponses.size());
      assertEquals(randomPackageResponse, packageResponses.get(0));
    }
  }

  @Test
  void testOrdering() {
    List<PackageResponse> randomPackageResponses = PackageGenerator.randomPackageWithVersion(SHUFFLED_VERSIONS);
    String groupId = randomPackageResponses.get(0).getGroupId();
    String artifactId = randomPackageResponses.get(0).getArtifactId();
    storage.addAll(randomPackageResponses);
    List<PackageResponse> packageResponses = storage.range(groupId, artifactId);
    List<String> observedVersions = packageResponses.stream()
        .map(PackageResponse::getVersion)
        .collect(Collectors.toList());
    List<String> expectedVersions = SORTED_VERSIONS;
    assertNotNull(packageResponses);
    assertEquals(expectedVersions.size(), packageResponses.size());
    assertIterableEquals(expectedVersions, observedVersions);
  }

  @Test
  void testTailRanging() {
    List<PackageResponse> randomPackageResponses = PackageGenerator.randomPackageWithVersion(SHUFFLED_VERSIONS);
    String groupId = randomPackageResponses.get(0).getGroupId();
    String artifactId = randomPackageResponses.get(0).getArtifactId();
    storage.addAll(randomPackageResponses);
    Version minVersion = Version.parseMaven(SORTED_VERSIONS.get(TAIL_INDEX));
    List<PackageResponse> packageResponses = storage.range(groupId, artifactId, minVersion, null);
    List<String> observedVersions = packageResponses.stream()
        .map(PackageResponse::getVersion)
        .collect(Collectors.toList());
    List<String> expectedVersions = SORTED_VERSIONS.subList(TAIL_INDEX, SORTED_VERSIONS.size());
    assertNotNull(packageResponses);
    assertEquals(expectedVersions.size(), packageResponses.size());
    assertIterableEquals(expectedVersions, observedVersions);
  }

  @Test
  void testHeadRanging() {
    List<PackageResponse> randomPackageResponses = PackageGenerator.randomPackageWithVersion(SHUFFLED_VERSIONS);
    String groupId = randomPackageResponses.get(0).getGroupId();
    String artifactId = randomPackageResponses.get(0).getArtifactId();
    storage.addAll(randomPackageResponses);
    Version maxVersion = Version.parseMaven(SORTED_VERSIONS.get(HEAD_INDEX));
    List<PackageResponse> packageResponses = storage.range(groupId, artifactId, null, maxVersion);
    List<String> observedVersions = packageResponses.stream()
        .map(PackageResponse::getVersion)
        .collect(Collectors.toList());
    List<String> expectedVersions = SORTED_VERSIONS.subList(0, HEAD_INDEX + 1);
    assertNotNull(packageResponses);
    assertEquals(expectedVersions.size(), packageResponses.size());
    assertIterableEquals(expectedVersions, observedVersions);
  }

  @Test
  void testHeadAndTailRanging() {
    List<PackageResponse> randomPackageResponses = PackageGenerator.randomPackageWithVersion(SHUFFLED_VERSIONS);
    String groupId = randomPackageResponses.get(0).getGroupId();
    String artifactId = randomPackageResponses.get(0).getArtifactId();
    storage.addAll(randomPackageResponses);
    Version minVersion = Version.parseMaven(SORTED_VERSIONS.get(TAIL_INDEX));
    Version maxVersion = Version.parseMaven(SORTED_VERSIONS.get(HEAD_INDEX));
    List<PackageResponse> packageResponses = storage.range(groupId, artifactId, minVersion, maxVersion);
    List<String> observedVersions = packageResponses.stream()
        .map(PackageResponse::getVersion)
        .collect(Collectors.toList());
    List<String> expectedVersions = SORTED_VERSIONS.subList(TAIL_INDEX, HEAD_INDEX + 1);
    assertNotNull(packageResponses);
    assertEquals(expectedVersions.size(), packageResponses.size());
    assertIterableEquals(expectedVersions, observedVersions);
  }

  @Test
  void testOverwriting() {
    PackageResponse randomPackageResponse = PackageGenerator.randomPackage();
    String groupId = randomPackageResponse.getGroupId();
    String artifactId = randomPackageResponse.getArtifactId();
    String version = randomPackageResponse.getVersion();
    PackageResponse overwrittenPackageResponse = new PackageResponse(groupId, artifactId,
        version, PropertyGenerator.randomPath());
    storage.add(randomPackageResponse);
    storage.add(overwrittenPackageResponse);
    List<PackageResponse> packageResponses = storage.range(groupId, artifactId);
    assertNotNull(packageResponses);
    assertEquals(1, packageResponses.size());
    assertEquals(overwrittenPackageResponse, packageResponses.get(0));
    assertEquals(overwrittenPackageResponse.getPath(), packageResponses.get(0).getPath()); // overwritten path
  }
}