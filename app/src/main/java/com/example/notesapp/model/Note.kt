package com.example.notesapp.model

import com.google.firebase.Timestamp

data class Note(var id : Int,var date : Timestamp, var note : String)