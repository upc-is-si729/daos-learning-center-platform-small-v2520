package pe.edu.upc.center.platform.profiles.domain.exceptions;

public class ProfileNotfoundException extends RuntimeException {

  public ProfileNotfoundException(Long profileId) {
    super(String.format("Profile with id %s does not exist.", profileId));
  }

  public ProfileNotfoundException(Long profileId, Throwable cause) {
    super(String.format("Profile with id %s does not exist", profileId), cause);
  }
}