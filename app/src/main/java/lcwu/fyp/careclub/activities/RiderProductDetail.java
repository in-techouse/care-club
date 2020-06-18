package lcwu.fyp.careclub.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import lcwu.fyp.careclub.R;
import lcwu.fyp.careclub.director.Helpers;
import lcwu.fyp.careclub.model.Product;
import lcwu.fyp.careclub.model.User;

public class RiderProductDetail extends AppCompatActivity implements View.OnClickListener {
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    private ValueEventListener listener;
    private List<String> productImagesUploaded;
    private Product productDetail;
    private Helpers helpers;
    private User productOwner;
    private CircleImageView userImage;
    private LinearLayout userMain;
    private ProgressBar userProgress;
    private TextView userName, userEmail, userPhone;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_product_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());

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

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        helpers = new Helpers();
        FloatingActionButton navigate = findViewById(R.id.navigate);
        AppCompatButton markedPicked = findViewById(R.id.markedPicked);

        navigate.setOnClickListener(this);
        markedPicked.setOnClickListener(this);

        productImagesUploaded = new ArrayList<>();
        productImagesUploaded.addAll(productDetail.getImages());

        SliderView sliderView = findViewById(R.id.imageSlider);

        SliderAdapter adapter = new SliderAdapter();
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.startAutoCycle();
        sliderView.setScrollTimeInSec(4);

        TextView productCategory = findViewById(R.id.productCategory);
        productCategory.setText(productDetail.getCategory());

        TextView productName = findViewById(R.id.productName);
        productName.setText(productDetail.getName());

        TextView productQuantity = findViewById(R.id.productQuantity);
        productQuantity.setText(productDetail.getQuantityOfProducts() + "");

        TextView productAddress = findViewById(R.id.productAddress);
        productAddress.setText(productDetail.getAddress());

        TextView productDescription = findViewById(R.id.productDescription);
        productDescription.setText(productDetail.getDescription());

        userProgress = findViewById(R.id.userProgress);
        userMain = findViewById(R.id.userMain);
        userImage = findViewById(R.id.userImage);
        userName = findViewById(R.id.userName);
        userEmail = findViewById(R.id.userEmail);
        userPhone = findViewById(R.id.userPhone);
        ImageView call = findViewById(R.id.call);

        call.setOnClickListener(this);

        if (productDetail.isPicked()) {
            navigate.setVisibility(View.GONE);
            markedPicked.setVisibility(View.GONE);
        }
        loadOwnerDetails();
    }

    private void loadOwnerDetails() {
        userProgress.setVisibility(View.VISIBLE);
        userMain.setVisibility(View.GONE);
        listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (listener != null)
                    reference.child("Users").child(productDetail.getUserId()).removeEventListener(listener);
                userProgress.setVisibility(View.GONE);
                userMain.setVisibility(View.VISIBLE);

                if (dataSnapshot.exists()) {
                    productOwner = dataSnapshot.getValue(User.class);
                    if (productOwner != null) {
                        if (productOwner.getImage() != null && productOwner.getImage().length() > 1) {
                            Glide.with(getApplicationContext()).load(productOwner.getImage()).into(userImage);
                        }
                        userName.setText(productOwner.getFname() + " " + productOwner.getLname());
                        userEmail.setText(productOwner.getEmail());
                        userPhone.setText(productOwner.getPhone());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                if (listener != null)
                    reference.child("Users").child(productDetail.getUserId()).removeEventListener(listener);
                userProgress.setVisibility(View.GONE);
                userMain.setVisibility(View.VISIBLE);
            }
        };

        reference.child("Users").child(productDetail.getUserId()).addValueEventListener(listener);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.navigate: {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?daddr=" + productDetail.getLatitude() + "," + productDetail.getLongitude() + ""));
                startActivity(intent);
                break;
            }
            case R.id.markedPicked: {
                markedProductComplete();
                break;
            }
            case R.id.call: {
                if (productOwner != null) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + productOwner.getPhone()));
                    startActivity(intent);
                }
                break;
            }
        }
    }

    private void markedProductComplete() {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Please wait...");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        productDetail.setPicked(true);
        reference.child("Products").child(productDetail.getId()).setValue(productDetail)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        if (dialog.isShowing())
                            dialog.dismiss();
                        helpers.showSuccess(RiderProductDetail.this, "PRODUCT MARKED!", "The product has been marked as picked successfully.");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (dialog.isShowing())
                            dialog.dismiss();
                        helpers.showError(RiderProductDetail.this, "ERROR!", "Something went wrong.\nPlease try again later.");
                    }
                });
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
        public SliderAdapter.SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
            return new SliderAdapter.SliderAdapterVH(inflate);
        }

        @Override
        public void onBindViewHolder(SliderAdapter.SliderAdapterVH viewHolder, int position) {
            Glide.with(viewHolder.itemView)
                    .load(productImagesUploaded.get(position))
                    .into(viewHolder.imageViewBackground);
        }

        @Override
        public int getCount() {
            return productImagesUploaded.size();
        }

        class SliderAdapterVH extends SliderViewAdapter.ViewHolder {
            View itemView;
            ImageView imageViewBackground;

            SliderAdapterVH(View itemView) {
                super(itemView);
                imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
                this.itemView = itemView;
            }
        }
    }
}