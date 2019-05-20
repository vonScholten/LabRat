package vonScholten.labrat

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_register.*
import java.util.*

class RegisterActivity : AppCompatActivity() {

    var uri : Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        register_button.setOnClickListener {
            register()
        }

        select_picture_button.setOnClickListener {
            Log.d("RegisterActivity", "Attempt to select picture.")

            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }

        already_have_account_textView.setOnClickListener {
            Log.d("RegisterActivity", "Already have an account")

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 0 && resultCode == Activity.RESULT_OK && data != null){
            uri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri) //map picture

            select_picture_button.background = BitmapDrawable(bitmap) //replace background with picture
        }
    }

    private fun register() {
        val email = register_editEmail.text.toString() //extract email address
        Log.d("RegisterActivity", "Email -> $email")

        val password = register_editPassword.text.toString() //extract password
        Log.d("RegisterActivity", "Password -> $password")

        if (email.isEmpty() || password.isEmpty()) return //make sure fields is not empty

        //firebase authentication create user using email and password
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if(!it.isSuccessful) return@addOnCompleteListener //if unsuccessful

                Log.d("RegisterActivity", "Successfully created user with uid: ${it.result!!.user.uid}")
                uploadImageToFirebaseStorage()

            }
            .addOnFailureListener{
                Log.d("RegisterActivity", "Failed to create user: ${it.message}")
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
    }

    private fun uploadImageToFirebaseStorage() {
        if (uri == null) return

        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")

        ref.putFile(uri!!)
            .addOnSuccessListener {
                Log.d("RegisterActivity", "Successfully uploaded image: ${it.metadata?.path}")
                saveUserToFirebaseDatabase(it.toString())
            }

    }

    private fun saveUserToFirebaseDatabase(profileImageUrl: String) {
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val username = register_editName.text.toString()

        val ref = FirebaseDatabase.getInstance().getReference("users/$uid")

        val user = User(uid, username, profileImageUrl)
        ref.setValue(user)
            .addOnSuccessListener {
                Log.d("RegisterActivity", "Saved user to database.")
            }
            .addOnFailureListener {
                Log.d("RegisterActivity", "Failed to save user to database: ${it.message}")
            }

    }

    class User(
        val uid : String,
        val username : String,
        val profileImageUrl : String)

}
