package com.example.novikatalogdelova;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.novikatalogdelova.model.Deo;

import java.io.File;
import java.util.List;

public class DeoAdapter extends RecyclerView.Adapter<DeoAdapter.DeoViewHolder> {
    public interface OnClickDeleteDeo{

        void onClickDeleteDeo(Deo mojDeo);
    }

    private List<Deo> listaDelova;
    private Context context;
    private OnItemClickListener listener;
    private OnClickDeleteDeo onClickDeleteDeo;

    public DeoAdapter(List<Deo> listaDelova, Context context,OnClickDeleteDeo onClickDeleteDeo) {
        this.listaDelova = listaDelova;
        this.context = context;
        this.onClickDeleteDeo = onClickDeleteDeo;
    }

    @NonNull
    @Override
    public DeoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View deoView = inflater.inflate(R.layout.single_item, parent, false);

        // Return a new holder instance
        return new DeoViewHolder(deoView);
    }

    @Override
    public void onBindViewHolder(@NonNull DeoViewHolder holder, int position) {
        Deo deo = listaDelova.get(position);
        holder.imeDela.setText(deo.getImeDela());
        //holder.slikaDela.setImageURI(Uri.parse(new File(deo.getPutanjaSlike()).toString()));
        Glide.with(context)
                .load(deo.getPutanjaSlike())
                .placeholder(R.drawable.id_placeholder)
                .into(holder.slikaDela);
        holder.cenaDela.setText(String.valueOf(deo.getCenaDela()));

    }

    @Override
    public int getItemCount() {
        return listaDelova.size();
    }

    public void setujDelove(List<Deo> delovi) {
        this.listaDelova = delovi;
        notifyDataSetChanged();
    }


    public class DeoViewHolder extends RecyclerView.ViewHolder {
        private TextView imeDela;
        private ImageView slikaDela;
        private TextView cenaDela;
        private ImageView obrisiDeo;


        public DeoViewHolder(@NonNull View itemView) {
            super(itemView);
            imeDela = itemView.findViewById(R.id.naziv_dela);
            slikaDela = itemView.findViewById(R.id.sinle_item_slika);
            cenaDela = itemView.findViewById(R.id.cena_dela);
            obrisiDeo = itemView.findViewById(R.id.delete_item);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener!=null && position!=RecyclerView.NO_POSITION){
                        listener.onItemClicked(listaDelova.get(position));
                    }
                }
            });
            obrisiDeo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickDeleteDeo != null){
                        onClickDeleteDeo.onClickDeleteDeo(listaDelova.get(getAdapterPosition()));
                    }
                }
            });

        }
    }
    public interface OnItemClickListener{
        void onItemClicked(Deo deo);
    }
    public void setOnClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
