package embeddedmajesty.driveawake10.pisleam.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import embeddedmajesty.driveawake10.pisleam.R;
import embeddedmajesty.driveawake10.pisleam.model.AwarenessItem;

public class AwarenessAdapter extends RecyclerView.Adapter<AwarenessAdapter.ViewHolder> {
    private Context mContext;
    private List<AwarenessItem> items;
    public AwarenessAdapter(Context c,List l){
        this.mContext=c;
        this.items=l;
    }
    @NonNull
    @Override
    public AwarenessAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.single_awareness_item,parent,false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull AwarenessAdapter.ViewHolder holder, int position) {
        AwarenessItem single = items.get(position);
        holder.txtCountry.setText(items.get(position).getDesc());
        holder.imgCountry.setImageResource(single.getImg());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtCountry;
        private ImageView imgCountry;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCountry = itemView.findViewById(R.id.nom);
            imgCountry = itemView.findViewById(R.id.imgCNT);
        }
    }
}
