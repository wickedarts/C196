package com.example.coursescheduler.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

@Entity(tableName = "course_table")
public class CourseEntity {
    @PrimaryKey
//    @ColumnInfo(name = "courseID")
    private int courseID;

//    @ColumnInfo(name = "courseTermID")
    private int courseTermID;

//    @ColumnInfo(name = "courseName")
    private String courseName;

//    @ColumnInfo(name = "startDate")
    private LocalDate startDate;

//    @ColumnInfo(name = "endDate")
    private LocalDate endDate;

//    @ColumnInfo(name = "courseMentor")
    private String courseMentor;

//    @ColumnInfo(name = "courseMentorPhone")
    private String courseMentorPhone;

//    @ColumnInfo(name = "courseMentorEmail")
    private String courseMentorEmail;

//    @ColumnInfo(name = "courseStatus")
    private String courseStatus;

//    private enum courseStatus {
//        PTT {
//            @Override
//            public String toString() {
//                return "Plan to take";
//            }
//        },
//        IP {
//            @Override
//            public String toString() {
//                return "In Progress";
//            }
//        },
//        D {
//            @Override
//            public String toString() {
//                return "Dropped";
//            }
//        },
//        C {
//            @Override
//            public String toString() {
//                return "Completed";
//            }
//        }
//    }

//    private enum courseStatus{
//        Dropped, In_Progress, Plan_To_Take, Completed;
//    }

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
                "courseTermID" + courseTermID +
                "courseName=" + courseName +
                "startDate=" + startDate +
                "eneDate=" + endDate +
                "courseMentor=" + courseMentor +
                "courseMentorPhone=" + courseMentorPhone +
                "courseMentorEmail=" + courseMentorEmail +
                "courseStatus" + courseStatus +
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
