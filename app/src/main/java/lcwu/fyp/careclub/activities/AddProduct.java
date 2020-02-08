package lcwu.fyp.careclub.activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import com.asksira.bsimagepicker.BSImagePicker;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.smarteist.autoimageslider.SliderViewAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import lcwu.fyp.careclub.R;
import lcwu.fyp.careclub.director.Helpers;
import lcwu.fyp.careclub.director.Session;
import lcwu.fyp.careclub.model.Products;
import lcwu.fyp.careclub.model.User;

public class AddProduct extends AppCompatActivity implements BSImagePicker.ImageLoaderDelegate, BSImagePicker.OnMultiImageSelectedListener,View.OnClickListener{
    private List<Uri>productImages;
    private SliderAdapter adapter;
    AppCompatButton submitproduct;
    EditText name,description,address,phoneno,category,qunatity;
    ProgressBar submitproductprogessbar;
    String straddress,strpname,strdescription,strphone,strquantity,strcategory;
    Helpers helpers;
    User user;
    Session session;
    private Products product;
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
        category=findViewById(R.id.category);
        qunatity=findViewById(R.id.quantity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SliderView sliderView = findViewById(R.id.imageSlider);
        submitproduct.setOnClickListener(this);
        helpers = new Helpers();
        session=new Session(AddProduct.this);
        user=session.getSession();
        product = new Products();

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

        int id = v.getId();
        switch (id) {
            case R.id.submitProduct: {
                boolean isConn = helpers.isConnected(AddProduct.this);
                if (!isConn) {
                    helpers.showError(AddProduct.this, "Internet Error", "Internet Error");
                    return;
                }
                strpname = name.getText().toString();
                strphone = phoneno.getText().toString();
                straddress = address.getText().toString();
                strdescription = description.getText().toString();
                strcategory=category.getText().toString();
                strquantity=qunatity.getText().toString();

                boolean flag = isValid();
                if (flag) {
                    submitproductprogessbar.setVisibility(View.VISIBLE);
                    submitproduct.setVisibility(View.GONE);
                    uploadImage(productImages.get(0));

                }
            }

        }
    }

    private void uploadImage(Uri imagePath){
        final StorageReference storageReference= FirebaseStorage.getInstance().getReference().child("Products").child(product.getId());
        Calendar calendar=Calendar.getInstance();
        storageReference.child(calendar.getTimeInMillis()+"").putFile(imagePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.e("Profile","in OnSuccess"+uri.toString());
                        product.getImages().add(uri.toString());
                        submitproductprogessbar.setVisibility(View.GONE);
                        submitproduct.setVisibility(View.VISIBLE);
                        saveToDatabase();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Profile","DownloadUrl:"+e.getMessage());
                        submitproductprogessbar.setVisibility(View.VISIBLE);
                        submitproduct.setVisibility(View.GONE);
                        helpers.showError(AddProduct.this,"Error","Something Went Wrong.\n Please Try Again");
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("Profile","UploadImageUrl:"+e.getMessage());
                submitproductprogessbar.setVisibility(View.GONE);
                submitproduct.setVisibility(View.VISIBLE);
                helpers.showError(AddProduct.this,"Error","Something Went Wrong.\n Please Try Again");
            }

        });
    }
        private void saveToDatabase(){
        submitproductprogessbar.setVisibility(View.VISIBLE);
        submitproduct.setVisibility(View.GONE);
        product.setName(strpname);
        product.setAddress(straddress);
        product.setDescription(strdescription);
        product.setCategory(strcategory);
        product.setPhoneno(strphone);
        product.setQuantityOfProducts(Integer.parseInt(strquantity));
            DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Products");
            String id  = databaseReference.push().getKey();
            product.setId(id);
            databaseReference.child(product.getId()).setValue(product).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    submitproductprogessbar.setVisibility(View.VISIBLE);
                    submitproduct.setVisibility(View.GONE);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    submitproductprogessbar.setVisibility(View.GONE);
                    submitproduct.setVisibility(View.VISIBLE);
                    helpers.showError(AddProduct.this,"Error","Something Went Wrong.\n Please Try Again");

                }
            });



        }


    private boolean isValid()
    {
        boolean flag=true;
        if(strpname.length()<3){
            name.setError("enter a valid Product name");
            flag=false;

        }
        else {
            name.setError(null);
        }
        if(strdescription.length()<15){
            description.setError("enter a valid Description");
            flag=false;

        }
        else {
            description.setError(null);
        }
        if(strphone.length()<11){
            phoneno.setError("enter a valid PhoneNo");
            flag=false;

        }
        else {
            phoneno.setError(null);
        }
        if(straddress.length()<15){
            address.setError("enter a valid Address");
            flag=false;

        }
        else {
            address.setError(null);
        }
        if(strcategory.length()<4){
            category.setError("enter a valid Category");
            flag=false;

        }
        else {
            category.setError(null);
        }
        if(strquantity.length()<1){
            qunatity.setError("enter a valid quantity");
            flag=false;

        }
        else {
            qunatity.setError(null);
        }
        return flag;


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
