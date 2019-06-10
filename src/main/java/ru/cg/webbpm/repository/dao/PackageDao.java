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
public class PackageDao {
  @Autowired
  private DSLContext dslContext;

  public VersionRange getAvailableVersions(String studioVersion, String groupId, String artifactId) {
    Record2<String, String> record = dslContext
        .select(LINK_STUDIO_PACKAGES.PACKAGE_MIN_VERSION, LINK_STUDIO_PACKAGES.PACKAGE_MAX_VERSION)
        .from(LINK_STUDIO_PACKAGES)
        .where(LINK_STUDIO_PACKAGES.STUDIO_VERSION.eq(studioVersion)
            .and(LINK_STUDIO_PACKAGES.PACKAGE_GROUP_ID.eq(groupId))
            .and(LINK_STUDIO_PACKAGES.PACKAGE_ARTIFACT_ID.eq(artifactId)))
        .fetchOne();

    String minVersionRecord = record.get(LINK_STUDIO_PACKAGES.PACKAGE_MIN_VERSION);
    String maxVersionRecord = record.get(LINK_STUDIO_PACKAGES.PACKAGE_MAX_VERSION);
    Version minVersion = minVersionRecord != null ? Version.parseMaven(minVersionRecord) : null;
    Version maxVersion = maxVersionRecord != null ? Version.parseMaven(maxVersionRecord) : null;
    return new VersionRange(minVersion, maxVersion);
  }
}
