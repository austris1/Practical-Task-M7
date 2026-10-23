package lv.bootcamp.shelter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lv.bootcamp.shelter.model.AnimalStatus;
import lv.bootcamp.shelter.model.AnimalType;

/**
 * Response body for a single animal returned by the API.
 * {@code adoptionNote} is only populated for ADMIN callers (see AnimalService#toResponse) —
 * everyone else just sees the plain {@code status}.
 */
public record AnimalResponse(
        @Schema(description = "Unique animal ID", example = "1")
        Long id,
        @Schema(description = "Animal's name", example = "Milo")
        String name,
        @Schema(description = "Type of animal", example = "CAT")
        AnimalType type,
        @Schema(description = "Breed, if known", example = "Siamese")
        String breed,
        @Schema(description = "Age in years", example = "3")
        Integer age,
        @Schema(description = "Short description of the animal's personality")
        String description,
        @Schema(description = "Current status", example = "AVAILABLE")
        AnimalStatus status,
        @Schema(description = "Resolved image path or URL", example = "/images/animals/Luna.jpeg")
        String imageUrl,
        @Schema(description = "Adoption date note, only set for adopted animals", example = "adopted on 2026-10-23")
        String adoptionNote
) {
}
