package com.example.coursescheduler.Database;

import androidx.room.TypeConverter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

public class Converters {

    @TypeConverter
    public static LocalDate fromEpochMilliToLocalDate(Long epoch) {
        return Instant.ofEpochMilli(epoch).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    @TypeConverter
    public static LocalTime fromEpochMilliToLocalTime(Long epoch) {
        return Instant.ofEpochMilli(epoch).atZone(ZoneId.systemDefault()).toLocalTime();
    }

    @TypeConverter
    public static Long locatDateToEpochMilli(LocalDate date) {
        Instant instant = date.atStartOfDay(ZoneId.systemDefault()).toInstant();
        return instant.toEpochMilli();
    }

    //Uncomment these after setting up the assessment types associated with each
//    @TypeConverter
//    public int fromAssessmentType(AssessmentEntity.AssessmentType assessmentType) {
//        return assessmentType.ordinal();
//    }

//    @TypeConverter
//    public AssessmentEntity.AssessmentType toAssessmentType(int ordinal) {
//        if (ordinal == 0) return AssessmentEntity.AssessmentType.Assessment;
//        else return AssessmentEntity.AssessmentType.Performance;
//    }

//    @TypeConverter
//    public int fromCourseStatus(CourseEntity.courseStatus courseStatus) {
//        return courseStatus.ordinal();
//    }

//    @TypeConverter
//    public CourseEntity.courseStatus fromCourseStatus(int ordinal) {
//        if (ordinal == 0) return CourseEntity.courseStatus.Inprogress;
//        else if (ordinal == 1) return CourseEntity.courseStatus.Complete;
//        else if (ordinal == 2) return CourseEntity.courseStatus.Dropped;
//        else return CourseEntity.courseStatus.PlanToTake;
//    }
}
