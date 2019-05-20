package vonScholten.labrat

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_button.setOnClickListener {
            login()
        }

        dont_have_an_account_textView.setOnClickListener {
            Log.d("LoginActivity", "Don't have an account")
            finish() //finish the activity and return
        }
    }

    private fun login(){
        val email = login_editEmail.text.toString() //extract email address
        Log.d("Login", "Email $email")

        val password = login_editPassword.text.toString() //extract password
        Log.d("Login", "Password: $password")

        if(email.isEmpty() || password.isEmpty()) return //make sure fields is not empty

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if(!it.isSuccessful) return@addOnCompleteListener //if unsuccessful
                else {
                    Log.d("MainActivity", "Successfully signed in user with uid: ${it.result!!.user.uid}")
                }
            }
            .addOnFailureListener {
                Log.d("MainActivity", "Authentication failed: ${it.message}")
                Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
            }
    }


}
