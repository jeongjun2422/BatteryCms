package com.battery.cms

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    // Google Login result
    private val RC_SIGN_IN = 9001

    // Google Api Client
    private var googleSigninClient: GoogleSignInClient? = null

    // Firebase Auth
    private var firebaseAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Strart config_signin
        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSigninClient = GoogleSignIn.getClient(this, gso)

        firebaseAuth = FirebaseAuth.getInstance()

        googleLoginBtn.setOnClickListener {

            Log.d("LoginActivity", "버튼 클릭")

            val signInIntent = googleSigninClient?.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

    fun toMainActivity(user: FirebaseUser?) {
        if (user != null) {
            val intent = Intent(Intent(this, MainActivity::class.java))
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
//            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.d("LoginActivity", "requestCode===>" + requestCode)

        // Google 로그인 인텐트 응답
        if(requestCode === RC_SIGN_IN) {

            Log.d("LoginActivity", "Google 로그인 인텐트 응답으로 들어옴111")

            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)

            Log.d("LoginActivity", "Google 로그인 인텐트 응답으로 들어옴222")

            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)

                Log.d("LoginActivity", "account=====>" + account?.idToken)

                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                Log.d("LoginActivity", "e===>" + e.message)
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount?) {

        val credential = GoogleAuthProvider.getCredential(acct?.idToken, null)
        firebaseAuth!!.signInWithCredential(credential)
            .addOnCompleteListener(this) {
                // Success
                if(it.isSuccessful) {
                    val user = firebaseAuth?.currentUser

                    Log.d("LoginActivity", "firebaseAuthWithGoogle 성공")
                    toMainActivity(user)

                }
                else {
                    Log.d("LoginActivity", "firebaseAuthWithGoogle 실패")
                }
            }
    }

    override fun onStart() {
        super.onStart()
        toMainActivity(firebaseAuth?.currentUser)
    }











}