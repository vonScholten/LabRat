package vonScholten.labrat

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        register_button.setOnClickListener {
            register()
        }

        already_have_account_textView.setOnClickListener {
            Log.d("MainActivity", "Already have an account")

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }

    private fun register() {
        val email = register_editEmail.text.toString() //extract email address
        Log.d("Main", "Email -> $email")

        val password = register_editPassword.text.toString() //extract password
        Log.d("Main", "Password -> $password")

        if (email.isEmpty() || password.isEmpty()) return //make sure fields is not empty

        //firebase authentication create user using email and password
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if(!it.isSuccessful) return@addOnCompleteListener //if unsuccessful
                else Log.d("Main", "Successfully created user with uid: ${it.result!!.user.uid}")
            }
            .addOnFailureListener{
                Log.d("Main", "Failed to create user: ${it.message}")
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
    }

}
