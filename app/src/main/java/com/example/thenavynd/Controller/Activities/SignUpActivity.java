package com.example.thenavynd.Controller.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thenavynd.Models.User;
import com.example.thenavynd.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    EditText fullName, email, password, confirmPassword;
    Button signUp;
    FirebaseAuth auth;
    TextView signIn;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        fullName = findViewById(R.id.fullName);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirm);
        signUp = findViewById(R.id.button);
        signIn = findViewById(R.id.sign_in);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToLogin();
            }
        });
    }

    private void createUser() {
        String _fullName = fullName.getText().toString();
        String _email = email.getText().toString();
        String _password = password.getText().toString();
        String _confirmPassword = confirmPassword.getText().toString();

        if(TextUtils.isEmpty(_fullName)){
            Toast.makeText(this, "B???n ??ang ????? tr???ng h??? v?? t??n !", Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(_email)){
            Toast.makeText(this, "B???n ??ang ????? tr???ng email !", Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(_password)){
            Toast.makeText(this, "B???n ??ang ????? tr???ng m???t kh???u !", Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(_confirmPassword)){
            Toast.makeText(this, "Vui l??ng nh???p l???i m???t kh???u !", Toast.LENGTH_SHORT).show();
        }

        auth.createUserWithEmailAndPassword(_email, _password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    User user = new User(_fullName, _email, _password);
                    String id = task.getResult().getUser().getUid();
                    database.getReference().child("Users").child(id).setValue(user);

                    Toast.makeText(SignUpActivity.this, "????ng k?? th??nh c??ng !", Toast.LENGTH_SHORT).show();
                    switchToLogin();
                }
                else{
                    Toast.makeText(SignUpActivity.this, "" + task.getException() , Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private void switchToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}