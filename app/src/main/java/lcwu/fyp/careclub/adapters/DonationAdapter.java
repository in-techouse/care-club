package lcwu.fyp.careclub.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import lcwu.fyp.careclub.R;
import lcwu.fyp.careclub.model.Donations;

public class DonationAdapter extends RecyclerView.Adapter<DonationAdapter.DonationHolder> {
    private List<Donations> data;

    public DonationAdapter() {
        data= new ArrayList<>();
    }

    public void setData(List<Donations> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DonationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donation,parent,false);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull DonationHolder holder, int position) {
      final Donations d=data.get(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class DonationHolder extends RecyclerView.ViewHolder {

        public DonationHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
