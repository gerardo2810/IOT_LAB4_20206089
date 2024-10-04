package com.example.iot_lab4_20206089;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
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

import androidx.appcompat.app.AlertDialog;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ResultadosFragment extends Fragment implements SensorEventListener {
    private RecyclerView recyclerViewResultados;
    private ResultadosAdapter resultadosAdapter;
    private List<Resultado> resultados;
    private EditText etIdLiga, etTemporada, etRonda;
    private Button btnBuscarResultados;

    // Variables del acelerómetro
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private float accelerationCurrent;
    private float accelerationLast;
    private float shakeThreshold = 20.0f;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resultados, container, false);

        recyclerViewResultados = view.findViewById(R.id.recyclerViewResultados);
        recyclerViewResultados.setLayoutManager(new LinearLayoutManager(getContext()));

        etIdLiga = view.findViewById(R.id.etIdLiga);
        etTemporada = view.findViewById(R.id.etTemporada);
        etRonda = view.findViewById(R.id.etRonda);
        btnBuscarResultados = view.findViewById(R.id.btnBuscarResultados);

        resultados = new ArrayList<>();
        resultadosAdapter = new ResultadosAdapter(resultados);
        recyclerViewResultados.setAdapter(resultadosAdapter);

        btnBuscarResultados.setOnClickListener(v -> {
            String idLiga = etIdLiga.getText().toString().trim();
            String temporada = etTemporada.getText().toString().trim();
            String ronda = etRonda.getText().toString().trim();
            if (!idLiga.isEmpty() && !temporada.isEmpty() && !ronda.isEmpty()) {
                obtenerResultados(idLiga, temporada, ronda);
            } else {
                Toast.makeText(getContext(), "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
            }
        });

        // Inicializar SensorManager
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        }

        return view;
    }

    private void obtenerResultados(String idLiga, String temporada, String ronda) {
        String url = "https://www.thesportsdb.com/api/v1/json/3/eventsround.php?id=" + idLiga + "&r=" + ronda + "&s=" + temporada;

        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        JSONArray eventsArray = jsonResponse.getJSONArray("events");

                        resultados.clear();
                        for (int i = 0; i < eventsArray.length(); i++) {
                            JSONObject eventObject = eventsArray.getJSONObject(i);
                            String nombreCompetencia = eventObject.getString("strLeague");
                            String fechaEncuentro = eventObject.getString("dateEvent");
                            String equipoLocal = eventObject.getString("strHomeTeam");
                            String equipoVisitante = eventObject.getString("strAwayTeam");
                            String resultado = eventObject.getString("intHomeScore") + " - " + eventObject.getString("intAwayScore");
                            String logoCompetenciaUrl = eventObject.getString("strThumb"); // URL del logo
                            int espectadores = eventObject.optInt("intSpectators", 0);

                            resultados.add(new Resultado(nombreCompetencia, fechaEncuentro, equipoLocal, equipoVisitante, resultado, logoCompetenciaUrl, espectadores));
                        }

                        resultadosAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
            Toast.makeText(getContext(), "Error al obtener los resultados", Toast.LENGTH_SHORT).show();
        });

        queue.add(stringRequest);
    }

    // Implementación del SensorEventListener para detectar agitación
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            // Calcular la magnitud de la aceleración
            accelerationLast = accelerationCurrent;
            accelerationCurrent = (float) Math.sqrt(x * x + y * y + z * z);
            float delta = accelerationCurrent - accelerationLast;

            // Verificar si el cambio en aceleración es mayor que el umbral
            if (delta > shakeThreshold) {
                // Mostrar dialog de confirmación
                new AlertDialog.Builder(getContext())
                        .setTitle("Confirmar acción")
                        .setMessage("¿Desea eliminar los últimos resultados?")
                        .setPositiveButton("Sí", (dialog, which) -> {
                            // Eliminar el último resultado si la lista no está vacía
                            if (!resultados.isEmpty()) {
                                resultados.remove(resultados.size() - 1);
                                resultadosAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // No se necesita implementar nada aquí
    }

    @Override
    public void onResume() {
        super.onResume();
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}
