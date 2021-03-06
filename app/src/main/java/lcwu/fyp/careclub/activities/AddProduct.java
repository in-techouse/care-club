package lcwu.fyp.careclub.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
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
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import lcwu.fyp.careclub.R;
import lcwu.fyp.careclub.director.Helpers;
import lcwu.fyp.careclub.director.Session;
import lcwu.fyp.careclub.model.Product;
import lcwu.fyp.careclub.model.User;

public class AddProduct extends AppCompatActivity implements View.OnClickListener {
    private List<Uri> productImages;
    private SliderAdapter adapter;
    private Product product;
    private Helpers helpers;
    private Spinner category;
    private EditText name, quantity, description, phoneno;
    private TextView address;
    private AppCompatButton submitProduct;
    private ProgressBar submitprductprogressbar;
    private String strCategory, strName, strQuantity, strDescription, strAddress, strPhoneNo = "";
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Products");
    private SliderView sliderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        productImages = new ArrayList<>();
        product = new Product();
        helpers = new Helpers();
        Session session = new Session(AddProduct.this);
        User user = session.getSession();
        String id = reference.push().getKey();
        product.setUserId(user.getId());
        product.setTaken(false);
        product.setNgoid("");
        product.setRiderId("");
        product.setId(id);

        submitProduct = findViewById(R.id.submitProduct);
        name = findViewById(R.id.name);
        description = findViewById(R.id.description);
        address = findViewById(R.id.address);
        phoneno = findViewById(R.id.phoneno);
        category = findViewById(R.id.category);
        quantity = findViewById(R.id.quantity);
        submitprductprogressbar = findViewById(R.id.submitprductprogressbar);

        RelativeLayout selectAddress = findViewById(R.id.selectAddress);
        selectAddress.setOnClickListener(this);

        sliderView = findViewById(R.id.imageSlider);
        submitProduct.setOnClickListener(this);

        adapter = new SliderAdapter();
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(4);
        sliderView.startAutoCycle();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 10) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            }
        }
    }

    private void openGallery() {
        ImagePicker.create(AddProduct.this)
                .toolbarImageTitle("Tap to select")
                .multi()
                .limit(2)
                .showCamera(true)
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, final int resultCode, Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            productImages.clear();
            adapter.notifyDataSetChanged();
            sliderView.setSliderAdapter(null);
            List<Image> images = ImagePicker.getImages(data);
            List<Uri> uriList = new ArrayList<>();
            for (Image img : images) {
                Uri uri = Uri.fromFile(new File(img.getPath()));
                uriList.add(uri);
            }
            productImages = uriList;
            sliderView.setSliderAdapter(adapter);
            sliderView.setIndicatorAnimation(IndicatorAnimations.WORM);
            sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
            sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
            sliderView.setIndicatorSelectedColor(Color.WHITE);
            sliderView.setIndicatorUnselectedColor(Color.GRAY);
            sliderView.setScrollTimeInSec(4);
            sliderView.startAutoCycle();
            adapter.notifyDataSetChanged();
        } else if (requestCode == 10 && resultCode == RESULT_OK) {
            if (data != null) {
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    Product p = (Product) bundle.getSerializable("result");
                    if (p != null) {
                        Log.e("AddProduct", "Location Received: " + p.getAddress());
                        product.setLatitude(p.getLatitude());
                        product.setLongitude(p.getLongitude());
                        product.setAddress(p.getAddress());
                        address.setText(product.getAddress());
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private boolean askForPermission() {
        if (ActivityCompat.checkSelfPermission(AddProduct.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(AddProduct.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AddProduct.this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 10);
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.submitProduct: {
                boolean isConn = helpers.isConnected(AddProduct.this);
                if (!isConn) {
                    helpers.showError(AddProduct.this, "Internet Error", "Internet Error");
                    return;
                }
                boolean flag = isValid();
                if (flag) {
                    submitprductprogressbar.setVisibility(View.VISIBLE);
                    submitProduct.setVisibility(View.GONE);
                    uploadImage(0);
                }
                break;
            }
            case R.id.fab: {
                if (askForPermission()) {
                    openGallery();
                }
                break;
            }
            case R.id.selectAddress: {
                Intent it = new Intent(AddProduct.this, SelectAddress.class);
                startActivityForResult(it, 10);
                break;
            }
        }
    }

    private void uploadImage(final int count) {
        final StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Products").child(product.getId());
        Calendar calendar = Calendar.getInstance();
        storageReference.child(calendar.getTimeInMillis() + "").putFile(productImages.get(count))
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        taskSnapshot.getMetadata().getReference().getDownloadUrl()
                                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Log.e("AddProduct", "in OnSuccess: " + uri.toString());
                                        product.getImages().add(uri.toString());
                                        if (product.getImages().size() == productImages.size()) {
                                            saveToDatabase();
                                        } else {
                                            uploadImage(count + 1);
                                        }
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.e("AddProduct", "DownloadUrl:" + e.getMessage());
                                        submitprductprogressbar.setVisibility(View.VISIBLE);
                                        submitProduct.setVisibility(View.GONE);
                                        helpers.showError(AddProduct.this, "Error", "Something Went Wrong.\n Please Try Again");
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Profile", "UploadImageUrl:" + e.getMessage());
                        submitprductprogressbar.setVisibility(View.VISIBLE);
                        submitProduct.setVisibility(View.GONE);
                        helpers.showError(AddProduct.this, "Error", "Something Went Wrong.\n Please Try Again");
                    }
                });
    }

    private void saveToDatabase() {
        product.setCategory(strCategory);
        product.setName(strName);
        product.setQuantityOfProducts(Integer.parseInt(strQuantity));
        product.setDescription(strDescription);
        product.setAddress(strAddress);
        product.setPhoneno(strPhoneNo);
        reference.child(product.getId()).setValue(product)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        submitprductprogressbar.setVisibility(View.GONE);
                        submitProduct.setVisibility(View.VISIBLE);
                        showSuccessMessage();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        submitprductprogressbar.setVisibility(View.GONE);
                        submitProduct.setVisibility(View.VISIBLE);
                        helpers.showError(AddProduct.this, "Error", "Something Went Wrong.\n Please Try Again");
                    }
                });
    }

    private void showSuccessMessage() {
        MaterialDialog mDialog = new MaterialDialog.Builder(AddProduct.this)
                .setTitle("PRODUCT")
                .setMessage("Your product is live for the NGO's.")
                .setCancelable(false)
                .setPositiveButton("Okay", R.drawable.ic_action_okay, new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
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
        boolean flag = true;
        String error = "";

        strName = name.getText().toString();
        strPhoneNo = phoneno.getText().toString();
        strAddress = address.getText().toString();
        strDescription = description.getText().toString();
        strCategory = category.getSelectedItem().toString();
        strQuantity = quantity.getText().toString();

        if (productImages.size() < 1) {
            error = error + "*Select product images.\n";
            flag = false;
        }

        if (category.getSelectedItemPosition() == 0) {
            error = error + "*Select product category.\n";
            flag = false;
        }

        if (strName.length() < 3) {
            name.setError("Enter a valid product name");
            flag = false;
        } else {
            name.setError(null);
        }

        if (strQuantity.length() < 1) {
            quantity.setError("Enter a valid quantity");
            flag = false;

        } else {
            quantity.setError(null);
        }

        if (strDescription.length() < 15) {
            description.setError("Enter a valid Description");
            flag = false;
        } else {
            description.setError(null);
        }

        if (strPhoneNo.length() != 11) {
            phoneno.setError("Enter a valid PhoneNo");
            flag = false;
        } else {
            phoneno.setError(null);
        }

        if (strAddress.length() < 15) {
            error = error + "*Select the product pickup address.\n";
            flag = false;
        }

        if (error.length() > 0) {
            helpers.showError(AddProduct.this, "ERROR!", error);
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

    public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderAdapterVH> {
        public SliderAdapter() {
        }

        @Override
        public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
            return new SliderAdapterVH(inflate);
        }

        @Override
        public void onBindViewHolder(SliderAdapterVH viewHolder, int position) {
            Glide.with(viewHolder.itemView)
                    .load(productImages.get(position))
                    .into(viewHolder.imageViewBackground);
        }

        @Override
        public int getCount() {
            return productImages.size();
        }

        class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

            View itemView;
            ImageView imageViewBackground;

            public SliderAdapterVH(View itemView) {
                super(itemView);
                imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
                this.itemView = itemView;
            }
        }
    }
}
