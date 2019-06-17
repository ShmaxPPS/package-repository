package ru.cg.webbpm.repository.storage;

import java.util.*;

import org.springframework.stereotype.Component;

import ru.cg.webbpm.repository.model.Version;
import ru.cg.webbpm.repository.model.Package;

/**
 * @author m.popov
 */
@Component
public class PackageStorageImpl implements PackageStorage {

  private Map<String, NavigableMap<Version, Package>> storage = new HashMap<>();

  @Override
  public void add(Package pPackage) {
    String packageId = pPackage.getPackageId();
    Version mavenVersion = Version.parseMaven(pPackage.getVersion());
    NavigableMap<Version, Package> map = storage.getOrDefault(packageId, new TreeMap<>());
    map.put(mavenVersion, pPackage);
    storage.put(packageId, map);
  }

  @Override
  public void addAll(Collection<? extends Package> packages) {
    packages.forEach(this::add);
  }

  @Override
  public List<Package> all(String groupId, String artifactId) {
    return range(groupId, artifactId, null, null);
  }

  @Override
  public List<Package> head(String groupId, String artifactId, Version toVersion) {
    return head(groupId, artifactId, toVersion, true);
  }

  @Override
  public List<Package> head(String groupId, String artifactId, Version toVersion, boolean toInclusive) {
    return range(groupId, artifactId, null, true, toVersion, toInclusive);
  }

  @Override
  public List<Package> tail(String groupId, String artifactId, Version fromVersion) {
    return tail(groupId, artifactId, fromVersion, true);
  }

  @Override
  public List<Package> tail(String groupId, String artifactId, Version fromVersion, boolean fromInclusive) {
    return range(groupId, artifactId, fromVersion, fromInclusive, null, true);
  }

  public List<Package> range(String groupId, String artifactId, Version fromVersion, Version toVersion) {
    return range(groupId, artifactId, fromVersion, true, toVersion, true);
  }

  @Override
  public List<Package> range(String groupId, String artifactId, Version fromVersion, boolean fromInclusive,
                             Version toVersion, boolean toInclusive) {
    String packageId = groupId + "." + artifactId;
    NavigableMap<Version, Package> map = storage.getOrDefault(packageId, new TreeMap<>());
    if (fromVersion == null && toVersion == null) {
      return new ArrayList<>(map.values());
    }
    else if (fromVersion == null) {
      return new ArrayList<>(map.headMap(toVersion, toInclusive).values());
    }
    else if (toVersion == null) {
      return new ArrayList<>(map.tailMap(fromVersion, fromInclusive).values());
    }
    else {
      return new ArrayList<>(map.subMap(fromVersion, fromInclusive, toVersion, toInclusive).values());
    }
  }
}
