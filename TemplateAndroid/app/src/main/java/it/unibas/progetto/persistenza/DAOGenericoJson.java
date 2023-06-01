package it.unibas.progetto.persistenza;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@SuppressWarnings("unchecked")
public class DAOGenericoJson {

    private static String TAG = "DAOGenerico";
    private String datePatternFormat = "dd-MM-yyyy HH:mm:ss";

    /* ******************************************
     *               Conversione
     * ****************************************** */
    public String toJson(Object oggetto){
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Date.class, new AdapterDate());
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        return gson.toJson(oggetto);
    }

    /* ******************************************
     *               Caricamento
     * ****************************************** */
    public <T> T carica(InputStream inputStream, Class<T> t) throws DAOException {
        Reader flusso = null;
        try {
            flusso = new InputStreamReader(inputStream);
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(Date.class, new AdapterDate());
            Gson gson = builder.create();
            return gson.fromJson(flusso, t);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException(e);
        } finally {
            try {
                if (flusso != null) {
                    flusso.close();
                }
            } catch (java.io.IOException ioe) {
            }
        }
    }

    public <T> List<T> caricaLista(InputStream inputStream, Class<T> t) throws DAOException {
        Reader flusso = null;
        try {
            flusso = new InputStreamReader(inputStream);
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(Date.class, new AdapterDate());
            Gson gson = builder.create();
            Type typeOfT = TypeToken.getParameterized(List.class, t).getType();
            return gson.fromJson(flusso, typeOfT);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException(e);
        } finally {
            try {
                if (flusso != null) {
                    flusso.close();
                }
            } catch (java.io.IOException ioe) {
            }
        }
    }



    /* ******************************************
     *               Salvataggio
     * ****************************************** */
    public void salva(Object oggetto, OutputStream out) throws DAOException {
        PrintWriter flusso = null;
        try {
            flusso = new java.io.PrintWriter(out);
            String stringaJson = toJson(oggetto);
            flusso.print(stringaJson);
        } catch (Exception ioe) {
            throw new DAOException(ioe);
        } finally {
            if (flusso != null) {
                flusso.close();
            }
        }

    }

    private class AdapterDate implements JsonSerializer<Date>, JsonDeserializer<Date> {

        public JsonElement serialize(Date date, Type tipo, JsonSerializationContext context) {
            DateFormat dateFormat = new SimpleDateFormat(datePatternFormat);
            return new JsonPrimitive(dateFormat.format(date.getTime()));
        }

        public Date deserialize(JsonElement json, Type tipo, JsonDeserializationContext context) throws JsonParseException {
            try {
                String stringaData = json.getAsString();
                DateFormat dateFormat = new SimpleDateFormat(datePatternFormat);
                Date dataRegistrazione = dateFormat.parse(stringaData);
                GregorianCalendar calendar = new GregorianCalendar();
                calendar.setTime(dataRegistrazione);
                return calendar.getTime();
            } catch (ParseException ex) {
                throw new JsonParseException(ex);
            }
        }
    }

    public String getDatePatternFormat() {
        return datePatternFormat;
    }

    public void setDatePatternFormat(String datePatternFormat) {
        this.datePatternFormat = datePatternFormat;
    }
}
