package ru.cg.webbpm.repository.dao;

import ru.cg.webbpm.repository.model.VersionRange;


/**
 * @author m.popov
 */
public interface PackageDao {

  VersionRange availableVersions(String studioVersion, String groupId, String artifactId);
}
