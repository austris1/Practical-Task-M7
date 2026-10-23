package lv.bootcamp.shelter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lv.bootcamp.shelter.model.AnimalType;

/**
 * JSON request body for creating a new animal via the REST API.
 * Status is not included; all new animals start as AVAILABLE.
 */
public record AnimalCreateRequest(

        @Schema(description = "Animal's name", example = "Milo")
        @NotBlank(message = "Name is required")
        String name,

        @Schema(description = "Type of animal", example = "CAT")
        @NotNull(message = "Type is required")
        AnimalType type,

        @Schema(description = "Breed, if known", example = "Siamese")
        String breed,

        @Schema(description = "Age in years", example = "3", minimum = "0")
        @Min(value = 0, message = "Age cannot be negative")
        Integer age,

        @Schema(description = "Short description of the animal's personality", example = "Calm and affectionate.")
        String description,

        @Schema(description = "Image filename from /images/animals/ or a full URL.", example = "Luna.jpeg")
        String imageUrl
) {
}