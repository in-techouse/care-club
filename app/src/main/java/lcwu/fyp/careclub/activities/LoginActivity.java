package lcwu.fyp.careclub.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

import lcwu.fyp.careclub.R;
import lcwu.fyp.careclub.director.Helpers;
import lcwu.fyp.careclub.director.Session;
import lcwu.fyp.careclub.model.User;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    Helpers h1;
    AppCompatButton signin ;
    TextView forgetpassword,signup;
    EditText email, password;
    String strEmail,strPassword;
    ProgressBar loginprogress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signin = findViewById(R.id.signin);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signup=findViewById(R.id.signup);
        forgetpassword = findViewById(R.id.forgetpassword);
        loginprogress= findViewById(R.id.Loginprogress);


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

                boolean isconn=h1.isConnected(LoginActivity.this);

                if(!isconn){
                    //show Erroe Message,because no internet found
                    MaterialDialog mDialog = new MaterialDialog.Builder(this)

                            .setTitle("Internet Error")
                            .setMessage("No internet connection found... Check your internet and Try again later ")
                            .setCancelable(false)
                            .setPositiveButton("Ok", R.drawable.ic_action_okay, new MaterialDialog.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int which) {
                                    dialogInterface.dismiss();
                                    // Delete Operation
                                }
                            })
                            .setNegativeButton("Close", R.drawable.ic_action_close, new MaterialDialog.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int which) {
                                    dialogInterface.dismiss();
                                }
                            })
                            .build();

                    // Show Dialog
                    mDialog.show();
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
                                    DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
                                    final User user=new User();
                                    reference.child("User").addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if(dataSnapshot.getValue() != null){
                                                // Data is Valid
                                               User u = dataSnapshot.getValue(User.class);
                                                Session session= new Session(LoginActivity.this);
                                                session.setSession(user);
                                                // Start Dashboard activity

                                            }
                                            else{
                                                loginprogress.setVisibility(View.GONE);
                                                signin.setVisibility(View.VISIBLE);
                                                Log.e("login", "failed");

                                            }

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                            loginprogress.setVisibility(View.GONE);
                                            signin.setVisibility(View.VISIBLE);
                                            Log.e("login", "failed");

                                        }
                                    });
//                                    loginprogress.setVisibility(View.GONE);
//                                    signin.setVisibility(View.VISIBLE);
//                                    Log.e("login", "success");
//                                    Intent it=new Intent(LoginActivity.this,Dashboard.class);
//                                    startActivity(it);
//                                    finish();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            h1.showError(LoginActivity.this,"Login Failed",e.getMessage());
                            loginprogress.setVisibility(View.GONE);
                            signin.setVisibility(View.VISIBLE);

                         //   Log.e("login", "failure " + e.getMessage());
//                            MaterialDialog mDialog = new MaterialDialog.Builder(LoginActivity.this)
//                                    .setTitle("Error")
//                                    .setMessage(e.getMessage())
//                                    .setCancelable(false)
//                                    .setPositiveButton("Ok", R.drawable.ic_action_okay, new MaterialDialog.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialogInterface, int which) {
//                                            dialogInterface.dismiss();
//                                            // Delete Operation
//                                        }
//                                    })
//                                    .setNegativeButton("Close", R.drawable.ic_action_close, new MaterialDialog.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialogInterface, int which) {
//                                            dialogInterface.dismiss();
//                                        }
//                                    })
//                                    .build();
//
//                            // Show Dialog
//                            mDialog.show();
                         }
                    });


                }
                break;
            }
            case R.id.signup:{
                Intent it=new Intent(LoginActivity.this,RegistrationActivity.class);
                startActivity(it);
                break;
            }
            case R.id.forgetpassword: {
                Intent it=new Intent(LoginActivity.this,ForgetpasswordActivity.class);
                startActivity(it);
                break;
            }



        }
    }

    private boolean isValid(){
        boolean flag =true;
        if (strEmail.length() < 6 || !Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()) {
            email.setError("Enter a Valid email");
            flag=false;
        } else {
            email.setError(null);
        }
        if (strPassword.length() < 6) {
            password.setError("Enter valid Password");
            flag=false;
        } else {
            password.setError(null);
        }
        return flag;
    }
    // Check Internet Connection

}

