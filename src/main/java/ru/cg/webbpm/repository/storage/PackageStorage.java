package ru.cg.webbpm.repository.storage;

import java.util.*;

import org.springframework.stereotype.Component;

import ru.cg.webbpm.repository.model.Package;
import ru.cg.webbpm.repository.model.Version;

/**
 * @author m.popov
 */
@Component
public class PackageStorage {

  private Map<String, NavigableMap<Version, Package>> storage = new HashMap<>();

  public void add(Package pPackage) {
    String packageId = pPackage.getPackageId();
    Version mavenVersion = Version.parseMaven(pPackage.getVersion());
    NavigableMap<Version, Package> map = storage.getOrDefault(packageId, new TreeMap<>());
    map.put(mavenVersion, pPackage);
    storage.put(packageId, map);
  }

  public void addAll(Collection<? extends Package> packages) {
    packages.forEach(this::add);
  }

  public List<Package> range(String groupId, String artifactId) {
    return range(groupId, artifactId, null);
  }

  public List<Package> range(String groupId, String artifactId, String minVersion) {
    return range(groupId, artifactId, minVersion, null);
  }

  public List<Package> range(String groupId, String artifactId, String minVersion, String maxVersion) {
    String packageId = groupId + "." + artifactId;
    Version minObjVersion = minVersion != null ? Version.parseMaven(minVersion) : null;
    Version maxObjVersion = maxVersion != null ? Version.parseMaven(maxVersion) : null;
    NavigableMap<Version, Package> map = storage.getOrDefault(packageId, new TreeMap<>());
    if (minObjVersion == null && maxObjVersion == null) {
      return new ArrayList<>(map.values());
    }
    else if (minObjVersion == null) {
      return new ArrayList<>(map.headMap(maxObjVersion, true).values());
    }
    else if (maxObjVersion == null) {
      return new ArrayList<>(map.tailMap(minObjVersion, true).values());
    }
    else {
      return new ArrayList<>(map.subMap(minObjVersion, true,
          maxObjVersion, true).values());
    }
  }
}
