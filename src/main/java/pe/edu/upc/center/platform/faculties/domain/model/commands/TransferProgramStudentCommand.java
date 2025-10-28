package pe.edu.upc.center.platform.faculties.domain.model.commands;

import pe.edu.upc.center.platform.faculties.domain.model.valueobjects.StudentCode;

/**
 * Command to transfer a student to a program and curriculum.
 *
 * @param studentCode the code of the student
 * @param programId   the ID of the program the student is enrolling in
 * @param curriculumId the ID of the curriculum the student will follow
 */
public record TransferProgramStudentCommand(StudentCode studentCode, Long programId,
                                            Long curriculumId) {
}
