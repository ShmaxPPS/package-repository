package ru.cg.webbpm.repository.service;

import java.util.List;

import ru.cg.webbpm.repository.api.PackageResponse;

public interface RepositoryService {

    List<PackageResponse> packages();

    byte[] download(String path);
}
