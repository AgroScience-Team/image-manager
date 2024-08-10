package com.github.agroscienceteam.imagemanager.infra.input;

import com.github.agroscienceteam.imagemanager.configs.annotations.Audit;
import com.github.agroscienceteam.imagemanager.domain.photo.PhotoRepository;
import com.github.agroscienceteam.imagemanager.domain.photo.PhotoWithProcessedPhotos;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Min;
import java.time.LocalDate;
import java.util.List;
import lombok.NonNull;
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


  @GetMapping("/{fieldId}")
  @Operation(description = "Получить метаинформацию снимков")
  @Audit
  public List<PhotoWithProcessedPhotos> getPhotosMetaInfo(@NonNull final @PathVariable @Min(0) Long fieldId,
                                                          @NonNull final @RequestParam LocalDate from,
                                                          @NonNull final @RequestParam LocalDate to) {
    return repo.findByFieldId(fieldId, from, to);
  }

}
