package com.example.thenavynd;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText email,password;
    Button signIn;
    TextView signUp;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        email = findViewById(R.id.sign_in_email);
        password = findViewById(R.id.sign_in_password);
        signIn = findViewById(R.id.sign_in);
        signUp = findViewById(R.id.dontHaveLink);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToSignIn();
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        String _email = email.getText().toString();
        String _password = password.getText().toString();

        if(TextUtils.isEmpty(_email)){
            Toast.makeText(this, "Email đang để trống !", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(_password)){
            Toast.makeText(this, "Email đang để trống !", Toast.LENGTH_SHORT).show();
        }

        auth.signInWithEmailAndPassword(_email,_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    switchToMainPage();
                }
                else{
                    Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void switchToMainPage() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void switchToSignIn() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
}