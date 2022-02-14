package com.example.notesapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notesapp.databinding.FragmentLoginBinding
import com.example.notesapp.databinding.FragmentNotesBinding

class NotesFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }
    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!
    private lateinit var notesList:ArrayList<String>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        val view = binding.root
        notesList = ArrayList<String>()
        notesList.add("1")
        notesList.add("2")
        notesList.add("3")
        notesList.add("4")
        notesList.add("5")
        notesList.add("6")
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager= GridLayoutManager(context,2)
        val notesAppAdapter = NotesAppAdapter(notesList)
        binding.recyclerView.adapter=notesAppAdapter
    }


}