package lcwu.fyp.careclub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    Button signin ;
    TextView forgetpassword;
    EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signin = findViewById(R.id.signin);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        forgetpassword = findViewById(R.id.forgetpassword);
        signin.setOnClickListener(this);
        forgetpassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.signin: {
                String strEmail = email.getText().toString();
                String strPassword = password.getText().toString();
                if (strEmail.length() < 6 || !Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()) {
                    email.setError("Enter a Valid email");
                } else {
                    email.setError(null);
                }
                if (strPassword.length() < 6) {
                    password.setError("Enter valid Password");
                } else {
                    password.setError(null);
                }
            }
         break;
            case R.id.forgetpassword: {
                Intent it=new Intent(LoginActivity.this,ForgetpasswordActivity.class);
                startActivity(it);
                break;
        }



        }
    }
}

