package lcwu.fyp.careclub.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import lcwu.fyp.careclub.R;
import lcwu.fyp.careclub.activities.ProductDetail;
import lcwu.fyp.careclub.activities.RiderProductDetail;
import lcwu.fyp.careclub.model.Product;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductHolder> {
    private List<Product> data;
    private Context context;
    private int role;

    public ProductsAdapter(Context c, int r) {
        data = new ArrayList<>();
        context = c;
        role = r;
    }

    public void setData(List<Product> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {

        final Product p = data.get(position);
        if (p.getImages() != null && p.getImages().size() > 0) {
            Glide.with(context).load(p.getImages().get(0)).into(holder.productImage);
        } else {
            holder.productImage.setVisibility(View.GONE);
        }

        holder.productName.setText(p.getName());
        holder.productCategory.setText(p.getCategory());
        holder.productQuantity.setText(p.getQuantityOfProducts() + "");
        holder.mainCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent id;
                if (role == 0) {
                    id = new Intent(context, ProductDetail.class);
                } else {
                    id = new Intent(context, RiderProductDetail.class);
                }
                Bundle b = new Bundle();
                b.putSerializable("product", p);
                id.putExtras(b);
                id.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ProductHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName, productCategory, productQuantity;
        CardView mainCard;

        ProductHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productCategory = itemView.findViewById(R.id.productCategory);
            productQuantity = itemView.findViewById(R.id.productQuantity);
            mainCard = itemView.findViewById(R.id.mainCard);
        }
    }

}
