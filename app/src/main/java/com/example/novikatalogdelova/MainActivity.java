package com.example.novikatalogdelova;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.novikatalogdelova.model.Deo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static com.example.novikatalogdelova.AddEditDeo.CENA_EXTRA;
import static com.example.novikatalogdelova.AddEditDeo.ID;
import static com.example.novikatalogdelova.AddEditDeo.IME_EXTRA;
import static com.example.novikatalogdelova.AddEditDeo.KOMENTAR_EXTRA;
import static com.example.novikatalogdelova.AddEditDeo.PUTANJA_SLIKE_EXTRA;

public class MainActivity extends AppCompatActivity implements DeoAdapter.OnClickDeleteDeo {
    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    private List<Deo> listaSvihDelova;
    private DeoAdapter adapter;
    public static final int ZAHTEV_ZA_DODAVANJE_DELA = 1;
    public static final int ZAHTEV_ZA_EDITOVANJE = 2;
    private DeoViewModel deoViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listaSvihDelova = new ArrayList<>();
        recyclerView = findViewById(R.id.recycle_view);
        adapter = new DeoAdapter(listaSvihDelova, this, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        deoViewModel = ViewModelProviders.of(this).get(DeoViewModel.class);
        deoViewModel.dohvatiSveDelove().observe(this, new Observer<List<Deo>>() {
            @Override
            public void onChanged(List<Deo> deos) {
                adapter.setujDelove(deos);
            }
        });

        floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditDeo.class);
                startActivityForResult(intent, ZAHTEV_ZA_DODAVANJE_DELA);
                Toast.makeText(MainActivity.this, "Unesi nov deo", Toast.LENGTH_SHORT).show();
            }
        });
        adapter.setOnClickListener(new DeoAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(Deo deo) {
                Intent intent = new Intent(MainActivity.this, AddEditDeo.class);
                intent.putExtra(ID, deo.getId());
                intent.putExtra(IME_EXTRA, deo.getImeDela());
                intent.putExtra(CENA_EXTRA, deo.getCenaDela());
                intent.putExtra(PUTANJA_SLIKE_EXTRA, deo.getPutanjaSlike());
                intent.putExtra(KOMENTAR_EXTRA, deo.getKomentar());
                startActivityForResult(intent, ZAHTEV_ZA_EDITOVANJE);
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ZAHTEV_ZA_DODAVANJE_DELA && resultCode == RESULT_OK) {
            String imeDela = data.getStringExtra(IME_EXTRA);
            int cenaDela = data.getIntExtra(CENA_EXTRA, 0);
            String komentar = data.getStringExtra(KOMENTAR_EXTRA);
            String putanjaSlike = data.getStringExtra(PUTANJA_SLIKE_EXTRA);
            Deo deo = new Deo(imeDela, cenaDela, putanjaSlike, komentar);
            deoViewModel.insert(deo);
            Toast.makeText(this, "Deo je uspesno sacuvan", Toast.LENGTH_SHORT).show();
        } else if (requestCode == ZAHTEV_ZA_EDITOVANJE && resultCode == RESULT_OK) {
            int id = data.getIntExtra(ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Deo ne moze biti azuriran", Toast.LENGTH_SHORT).show();
                return;
            }
            String imeDela = data.getStringExtra(IME_EXTRA);
            int cenaDela = data.getIntExtra(CENA_EXTRA, 1);
            String komentar = data.getStringExtra(KOMENTAR_EXTRA);
            String putanjaSlike = data.getStringExtra(PUTANJA_SLIKE_EXTRA);
            Deo deo = new Deo(imeDela, cenaDela, putanjaSlike, komentar);
            deo.setId(id);
            deoViewModel.update(deo);
        } else
            Toast.makeText(this, "Deo nije sacuvan", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onClickDeleteDeo(final Deo mojDeo) {
        AlertDialog.Builder dialogBuider = new AlertDialog.Builder(this);
        dialogBuider.setMessage("Da li ste sigurni da zelite da obrisete sletovani deo?");
        dialogBuider.setPositiveButton("Obrisi deo", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deoViewModel.delete(mojDeo);
                Toast.makeText(MainActivity.this, "Deo je uspesno obrisan", Toast.LENGTH_SHORT).show();
            }
        });
        dialogBuider.setNegativeButton("Odustani", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                if (dialog != null) {
                    dialog.dismiss();
                    Toast.makeText(MainActivity.this, "Brisanje dela je otkazano", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = dialogBuider.create();
        alertDialog.show();

    }
}
