package com.mahmood.coffeeshop.Activities.Auth;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.mahmood.coffeeshop.R;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    TextView login_redirect_text;
    AppCompatEditText sign_up_name_edt, sign_up_email_edt, sign_up_phone_edt, sign_up_password_edt;
    AppCompatButton sign_up_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        login_redirect_text = findViewById(R.id.login_redirect_text);
        sign_up_name_edt = findViewById(R.id.sign_up_name_edt);
        sign_up_email_edt = findViewById(R.id.sign_up_email_edt);
        sign_up_phone_edt = findViewById(R.id.sign_up_phone_edt);
        sign_up_password_edt = findViewById(R.id.sign_up_password_edt);
        sign_up_btn = findViewById(R.id.sign_up_btn);
        auth = FirebaseAuth.getInstance();
        spannableText();
        sign_up_btn.setOnClickListener(v -> signUpUser());
        login_redirect_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));

            }
        });


    }

    public void spannableText() {
        String text = "Already have an account ? Login";
        SpannableString spannableString = new SpannableString(text);

// Set color for "Already have an account ?"
        spannableString.setSpan(new ForegroundColorSpan(Color.WHITE), 0, 24, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

// Set color for "Login"
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#CE9760")), 25, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        login_redirect_text.setText(spannableString);
    }

    private void signUpUser() {
        String email = sign_up_email_edt.getText().toString().trim();
        String password = sign_up_password_edt.getText().toString().trim();

        if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            if (!password.isEmpty()) {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "SignUp Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                        } else {
                            Toast.makeText(SignUpActivity.this, "SignUp Failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                sign_up_password_edt.setError("Password cannot be empty");

            }
        } else {
            sign_up_email_edt.setError("Invalid Email");
            if (email.isEmpty()) {
                sign_up_email_edt.setError("Email cannot be empty");

            }

        }
    }
}