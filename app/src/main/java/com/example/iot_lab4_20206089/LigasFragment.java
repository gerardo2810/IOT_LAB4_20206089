package com.example.iot_lab4_20206089;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
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

public class LigasFragment extends Fragment {
    private RecyclerView recyclerViewLigas;
    private LigasAdapter ligasAdapter;
    private List<Liga> ligas;
    private EditText etBuscarPais;
    private Button btnBuscarPais;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ligas, container, false);

        recyclerViewLigas = view.findViewById(R.id.recyclerViewLigas);
        recyclerViewLigas.setLayoutManager(new LinearLayoutManager(getContext()));

        etBuscarPais = view.findViewById(R.id.etBuscarPais);
        btnBuscarPais = view.findViewById(R.id.btnBuscarPais);

        ligas = new ArrayList<>();
        ligasAdapter = new LigasAdapter(ligas);
        recyclerViewLigas.setAdapter(ligasAdapter);

        // Cargar todas las ligas cuando el fragmento se inicia
        obtenerLigasGenerales();

        btnBuscarPais.setOnClickListener(v -> {
            String pais = etBuscarPais.getText().toString().trim();
            if (!pais.isEmpty()) {
                obtenerLigasPorPais(pais);
            } else {
                Toast.makeText(getContext(), "Por favor ingrese un país", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void obtenerLigasGenerales() {
        String url = "https://www.thesportsdb.com/api/v1/json/3/all_leagues.php";

        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        JSONArray leaguesArray = jsonResponse.getJSONArray("leagues");

                        ligas.clear();
                        for (int i = 0; i < leaguesArray.length(); i++) {
                            JSONObject leagueObject = leaguesArray.getJSONObject(i);
                            String nombre = leagueObject.getString("strLeague");
                            String id = leagueObject.getString("idLeague");
                            String altName1 = leagueObject.optString("strLeagueAlternate", "N/A");
                            String altName2 = leagueObject.optString("strLeague2", "N/A");

                            ligas.add(new Liga(nombre, id, altName1, altName2));
                        }

                        ligasAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
            Toast.makeText(getContext(), "Error al obtener las ligas", Toast.LENGTH_SHORT).show();
        });

        queue.add(stringRequest);
    }

    private void obtenerLigasPorPais(String pais) {
        String url = "https://www.thesportsdb.com/api/v1/json/3/search_all_leagues.php?c=" + pais;

        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    // Registrar la respuesta de la API para depuración
                    Log.d("API_RESPONSE", response);

                    try {
                        JSONObject jsonResponse = new JSONObject(response);

                        // Cambia 'countrys' por 'countries'
                        if (jsonResponse.has("countries")) {
                            JSONArray leaguesArray = jsonResponse.getJSONArray("countries");

                            ligas.clear();
                            for (int i = 0; i < leaguesArray.length(); i++) {
                                JSONObject leagueObject = leaguesArray.getJSONObject(i);
                                String nombre = leagueObject.getString("strLeague");
                                String id = leagueObject.getString("idLeague");
                                String altName1 = leagueObject.optString("strLeagueAlternate", "N/A");
                                String altName2 = leagueObject.optString("strLeague2", "N/A");

                                ligas.add(new Liga(nombre, id, altName1, altName2));
                            }

                            ligasAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getContext(), "No se encontraron ligas para este país", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "Error al procesar la respuesta de la API", Toast.LENGTH_SHORT).show();
                    }
                }, error -> {
            Toast.makeText(getContext(), "País no encontrado o error en la búsqueda", Toast.LENGTH_SHORT).show();
        });

        queue.add(stringRequest);
    }
}
