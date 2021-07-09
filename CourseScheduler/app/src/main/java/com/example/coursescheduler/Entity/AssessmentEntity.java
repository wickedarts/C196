package com.example.coursescheduler.Entity;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

@Entity(tableName = "assessment_table")
public class AssessmentEntity {
    @PrimaryKey
    private int assessmentID;
    private int assessmentCourseID;
    private String assessmentName;
    private LocalDate startDate;
//    private LocalDate endDate;
    private String assessmentType;

    @Override
    public String toString() {
        return "AssessmentEntity{" +
                "assessmentID=" + assessmentID  +
                "assessmentCourseID=" + assessmentCourseID +
                "startDate=" + startDate +
//                "startTime=" + endDate +
                "assessmentType" + assessmentType +
                '}';
    }

    public AssessmentEntity(int assessmentID, int assessmentCourseID, String assessmentName, LocalDate startDate, String assessmentType) {
        this.assessmentID = assessmentID;
        this.assessmentCourseID = assessmentCourseID;
        this.assessmentName = assessmentName;
        this.startDate = startDate;
//        this.endDate = endDate;
        this.assessmentType = assessmentType;
    }

    public int getAssessmentID() { return assessmentID; }

    public void setAssessmentID(int assessmentID) { this.assessmentID = assessmentID; }

    public int getAssessmentCourseID() { return assessmentCourseID; }

    public void setAssessmentCourseID(int assessmentCourseID) {this.assessmentCourseID = assessmentCourseID; }

    public String getAssessmentName() { return assessmentName; }

    public void setAssessmentName(String assessmentName) { this.assessmentName = assessmentName; }

    public LocalDate getStartDate() { return startDate; }

    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

//    public LocalDate getEndDate() { return endDate; }
//
//    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public String getAssessmentType() { return assessmentType; }

    public void setAssessmentType(String assessmentType) { this.assessmentType = assessmentType; }
}
