package com.example.iot_lab4_20206089;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class PosicionesAdapter extends RecyclerView.Adapter<PosicionesAdapter.PosicionViewHolder> {
    private List<Equipo> equipos;

    public PosicionesAdapter(List<Equipo> equipos) {
        this.equipos = equipos;
    }

    @NonNull
    @Override
    public PosicionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_equipo, parent, false);
        return new PosicionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PosicionViewHolder holder, int position) {
        Equipo equipo = equipos.get(position);
        holder.nombreTextView.setText(equipo.getNombre());
        holder.rankingTextView.setText(String.valueOf(equipo.getPosicion()));
        holder.resultadosTextView.setText(
                "Victorias: " + equipo.getVictorias() +
                        " Empates: " + equipo.getEmpates() +
                        " Derrotas: " + equipo.getDerrotas()
        );
        holder.golesTextView.setText(
                "GF: " + equipo.getGolesFavor() +
                        " GC: " + equipo.getGolesContra() +
                        " DG: " + equipo.getDiferenciaGoles()
        );

        // Cargar el badge del equipo con Glide
        Glide.with(holder.itemView.getContext())
                .load(equipo.getBadge())
                .into(holder.badgeImageView);
    }

    @Override
    public int getItemCount() {
        return equipos.size();
    }

    public static class PosicionViewHolder extends RecyclerView.ViewHolder {
        TextView nombreTextView, rankingTextView, resultadosTextView, golesTextView;
        ImageView badgeImageView;

        public PosicionViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreTextView = itemView.findViewById(R.id.tvNombreEquipo);
            rankingTextView = itemView.findViewById(R.id.tvRanking);
            resultadosTextView = itemView.findViewById(R.id.tvResultados);
            golesTextView = itemView.findViewById(R.id.tvGoles);
            badgeImageView = itemView.findViewById(R.id.imgBadgeEquipo);
        }
    }
}
