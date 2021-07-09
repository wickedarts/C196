package com.example.coursescheduler.Entity;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

@Entity(tableName = "term_table")
public class TermEntity {

    //Column (Fields)
    @PrimaryKey
//    (autoGenerate = true)
//    @ColumnInfo(name = "termID")
    private int termID;

//    @ColumnInfo(name = "termName")
    private String termName;

//    @ColumnInfo(name = "startDate")
    private LocalDate startDate;

//    @ColumnInfo(name = "endDate")
    private LocalDate endDate;

//    @ColumnInfo(name = "currentTerm")
    private boolean currentTerm;

    public TermEntity(int termID, String termName, LocalDate startDate, LocalDate endDate, boolean currentTerm) {
        this.termID = termID;
        this.termName = termName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.currentTerm = currentTerm;
    }

    @Override
    public String toString() {
        return "TermEntity{" +
                "termID'" + termID +
                "termName='" + termName +
                "startDate=" + startDate +
                "endDate=" + endDate +
                "currentTerm" + currentTerm +
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

    public boolean getCurrentTerm() { return currentTerm; }

    public void setCurrentTerm(boolean currentTerm) { this.currentTerm = currentTerm; }
}
