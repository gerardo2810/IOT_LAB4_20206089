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

public class ResultadosAdapter extends RecyclerView.Adapter<ResultadosAdapter.ResultadoViewHolder> {
    private List<Resultado> resultados;

    public ResultadosAdapter(List<Resultado> resultados) {
        this.resultados = resultados;
    }

    @NonNull
    @Override
    public ResultadoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_resultado, parent, false);
        return new ResultadoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultadoViewHolder holder, int position) {
        Resultado resultado = resultados.get(position);
        holder.nombreCompetenciaTextView.setText(resultado.getNombreCompetencia());
        holder.fechaEncuentroTextView.setText(resultado.getFechaEncuentro());
        holder.equipoLocalTextView.setText(resultado.getEquipoLocal());
        holder.equipoVisitanteTextView.setText(resultado.getEquipoVisitante());
        holder.resultadoTextView.setText(resultado.getResultado());
        holder.espectadoresTextView.setText("Espectadores: " + resultado.getEspectadores());

        // Cargar el logo de la competencia
        Glide.with(holder.itemView.getContext())
                .load(resultado.getLogoCompetenciaUrl())  // URL del logo de la competencia
                .into(holder.logoCompetenciaImageView);
    }

    @Override
    public int getItemCount() {
        return resultados.size();
    }

    public static class ResultadoViewHolder extends RecyclerView.ViewHolder {
        TextView nombreCompetenciaTextView, fechaEncuentroTextView, equipoLocalTextView, equipoVisitanteTextView, resultadoTextView, espectadoresTextView;
        ImageView logoCompetenciaImageView;

        public ResultadoViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreCompetenciaTextView = itemView.findViewById(R.id.tvNombreCompetencia);
            fechaEncuentroTextView = itemView.findViewById(R.id.tvFechaEncuentro);
            equipoLocalTextView = itemView.findViewById(R.id.tvEquipoLocal);
            equipoVisitanteTextView = itemView.findViewById(R.id.tvEquipoVisitante);
            resultadoTextView = itemView.findViewById(R.id.tvResultado);
            espectadoresTextView = itemView.findViewById(R.id.tvEspectadores);
            logoCompetenciaImageView = itemView.findViewById(R.id.imgLogoCompetencia);
        }
    }
}
