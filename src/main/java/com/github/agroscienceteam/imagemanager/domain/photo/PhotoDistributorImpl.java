package com.github.agroscienceteam.imagemanager.domain.photo;

import com.github.agroscienceteam.imagemanager.configs.annotations.Command;
import com.github.agroscienceteam.imagemanager.domain.EventSender;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Command
public class PhotoDistributorImpl implements PhotoDistributor {

  private final List<String> indexes;
  private final EventSender<Photo> toWorkers;

  private final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

  public PhotoDistributorImpl(EventSender<Photo> toWorkers, PhotoRepository repo) {
    this.toWorkers = toWorkers;
    indexes = repo.findAllIndexes();
  }

  @Override
  public void distribute(Photo photo) {
    indexes.forEach(index -> executor.submit(() -> toWorkers.send(index, photo)));
  }

}
