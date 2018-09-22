package com.example.sumon.tourmate;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText nameET,emailET,passwordET,phoneET;
    private Button signInBtn;
    private TextView signInTV;

    ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;

    DatabaseReference rootReference;
    FirebaseUser user;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        nameET= findViewById(R.id.editTxtName);
        emailET=findViewById(R.id.editTxtEmail);
        phoneET=findViewById(R.id.editTxtPhone);
        passwordET=findViewById(R.id.editTxtPwd);
        signInBtn =findViewById(R.id.btnSignup);
        signInTV=findViewById(R.id.tvSignIn);

        rootReference = FirebaseDatabase.getInstance().getReference("Somon");
        user = FirebaseAuth.getInstance().getCurrentUser();

        signInBtn.setOnClickListener(this);
        signInTV.setOnClickListener(this);
        progressBar=new ProgressBar(this);

        mAuth = FirebaseAuth.getInstance();


    }

    @Override
    public void onClick(View v) {
        if (v == signInBtn) {
            registerUser();

        }

        if (v == signInTV) {
            goSignIn();
        }
    }

        private void registerUser(){
            final String name = nameET.getText().toString().trim();
            final String email = emailET.getText().toString().trim();
            String password = passwordET.getText().toString().trim();
            final String phone = phoneET.getText().toString().trim();

            if (name.isEmpty()) {
                nameET.setError(getString(R.string.input_error_name));
                nameET.requestFocus();
                return;
            }

            if (email.isEmpty()) {
                emailET.setError(getString(R.string.input_error_email));
                emailET.requestFocus();
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailET.setError(getString(R.string.input_error_email_invalid));
                emailET.requestFocus();
                return;
            }

            if (password.isEmpty()) {
                passwordET.setError(getString(R.string.input_error_password));
                passwordET.requestFocus();
                return;
            }

            if (password.length() < 6) {
                passwordET.setError(getString(R.string.input_error_password_length));
                passwordET.requestFocus();
                return;
            }


            progressBar.setVisibility(View.VISIBLE);

            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(SignUpActivity.this, "User Register Successful", Toast.LENGTH_SHORT).show();
                                goSignInActivity();

                            }else{
                                Toast.makeText(SignUpActivity.this, "User Register UnSuccessful", Toast.LENGTH_SHORT).show();

                            }
                            String uId = user.getUid();
                            String id =rootReference.push().getKey();
                            final User user = new User(id,name,phone,email);

                            rootReference.child(uId).child("Events").child(id).setValue(user)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful())
                                            {
                                                Toast.makeText(SignUpActivity.this, user.getEmail(), Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });





                        }
                    });
        }

        public void goSignInActivity(){

            Intent intentSignIn=new Intent(SignUpActivity.this,SignInActivity.class);
            startActivity(intentSignIn);
        }
    public void goSignIn(){
        Intent  intent=new Intent(SignUpActivity.this,SignInActivity.class);
        startActivity(intent);
    }



}
