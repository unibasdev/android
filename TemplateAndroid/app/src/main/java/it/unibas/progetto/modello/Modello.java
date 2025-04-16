package it.unibas.progetto.modello;

import java.util.HashMap;
import java.util.Map;

public class Modello {

    private final Map<EBean, Object> mappaBean = new HashMap<>();

    public void putBean(EBean nome, Object bean) {
        this.mappaBean.put(nome, bean);
    }

    public Object getBean(EBean nome) {
        return this.mappaBean.get(nome);
    }

}
