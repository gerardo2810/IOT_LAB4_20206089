package com.example.iot_lab4_20206089;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PosicionesFragment extends Fragment {
    private RecyclerView recyclerViewPosiciones;
    private PosicionesAdapter posicionesAdapter;
    private List<Equipo> equipos;
    private EditText etIdLiga, etTemporada;
    private Button btnBuscarPosiciones;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_posiciones, container, false);

        recyclerViewPosiciones = view.findViewById(R.id.recyclerViewPosiciones);
        recyclerViewPosiciones.setLayoutManager(new LinearLayoutManager(getContext()));

        etIdLiga = view.findViewById(R.id.etIdLiga);
        etTemporada = view.findViewById(R.id.etTemporada);
        btnBuscarPosiciones = view.findViewById(R.id.btnBuscarPosiciones);

        equipos = new ArrayList<>();
        posicionesAdapter = new PosicionesAdapter(equipos);
        recyclerViewPosiciones.setAdapter(posicionesAdapter);

        btnBuscarPosiciones.setOnClickListener(v -> {
            String idLiga = etIdLiga.getText().toString().trim();
            String temporada = etTemporada.getText().toString().trim();
            if (!idLiga.isEmpty() && !temporada.isEmpty()) {
                obtenerPosiciones(idLiga, temporada);
            } else {
                Toast.makeText(getContext(), "Por favor ingrese el idLiga y la temporada", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void obtenerPosiciones(String idLiga, String temporada) {
        String url = "https://www.thesportsdb.com/api/v1/json/3/lookuptable.php?l=" + idLiga + "&s=" + temporada;

        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        JSONArray tableArray = jsonResponse.getJSONArray("table");

                        equipos.clear();
                        for (int i = 0; i < tableArray.length(); i++) {
                            JSONObject equipoObject = tableArray.getJSONObject(i);
                            String nombre = equipoObject.getString("strTeam");
                            int posicion = equipoObject.getInt("intRank");
                            int victorias = equipoObject.getInt("intWin");
                            int empates = equipoObject.getInt("intDraw");
                            int derrotas = equipoObject.getInt("intLoss");
                            int golesFavor = equipoObject.getInt("intGoalsFor");
                            int golesContra = equipoObject.getInt("intGoalsAgainst");
                            int diferenciaGoles = equipoObject.getInt("intGoalDifference");

                            // Usar optString en lugar de getString para evitar excepciones
                            String badge = equipoObject.optString("strTeamBadge", "");  // Cadena vacía si no está presente

                            equipos.add(new Equipo(nombre, posicion, victorias, empates, derrotas,
                                    golesFavor, golesContra, diferenciaGoles, badge));
                        }

                        posicionesAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
            Toast.makeText(getContext(), "Error al obtener las posiciones", Toast.LENGTH_SHORT).show();
        });

        queue.add(stringRequest);
    }
}
