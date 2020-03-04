package com.allen.noteapp

class Notes {
    var id:Number ?= null;
    var noteName:String ?= null;
    var noteDes:String ?= null;

    constructor(id:Number, noteName:String, noteDes:String){
        this.id = id;
        this.noteName = noteName;
        this.noteDes = noteDes;
    }
}