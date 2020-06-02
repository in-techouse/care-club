package lcwu.fyp.careclub.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

import lcwu.fyp.careclub.R;
import lcwu.fyp.careclub.director.Helpers;
import lcwu.fyp.careclub.model.Product;

public class ProductDetail extends AppCompatActivity implements View.OnClickListener {
    private List<Uri> productImages;
    private List<String> productImagesUploaded;
    private SliderAdapter adapter;
    private Product productDetail;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Products");
    private Spinner category;
    private EditText name, quantity, description, address, phoneNo;
    private String strCategory, strName, strQuantity, strDescription, strAddress, strPhoneNo = "";
    private AppCompatButton edit;
    private ProgressBar progressbar;
    private boolean isEditing = false;
    private boolean isImage = false;
    private SliderView sliderView;
    private Helpers helpers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Intent it = getIntent();
        if (it == null) {
            finish();
            return;
        }

        Bundle b = it.getExtras();
        if (b == null) {
            finish();
            return;
        }
        productDetail = (Product) b.getSerializable("product");
        if (productDetail == null) {
            finish();
            return;
        }

        productImages = new ArrayList<>();
        productImagesUploaded = new ArrayList<>();
        for (String string : productDetail.getImages()) {
            productImagesUploaded.add(string);
        }

        name = findViewById(R.id.name);
        description = findViewById(R.id.description);
        address = findViewById(R.id.address);
        phoneNo = findViewById(R.id.phoneno);
        category = findViewById(R.id.category);
        quantity = findViewById(R.id.quantity);
        edit = findViewById(R.id.edit);
        progressbar = findViewById(R.id.progressbar);
        sliderView = findViewById(R.id.imageSlider);
        edit.setOnClickListener(this);

        adapter = new SliderAdapter(ProductDetail.this);
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.startAutoCycle();
        sliderView.setScrollTimeInSec(4);

        name.setText(productDetail.getName());
        String[] categories = getResources().getStringArray(R.array.categories);
        int index = 0;
        for (String str : categories) {
            if (str.equalsIgnoreCase(productDetail.getCategory()))
                break;
            index++;
        }
        category.setSelection(index);
        quantity.setText(productDetail.getQuantityOfProducts() + "");
        description.setText(productDetail.getDescription());
        address.setText(productDetail.getAddress());
        phoneNo.setText(productDetail.getPhoneno());

        category.setEnabled(false);
        category.setClickable(false);
        name.setFocusable(false);
        quantity.setFocusable(false);
        description.setFocusable(false);
        address.setFocusable(false);
        phoneNo.setFocusable(false);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.edit: {
                if (isEditing) {
                    // Update Product
                    if(isValid()){
                        progressbar.setVisibility(View.VISIBLE);
                        edit.setVisibility(View.GONE);
                        saveToDatabase();
                    }

                } else {
                    // Open for editing
                    isEditing = true;
                    edit.setText("UPDATE");
                    category.setEnabled(true);
                    category.setClickable(true);
                    name.setFocusableInTouchMode(true);
                    name.setFocusable(true);
                    quantity.setFocusableInTouchMode(true);
                    quantity.setFocusable(true);
                    description.setFocusableInTouchMode(true);
                    description.setFocusable(true);
                    address.setFocusableInTouchMode(true);
                    address.setFocusable(true);
                    phoneNo.setFocusableInTouchMode(true);
                    phoneNo.setFocusable(true);

                    adapter = new SliderAdapter(ProductDetail.this);
                    sliderView.setSliderAdapter(adapter);
                    sliderView.setIndicatorAnimation(IndicatorAnimations.WORM);
                    sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
                    sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
                    sliderView.setIndicatorSelectedColor(Color.WHITE);
                    sliderView.setIndicatorUnselectedColor(Color.GRAY);
                    sliderView.startAutoCycle();
                    sliderView.setScrollTimeInSec(4);
                }
                break;
            }
        }
    }

    private boolean isValid() {
        boolean flag = true;
        String error = "";

        strName = name.getText().toString();
        strPhoneNo = phoneNo.getText().toString();
        strAddress = address.getText().toString();
        strDescription = description.getText().toString();
        strCategory = category.getSelectedItem().toString();
        strQuantity = quantity.getText().toString();

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
            phoneNo.setError("Enter a valid PhoneNo");
            flag = false;
        } else {
            phoneNo.setError(null);
        }

        if (strAddress.length() < 15) {
            address.setError("Enter a valid Address");
            flag = false;
        } else {
            address.setError(null);
        }

        if (error.length() > 0) {
            helpers.showError(ProductDetail.this, "ERROR!", error);
        }

        return flag;
    }

    private void saveToDatabase() {
        productDetail.setCategory(strCategory);
        productDetail.setName(strName);
        productDetail.setQuantityOfProducts(Integer.parseInt(strQuantity));
        productDetail.setDescription(strDescription);
        productDetail.setAddress(strAddress);
        productDetail.setPhoneno(strPhoneNo);
        reference.child(productDetail.getId()).setValue(productDetail).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                progressbar.setVisibility(View.GONE);
                edit.setVisibility(View.VISIBLE);
                showSuccessMessage();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressbar.setVisibility(View.GONE);
                edit.setVisibility(View.VISIBLE);
                helpers.showError(ProductDetail.this, "Error", "Something Went Wrong.\n Please Try Again");
            }
        });
    }

    private void showSuccessMessage() {
        MaterialDialog mDialog = new MaterialDialog.Builder(ProductDetail.this)
                .setTitle("PRODUCT")
                .setMessage("Your product is updated successfully.")
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
        private Context context;

        public SliderAdapter(Context context) {
            this.context = context;
        }

        @Override
        public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
            return new SliderAdapterVH(inflate);
        }

        @Override
        public void onBindViewHolder(SliderAdapterVH viewHolder, int position) {
            if (isImage)
                Glide.with(viewHolder.itemView)
                        .load(productImages.get(position))
                        .into(viewHolder.imageViewBackground);
            else
                Glide.with(viewHolder.itemView)
                        .load(productImagesUploaded.get(position))
                        .into(viewHolder.imageViewBackground);

        }

        @Override
        public int getCount() {
            if (isImage)
                return productImages.size();
            else
                return productImagesUploaded.size();

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

