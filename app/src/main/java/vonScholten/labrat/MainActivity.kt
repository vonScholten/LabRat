package vonScholten.labrat

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        register_button.setOnClickListener {
            val email = register_editEmail.text.toString() //extract email address
            Log.d("MainActivity", "email -> $email")

            val password = register_editPassword.text.toString() //extract password
            Log.d("MainActivity", "password -> $password")
        }

        already_have_account_textView.setOnClickListener {
            Log.d("MainActivity", "already have an account")

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }


    }
}
