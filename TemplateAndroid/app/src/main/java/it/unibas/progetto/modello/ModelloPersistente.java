package it.unibas.progetto.modello;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import it.unibas.progetto.Applicazione;
import it.unibas.progetto.persistenza.DAOException;
import it.unibas.progetto.persistenza.DAOGenericoJson;

public class ModelloPersistente {

    private final static String TAG = ModelloPersistente.class.getSimpleName();
    private final DAOGenericoJson daoGenericoJson = new DAOGenericoJson();
    private final Map<String, Object> cache = new HashMap<String, Object>();

    public void saveBean(String key, Object bean) {
        this.cache.put(key, bean);
        save(key, bean);
    }

    public Object getPersistentBean(String key, Class type) {
        Object cachedObject = this.cache.get(key);
        if (cachedObject != null) {
            return cachedObject;
        }
        Object persistentObject = load(key, type);
        if (persistentObject == null) {
            return null;
        }
        cache.put(key, persistentObject);
        return persistentObject;
    }

    private Object load(String key, Class type) {
        File file = new File(Applicazione.getInstance().getFilesDir(), getFileName(key));
        if (!file.exists()) {
            return null;
        }
        InputStream in = null;
        try {
            in = new FileInputStream(file);
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

    private void save(String key, Object bean) {
        File file = new File(Applicazione.getInstance().getFilesDir(), getFileName(key));
        try {
            daoGenericoJson.salva(bean, new FileOutputStream(file));
        } catch (Exception e) {
            Log.e(TAG, e.getLocalizedMessage());
            throw new DAOException(e.getLocalizedMessage());
        }
    }

    private String getFileName(String key) {
        return key + ".json";
    }
}