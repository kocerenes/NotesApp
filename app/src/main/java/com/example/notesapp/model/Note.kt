package com.example.notesapp.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize

@Parcelize
data class Note(var id : String, var documentId : String, var date : Timestamp, var note : String):Parcelable