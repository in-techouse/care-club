package lcwu.fyp.careclub.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import lcwu.fyp.careclub.R;
import lcwu.fyp.careclub.model.Product;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProdctHolder> {
    private List<Product> data;
    private Context context;

    public ProductsAdapter(Context c) {
        data = new ArrayList<>();
        context = c;
    }

    public void setData(List<Product> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override

    public ProdctHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProdctHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdctHolder holder, int position) {

        final Product p = data.get(position);
        if (p.getImages() != null && p.getImages().size() > 0) {
            Glide.with(context).load(p.getImages().get(0)).into(holder.productImage);
        } else {
            holder.productImage.setVisibility(View.GONE);
        }

        holder.productName.setText(p.getName());
        holder.productCategory.setText(p.getCategory());
        holder.productContact.setText(p.getPhoneno());
        holder.productAddress.setText(p.getAddress());
        holder.productQuantity.setText(p.getQuantityOfProducts() + "");
    }

    @Override
    public int getItemCount() {
        return data.size();

    }

    class ProdctHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName, productCategory, productContact, productQuantity, productAddress;

        public ProdctHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productCategory = itemView.findViewById(R.id.productCategory);
            productQuantity = itemView.findViewById(R.id.productQuantity);
            productContact = itemView.findViewById(R.id.productContact);
            productAddress = itemView.findViewById(R.id.productAddress);

        }
    }
}
