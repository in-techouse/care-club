package lcwu.fyp.careclub.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import lcwu.fyp.careclub.R;
import lcwu.fyp.careclub.model.Products;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProdctHolder>{
    private List<Products> data;

    public ProductsAdapter() {
        data=new ArrayList<>();
    }

    public void setData(List<Products> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override

    public ProdctHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product,parent,false);


        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ProdctHolder holder, int position) {

        final Products p=data.get(position);
    }

    @Override
    public int getItemCount() {
        return data.size();

    }

    class ProdctHolder extends RecyclerView.ViewHolder {
        public ProdctHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}
