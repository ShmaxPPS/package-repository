package ru.cg.webbpm.repository.storage;

import java.util.Collection;
import java.util.List;

import ru.cg.webbpm.repository.model.Version;
import ru.cg.webbpm.repository.model.Package;

/**
 * @author m.popov
 */
public interface PackageStorage {

  void add(Package pPackage);

  void addAll(Collection<? extends Package> packages);

  List<Package> all(String groupId, String artifactId);

  List<Package> head(String groupId, String artifactId, Version toVersion);

  List<Package> head(String groupId, String artifactId, Version toVersion, boolean toInclusive);

  List<Package> tail(String groupId, String artifactId, Version fromVersion);

  List<Package> tail(String groupId, String artifactId, Version fromVersion, boolean fromInclusive);

  List<Package> range(String groupId, String artifactId, Version minVersion, Version maxVersion);

  List<Package> range(String groupId, String artifactId, Version fromVersion, boolean fromInclusive,
                      Version toVersion, boolean toInclusive);
}
