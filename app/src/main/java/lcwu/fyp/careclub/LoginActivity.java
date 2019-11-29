package lcwu.fyp.careclub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    Button signin ;
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
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.signin: {

                boolean isconn=isConnected();

                if(!isconn){
                    //show Erroe Message,because no internet found
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
                                    loginprogress.setVisibility(View.GONE);
                                    signin.setVisibility(View.VISIBLE);
                                    Log.e("login", "success");
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            loginprogress.setVisibility(View.GONE);
                            signin.setVisibility(View.VISIBLE);
                            Log.e("login", "failure " + e.getMessage());
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
    private boolean isConnected(){
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
            connected = true;
        else
            connected = false;
        return  connected;
    }
}

