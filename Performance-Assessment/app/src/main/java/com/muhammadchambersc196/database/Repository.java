package com.muhammadchambersc196.database;

import android.app.Application;

import com.muhammadchambersc196.dao.AssessmentDAO;
import com.muhammadchambersc196.dao.CourseDAO;
import com.muhammadchambersc196.dao.CourseInstructorDAO;
import com.muhammadchambersc196.dao.CourseNoteDAO;
import com.muhammadchambersc196.dao.TermDAO;
import com.muhammadchambersc196.entities.Assessment;
import com.muhammadchambersc196.entities.Course;
import com.muhammadchambersc196.entities.CourseInstructor;
import com.muhammadchambersc196.entities.CourseNote;
import com.muhammadchambersc196.entities.Term;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private AssessmentDAO mAssessmentDAO;
    private CourseDAO mCourseDAO;
    private CourseInstructorDAO mCourseInstructorDAO;
    private CourseNoteDAO mCourseNoteDAO;
    private TermDAO mTermDAO;

    private List<Assessment> mAllAssessments;
    private List<Course> mAllCourses;
    private List<CourseInstructor> mAllCourseInstructors;
    private List<CourseNote> mAllCourseNotes;
    private List<Term> mAllTerms;

    private static int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application) {
        DatabaseBuilder db = DatabaseBuilder.getDatabase(application);
        mAssessmentDAO = db.assessmentDAO();
        mCourseDAO = db.courseDAO();
        mCourseInstructorDAO = db.courseInstructorDAO();
        mCourseNoteDAO = db.courseNoteDAO();
        mTermDAO = db.termDAO();
    }

    public List<Assessment> getmAllAssessments() throws InterruptedException {
        databaseExecutor.execute(() -> {
            mAllAssessments = mAssessmentDAO.getAllAssessments();
        });

        //This is needed to give the database time to retrieve the information
        Thread.sleep(500);

        return mAllAssessments;
    }

    public List<Course> getmAllCourses() throws InterruptedException {

        databaseExecutor.execute(() -> {
            mAllCourses = mCourseDAO.getAllCourses();
        });

        //This is needed to give the database time to retrieve the information
        Thread.sleep(500);

        return mAllCourses;
    }

    public List<CourseInstructor> getmAllCourseInstructors() throws InterruptedException {
        databaseExecutor.execute(() -> {
            mAllCourseInstructors = mCourseInstructorDAO.getAllCourseInstructors();
        });

        //This is needed to give the database time to retrieve the information
        Thread.sleep(500);

        return mAllCourseInstructors;
    }

    public List<CourseNote> getmAllCourseNotes() throws InterruptedException {
        databaseExecutor.execute(() -> {
            mAllCourseNotes = mCourseNoteDAO.getAllCourseNotes();
        });

        //This is needed to give the database time to retrieve the information
        Thread.sleep(500);

        return mAllCourseNotes;
    }

    public List<Term> getmAllTerms() throws InterruptedException {
        databaseExecutor.execute(() -> {
            mAllTerms = mTermDAO.getAllTerms();
        });

        //This is needed to give the database time to retrieve the information
        Thread.sleep(500);

        return mAllTerms;
    }

    public void insert(Term term) throws InterruptedException {
        databaseExecutor.execute(() -> {
            mTermDAO.insert(term);
        });

        //This is needed to give the database time to retrieve the information
        Thread.sleep(500);
    }

    public void insert(Course course) throws InterruptedException {
        databaseExecutor.execute(() -> {
            mCourseDAO.insert(course);
        });

        //This is needed to give the database time to retrieve the information
        Thread.sleep(500);
    }

    public void insert(Assessment assessment) throws InterruptedException {
        databaseExecutor.execute(() -> {
            mAssessmentDAO.insert(assessment);
        });

        //This is needed to give the database time to retrieve the information
        Thread.sleep(500);
    }

    public void insert(CourseNote courseNote) throws InterruptedException {
        databaseExecutor.execute(() -> {
            mCourseNoteDAO.insert(courseNote);
        });

        //This is needed to give the database time to retrieve the information
        Thread.sleep(500);
    }

    public void insert(CourseInstructor courseInstructor) throws InterruptedException {
        databaseExecutor.execute(() -> {
            mCourseInstructorDAO.insert(courseInstructor);
        });

        //This is needed to give the database time to retrieve the information
        Thread.sleep(500);
    }

    public void update(Term term) throws InterruptedException {
        databaseExecutor.execute(() -> {
            mTermDAO.update(term);
        });

        //This is needed to give the database time to retrieve the information
        Thread.sleep(500);
    }

    public void update(Course course) throws InterruptedException {
        databaseExecutor.execute(() -> {
            mCourseDAO.update(course);
        });

        //This is needed to give the database time to retrieve the information
        Thread.sleep(500);
    }

    public void update(Assessment assessment) throws InterruptedException {
        databaseExecutor.execute(() -> {
            mAssessmentDAO.update(assessment);
        });

        //This is needed to give the database time to retrieve the information
        Thread.sleep(500);
    }

    public void update(CourseNote courseNote) throws InterruptedException {
        databaseExecutor.execute(() -> {
            mCourseNoteDAO.update(courseNote);
        });

        //This is needed to give the database time to retrieve the information
        Thread.sleep(500);
    }

    public void update(CourseInstructor courseInstructor) throws InterruptedException {
        databaseExecutor.execute(() -> {
            mCourseInstructorDAO.update(courseInstructor);
        });

        //This is needed to give the database time to retrieve the information
        Thread.sleep(500);
    }

    public void delete(Term term) throws InterruptedException {
        databaseExecutor.execute(() -> {
            mTermDAO.delete(term);
        });

        //This is needed to give the database time to retrieve the information
        Thread.sleep(500);
    }

    public void delete(Assessment assessment) throws InterruptedException {
        databaseExecutor.execute(() -> {
            mAssessmentDAO.delete(assessment);
        });

        //This is needed to give the database time to retrieve the information
        Thread.sleep(500);
    }

    public void delete(Course course) throws InterruptedException {
        databaseExecutor.execute(() -> {
            mCourseDAO.delete(course);
        });

        //This is needed to give the database time to retrieve the information
        Thread.sleep(500);
    }

    public void delete(CourseNote courseNote) throws InterruptedException {
        databaseExecutor.execute(() -> {
            mCourseNoteDAO.delete(courseNote);
        });

        //This is needed to give the database time to retrieve the information
        Thread.sleep(500);
    }

    public void delete(CourseInstructor courseInstructor) throws InterruptedException {
        databaseExecutor.execute(() -> {
            mCourseInstructorDAO.delete(courseInstructor);
        });

        //This is needed to give the database time to retrieve the information
        Thread.sleep(500);
    }
}
