package ru.cg.webbpm.repository.service;

import java.util.List;

import ru.cg.webbpm.repository.model.Package;

public interface RepositoryService {

    List<Package> packages();

    byte[] download(String path);
}
