package pe.edu.upc.center.platform.faculties.interfaces.rest.resources;

/**
 * Response object for student information.
 *
 * @param studentCode  The unique code of the student.
 * @param profileId   the ID of the profile associated with the student
 * @param programId    The ID of the program the student is enrolled in.
 * @param curriculumId The ID of the curriculum the student follows.
 * @param startPeriod  The period when the student started.
 */
public record StudentResponse(String studentCode, Long profileId, Long programId,
                              Long curriculumId, String startPeriod) {
}
