package ru.cg.webbpm.repository.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ru.cg.webbpm.repository.service.RepositoryService;
import ru.cg.webbpm.repository.storage.PackageStorageImpl;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author m.popov
 */
class RepositoryControllerTest {

  @InjectMocks
  private RepositoryController controller;

  @Mock
  private RepositoryService service;

  @Mock
  private PackageStorageImpl storage;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  void testPackages() {
    fail("Not implemented yet");
  }

  @Test
  void download() {
    fail("Not implemented yet");
  }
}