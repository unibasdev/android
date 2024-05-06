package it.unibas.mediapesataclient.controllo;

import android.util.Log;
import android.view.View;

import it.unibas.mediapesataclient.Applicazione;
import it.unibas.mediapesataclient.activity.ActivityLogin;
import it.unibas.mediapesataclient.modello.dto.RichiestaLogin;
import it.unibas.mediapesataclient.vista.VistaLogin;

public class ControlloLogin {

    private String TAG = ControlloLogin.class.getSimpleName();
    private View.OnClickListener azioneLogin = new AzioneLogin();

    public View.OnClickListener getAzioneLogin() {
        return azioneLogin;
    }

    private class AzioneLogin implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            ActivityLogin activityLogin = (ActivityLogin) Applicazione.getInstance().getCurrentActivity();
            VistaLogin vistaLogin = activityLogin.getVistaLogin();
            boolean errori = convalida(vistaLogin);
            if(errori){
                return;
            }
            String username = vistaLogin.getCampoEmail().getText().toString();
            String password = vistaLogin.getCampoPassword().getText().toString();
            Log.d(TAG, "Effettuo login - Username: " + username + " - Password: " + password);
            new AsyncTaskLogin(new RichiestaLogin(username, password)).execute();
        }

        private boolean convalida(VistaLogin vistaLogin) {
            //TODO
            return false;
        }
    }
}
