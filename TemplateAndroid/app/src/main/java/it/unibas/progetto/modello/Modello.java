package it.unibas.progetto.modello;

import java.util.HashMap;
import java.util.Map;

public class Modello {

    private final Map<String, Object> mappaBean = new HashMap<String, Object>();

    public void putBean(String nome, Object bean) {
        this.mappaBean.put(nome, bean);
    }

    public Object getBean(String nome) {
        return this.mappaBean.get(nome);
    }

}
