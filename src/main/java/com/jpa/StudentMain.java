package com.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class StudentMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpatest");
        EntityManager em = emf.createEntityManager();

        // Create a new student
        em.getTransaction().begin();
        Student s1 = new Student();
        s1.setName("Minn Minn");
        em.persist(s1);
        em.getTransaction().commit();

        // Find all students
        findAllStudents(em);

        // Update a student
        updateStudent(em,1, "Updated Minn Minn");

        // Delete a student
        deleteStudent(em, s1.getId());
        em.close();
        emf.close();
    }

    public static void findAllStudents(EntityManager em) {
        TypedQuery<Student> query = em.createQuery("SELECT s FROM Student s", Student.class);
        List<Student> students = query.getResultList();
        System.out.println("All Students:");
        for (Student student : students) {
            System.out.println("ID: " + student.getId() + ", Name: " + student.getName());
        }
    }

    public static void updateStudent(EntityManager em, int id, String newName) {
        em.getTransaction().begin();
        Student student = em.find(Student.class, id);
        if (student != null) {
            student.setName(newName);
            em.merge(student);
            System.out.println("Updated Student ID: " + student.getId() + " to Name: " + student.getName());
        } else {
            System.out.println("Student not found with ID: " + id);
        }
        em.getTransaction().commit();
    }

    public static void deleteStudent(EntityManager em, int id) {
        em.getTransaction().begin();
        Student student = em.find(Student.class, id);
        if (student != null) {
            em.remove(student);
            System.out.println("Deleted Student ID: " + student.getId());
        } else {
            System.out.println("Student not found with ID: " + id);
        }
        em.getTransaction().commit();
    }
}
