package com.github.agroscienceteam.imagemanager.domain.photo;

import static com.github.agroscienceteam.imagemanager.domain.photo.IndexesConstants.INDEXES;

import com.github.agroscienceteam.imagemanager.configs.annotations.Command;
import com.github.agroscienceteam.imagemanager.domain.EventSender;
import com.github.agroscienceteam.imagemanager.domain.PhotoMapper;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.RequiredArgsConstructor;

@Command
@RequiredArgsConstructor
public class PhotoDistributorImpl implements PhotoDistributor {

  private final EventSender<WorkerRequest> toWorkers;
  private final PhotoMapper photoMapper;
  private final PhotoRepository repo;
  private final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

  @Override
  public void distribute(Photo photo) {
    INDEXES.forEach(
            index -> executor.submit(
                    () -> toWorkers.send(
                            index, photoMapper.toWorkerRequest(photo, repo.generateJobId().toString())
                    )
            )
    );
  }

}