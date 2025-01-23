package com.mahmood.coffeeshop.Activities.Auth;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Patterns;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.mahmood.coffeeshop.Activities.MainActivity.MainActivity;
import com.mahmood.coffeeshop.R;

public class LoginActivity extends AppCompatActivity {

    TextView signup_redirect_text;
    String text = "Don’t have an account ? Register";
    AppCompatEditText sign_in_email_edt, sign_in_password_edt;
    TextView forgot_password;
    AppCompatButton sign_in_btn;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        signup_redirect_text = findViewById(R.id.signup_redirect_text);
        sign_in_email_edt = findViewById(R.id.sign_in_email_edt);
        sign_in_password_edt = findViewById(R.id.sign_in_password_edt);
        forgot_password = findViewById(R.id.forgot_password);
        sign_in_btn = findViewById(R.id.sign_in_btn);
        auth = FirebaseAuth.getInstance();
        spannableText();
        sign_in_btn.setOnClickListener(v -> loginUser());
        signup_redirect_text.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, SignUpActivity.class)));
        forgot_password.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class)));
    }

    public void spannableText() {
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new ForegroundColorSpan(Color.WHITE), 0, 23, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#CE9760")), 24, text.length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);

        signup_redirect_text.setText(spannableString);
    }

    private void loginUser() {

        String email = sign_in_email_edt.getText().toString().trim();
        String password = sign_in_password_edt.getText().toString().trim();

        if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            if (!password.isEmpty()) {
                auth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity.this, "Login Failed" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            } else {
                sign_in_password_edt.setError("Password cannot be empty");
            }
        } else {
            sign_in_email_edt.setError("Invalid Email");
            if (email.isEmpty()) {
                sign_in_email_edt.setError("Email cannot be empty");
            }
        }

    }

}