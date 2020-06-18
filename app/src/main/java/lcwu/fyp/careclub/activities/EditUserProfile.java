package lcwu.fyp.careclub.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.Calendar;

import lcwu.fyp.careclub.R;
import lcwu.fyp.careclub.director.Helpers;
import lcwu.fyp.careclub.director.Session;
import lcwu.fyp.careclub.model.User;

public class EditUserProfile extends AppCompatActivity implements View.OnClickListener {
    private Session session;
    private Helpers helpers;
    private User user;
    private EditText fname, lname, phoneno;
    private String strfname, strlname, strphoneno;
    private Button update;
    private ProgressBar pb;
    private ImageView profile_image;
    private boolean isImage = false;
    private Uri imageUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);
        getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.care_club_bg));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        fname = findViewById(R.id.fname);
        lname = findViewById(R.id.lname);
        EditText email = findViewById(R.id.email);
        phoneno = findViewById(R.id.phoneno);
        update = findViewById(R.id.updatebtn);
        pb = findViewById(R.id.progressbar);
        profile_image = findViewById(R.id.profile_image);

        update.setOnClickListener(this);

        helpers = new Helpers();
        session = new Session(EditUserProfile.this);
        user = session.getSession();


        fname.setText(user.getFname());
        lname.setText(user.getLname());
        email.setText(user.getEmail());
        phoneno.setText(user.getPhone());

        if (user.getImage() != null && user.getImage().length() > 0) {
            Glide.with(getApplicationContext()).load(user.getImage()).into(profile_image);
        } else {
            profile_image.setImageDrawable(getResources().getDrawable(R.drawable.profile));
        }


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.updatebtn: {
                boolean isConn = helpers.isConnected(EditUserProfile.this);
                if (!isConn) {
                    helpers.showError(EditUserProfile.this, "Internet Error", "Internet Error");
                    return;
                }
                strfname = fname.getText().toString();
                strlname = lname.getText().toString();
                strphoneno = phoneno.getText().toString();

                boolean flag = isValid();
                if (flag) {
                    //Show progress bar
                    pb.setVisibility(View.VISIBLE);
                    update.setVisibility(View.GONE);
                    if (isImage) {
                        uploadImage();
                    } else {
                        saveToDatabase();
                    }
                }
                break;
            }
            case R.id.fab: {
                if (askForPermission()) {
                    openGallery();
                }
                break;
            }
        }
    }

    private void saveToDatabase() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        user.setFname(strfname);
        user.setLname(strlname);
        user.setPhone(strphoneno);
        reference.child("Users").child(user.getId()).setValue(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        session.setSession(user);
                        //Start dashboard activity
                        Intent intent;
                        if (user.getRole() == 0) {
                            intent = new Intent(EditUserProfile.this, UserDashboard.class);
                        } else {
                            intent = new Intent(EditUserProfile.this, RiderDashboard.class);
                        }
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pb.setVisibility(View.GONE);
                        update.setVisibility(View.VISIBLE);
                        helpers.showError(EditUserProfile.this, "Error", "Something Went Wrong.\n Please Try Again");
                    }
                });
    }

    private void uploadImage() {
        final StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Users").child(user.getId());
        Calendar calendar = Calendar.getInstance();
        storageReference.child(calendar.getTimeInMillis() + "").putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        taskSnapshot.getMetadata().getReference().getDownloadUrl()
                                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Log.e("AddProduct", "in OnSuccess: " + uri.toString());
                                        user.setImage(uri.toString());
                                        saveToDatabase();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.e("AddProduct", "DownloadUrl:" + e.getMessage());
                                        pb.setVisibility(View.VISIBLE);
                                        update.setVisibility(View.GONE);
                                        helpers.showError(EditUserProfile.this, "Error", "Something Went Wrong.\n Please Try Again");
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Profile", "UploadImageUrl:" + e.getMessage());
                        pb.setVisibility(View.VISIBLE);
                        update.setVisibility(View.GONE);
                        helpers.showError(EditUserProfile.this, "Error", "Something Went Wrong.\n Please Try Again");
                    }
                });
    }

    private boolean askForPermission() {
        if (ActivityCompat.checkSelfPermission(EditUserProfile.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(EditUserProfile.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(EditUserProfile.this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 10);
            return false;
        }
        return true;
    }

    private void openGallery() {
        ImagePicker.create(EditUserProfile.this)
                .toolbarImageTitle("Tap to select")
                .single()
                .showCamera(true)
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, final int resultCode, Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            isImage = true;
            Image image = ImagePicker.getFirstImageOrNull(data);
            if (image != null) {
                imageUri = Uri.fromFile(new File(image.getPath()));
                Glide.with(getApplicationContext())
                        .load(imageUri)
                        .into(profile_image);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private boolean isValid() {
        boolean flag = true;
        if (strfname.length() < 3) {
            fname.setError("enter a valid first name");
            flag = false;

        } else {
            fname.setError(null);
        }
        if (strlname.length() < 3) {
            lname.setError("Enter valid last name");
            flag = false;
        } else {
            lname.setError(null);
        }
        if (strphoneno.length() < 11) {
            phoneno.setError("Enter valid phone number");
            flag = false;
        } else {
            phoneno.setError(null);
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