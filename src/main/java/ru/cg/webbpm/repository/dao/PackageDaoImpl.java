package ru.cg.webbpm.repository.dao;

import org.jooq.DSLContext;
import org.jooq.Record2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ru.cg.webbpm.repository.model.Version;
import ru.cg.webbpm.repository.model.VersionRange;

import static ru.cg.webbpm.repository.db.beans.Tables.LINK_STUDIO_PACKAGES;

/**
 * @author m.popov
 */
@Component
public class PackageDaoImpl implements PackageDao {

  @Autowired
  private DSLContext dslContext;

  @Override
  public VersionRange availableVersions(String studioVersion, String groupId, String artifactId) {
    Record2<String, String> record = dslContext
        .select(LINK_STUDIO_PACKAGES.PACKAGE_FROM_VERSION, LINK_STUDIO_PACKAGES.PACKAGE_TO_VERSION)
        .from(LINK_STUDIO_PACKAGES)
        .where(LINK_STUDIO_PACKAGES.STUDIO_VERSION.eq(studioVersion)
            .and(LINK_STUDIO_PACKAGES.PACKAGE_GROUP_ID.eq(groupId))
            .and(LINK_STUDIO_PACKAGES.PACKAGE_ARTIFACT_ID.eq(artifactId)))
        .fetchOne();

    String fromVersionRecord = record.get(LINK_STUDIO_PACKAGES.PACKAGE_FROM_VERSION);
    String toVersionRecord = record.get(LINK_STUDIO_PACKAGES.PACKAGE_TO_VERSION);
    Version fromVersion = fromVersionRecord != null
        ? Version.parseMaven(fromVersionRecord)
        : Version.NEGATIVE_INFINITY;
    Version toVersion = toVersionRecord != null
        ? Version.parseMaven(toVersionRecord)
        : Version.POSITIVE_INFINITY;
    return new VersionRange(fromVersion, toVersion);
  }
}
