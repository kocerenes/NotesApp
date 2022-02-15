package com.example.notesapp.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.notesapp.R
import com.example.notesapp.databinding.ActivityMainBinding
import com.example.notesapp.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class LoginFragment : Fragment() {

    //use view binding with fragment
    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater,container,false)
        val view = binding.root
        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize Firebase Auth
        auth = Firebase.auth

        val currentUser = auth.currentUser
        if (currentUser != null){
            val action = LoginFragmentDirections.actionLoginFragmentToNotesFragment()
            Navigation.findNavController(view).navigate(action)
        }

        binding.signUpButton.setOnClickListener{
            signUp(it)
        }
        binding.signInButton.setOnClickListener{
            signIn(it)
        }
    }

    fun signUp(view : View){
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()

        if(email.equals("") || password.equals("")){
            Toast.makeText(context,"Enter email and password!",Toast.LENGTH_LONG).show()
        }else{
            auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener {
                val action = LoginFragmentDirections.actionLoginFragmentToNotesFragment()
                Navigation.findNavController(view).navigate(action)

                }.addOnFailureListener{
                Toast.makeText(context,it.localizedMessage,Toast.LENGTH_LONG).show()
            }
        }
    }

    fun signIn(view : View){

        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()

        if (email.equals("") || password.equals("")){
            Toast.makeText(context,"Email or password is incorrect",Toast.LENGTH_LONG).show()
        }else{
            auth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
                //it.user!!.email
                val action = LoginFragmentDirections.actionLoginFragmentToNotesFragment()
                Navigation.findNavController(view).navigate(action)
            }.addOnFailureListener{
                Toast.makeText(context,it.localizedMessage,Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    
}