package com.example.sumon.tourmate;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText emailET,passwordET;
    private Button signInBtn;
    private TextView signUpTV;

    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        emailET=findViewById(R.id.edt_email);
        passwordET=findViewById(R.id.edt_password);
        signInBtn= findViewById(R.id.btn_signin);
        signUpTV=findViewById(R.id.dont_account);

        mAuth=FirebaseAuth.getInstance();
        signInBtn.setOnClickListener(this);
        signUpTV.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==signInBtn){
            signIn();
        }
        if(v==signUpTV){
            goSignUP();

        }

    }

    private void signIn() {
        String email = emailET.getText().toString().trim();
        String pass = passwordET.getText().toString().trim();

        if (emailET.getText().toString().trim().isEmpty()){
            emailET.setError("Email Required");
            emailET.requestFocus();
            return;
        }

        if (passwordET.getText().toString().trim().isEmpty()){
            passwordET.setError("Password Required");
            passwordET.requestFocus();
            return;
        }

        //progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //progressBar.setVisibility(View.GONE);
                            Toast.makeText(SignInActivity.this, "User Sign inSuccessful", Toast.LENGTH_SHORT).show();
                            goMainActivity();

                        }else{
                            Toast.makeText(SignInActivity.this, "User Sign in UnSuccessful", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }




    public void goSignUP(){
        Intent  intent=new Intent(SignInActivity.this,SignUpActivity.class);
        startActivity(intent);
    }

    public void goMainActivity(){
        Intent intentMain=new Intent(SignInActivity.this,MainActivity.class);
        startActivity(intentMain);
    }

}

