package vonScholten.labrat

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_button.setOnClickListener {
            val email = login_editEmail.text.toString() //extract email address
            Log.d("LoginActivity", "email -> $email")

            val password = login_editEmail.text.toString() //extract password
            Log.d("LoginActivity", "password -> $password")
        }

        dont_have_an_account_textView.setOnClickListener {
            Log.d("LoginActivity", "don't have an account")
            finish() //finish the activity and return
        }
    }
}
