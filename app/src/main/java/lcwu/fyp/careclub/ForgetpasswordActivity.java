package lcwu.fyp.careclub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetpasswordActivity extends AppCompatActivity implements View.OnClickListener {

    Button forgottonpass;
    String stremail;
    EditText email;
    ProgressBar forgetprogress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);

        email=findViewById(R.id.email);
        forgottonpass=findViewById(R.id.send);

        forgottonpass.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch(id){
            case R.id.send:{
                 stremail=email.getText().toString();
                 boolean flag=forgetpass();

                 if(flag==true){
                     forgetprogress.setVisibility(View.VISIBLE);
                     forgottonpass.setVisibility(View.GONE);
                     Log.e("Recover pass","Gooning to start");
                     FirebaseAuth auth=FirebaseAuth.getInstance();
                     auth.sendPasswordResetEmail(stremail).addOnSuccessListener(new OnSuccessListener<Void>() {
                         @Override
                         public void onSuccess(Void aVoid) {
                             forgetprogress.setVisibility(View.GONE);
                             forgottonpass.setVisibility(View.VISIBLE);
                             Log.e("Recover password","success");

                         }
                     }).addOnFailureListener(new OnFailureListener() {
                         @Override
                         public void onFailure(@NonNull Exception e) {
                             forgetprogress.setVisibility(View.GONE);
                             forgottonpass.setVisibility(View.VISIBLE);
                             Log.e("Recover password","Failure"+e.getMessage());
                         }
                     });
                 {

                     }


                 }
                break;
            }
        }
    }
    private boolean forgetpass(){
        boolean flag;
     if(stremail.length()<=6||!Patterns.EMAIL_ADDRESS.matcher(stremail).matches()){
        email.setError("Enter a valid email");
        flag=false;
    }
                else {
        email.setError("Your varification code has been send on your email");
        flag=true;
    }
                return flag;
}}
