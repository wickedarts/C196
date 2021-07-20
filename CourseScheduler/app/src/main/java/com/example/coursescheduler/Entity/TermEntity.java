package com.example.coursescheduler.Entity;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

@Entity(tableName = "term_table")
public class TermEntity {

    @PrimaryKey
    private int termID;
    private String termName;
    private LocalDate startDate;
    private LocalDate endDate;
    private int currentTerm;

    public TermEntity(int termID, String termName, LocalDate startDate, LocalDate endDate, int currentTerm) {
        this.termID = termID;
        this.termName = termName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.currentTerm = currentTerm;
    }

    @Override
    public String toString() {
        return "TermEntity{" +
                "termID=" + termID +
                "termName='" + termName +
                "startDate=" + startDate +
                "endDate=" + endDate +
                "currentTerm=" + currentTerm +
                '}';
    }

    public int getTermID() { return termID; }

    public void setTermID(int termID) {this.termID = termID; }

    public String getTermName() { return termName; }

    public void setTermName(String termName) { this.termName = termName; }

    public LocalDate getStartDate() { return startDate; }

    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }

    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public int getCurrentTerm() { return currentTerm; }

    public void setCurrentTerm(int currentTerm) { this.currentTerm = currentTerm; }
}
