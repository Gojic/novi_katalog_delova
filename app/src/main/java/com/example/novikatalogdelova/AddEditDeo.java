package com.example.novikatalogdelova;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class AddEditDeo extends AppCompatActivity {
    public static final String IME_EXTRA = "IME_EXTRA";
    public static final String CENA_EXTRA = "CENA_EXTRA";
    public static final String PUTANJA_SLIKE_EXTRA = "PUTANJA_SLIKE_EXTRA";
    public static final String KOMENTAR_EXTRA = "KOMENTAR_EXTRA";
    public static final String ID = "id";
    public static final int ZAHTEV_ZA_BIRANE_SLIKE = 3;

    private EditText unosImenaDela;
    private EditText unosCeneDela;
    private ImageView slikaDela;
    private EditText unosKomentara;
    private String imageUriString;
    private Uri selectedImageUri;
    private int id;
    private Button saveButton;
    private boolean deoJePromenjen;

    //pocinjemo sa boolean vrednoscu deoJeIzmenjen koji nije inicijalizovan znaci degoult vrednost mu je false
    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            //kada je aktiviran onTouchListener boolean vrednost je true
            deoJePromenjen = true;
            return false;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_view);

        unosImenaDela = findViewById(R.id.imedela_edit_text);
        unosCeneDela = findViewById(R.id.cena_dela_text_view);
        slikaDela = findViewById(R.id.slika);
        unosKomentara = findViewById(R.id.komentar_text_view);

        unosImenaDela.setOnTouchListener(onTouchListener);
        unosCeneDela.setOnTouchListener(onTouchListener);
        slikaDela.setOnTouchListener(onTouchListener);
        unosKomentara.setOnTouchListener(onTouchListener);


        slikaDela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                startActivityForResult(Intent.createChooser(intent, "select a picture"), ZAHTEV_ZA_BIRANE_SLIKE);
                Toast.makeText(AddEditDeo.this, "izaberi jednu sliku", Toast.LENGTH_SHORT).show();
            }
        });

        saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sacuvajDeo();

            }
        });
        if (getIntent() != null) {
            imageUriString = getIntent().getStringExtra(PUTANJA_SLIKE_EXTRA);
            id = getIntent().getIntExtra(ID, 0);

        }
        Intent intent = getIntent();
        if (intent.hasExtra(ID)) {
            String slika = getIntent().getExtras().getString(PUTANJA_SLIKE_EXTRA);
            setTitle("Izmeni Deo");

            if (slika == null) {
                slikaDela.setImageResource(R.drawable.id_placeholder);
            } else {
                slikaDela.setImageURI(Uri.parse(imageUriString));
            }
            unosImenaDela.setText(intent.getStringExtra(IME_EXTRA));
            int cenaInt = intent.getIntExtra(CENA_EXTRA, 0);
            unosCeneDela.setText(String.valueOf(cenaInt));

            unosKomentara.setText(intent.getStringExtra(KOMENTAR_EXTRA));

        } else {
            setTitle("Dodaj novi deo");

            invalidateOptionsMenu();
        }


    }

    private void sacuvajDeo() {

        String imeDelaString = unosImenaDela.getText().toString().trim();
        String cenaDelaString = unosCeneDela.getText().toString().trim();
        String komentarString = unosKomentara.getText().toString().trim();
        String slikaString = imageUriString;

        int cenaJeNula = 0;
        if (!TextUtils.isEmpty(cenaDelaString)) {
            cenaJeNula = Integer.parseInt(cenaDelaString);
        }

        //proveravamo da li su polja prazna,ukolko jesu ispisujemo tst poruku i izlazimo iz metode
        if (TextUtils.isEmpty(imeDelaString)) {
            Toast.makeText(this, "Morate uneti sva polja", Toast.LENGTH_SHORT).show();
            return;
        }


        Intent intent = new Intent();
        intent.putExtra(IME_EXTRA, imeDelaString);
        intent.putExtra(CENA_EXTRA, cenaJeNula);
        intent.putExtra(KOMENTAR_EXTRA, komentarString);
        intent.putExtra(PUTANJA_SLIKE_EXTRA, slikaString);
        setResult(RESULT_OK, intent);
        int id = getIntent().getIntExtra(ID, -1);
        //ovde proveravamo da id nije -1.
        //samo ako je to istina onda stavljamo id
        if (id != -1) {
            intent.putExtra(ID, id);
        }

        setResult(RESULT_OK, intent);
        finish();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ZAHTEV_ZA_BIRANE_SLIKE && resultCode == RESULT_OK) {
            selectedImageUri = data.getData();
            imageUriString = selectedImageUri.toString();
            slikaDela.setImageURI(selectedImageUri);

        }
    }

    private void prikaziPorukuOIzmenama(DialogInterface.OnClickListener discardButtonClickListener) {

        AlertDialog.Builder odbBuilder = new AlertDialog.Builder(this);
        odbBuilder.setMessage("Odbaci izmene i vrati se na pocetni ekran?");
        odbBuilder.setPositiveButton("Da", discardButtonClickListener);
        odbBuilder.setNegativeButton("Ne,nastavi sa izmenom dela", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialog != null) {
                    dialog.dismiss();
                }
                Toast.makeText(AddEditDeo.this, "Nastaviti sa izmenama", Toast.LENGTH_SHORT).show();
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = odbBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        if (!deoJePromenjen) {
            super.onBackPressed();
            return;
        }
        DialogInterface.OnClickListener discard = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        };

        prikaziPorukuOIzmenama(discard);
    }
}
