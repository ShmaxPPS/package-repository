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
import ru.cg.webbpm.repository.storage.PackageStorage;

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
  private PackageStorage storage;
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
        .map(request -> {
          VersionRange versionRange = dao.getAvailableVersions(
              studioVersion,
              request.getGroupId(),
              request.getArtifactId()
          );
          return storage.range(
              request.getGroupId(),
              request.getArtifactId(),
              versionRange.getMinVersion(),
              versionRange.getMaxVersion()
          );
        })
        .collect(Collectors.toList());
  }

  @RequestMapping(value = "/download", method = RequestMethod.POST)
  @ResponseBody
  public byte[] download(@RequestParam(name = "path") String path) {
    LOGGER.info("Start downloading packages");
    return service.download(path);
  }
}
