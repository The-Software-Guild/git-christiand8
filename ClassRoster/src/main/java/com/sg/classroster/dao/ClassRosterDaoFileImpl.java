/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.classroster.dao;

import com.sg.classroster.dto.ClassRosterDaoException;
import com.sg.classroster.dto.Student;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author cd
 */
public class ClassRosterDaoFileImpl implements ClassRosterDao{
    
    public static final String ROSTER_FILE = "roster.txt";
    public static final String DELIMITER = ": :";
    
    private Map<String, Student> students = new HashMap<>();

    @Override
    public Student addStudent(String studentID, Student student) 
            throws ClassRosterDaoException {
        loadRoster();
        Student newStudent = students.put(studentID, student);
        writeRoster();
        return newStudent;
    }

    @Override
    public List<Student> getAllStudents() 
        throws ClassRosterDaoException {
            loadRoster();
            return new ArrayList(students.values());
    }

    @Override
    public Student getStudent(String studentID)
        throws ClassRosterDaoException {
            loadRoster();
            return students.get(studentID);
    }

    @Override
    public Student removeStudent(String studentID)
        throws ClassRosterDaoException {
            loadRoster();
            Student removedStudent = students.remove(studentID);
            writeRoster();
            return removedStudent;
    }
    
    private Student unmarshallStudent(String studentAsText) {
        
    String[] studentTokens = studentAsText.split(DELIMITER);

    String studentId = studentTokens[0];

    Student studentFromFile = new Student(studentId);

    studentFromFile.setFirstName(studentTokens[1]);

    studentFromFile.setLastName(studentTokens[2]);

    studentFromFile.setCohort(studentTokens[3]);

    return studentFromFile;
    }
    
    private void loadRoster() throws ClassRosterDaoException {
    Scanner scanner;

    try {
        scanner = new Scanner(new BufferedReader(new FileReader(ROSTER_FILE)));
    } 
    catch (FileNotFoundException e) {
        throw new ClassRosterDaoException("-_- Could not load roster data into memory.", e);
    }
    String currentLine;
    Student currentStudent;
    while (scanner.hasNextLine()) {
        currentLine = scanner.nextLine();
        currentStudent = unmarshallStudent(currentLine);

        students.put(currentStudent.getStudentID(), currentStudent);
    }
    scanner.close();
    }
    
    private String marshallStudent(Student aStudent){
        
    String studentAsText = aStudent.getStudentID() + DELIMITER;
    studentAsText += aStudent.getFirstName() + DELIMITER;
    studentAsText += aStudent.getLastName() + DELIMITER;
    studentAsText += aStudent.getCohort();

    return studentAsText;
    }
    
private void writeRoster() throws ClassRosterDaoException {
    PrintWriter out;

    try {
        out = new PrintWriter(new FileWriter(ROSTER_FILE));
    } catch (IOException e) {
        throw new ClassRosterDaoException(
                "Could not save student data.", e);
    }
    
    String studentAsText;
    List<Student> studentList = this.getAllStudents();
    for (Student currentStudent : studentList) {
        studentAsText = marshallStudent(currentStudent);
        out.println(studentAsText);
        out.flush();
    }
    out.close();
}
}
    
