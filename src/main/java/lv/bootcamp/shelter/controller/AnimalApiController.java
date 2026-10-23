package lv.bootcamp.shelter.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lv.bootcamp.shelter.dto.AnimalCreateRequest;
import lv.bootcamp.shelter.dto.AnimalResponse;
import lv.bootcamp.shelter.service.AnimalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for shelter animal endpoints.
 * Returns JSON — does not render HTML pages.
 */
@Tag(name = "Animals", description = "Manage shelter animals")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/animals")
public class AnimalApiController {

    private final AnimalService animalService;

    @Operation(summary = "List all animals", description = "Returns all animals currently in memory.")
    @ApiResponse(responseCode = "200", description = "Animals returned successfully")
    @GetMapping
    public List<AnimalResponse> findAll() {
        return animalService.findAll();
    }

    @Operation(summary = "Get an animal by ID")
    @ApiResponse(responseCode = "200", description = "Animal found")
    @ApiResponse(responseCode = "404", description = "No animal with this ID")
    @GetMapping("/{id}")
    public ResponseEntity<AnimalResponse> findById(@Parameter(description = "ID of the animal", example = "1") @PathVariable Long id) {
        return animalService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Lists adopted animals.
     */
    @Operation(summary = "List adopted animals", description = "Returns only animals with status ADOPTED.")
    @ApiResponse(responseCode = "200", description = "Adopted animals returned successfully")
    @GetMapping("/adopted")
    public List<AnimalResponse> findAdopted() {
        return animalService.findAdopted();
    }

    /**
     * Creates a new animal.
     */
    @Operation(summary = "Create an animal")
    @ApiResponse(responseCode = "201", description = "Animal created")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AnimalResponse create(@RequestBody @Valid AnimalCreateRequest request) {
        return animalService.create(request);
    }

    /**
     * Adopts an animal
     */
    @Operation(summary = "Adopt an animal", description = "Marks the animal as ADOPTED and records today's date.")
    @ApiResponse(responseCode = "200", description = "Animal adopted")
    @ApiResponse(responseCode = "404", description = "No animal with this ID")
    @ApiResponse(responseCode = "409", description = "Animal is already adopted")
    @PostMapping("/{id}/adopt")
    public ResponseEntity<AnimalResponse> adopt(@Parameter(description = "ID of the animal", example = "1") @PathVariable Long id) {
        return animalService.adopt(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleAlreadyAdopted(IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}
