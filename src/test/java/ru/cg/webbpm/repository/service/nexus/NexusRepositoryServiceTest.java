package ru.cg.webbpm.repository.service.nexus;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import ru.cg.webbpm.repository.model.Package;
import ru.cg.webbpm.repository.model.nexus.NexusPackages;

import static org.mockito.Mockito.when;

/**
 * @author m.popov
 */
class NexusRepositoryServiceTest {

  private static final String GETTING_PACKAGES_URL = "http://repo.cg.ru/service/rest/v1/search?repository=packages-releases-local";

  @Mock
  private RestTemplate restTemplate;

  @InjectMocks
  private NexusRepositoryService repositoryService = new NexusRepositoryService();

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testGettingPackagesMethod() throws IOException {
    File nexusPackagesFile = new File("src\\test\\resources\\ru.cg.webbpm.repository\\service.nexus\\NexusPackages.json");
    NexusPackages nexusPackages = new ObjectMapper().readValue(nexusPackagesFile, NexusPackages.class);
    ResponseEntity<NexusPackages> myEntity = new ResponseEntity<>(nexusPackages, HttpStatus.OK);
    when(restTemplate.exchange(
        ArgumentMatchers.eq(GETTING_PACKAGES_URL),
        ArgumentMatchers.eq(HttpMethod.GET),
        ArgumentMatchers.any(),
        ArgumentMatchers.eq(NexusPackages.class))
    ).thenReturn(myEntity);
    List<Package> observedPackages = repositoryService.packages();
    File packagesFile = new File("src\\test\\resources\\ru.cg.webbpm.repository\\service.nexus\\Packages.json");
    List<Package> expectedPackages = new ObjectMapper().readValue(packagesFile, new TypeReference<List<Package>>() {});
    Assertions.assertEquals(expectedPackages, observedPackages);
  }
}
