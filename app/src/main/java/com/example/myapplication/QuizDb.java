package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.myapplication.QuizContract.*;

import java.util.ArrayList;
import java.util.List;

public class QuizDb extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="quiz.db";
    private static final int DATABASE_VERSION=1;
    private SQLiteDatabase db;
    public QuizDb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        this.db=db;
        final String SQL_CREATE_QUESTION_TABLE="CREATE TABLE "+
                QuizContract.QuestionTable.TABLE_NAME+ " ( " +
                QuestionTable._ID + "INTEGER PRIMARY KEY AUTOINCREMENT, "+
                QuestionTable.COLUMN_QUESTION + " TEXT," +
                QuestionTable.COLUMN_OPTION1 + "TEXT, " +
                QuestionTable.COLUMN_OPTION2 + "TEXT," +
                QuestionTable.COLUMN_OPTION3 + "TEXT," +
                QuestionTable.COLUMN_OPTION4 + "TEXT," +
                QuestionTable.COLUMN_ANSWER + "INTEGER"+
                ")";
        db.execSQL(SQL_CREATE_QUESTION_TABLE);
        fillQuestionTable();



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionTable.TABLE_NAME );
        onCreate(db);

    }
    private void fillQuestionTable(){
        Qestion q1= new Qestion("A is correct","A","B","C","D",1);
    }
    private void addQestion(Qestion qestion){
        ContentValues cv = new ContentValues();
        cv.put(QuestionTable.COLUMN_QUESTION,qestion.getQuestion());
        cv.put(QuestionTable.COLUMN_OPTION1,qestion.getOption1());
        cv.put(QuestionTable.COLUMN_OPTION2,qestion.getOption2());
        cv.put(QuestionTable.COLUMN_OPTION3,qestion.getOption3());
        cv.put(QuestionTable.COLUMN_OPTION4,qestion.getOption4());
        cv.put(QuestionTable.COLUMN_ANSWER,qestion.getAnswer());
        db.insert(QuestionTable.TABLE_NAME,null,cv);

    }
    public List<Qestion> getAllQuestions() {
        List<Qestion> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionTable.TABLE_NAME, null);
        if (c.moveToFirst()) {
            do {
                Qestion qestion = new Qestion();
                qestion.setQuestion(c.getString(c.getColumnIndex(QuestionTable.COLUMN_QUESTION)));
                qestion.setOption1(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION1)));
                qestion.setOption2(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION2)));
                qestion.setOption3(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION3)));
                qestion.setOption4(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION4)));
                qestion.setAnswer(c.getInt(c.getColumnIndex(QuestionTable.COLUMN_ANSWER)));
                questionList.add(qestion);
            } while (c.moveToNext());
        }
        c.close();
        return questionList;
    }
}


