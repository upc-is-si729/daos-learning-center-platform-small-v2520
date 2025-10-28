package pe.edu.upc.center.platform.profiles.interfaces.rest.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.center.platform.profiles.domain.model.aggregates.Profile;
import pe.edu.upc.center.platform.profiles.domain.model.commands.DeleteProfileCommand;
import pe.edu.upc.center.platform.profiles.domain.model.queries.GetAllProfilesQuery;
import pe.edu.upc.center.platform.profiles.domain.model.queries.GetProfileByAgeQuery;
import pe.edu.upc.center.platform.profiles.domain.model.queries.GetProfileByIdQuery;
import pe.edu.upc.center.platform.profiles.domain.services.ProfileCommandService;
import pe.edu.upc.center.platform.profiles.domain.services.ProfileQueryService;
import pe.edu.upc.center.platform.profiles.interfaces.rest.assemblers.ProfileAssembler;
import pe.edu.upc.center.platform.profiles.interfaces.rest.resources.CreateProfileRequest;
import pe.edu.upc.center.platform.profiles.interfaces.rest.resources.ProfileMinimalResponse;
import pe.edu.upc.center.platform.profiles.interfaces.rest.resources.ProfileResponse;
import pe.edu.upc.center.platform.profiles.interfaces.rest.resources.UpdateProfileRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * REST controller for managing profiles.
 */
@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET,
    RequestMethod.PUT, RequestMethod.DELETE })
@RestController
@RequestMapping(value = "/api/v1/profiles", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Profiles", description = "Profile Management Endpoints")
public class ProfilesController {

  private final ProfileQueryService profileQueryService;
  private final ProfileCommandService profileCommandService;

  /**
   * Constructor for ProfilesController.
   *
   * @param profileQueryService   the service for handling profile queries
   * @param profileCommandService the service for handling profile commands
   */
  public ProfilesController(ProfileQueryService profileQueryService,
                            ProfileCommandService profileCommandService) {
    this.profileQueryService = profileQueryService;
    this.profileCommandService = profileCommandService;
  }

  /**
   * Endpoint to create a new profile.
   *
   * @param request the profile data to be created
   * @return a ResponseEntity containing the created profile resource or a bad request status
   *     if creation fails
   */
  @Operation(summary = "Create a new profile",
      description = "Creates a new profile with the provided data",
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "Profile data for creation", required = true,
          content = @Content (
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = CreateProfileRequest.class))),
      responses = {
        @ApiResponse(responseCode = "201", description = "Profile created successfully",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = ProfileMinimalResponse.class))),
        @ApiResponse(responseCode = "400", description = "Bad request - Invalid input data",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = RuntimeException.class)))
      }
  )
  @PostMapping
  public ResponseEntity<ProfileMinimalResponse> createProfile(
      @RequestBody CreateProfileRequest request) {

    var createProfileCommand = ProfileAssembler.toCommandFromRequest(request);
    var profileId = this.profileCommandService.handle(createProfileCommand);

    if (Objects.isNull(profileId) || profileId.equals(0L)) {
      return ResponseEntity.badRequest().build();
    }

    var getProfileByIdQuery = new GetProfileByIdQuery(profileId);
    var optionalProfile = this.profileQueryService.handle(getProfileByIdQuery);

    var profileMinimalResponse = ProfileAssembler.toResponseMinimalFromEntity(optionalProfile.get());
    return new ResponseEntity<>(profileMinimalResponse, HttpStatus.CREATED);
  }

  /**
   * Endpoint to retrieve all profiles or filter by age.
   *
   * @param age optional age parameter to filter profiles
   * @return a list of ResponseMinimalEntity
   */
  @Operation( summary = "Retrieve all profiles",
    description = "Retrieves all profiles or filters by age if provided",
    responses = {
      @ApiResponse(responseCode = "200", description = "Profiles retrieved successfully",
          content = @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              array = @ArraySchema(schema = @Schema(implementation = ProfileMinimalResponse.class)) ))
    }
  )
  @GetMapping
  public ResponseEntity<List<ProfileMinimalResponse>> getAllProfiles(
      @RequestParam(required = false) Integer age) {

    List<Profile> profiles;
    if (Objects.isNull(age)) {
      var getAllProfilesQuery = new GetAllProfilesQuery();
      profiles = this.profileQueryService.handle(getAllProfilesQuery);
    }
    else {
      var query = new GetProfileByAgeQuery(age);
      profiles = this.profileQueryService.handle(query);
    }

    var profileMinimalResponses = profiles.stream()
        .map(ProfileAssembler::toResponseMinimalFromEntity)
        .collect(Collectors.toList());
    return ResponseEntity.ok(profileMinimalResponses);
  }

  /**
   * Endpoint to retrieve a profile by its ID.
   *
   * @param profileId the ID of the profile to be retrieved
   * @return a ResponseEntity containing the profile resource or a bad request status if not found
   */
  @Operation(summary = "Retrieve a profile by its ID",
    description = "Retrieves a profile using its unique ID",
      responses = {
          @ApiResponse(responseCode = "200", description = "Profiles retrieved successfully",
              content = @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = ProfileResponse.class)))
      }
  )

  @GetMapping("/{profileId}")
  public ResponseEntity<ProfileResponse> getProfileById(@PathVariable Long profileId) {
    var getProfileByIdQuery = new GetProfileByIdQuery(profileId);
    var optionalProfile = this.profileQueryService.handle(getProfileByIdQuery);
    if (optionalProfile.isEmpty()) {
      return ResponseEntity.badRequest().build();
    }
    var profileResponse = ProfileAssembler.toResponseFromEntity(optionalProfile.get());
    return ResponseEntity.ok(profileResponse);
  }

  /**
   * Endpoint to update an existing profile.
   *
   * @param profileId the ID of the profile to be updated
   * @param request  the updated profile data
   * @return a ResponseEntity containing the updated profile resource or a bad request status
   *     if the update fails
   */
  @Operation(summary = "Update an existing profile",
    description = "Update an existing profile with the provided data",
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "Profile data for update", required = true,
          content = @Content (
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = UpdateProfileRequest.class))),
      responses = {
          @ApiResponse(responseCode = "200", description = "Profile updated successfully",
              content = @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = ProfileResponse.class))),
          @ApiResponse(responseCode = "400", description = "Bad request - Invalid input data",
              content = @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = RuntimeException.class)))
      }
  )
  @PutMapping("/{profileId}")
  public ResponseEntity<ProfileResponse> updateProfile(@PathVariable Long profileId,
                                                       @RequestBody UpdateProfileRequest request) {
    var updateProfileCommand = ProfileAssembler.toCommandFromRequest(profileId, request);
    var optionalProfile = this.profileCommandService.handle(updateProfileCommand);

    if (optionalProfile.isEmpty()) {
      return ResponseEntity.badRequest().build();
    }
    var profileResource = ProfileAssembler.toResponseFromEntity(optionalProfile.get());
    return ResponseEntity.ok(profileResource);
  }

  /**
   * Endpoint to delete a profile by its ID.
   *
   * @param profileId the ID of the profile to be deleted
   * @return a ResponseEntity with no content if deletion is successful
   */
  @Operation(summary = "Delete a profile by its ID",
    description = "Deletes a profile using its unique ID",
      responses = {
          @ApiResponse(responseCode = "204", description = "Profile deleted successfully"),
          @ApiResponse(responseCode = "400", description = "Bad request - Invalid profile ID",
              content = @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = RuntimeException.class)))
      }
  )
  @DeleteMapping("/{profileId}")
  public ResponseEntity<?> deleteProfile(@PathVariable Long profileId) {
    var deleteProfileCommand = new DeleteProfileCommand(profileId);
    this.profileCommandService.handle(deleteProfileCommand);
    return ResponseEntity.noContent().build();
  }

  /**
   * Endpoint to search profiles by age.
   *
   * @param age the age to filter profiles (optional)
   * @return a list of profiles matching the specified age
   */
  @GetMapping("/search")
  public ResponseEntity<List<ProfileResponse>> getProfileByAge(
      @RequestParam(required = false) Integer age) {
    if (Objects.nonNull(age)) {
      var query = new GetProfileByAgeQuery(age);
      var profiles = this.profileQueryService.handle(query);

      var profileResources = profiles.stream()
          .map(ProfileAssembler::toResponseFromEntity)
          .collect(Collectors.toList());
      return ResponseEntity.ok(profileResources);
    }
    else {
      return ResponseEntity.badRequest().build();
    }
  }
}
