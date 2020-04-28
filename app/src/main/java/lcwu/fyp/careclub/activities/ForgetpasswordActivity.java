package lcwu.fyp.careclub.activities;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

import lcwu.fyp.careclub.R;
import lcwu.fyp.careclub.director.Helpers;

public class ForgetpasswordActivity extends AppCompatActivity implements View.OnClickListener {

    Button send;
    String stremail;
    EditText email;
    ProgressBar forgetpass;
    Helpers helpers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);

        email = findViewById(R.id.email);
        send = findViewById(R.id.send);
        forgetpass = findViewById(R.id.forgetpass);

        send.setOnClickListener(this);
        helpers = new Helpers();


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.send: {
                stremail = email.getText().toString();
                boolean flag = isValid();

                if (flag) {
                    forgetpass.setVisibility(View.VISIBLE);
                    send.setVisibility(View.GONE);
                    Log.e("Recover pass", "Gooning to start");
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    auth.sendPasswordResetEmail(stremail).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            forgetpass.setVisibility(View.GONE);
                            send.setVisibility(View.VISIBLE);
                            Log.e("Recover password", "success");
                            showSuccessMessage();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            forgetpass.setVisibility(View.GONE);
                            send.setVisibility(View.VISIBLE);
                            Log.e("Recover password", "Failure" + e.getMessage());
                            helpers.showError(ForgetpasswordActivity.this, "ERROR", e.getMessage());
                        }
                    });


                }
                break;
            }
        }
    }

    private void showSuccessMessage() {
        MaterialDialog mDialog = new MaterialDialog.Builder(ForgetpasswordActivity.this)
                .setTitle("FORGOT PASSWORD")
                .setMessage("A password recovery email has been sent to your account")
                .setCancelable(false)
                .setPositiveButton("Ok", R.drawable.ic_action_okay, new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                        // Delete Operation
                        finish();
                    }
                })
                .setNegativeButton("Close", R.drawable.ic_action_close, new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                        finish();
                    }
                })
                .build();

        // Show Dialog
        mDialog.show();
    }

    private boolean isValid() {
        boolean flag;
        if (stremail.length() <= 6 || !Patterns.EMAIL_ADDRESS.matcher(stremail).matches()) {
            email.setError("Enter a valid email");
            flag = false;
        } else {
            email.setError(null);
            flag = true;
        }
        return flag;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                break;
            }
        }
        return true;
    }
}
