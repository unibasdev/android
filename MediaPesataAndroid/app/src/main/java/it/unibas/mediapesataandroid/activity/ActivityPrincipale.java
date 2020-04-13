package it.unibas.mediapesataandroid.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import it.unibas.mediapesataandroid.Applicazione;
import it.unibas.mediapesataandroid.Costanti;
import it.unibas.mediapesataandroid.R;
import it.unibas.mediapesataandroid.modello.ModelloPersistente;
import it.unibas.mediapesataandroid.modello.Studente;
import it.unibas.mediapesataandroid.vista.VistaEsami;
import it.unibas.mediapesataandroid.vista.VistaStudente;

public class ActivityPrincipale extends AppCompatActivity {

    public static final String TAG = "ActivityPrincipale";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_principale);
        this.azioneIniziale();
    }

    private void azioneIniziale() {
        ModelloPersistente modello = Applicazione.getInstance().getModello();
        Studente studente = (Studente) modello.getPersistentBean(Costanti.STUDENTE, Studente.class);
        if (studente == null) {
            modello.saveBean(Costanti.STUDENTE, new Studente());
            schermoFormStudente();
        }
    }

    public VistaStudente getVistaStudente() {
        return (VistaStudente) this.getSupportFragmentManager().findFragmentById(R.id.vistaStudente);
    }

    public VistaEsami getVistaEsami() {
        return (VistaEsami) this.getSupportFragmentManager().findFragmentById(R.id.vistaEsami);
    }

    ////////////////////////// MENU ////////////////////////////

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principale, menu);
        MenuItem menuItemModifica = menu.findItem(R.id.menu_modifica_studente);
        menuItemModifica.setOnMenuItemClickListener(Applicazione.getInstance().getControlloMenu().getAzioneModificaDatiStudente());
        MenuItem menuImporta = menu.findItem(R.id.menu_importa);
        menuImporta.setOnMenuItemClickListener(Applicazione.getInstance().getControlloMenu().getAzioneScegliFileImporta());
        MenuItem menuEsporta = menu.findItem(R.id.menu_esporta);
        menuEsporta.setOnMenuItemClickListener(Applicazione.getInstance().getControlloMenu().getAzioneScegliFileEsporta());
        MenuItem menuItemInformazioni = menu.findItem(R.id.menu_informazioni);
        menuItemInformazioni.setOnMenuItemClickListener(Applicazione.getInstance().getControlloMenu().getAzioneInformazioni());
        return true;
    }

    ////////////////////////// SCHERMI ////////////////////////////

    public void schermoFormStudente() {
        Intent intent = new Intent(getApplicationContext(), ActivityFormStudente.class);
        startActivity(intent);
    }

    public void schermoFormEsame() {
        Intent intent = new Intent(getApplicationContext(), ActivityFormEsame.class);
        startActivity(intent);
    }

    public void finestraErrore(String errore) {
        mostraMessaggio(errore, getString(R.string.StringaErrore));
    }

    private void mostraMessaggio(String messaggio, String titolo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(titolo);
        builder.setMessage(messaggio);
        builder.setPositiveButton(getString(R.string.StringaOk), null);
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void finestraOperazioniEsame() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(new String[]{getString(R.string.StringaEliminaEsame)}, Applicazione.getInstance().getControlloPrincipale().getAzioneEseguiOperazioneEsame());
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void finestraConfermaEliminazione(DialogInterface.OnClickListener azioneOk) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.StringaEliminazioneEsame));
        builder.setMessage(getString(R.string.StringaVuoiEliminareEsame));
        builder.setPositiveButton(getString(R.string.StringaOk), azioneOk);
        builder.setNegativeButton(getString(R.string.StringaAnnulla), null);
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void finestraApriFile() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
//        intent.setType("text/plain");
        try {
            startActivityForResult(intent, Costanti.AZIONE_APRI);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, getString(R.string.StringaInstallareFileManager), Toast.LENGTH_LONG).show();
        }
    }

    public void finestraSalvaFile() {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.putExtra(Intent.EXTRA_TITLE, "studente");
        intent.setType("text/plain");
        try {
            startActivityForResult(intent, Costanti.AZIONE_SALVA);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, getString(R.string.StringaInstallareFileManager), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Costanti.AZIONE_APRI) {
            if (resultCode == RESULT_OK && data != null) {
                Uri fileSelezionato = data.getData();
                Applicazione.getInstance().getControlloMenu().azioneImporta(fileSelezionato);
            }
        }
        if (requestCode == Costanti.AZIONE_SALVA) {
            if (resultCode == RESULT_OK && data != null) {
                Uri fileSelezionato = data.getData();
                Applicazione.getInstance().getControlloMenu().azioneEsporta(fileSelezionato);
            }
        }
    }

    public void mostraImpostazioni() {
        startActivity(new Intent(this, ActivityImpostazioni.class));
    }
}
