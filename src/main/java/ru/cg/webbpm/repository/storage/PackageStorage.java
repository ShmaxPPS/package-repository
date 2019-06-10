package ru.cg.webbpm.repository.storage;

import java.util.*;

import org.springframework.stereotype.Component;

import ru.cg.webbpm.repository.api.PackageResponse;
import ru.cg.webbpm.repository.model.Version;

/**
 * @author m.popov
 */
@Component
public class PackageStorage {

  private Map<String, NavigableMap<Version, PackageResponse>> storage = new HashMap<>();

  public void add(PackageResponse pPackageResponse) {
    String packageId = pPackageResponse.getPackageId();
    Version mavenVersion = Version.parseMaven(pPackageResponse.getVersion());
    NavigableMap<Version, PackageResponse> map = storage.getOrDefault(packageId, new TreeMap<>());
    map.put(mavenVersion, pPackageResponse);
    storage.put(packageId, map);
  }

  public void addAll(Collection<? extends PackageResponse> packages) {
    packages.forEach(this::add);
  }

  public List<PackageResponse> range(String groupId, String artifactId) {
    return range(groupId, artifactId, null, null);
  }

  public List<PackageResponse> range(String groupId, String artifactId, Version minVersion, Version maxVersion) {
    String packageId = groupId + "." + artifactId;
    NavigableMap<Version, PackageResponse> map = storage.getOrDefault(packageId, new TreeMap<>());
    if (minVersion == null && maxVersion == null) {
      return new ArrayList<>(map.values());
    }
    else if (minVersion == null) {
      return new ArrayList<>(map.headMap(maxVersion, true).values());
    }
    else if (maxVersion == null) {
      return new ArrayList<>(map.tailMap(minVersion, true).values());
    }
    else {
      return new ArrayList<>(map.subMap(minVersion, true, maxVersion, true).values());
    }
  }
}
