package ru.cg.webbpm.repository.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ru.cg.webbpm.repository.dao.PackageDao;
import ru.cg.webbpm.repository.api.PackageResponse;
import ru.cg.webbpm.repository.api.PackageRequest;
import ru.cg.webbpm.repository.model.VersionRange;
import ru.cg.webbpm.repository.service.RepositoryService;
import ru.cg.webbpm.repository.storage.PackageStorageImpl;

/**
 * @author m.popov
 */
@RestController
@RequestMapping("/repository/v1")
public class RepositoryController {
  private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryController.class);

  @Autowired
  private RepositoryService service;
  @Autowired
  private PackageStorageImpl storage;
  @Autowired
  private PackageDao dao;

  @RequestMapping(value = "/packages", method = RequestMethod.GET)
  @ResponseBody
  public List<List<PackageResponse>> packages(
      @RequestParam(name = "studio") String studioVersion,
      @RequestParam(name = "requests") List<PackageRequest> requests
  ) {
    LOGGER.info("Start getting packages");
    return requests.stream()
        .map(request -> processPackageRequest(studioVersion, request))
        .collect(Collectors.toList());
  }

  private List<PackageResponse> processPackageRequest(String studioVersion, PackageRequest request) {
    VersionRange versionRange = dao.availableVersions(
        studioVersion,
        request.getGroupId(),
        request.getArtifactId()
    );
    boolean fromInclusive =
        versionRange.getFromVersion().toString().equals(request.getFromVersion()) &&
        request.getFromInclusive();

    boolean toInclusive =
        versionRange.getToVersion().toString().equals(request.getToVersion()) &&
        request.getToInclusive();

    return storage.range(
        request.getGroupId(), request.getArtifactId(),
        versionRange.getFromVersion(), fromInclusive,
        versionRange.getToVersion(), toInclusive
    ).stream()
        .map(pPackage -> new PackageResponse(
            pPackage.getGroupId(),
            pPackage.getArtifactId(),
            pPackage.getVersion(),
            pPackage.getPath()
        ))
        .collect(Collectors.toList());
  }

  @RequestMapping(value = "/download", method = RequestMethod.POST)
  @ResponseBody
  public byte[] download(@RequestParam(name = "path") String path) {
    LOGGER.info("Start downloading packages");
    return service.download(path);
  }
}
