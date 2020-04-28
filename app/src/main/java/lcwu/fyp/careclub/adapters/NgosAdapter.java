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
import lcwu.fyp.careclub.model.NGOs;

public class NgosAdapter extends RecyclerView.Adapter<NgosAdapter.NgosHolder> {
    private List<NGOs> data;
    private Context context;

    public NgosAdapter(Context c) {
        data = new ArrayList<>();
        context = c;
    }

    public void setData(List<NGOs> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public NgosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ngos, parent, false);
        return new NgosHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NgosHolder holder, int position) {
        final NGOs ngos = data.get(position);
        if (ngos.getImages() != null && ngos.getImages().size() > 0) {
            Glide.with(context).load(ngos.getImages().get(0)).into(holder.image);
        } else {
            holder.image.setVisibility(View.GONE);
        }
        holder.name.setText(ngos.getName());
        holder.address.setText(ngos.getAddress());
        holder.category.setText(ngos.getCategory());
        holder.contact.setText(ngos.getPhone());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class NgosHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, address, category, contact;

        public NgosHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.address);
            contact = itemView.findViewById(R.id.contact);
            category = itemView.findViewById(R.id.category);
        }
    }
}
