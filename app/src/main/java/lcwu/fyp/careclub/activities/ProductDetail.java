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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

import lcwu.fyp.careclub.R;
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
        SliderView sliderView = findViewById(R.id.imageSlider);
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
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.edit: {
                break;
            }
        }
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
            if (isEditing)
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
            if (isEditing)
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

