package pe.edu.upc.center.platform.faculties.domain.model.commands;

import pe.edu.upc.center.platform.faculties.domain.model.valueobjects.StudentCode;

/**
 * Command to change the curriculum of a student.
 *
 * @param studentCode  The unique code of the student.
 * @param curriculumId The ID of the new curriculum to assign to the student.
 */
public record ChangeCurriculumStudentCommand(StudentCode studentCode, Long curriculumId) {
}
