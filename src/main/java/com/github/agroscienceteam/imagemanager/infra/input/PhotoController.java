package com.github.agroscienceteam.imagemanager.infra.input;

import com.github.agroscienceteam.imagemanager.configs.annotations.Audit;
import com.github.agroscienceteam.imagemanager.domain.photo.PhotoRepository;
import com.github.agroscienceteam.imagemanager.domain.photo.PhotoWithWorkersResults;
import io.swagger.v3.oas.annotations.Operation;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/image-manager")
public class PhotoController {

  private final PhotoRepository repo;


  @GetMapping("contours/{contourId}/photos")
  @Operation(description = "Получить метаинформацию снимков")
  @Audit
  public List<PhotoWithWorkersResults> getPhotosMetaInfo(
          final @PathVariable UUID contourId,
          final @RequestParam LocalDate from,
          final @RequestParam LocalDate to
  ) {
    return repo.findByFieldId(contourId, from, to);
  }

}