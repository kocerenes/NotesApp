package com.example.notesapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notesapp.adapter.NotesAppAdapter
import com.example.notesapp.databinding.FragmentNotesBinding

class NotesFragment : Fragment() {

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    private lateinit var notesList: ArrayList<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notesList = ArrayList()
        notesList.add("1")
        notesList.add("2")
        notesList.add("3")
        notesList.add("4")
        notesList.add("5")
        notesList.add("6")

        binding.recyclerView.layoutManager = GridLayoutManager(context, 2)
        val notesAppAdapter = NotesAppAdapter(notesList)
        binding.recyclerView.adapter = notesAppAdapter
    }

}