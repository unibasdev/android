package it.unibas.convertitore.modello;

import java.util.HashMap;
import java.util.Map;

public class Modello {

	//Notice: in this application the class Modello could easily be avoided altogether.
	//        we are using it anyways to show the standard way of organizing code for MVC
	//        even in this very simple example

    private final Map<String, Object> mappaBean = new HashMap<String, Object>();

    public void putBean(String nome, Object bean) {
        this.mappaBean.put(nome, bean);
    }

    public Object getBean(String nome) {
        return this.mappaBean.get(nome);
    }

}
