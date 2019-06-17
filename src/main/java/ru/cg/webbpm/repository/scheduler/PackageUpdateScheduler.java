package ru.cg.webbpm.repository.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ru.cg.webbpm.repository.service.RepositoryService;
import ru.cg.webbpm.repository.storage.PackageStorageImpl;

/**
 * @author m.popov
 */
@Component
public class PackageUpdateScheduler {

  @Autowired
  private RepositoryService service;

  @Autowired
  private PackageStorageImpl storage;

  @Scheduled(cron = "* */5 * * * ?")
  public void run() {
    storage.addAll(service.packages());
  }
}
