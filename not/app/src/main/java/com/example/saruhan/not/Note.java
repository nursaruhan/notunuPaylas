package com.example.saruhan.not;

/**
 * Created by saruhan on 19.09.2017.
 */

public class Note {


    String currentUser, lessonKey, noteKey, noteAd, noteImage;

    public Note() {

    }
    public Note(String currentUser, String lessonKey, String noteKey, String noteAd, String noteImage) {
        this.currentUser =currentUser;
        this.lessonKey = lessonKey;
        this.noteKey = noteKey;
        this.noteAd = noteAd;
        this.noteImage = noteImage;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public String getLessonKey() {
        return lessonKey;
    }

    public String getNoteKey() {
        return noteKey;
    }

    public String getNoteAd() {
        return noteAd;
    }



    public String getNoteImage() {
        return noteImage;
    }



}
