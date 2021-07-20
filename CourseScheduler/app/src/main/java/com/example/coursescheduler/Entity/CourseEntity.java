package com.example.coursescheduler.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

@Entity(tableName = "course_table")
public class CourseEntity {
    @PrimaryKey
    private int courseID;
    private int courseTermID;
    private String courseName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String courseMentor;
    private String courseMentorPhone;
    private String courseMentorEmail;
    private String courseStatus;

    public CourseEntity(int courseID, int courseTermID, String courseName, LocalDate startDate, LocalDate endDate, String courseMentor, String courseMentorPhone, String courseMentorEmail, String courseStatus) {
        this.courseID = courseID;
        this.courseTermID = courseTermID;
        this.courseName = courseName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.courseMentor = courseMentor;
        this.courseMentorPhone = courseMentorPhone;
        this.courseMentorEmail = courseMentorEmail;
        this.courseStatus = courseStatus;
    }

    @Override
    public String toString() {
        return "CourseEntity{" +
                "courseID=" + courseID +
                "termID=" + courseTermID +
                "courseName=" + courseName +
                "startDate=" + startDate +
                "eneDate=" + endDate +
                "courseMentor=" + courseMentor +
                "courseMentorPhone=" + courseMentorPhone +
                "courseMentorEmail=" + courseMentorEmail +
                "courseStatus=" + courseStatus +
                '}';
    }

    public int getCourseID() { return courseID; }

    public void setCourseID(int courseID) { this.courseID = courseID; }

    public int getCourseTermID() { return courseTermID; }

    public void setCourseTermID(int courseTermID) { this.courseTermID = courseTermID; }

    public String getCourseName() { return courseName; }

    public void setCourseName(String courseName) {this.courseName = courseName; }

    public LocalDate getStartDate() { return startDate; }

    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate;}

    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public String getCourseMentor() { return courseMentor; }

    public void setCourseMentor(String courseMentor) { this.courseMentor = courseMentor; }

    public String getCourseMentorPhone() { return courseMentorPhone; }

    public void setCourseMentorPhone(String courseMentorPhone) { this.courseMentorPhone = courseMentorPhone; }

    public String getCourseMentorEmail() { return courseMentorEmail; }

    public void setCourseMentorEmail(String courseMentorEmail) { this.courseMentorEmail = courseMentorEmail; }

    public String getCourseStatus() { return courseStatus; }

    public void setCourseStatus(String courseStatus) { this.courseStatus = courseStatus; }
}
