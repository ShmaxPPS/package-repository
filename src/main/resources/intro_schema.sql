DROP TABLE IF EXISTS link_studio_packages;

CREATE TABLE link_studio_packages (
  id                  INT          NOT NULL PRIMARY KEY,
  studio_version      VARCHAR(50)  NOT NULL,
  package_group_id    VARCHAR(50)  NOT NULL,
  package_artifact_id VARCHAR(50)  NOT NULL,
  package_from_version VARCHAR(50)  NOT NULL,
  package_to_version VARCHAR(50)  NOT NULL
);

INSERT INTO link_studio_packages VALUES
  (1, '3.0.0', 'ru.cg.webbpm.packages.base', 'resources',       '3.0.0', '3.0.15'),
  (2, '3.0.0', 'ru.cg.webbpm.packages.base', 'electronic_sign', '3.0.0', '3.0.1'),
  (3, '3.0.0', 'ru.cg.webbpm.packages.base', 'fias',            '3.0.0', '3.0.3'),
  (4, '3.1.0', 'ru.cg.webbpm.packages.base', 'resources',       '3.1.0', '3.1.5'),
  (5, '3.1.0', 'ru.cg.webbpm.packages.base', 'electronic_sign', '3.1.0', '3.1.1'),
  (6, '3.2.0', 'ru.cg.webbpm.packages.base', 'fias',            '3.2.0', '3.2.5');