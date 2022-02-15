package com.example.notesapp.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.notesapp.R
import com.example.notesapp.adapter.NotesAppAdapter
import com.example.notesapp.databinding.FragmentNotesBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class NotesFragment : Fragment() {
    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!
    private lateinit var notesList:ArrayList<String>

    private lateinit var auth: FirebaseAuth

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

        auth = Firebase.auth

        setHasOptionsMenu(true)

        binding.addNoteButton.setOnClickListener {
            val action = NotesFragmentDirections.actionNotesFragmentToNoteTransactionFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.exit_menu, menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.signout){
            auth.signOut()
            findNavController().navigate(R.id.action_notesFragment_to_loginFragment)
        }
        return super.onOptionsItemSelected(item)
    }

}