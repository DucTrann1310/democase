package service;

import model.EGender;
import model.Students;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StudentService {
    private static final List<Students> students;
    private static List<Students> listRestoreStudent;
    private static int idCurrent;
    static {
        students = new ArrayList<>();
        listRestoreStudent = new ArrayList<>();
        students.add(new Students(++idCurrent,"Duc", Date.valueOf(LocalDate.now()), EGender.MALE));
        students.add(new Students(++idCurrent,"Thang", Date.valueOf(LocalDate.now()), EGender.MALE));
        students.add(new Students(++idCurrent,"Quang", Date.valueOf(LocalDate.now()), EGender.MALE));
        students.add(new Students(++idCurrent,"Huy", Date.valueOf(LocalDate.now()), EGender.MALE));
        students.add(new Students(++idCurrent,"Dat", Date.valueOf(LocalDate.now()), EGender.MALE));
    }
    public static List<Students> getStudents(boolean deleted){
        return students.stream()
                .filter(s -> s.isDeleted() == deleted)
                .collect(Collectors.toList());
    }
    public void addStudent(String name, String dob, String gender){
        Students student = new Students(++idCurrent, name, Date.valueOf(dob), EGender.valueOf(gender));
        students.add(student);
    }

    public Students findById(int id) {
       return students.stream()
                .filter(s -> s.getId() == id)
                .findFirst().orElse(null);
    }

    public void updateStudent(int id, String name, String dob, String gender) {
        students.stream()
                .filter(s -> s.getId() == id)
                .collect(Collectors.toList())
                .forEach(s -> {
                    s.setName(name);
                    s.setDOB(Date.valueOf(dob));
                    s.setGender(EGender.valueOf(gender));
                });
    }

    public void removeStudent(int id) {
        Students student = findById(id);
        student.setDeleted(true);
    }
    public void restoreStudent(int id ){
        Students student = findById(id);
        student.setDeleted(false);

    }
}
