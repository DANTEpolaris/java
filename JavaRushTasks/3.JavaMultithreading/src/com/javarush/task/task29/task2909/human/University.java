package com.javarush.task.task29.task2909.human;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class University {

    private List<Student> students = new ArrayList<>();
    String name;
    int age;

    public University(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Student getStudentWithAverageGrade(double averageGrade) {
        for (Student s : students){
            if (s.getAverageGrade() == averageGrade)
        return s;
        }
        return null;
    }

    public Student getStudentWithMaxAverageGrade() {
        TreeMap<Double, Student> map = new TreeMap<>();
        for (Student s : students)
        map.put(s.getAverageGrade(), s);
        return map.get(map.lastKey());
    }

    public Student getStudentWithMinAverageGrade() {
        TreeMap<Double, Student> map = new TreeMap<>();
        for (Student s : students)
            map.put(s.getAverageGrade(), s);
        return map.get(map.firstKey());//TODO:
    }

    public void expel(Student student)
    {
        students.remove(student);
    }
}