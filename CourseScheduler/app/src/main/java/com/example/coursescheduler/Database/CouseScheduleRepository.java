package com.example.coursescheduler.Database;

import android.app.Application;

import com.example.coursescheduler.DAO.AssessmentDAO;
import com.example.coursescheduler.DAO.CourseDAO;
import com.example.coursescheduler.DAO.NoteDAO;
import com.example.coursescheduler.DAO.TermDAO;
import com.example.coursescheduler.Entity.AssessmentEntity;
import com.example.coursescheduler.Entity.CourseEntity;
import com.example.coursescheduler.Entity.NoteEntity;
import com.example.coursescheduler.Entity.TermEntity;

import java.util.List;


public class CouseScheduleRepository {
    private TermDAO mTermDAO;
    private CourseDAO mCourseDAO;
    private AssessmentDAO mAssessmentDAO;
    private NoteDAO mNotesDAO;
    private List<TermEntity> mAllTerms;
    private List<CourseEntity> mAllCourses;
    private List<AssessmentEntity> mAllAssessments;
    private List<NoteEntity> mAllNotes;
    private List<CourseEntity> mAssociatedCourses;
//    private int termID;
    private int courseTermID;

    public CouseScheduleRepository(Application application) {
        CourseScheduleDatabase db = CourseScheduleDatabase.getDatabase(application);
        mTermDAO = db.termDAO();
        mCourseDAO = db.courseDAO();
        mAssessmentDAO = db.assessmentDAO();
        mNotesDAO = db.noteDAO();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<TermEntity> getAllTerms() {
        CourseScheduleDatabase.databaseWriteExecutor.execute(() -> {
            mAllTerms = mTermDAO.getAllTerms();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllTerms;
    }

    public List<CourseEntity> getAllCourses() {
        CourseScheduleDatabase.databaseWriteExecutor.execute(() -> {
            mAllCourses = mCourseDAO.getAllCourses();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllCourses;
    }

    public List<CourseEntity> getAssociatedCourses() {
        CourseScheduleDatabase.databaseWriteExecutor.execute(() -> {
            mAssociatedCourses = mCourseDAO.getAllAssociatedCourses(courseTermID);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAssociatedCourses;
    }

    public List<AssessmentEntity> getAllAssessments() {
        CourseScheduleDatabase.databaseWriteExecutor.execute(() -> {
            mAllAssessments = mAssessmentDAO.getAllAssessments();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllAssessments;
    }

    public List<NoteEntity> getAllNotes() {
        CourseScheduleDatabase.databaseWriteExecutor.execute(() -> {
            mAllNotes = mNotesDAO.getAllNotes();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllNotes;
    }

    public void insert(TermEntity termEntity) {
        CourseScheduleDatabase.databaseWriteExecutor.execute(() -> {
            mTermDAO.insert(termEntity);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void insert(CourseEntity courseEntity) {
        CourseScheduleDatabase.databaseWriteExecutor.execute(() -> {
            mCourseDAO.insert(courseEntity);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void insert(AssessmentEntity assessmentEntity) {
        CourseScheduleDatabase.databaseWriteExecutor.execute(() -> {
            mAssessmentDAO.insert(assessmentEntity);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void insert(NoteEntity noteEntity) {
        CourseScheduleDatabase.databaseWriteExecutor.execute(() -> {
            mNotesDAO.insert(noteEntity);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(TermEntity termEntity) {
        CourseScheduleDatabase.databaseWriteExecutor.execute(() -> {
            mTermDAO.update(termEntity);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(CourseEntity courseEntity) {
        CourseScheduleDatabase.databaseWriteExecutor.execute(() -> {
            mCourseDAO.update(courseEntity);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(AssessmentEntity assessmentEntity) {
        CourseScheduleDatabase.databaseWriteExecutor.execute(() -> {
            mAssessmentDAO.update(assessmentEntity);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(NoteEntity noteEntity) {
        CourseScheduleDatabase.databaseWriteExecutor.execute(() -> {
            mNotesDAO.update(noteEntity);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void deleteALl(TermEntity termEntity) {
        CourseScheduleDatabase.databaseWriteExecutor.execute(() -> {
            mTermDAO.deleteAllTerms();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void deleteAll(CourseEntity courseEntity) {
        CourseScheduleDatabase.databaseWriteExecutor.execute(() -> {
            mCourseDAO.deleteAllCourses();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void deleteAll(AssessmentEntity assessmentEntity) {
        CourseScheduleDatabase.databaseWriteExecutor.execute(() -> {
            mAssessmentDAO.deleteAllAssessments();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void deleteAll(NoteEntity noteEntity) {
        CourseScheduleDatabase.databaseWriteExecutor.execute(() -> {
            mNotesDAO.deleteAllNotes();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(TermEntity termEntity) {
        CourseScheduleDatabase.databaseWriteExecutor.execute(() -> {
            mTermDAO.delete(termEntity);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(CourseEntity courseEntity) {
        CourseScheduleDatabase.databaseWriteExecutor.execute(() -> {
            mCourseDAO.delete(courseEntity);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(AssessmentEntity assessmentEntity) {
        CourseScheduleDatabase.databaseWriteExecutor.execute(() -> {
            mAssessmentDAO.delete(assessmentEntity);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(NoteEntity noteEntity) {
        CourseScheduleDatabase.databaseWriteExecutor.execute(() -> {
            mNotesDAO.delete(noteEntity);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
