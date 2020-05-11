package com.allen.noteapp

class Note {
    var noteID:Number ?= null;
    var noteName:String ?= null;
    var noteDes:String ?= null;

    constructor(noteID:Number, noteName:String, noteDes:String){
        this.noteID = noteID;
        this.noteName = noteName;
        this.noteDes = noteDes;
    }
}