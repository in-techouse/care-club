package lcwu.fyp.careclub.activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import com.asksira.bsimagepicker.BSImagePicker;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.smarteist.autoimageslider.SliderViewAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lcwu.fyp.careclub.R;

public class AddProduct extends AppCompatActivity implements BSImagePicker.ImageLoaderDelegate, BSImagePicker.OnMultiImageSelectedListener,View.OnClickListener{
    private List<Uri>productImages;
    private SliderAdapter adapter;
    AppCompatButton submitproduct;
    EditText name,description,address,phoneno;
    ProgressBar submitproductprogessbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        productImages=new ArrayList<>();
        submitproduct=findViewById(R.id.submitProduct);
        name=findViewById(R.id.name);
        description=findViewById(R.id.description);
        address=findViewById(R.id.address);
        phoneno=findViewById(R.id.phoneno);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SliderView sliderView = findViewById(R.id.imageSlider);
        submitproduct.setOnClickListener(this);
         adapter = new SliderAdapter(AddProduct.this);
        sliderView.setSliderAdapter(adapter);


        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
        sliderView.startAutoCycle();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(askForPermission()) {
                       openGallery();
                }


            }

        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==10){
            if (grantResults.length > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                openGallery();
            }
        }
    }
      private void openGallery(){
          BSImagePicker multiSelectionPicker = new BSImagePicker.Builder("lcwu.fyp.careclub.fileprovider")
                  .isMultiSelect() //Set this if you want to use multi selection mode.
                  .setMinimumMultiSelectCount(1) //Default: 1.
                  .setMaximumMultiSelectCount(2) //Default: Integer.MAX_VALUE (i.e. User can select as many images as he/she wants)
                  .setMultiSelectBarBgColor(android.R.color.white) //Default: #FFFFFF. You can also set it to a translucent color.
                  .setMultiSelectTextColor(R.color.primary_text) //Default: #212121(Dark grey). This is the message in the multi-select bottom bar.
                  .setMultiSelectDoneTextColor(R.color.colorAccent) //Default: #388e3c(Green). This is the color of the "Done" TextView.
                  .setOverSelectTextColor(R.color.error_text) //Default: #b71c1c. This is the color of the message shown when user tries to select more than maximum select count.
                  .disableOverSelectionMessage() //You can also decide not to show this over select message.
                  .build();
          multiSelectionPicker.show(getSupportFragmentManager(), "picker");
      }
    private boolean askForPermission(){
        if(ActivityCompat.checkSelfPermission(AddProduct.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(AddProduct.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
         ActivityCompat.requestPermissions(AddProduct.this,new String[]{
                 Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},10);
            return false;
        }
        return true;


    }

    @Override
    public void loadImage(Uri imageUri, ImageView ivImage) {

    }

    @Override
    public void onMultiImageSelected(List<Uri> uriList, String tag) {
        productImages=uriList;
        adapter.notifyDataSetChanged();

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {


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
            Toast.makeText(AddProduct.this, "Position: " + position, Toast.LENGTH_LONG).show();
            Glide.with(viewHolder.itemView)
                    .load(productImages.get(position))
                    .into(viewHolder.imageViewBackground);

        }

        @Override
        public int getCount() {
            //slider view count could be dynamic size
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
