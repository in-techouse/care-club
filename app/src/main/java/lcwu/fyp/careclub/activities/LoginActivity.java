package lcwu.fyp.careclub.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import lcwu.fyp.careclub.R;
import lcwu.fyp.careclub.director.Helpers;
import lcwu.fyp.careclub.director.Session;
import lcwu.fyp.careclub.model.User;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    Helpers h1;
    AppCompatButton signin;
    TextView forgetpassword, signup;
    EditText email, password;
    String strEmail, strPassword;
    ProgressBar loginprogress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signin = findViewById(R.id.signin);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signup = findViewById(R.id.signup);
        forgetpassword = findViewById(R.id.forgetpassword);
        loginprogress = findViewById(R.id.Loginprogress);


        signin.setOnClickListener(this);
        signup.setOnClickListener(this);
        forgetpassword.setOnClickListener(this);


        h1 = new Helpers();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.signin: {

                boolean isconn = h1.isConnected(LoginActivity.this);

                if (!isconn) {
                    //show Erroe Message,because no internet found
                    h1.showError(LoginActivity.this, "ERROR", "No internet connection found.\nConnect to a network and try again.");
                    return;
                }
                strEmail = email.getText().toString();
                strPassword = password.getText().toString();
                Boolean flag = isValid();
                if (flag) {
                    loginprogress.setVisibility(View.VISIBLE);
                    signin.setVisibility(View.GONE);
                    //Firebase
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    auth.signInWithEmailAndPassword(strEmail, strPassword)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                                    String id = strEmail.replace("@", "-");
                                    id = id.replace(".", "_");
                                    reference.child("Users").child(id).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.getValue() != null) {
                                                // Data is Valid
                                                User u = dataSnapshot.getValue(User.class);
                                                Session session = new Session(LoginActivity.this);
                                                session.setSession(u);
                                                Intent it = new Intent(LoginActivity.this, Dashboard.class);
                                                startActivity(it);
                                                finish();

                                            } else {
                                                loginprogress.setVisibility(View.GONE);
                                                signin.setVisibility(View.VISIBLE);
                                                h1.showError(LoginActivity.this, "Login Failed", "Something Went Wrong");
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                            loginprogress.setVisibility(View.GONE);
                                            signin.setVisibility(View.VISIBLE);
                                            h1.showError(LoginActivity.this, "Login Failed", "Something Went Wrong");
                                        }
                                    });

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            h1.showError(LoginActivity.this, "Login Failed", e.getMessage());
                            loginprogress.setVisibility(View.GONE);
                            signin.setVisibility(View.VISIBLE);
                        }
                    });


                }
                break;
            }
            case R.id.signup: {
                Intent it = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(it);
                break;
            }
            case R.id.forgetpassword: {
                Intent it = new Intent(LoginActivity.this, ForgetpasswordActivity.class);
                startActivity(it);
                break;
            }
        }
    }

    private boolean isValid() {
        boolean flag = true;
        if (strEmail.length() < 6 || !Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()) {
            email.setError("Enter a Valid email");
            flag = false;
        } else {
            email.setError(null);
        }
        if (strPassword.length() < 6) {
            password.setError("Enter valid Password");
            flag = false;
        } else {
            password.setError(null);
        }
        return flag;
    }
}

