package lcwu.fyp.careclub.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import lcwu.fyp.careclub.R;
import lcwu.fyp.careclub.fragment.Ngos;
import lcwu.fyp.careclub.model.Donations;

public class NgosAdapter extends RecyclerView.Adapter<NgosAdapter.NgosHolder> {
    private List<Ngos> data;

    public NgosAdapter() {
        data= new ArrayList<>();

    }

    public void setData(List<Ngos> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public NgosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ngos,parent,false);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull NgosHolder holder, int position) {
        final Ngos ngos=data.get(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class NgosHolder extends RecyclerView.ViewHolder{

        public NgosHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
