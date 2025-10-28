package pe.edu.upc.center.platform.faculties.domain.model.commands;

/**
 * Command to create a new student.
 *
 * @param profileId   the ID of the profile associated with the student
 * @param programId   the ID of the program the student is enrolling in
 * @param curriculumId the ID of the curriculum the student will follow
 */
public record CreateStudentCommand(Long profileId, Long programId, Long curriculumId) {
}
