package lcwu.fyp.careclub.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import lcwu.fyp.careclub.R;
import lcwu.fyp.careclub.model.PaymentMethod;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.PaymentHolder> {
    private List<PaymentMethod> methods;

    public PaymentAdapter() {
        methods = new ArrayList<>();
    }

    public void setMethods(List<PaymentMethod> methods) {
        this.methods = methods;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PaymentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_payment, parent, false);
        return new PaymentHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentHolder holder, int position) {
        final PaymentMethod method = methods.get(position);
        holder.name.setText(method.getName());
        holder.providerName.setText(method.getProviderName());
        holder.accountHolderName.setText(method.getAccountHolderName());
        holder.accountNumber.setText(method.getAccountNumber());
    }

    @Override
    public int getItemCount() {
        return methods.size();
    }

    class PaymentHolder extends RecyclerView.ViewHolder {
        TextView name, providerName, accountHolderName, accountNumber;

        PaymentHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            providerName = itemView.findViewById(R.id.providerName);
            accountHolderName = itemView.findViewById(R.id.accountHolderName);
            accountNumber = itemView.findViewById(R.id.accountNumber);
        }
    }
}
