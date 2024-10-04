package com.example.iot_lab4_20206089;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LigasAdapter extends RecyclerView.Adapter<LigasAdapter.LigaViewHolder> {
    private List<Liga> ligas;

    public LigasAdapter(List<Liga> ligas) {
        this.ligas = ligas;
    }

    @NonNull
    @Override
    public LigaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_liga, parent, false);
        return new LigaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LigaViewHolder holder, int position) {
        Liga liga = ligas.get(position);
        holder.nombreTextView.setText(liga.getNombre());
        holder.idTextView.setText(liga.getId());
        holder.altName1TextView.setText(liga.getAltName1());
        holder.altName2TextView.setText(liga.getAltName2());
    }

    @Override
    public int getItemCount() {
        return ligas.size();
    }

    public static class LigaViewHolder extends RecyclerView.ViewHolder {
        TextView nombreTextView, idTextView, altName1TextView, altName2TextView;

        public LigaViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreTextView = itemView.findViewById(R.id.tvNombre);
            idTextView = itemView.findViewById(R.id.tvId);
            altName1TextView = itemView.findViewById(R.id.tvAltName1);
            altName2TextView = itemView.findViewById(R.id.tvAltName2);
        }
    }
}
