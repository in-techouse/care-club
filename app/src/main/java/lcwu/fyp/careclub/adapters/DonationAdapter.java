package lcwu.fyp.careclub.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import lcwu.fyp.careclub.R;
import lcwu.fyp.careclub.model.Donation;

public class DonationAdapter extends RecyclerView.Adapter<DonationAdapter.DonationHolder> {
    private List<Donation> data;

    public DonationAdapter() {
        data = new ArrayList<>();
    }

    public void setData(List<Donation> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DonationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donation, parent, false);
        return new DonationHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DonationHolder holder, int position) {
        final Donation d = data.get(position);
        holder.amount.setText(d.getAmount() + " RS");
        holder.date.setText(d.getDate());
        holder.paymentmethod.setText("");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class DonationHolder extends RecyclerView.ViewHolder {
        TextView date, amount, paymentmethod;

        public DonationHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            amount = itemView.findViewById(R.id.amount);
            paymentmethod = itemView.findViewById(R.id.paymentMethod);
        }
    }
}
