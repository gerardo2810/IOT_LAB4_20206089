package com.example.iot_lab4_20206089;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.iot_lab4_20206089.R;

public class AppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        if (savedInstanceState == null) {
            loadFragment(new LigasFragment());
        }

        findViewById(R.id.btnLigas).setOnClickListener(v -> {
            loadFragment(new LigasFragment());
        });

        findViewById(R.id.btnPosiciones).setOnClickListener(v -> {
            loadFragment(new PosicionesFragment());
        });

        findViewById(R.id.btnResultados).setOnClickListener(v -> {
            loadFragment(new ResultadosFragment());
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
