package ru.cg.webbpm.repository.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ru.cg.webbpm.repository.model.Package;
import ru.cg.webbpm.repository.model.PackageRequest;
import ru.cg.webbpm.repository.service.RepositoryService;
import ru.cg.webbpm.repository.storage.PackageStorage;

/**
 * @author m.popov
 */
@RestController
@RequestMapping("/repository/v1")
public class RepositoryController {

  @Autowired
  private RepositoryService service;
  @Autowired
  private PackageStorage storage;

  @RequestMapping(value = "/packages", method = RequestMethod.GET)
  @ResponseBody
  public List<List<Package>> packages(
      @RequestParam(name = "studio") String studio,
      @RequestParam(name = "requests") List<PackageRequest> requests
  ) {
    // Todo studio
    return requests.stream()
        .map(request -> storage.range(
            request.getGroupId(),
            request.getArtifactId(),
            request.getMinVersion(),
            request.getMaxVersion()
        ))
        .collect(Collectors.toList());
  }

  @RequestMapping(value = "/download", method = RequestMethod.POST)
  @ResponseBody
  public byte[] download(@RequestParam(name = "path") String path) {
    return service.download(path);
  }

}
