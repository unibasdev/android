package it.unibas.mediapesataclient.vista;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import it.unibas.mediapesataclient.Applicazione;
import it.unibas.mediapesataclient.R;

public class VistaLogin extends Fragment {
    private ProgressDialog progressDialog;

    private EditText campoEmail;
    private EditText campoPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.vista_login, container, false);
        this.campoEmail = vista.findViewById(R.id.campoEmail);
        this.campoPassword = vista.findViewById(R.id.campoPassword);
        Button bottoneLogin = vista.findViewById(R.id.bottoneLogin);
        bottoneLogin.setOnClickListener(Applicazione.getInstance().getControlloLogin().getAzioneLogin());
        return vista;
    }

    public EditText getCampoEmail() {
        return campoEmail;
    }

    public EditText getCampoPassword() {
        return campoPassword;
    }

    public void mostraFinestraCaricamento() {
        progressDialog = ProgressDialog.show(Applicazione.getInstance().getCurrentActivity(), "Caricamento", "Caricamento in corso. Attendi...", true);
        progressDialog.show();
    }

    public void chiudiFinestraCaricamento() {
        progressDialog.dismiss();
    }
}
