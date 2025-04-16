package it.unibas.mediapesataandroid.controllo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import it.unibas.mediapesataandroid.Applicazione;
import it.unibas.mediapesataandroid.Costanti;
import it.unibas.mediapesataandroid.R;
import it.unibas.mediapesataandroid.activity.ActivityPrincipale;
import it.unibas.mediapesataandroid.modello.EBean;
import it.unibas.mediapesataandroid.modello.Studente;

public class ControlloMenu {

    private String TAG  = "ControlloMenu";

    private final MenuItem.OnMenuItemClickListener azioneInformazioni = new AzioneInformazioni();
    private final MenuItem.OnMenuItemClickListener azioneModificaDatiStudente = new AzioneModificaDatiStudente();
    private final MenuItem.OnMenuItemClickListener azioneScegliFileImporta = new AzioneScegliFileImporta();
    private final MenuItem.OnMenuItemClickListener azioneScegliFileEsporta = new AzioneScegliFileEsporta();
    private final MenuItem.OnMenuItemClickListener azioneImportaExternalStorage = new AzioneImportaExternalStorage();
    private final MenuItem.OnMenuItemClickListener azioneEsportaExternalStorage = new AzioneEsportaExternalStorage();

    public MenuItem.OnMenuItemClickListener getAzioneModificaDatiStudente() {
        return azioneModificaDatiStudente;
    }

    public MenuItem.OnMenuItemClickListener getAzioneScegliFileImporta() {
        return azioneScegliFileImporta;
    }

    public MenuItem.OnMenuItemClickListener getAzioneScegliFileEsporta() {
        return azioneScegliFileEsporta;
    }

    public MenuItem.OnMenuItemClickListener getAzioneImportaExternalStorage() {
        return azioneImportaExternalStorage;
    }

    public MenuItem.OnMenuItemClickListener getAzioneEsportaExternalStorage() {
        return azioneEsportaExternalStorage;
    }

    public MenuItem.OnMenuItemClickListener getAzioneInformazioni() {
        return azioneInformazioni;
    }


    ///////////////////////////////////////////////////////////////////////////////////
    private class AzioneInformazioni implements MenuItem.OnMenuItemClickListener {

        public final String TAG = AzioneInformazioni.class.getSimpleName();

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            ActivityPrincipale activityPrincipale = (ActivityPrincipale) Applicazione.getInstance().getCurrentActivity();
            activityPrincipale.mostraImpostazioni();
            return true;
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////
    private class AzioneModificaDatiStudente implements MenuItem.OnMenuItemClickListener {

        public final String TAG = AzioneModificaDatiStudente.class.getSimpleName();

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            ActivityPrincipale activityPrincipale = (ActivityPrincipale) Applicazione.getInstance().getCurrentActivity();
            activityPrincipale.schermoFormStudente();
            return true;
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////
    private class AzioneScegliFileImporta implements MenuItem.OnMenuItemClickListener {

        public final String TAG = AzioneScegliFileImporta.class.getSimpleName();

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            ActivityPrincipale activityPrincipale = (ActivityPrincipale) Applicazione.getInstance().getCurrentActivity();
            activityPrincipale.finestraApriFile();
            return true;
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////
    private class AzioneScegliFileEsporta implements MenuItem.OnMenuItemClickListener {

        public final String TAG = AzioneScegliFileEsporta.class.getSimpleName();

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            ActivityPrincipale activityPrincipale = (ActivityPrincipale) Applicazione.getInstance().getCurrentActivity();
            activityPrincipale.finestraSalvaFile();
            return true;
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////

    public void azioneEsporta(Uri fileSelezionato) {
        Studente studente = (Studente) Applicazione.getInstance().getModello().getPersistentBean(EBean.STUDENTE, Studente.class);
        OutputStream os = null;
        try {
            os = Applicazione.getInstance().getContentResolver().openOutputStream(fileSelezionato);
            Applicazione.getInstance().getDaoStudente().salva(studente, os);
        } catch (Exception e) {
            Log.e("Azione importa", "Impossibile importare lo studente", e);
            e.printStackTrace();
            String errore = Applicazione.getInstance().getString(R.string.StringaImpossibileSalvare) + ".\n" + e.getLocalizedMessage();
            Toast.makeText(Applicazione.getInstance(), errore, Toast.LENGTH_LONG).show();
//            ActivityPrincipale activityPrincipale = (ActivityPrincipale) Applicazione.getInstance().getCurrentActivity();
//            activityPrincipale.finestraErrore(errore);
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////
    public void azioneImporta(Uri fileSelezionato) {
        InputStream is = null;
        try {
            is = Applicazione.getInstance().getContentResolver().openInputStream(fileSelezionato);
            Studente studente = (Studente) Applicazione.getInstance().getDaoStudente().carica(is, Studente.class);
            Applicazione.getInstance().getModello().saveBean(EBean.STUDENTE, studente);
        } catch (Exception e) {
            Log.e("Azione importa", "Impossibile esportare lo studente", e);
            e.printStackTrace();
            String errore = Applicazione.getInstance().getString(R.string.StringaImpossibileCaricare) + ".\n" + e.getLocalizedMessage();
            Toast.makeText(Applicazione.getInstance(), errore, Toast.LENGTH_LONG).show();
//            ActivityPrincipale activityPrincipale = (ActivityPrincipale) Applicazione.getInstance().getCurrentActivity();
//            activityPrincipale.finestraErrore(errore);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////
    private class AzioneImportaExternalStorage implements MenuItem.OnMenuItemClickListener {

        public final String TAG = AzioneScegliFileImporta.class.getSimpleName();

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (ContextCompat.checkSelfPermission(Applicazione.getInstance().getCurrentActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)   != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Applicazione.getInstance().getCurrentActivity(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},Costanti.RICHIESTA_PERMESSO_IMPORT);
            }else {
                importaExternalStorage();
            }
            return true;
        }
    }

    public void importaExternalStorage(){
        try {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/studente_export.json";
            Studente studente = (Studente) Applicazione.getInstance().getDaoStudente().carica(new FileInputStream(path), Studente.class);
            Applicazione.getInstance().getModello().saveBean(EBean.STUDENTE, studente);
            Toast.makeText(Applicazione.getInstance(), R.string.StringaImportSuccesso, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.e("Azione importa", "Impossibile esportare lo studente", e);
            e.printStackTrace();
            String errore = Applicazione.getInstance().getString(R.string.StringaImpossibileSalvare) + ".\n" + e.getLocalizedMessage();
            Toast.makeText(Applicazione.getInstance(), errore, Toast.LENGTH_LONG).show();
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////
    private class AzioneEsportaExternalStorage implements MenuItem.OnMenuItemClickListener {

        public final String TAG = AzioneScegliFileEsporta.class.getSimpleName();

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (ContextCompat.checkSelfPermission(Applicazione.getInstance(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Applicazione.getInstance().getCurrentActivity(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        Costanti.RICHIESTA_PERMESSO_EXPORT);
            }else {
                esportaExternalStorage();
            }
            return true;
        }
    }

    public void esportaExternalStorage(){
        try {
            Studente studente = (Studente) Applicazione.getInstance().getModello().getPersistentBean(EBean.STUDENTE, Studente.class);
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/studente_export.json";
            Log.d(TAG, "Salvo lo studente nel percorso " + path);
            Applicazione.getInstance().getDaoStudente().salva(studente, new FileOutputStream(path));
            Toast.makeText(Applicazione.getInstance(), R.string.StringaExportSuccesso, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.e("Azione importa", "Impossibile esportare lo studente", e);
            e.printStackTrace();
            String errore = Applicazione.getInstance().getString(R.string.StringaImpossibileSalvare) + ".\n" + e.getLocalizedMessage();
            Toast.makeText(Applicazione.getInstance(), errore, Toast.LENGTH_LONG).show();
        }
    }
}
