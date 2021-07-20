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
    private LocalDate endDate;
    private String assessmentType;
    private int assessmentSelectionPosition;
    private String descriptionText;

    @Override
    public String toString() {
        return "AssessmentEntity{" +
                "assessmentID=" + assessmentID  +
                "courseID=" + assessmentCourseID +
                "assessmentName=" + assessmentName +
                "assessmentStartDate=" + startDate +
                "assessmentEndDate=" + endDate +
                "assessmentType=" + assessmentType +
                "assessmentSelectionPosition=" + assessmentSelectionPosition +
                "descriptionText" + descriptionText +
                '}';
    }

    public AssessmentEntity(int assessmentID, int assessmentCourseID, String assessmentName, LocalDate startDate, LocalDate endDate, String assessmentType, int assessmentSelectionPosition, String descriptionText) {
        this.assessmentID = assessmentID;
        this.assessmentCourseID = assessmentCourseID;
        this.assessmentName = assessmentName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.assessmentType = assessmentType;
        this.assessmentSelectionPosition = assessmentSelectionPosition;
        this.descriptionText = descriptionText;
    }

    public int getAssessmentID() { return assessmentID; }

    public void setAssessmentID(int assessmentID) { this.assessmentID = assessmentID; }

    public int getAssessmentCourseID() { return assessmentCourseID; }

    public void setAssessmentCourseID(int assessmentCourseID) {this.assessmentCourseID = assessmentCourseID; }

    public String getAssessmentName() { return assessmentName; }

    public void setAssessmentName(String assessmentName) { this.assessmentName = assessmentName; }

    public LocalDate getStartDate() { return startDate; }

    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }

    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public String getAssessmentType() { return assessmentType; }

    public void setAssessmentType(String assessmentType) { this.assessmentType = assessmentType; }

    public int getAssessmentSelectionPosition() { return assessmentSelectionPosition; }

    public void setAssessmentSelectionPosition(int assessmentSelectionPosition) {this.assessmentSelectionPosition = assessmentSelectionPosition;}

    public String getDescriptionText() { return descriptionText; }

    public void setDescriptionText(String descriptionText) { this.descriptionText = descriptionText; }
}
