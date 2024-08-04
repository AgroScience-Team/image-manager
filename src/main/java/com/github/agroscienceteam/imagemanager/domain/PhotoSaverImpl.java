package com.github.agroscienceteam.imagemanager.domain;

import com.github.agroscienceteam.imagemanager.configs.annotations.DomainService;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class PhotoSaverImpl implements PhotoSaver {

  private final PhotoRepository repo;

  @Override
  public void save(Photo photo) {
    repo.save(photo);
  }

}
