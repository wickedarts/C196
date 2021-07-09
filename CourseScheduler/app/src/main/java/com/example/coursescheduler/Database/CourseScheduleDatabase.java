package com.example.coursescheduler.Database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.coursescheduler.DAO.AssessmentDAO;
import com.example.coursescheduler.DAO.CourseDAO;
import com.example.coursescheduler.DAO.NoteDAO;
import com.example.coursescheduler.DAO.TermDAO;
import com.example.coursescheduler.Entity.AssessmentEntity;
import com.example.coursescheduler.Entity.CourseEntity;
import com.example.coursescheduler.Entity.NoteEntity;
import com.example.coursescheduler.Entity.TermEntity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//AssessmentEntity.class, NoteEntity.class
@Database(entities = {TermEntity.class, CourseEntity.class, AssessmentEntity.class, NoteEntity.class}, version = 3
//        , exportSchema = true
)
@TypeConverters(Converters.class)

public abstract class CourseScheduleDatabase extends RoomDatabase {
//    Database db;

    public abstract TermDAO termDAO();

    public abstract CourseDAO courseDAO();

    public abstract AssessmentDAO assessmentDAO();

    public abstract NoteDAO noteDAO();

    private static final int NUMBER_OF_THREADS = 4;
    /**
     * The Database Write Executor.
     */
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static volatile CourseScheduleDatabase INSTANCE;

    static CourseScheduleDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CourseScheduleDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), CourseScheduleDatabase.class, "course_scheduler_database.db")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            // If you want to keep the data through app restarts,
            // comment out the following line.
            databaseWriteExecutor.execute(() -> {
            /**
             * Populate the database in the background.
             * If you want to start with more words, just add them.
             */

            TermDAO mTermDao = INSTANCE.termDAO();
            CourseDAO mCourseDao = INSTANCE.courseDAO();
            AssessmentDAO mAssessmentDao = INSTANCE.assessmentDAO();
            NoteDAO mNoteDao = INSTANCE.noteDAO();

            //start the app with a clean database every time.
            //Not needed if you only populate on creation.
//            mTermDao.deleteAllTerms();
//            mCourseDao.deleteAllCourses();

//            AssessmentEntity assessment = new AssessmentEntity(1, 1, "Test Assessment 1", LocalDate.parse("2021-07-01"), LocalDate.parse("2021-07-01"));
//            mAssessmentDao.insert(assessment);
////                                        (int courseID, String courseName, Date startDate, Date eneDate, String courseMentor, String courseMentorPhone, String courseMentorEmail, String courseStatus)
//            CourseEntity course=new CourseEntity(1, 1,"Test Course 1", LocalDate.parse("2021-01-01"), LocalDate.parse("2021-01-01"), "Dr Dude", "3035648198", "dude@gmail.com", "NOT STARTED");
//            mCourseDao.insert(course);
//
////                                          (int termID, String termName, Date startDate, Date endDate, boolean currentTerm)
//            TermEntity term= new TermEntity(1,"Test Term 1", LocalDate.parse("2021-05-01"), LocalDate.parse("2021-05-31"), false);
//            mTermDao.insert(term);
//
//            term = new TermEntity(2,"Test Term 2", LocalDate.parse("2021-06-01"), LocalDate.parse("2021-06-30"), false);
//            mTermDao.insert(term);
            });
        };

    };
}
