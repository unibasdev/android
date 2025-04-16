package it.unibas.progetto.modello;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import it.unibas.progetto.Applicazione;
import it.unibas.progetto.persistenza.DAOException;
import it.unibas.progetto.persistenza.DAOGenericoJson;

@SuppressWarnings("unchecked")
public class ModelloPersistente {

    private final static String TAG = ModelloPersistente.class.getSimpleName();
    private final DAOGenericoJson daoGenericoJson = new DAOGenericoJson();
    private final Map<EBean, Object> cache = new HashMap<>();

    public void saveBean(EBean key, Object bean) {
        this.cache.put(key, bean);
        save(key, bean);
    }

    public <T> T getPersistentBean(EBean key, Class<T> type) {
        T cachedObject = (T) this.cache.get(key);
        if (cachedObject != null) {
            return cachedObject;
        }
        T persistentObject = (T) load(key, type);
        if (persistentObject == null) {
            return null;
        }
        cache.put(key, persistentObject);
        return persistentObject;
    }

    private <T> T load(EBean key, Class<T> type) {
        File file = new File(Applicazione.getInstance().getFilesDir(), getFileName(key));
        if (!file.exists()) {
            return null;
        }
        InputStream in = null;
        try {
            in = Files.newInputStream(file.toPath());
            return daoGenericoJson.carica(in, type);
        } catch (Exception e) {
            Log.e(TAG, e.getLocalizedMessage());
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }
        return null;
    }

    private void save(EBean key, Object bean) {
        File file = new File(Applicazione.getInstance().getFilesDir(), getFileName(key));
        try {
            daoGenericoJson.salva(bean, Files.newOutputStream(file.toPath()));
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            throw new DAOException(e.getLocalizedMessage());
        }
    }

    private String getFileName(EBean key) {
        return key.toString() + ".json";
    }
}