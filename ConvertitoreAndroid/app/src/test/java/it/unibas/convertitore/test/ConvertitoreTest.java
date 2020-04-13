package it.unibas.convertitore.test;

import junit.framework.*;

import it.unibas.convertitore.modello.Convertitore;

public class ConvertitoreTest extends TestCase {

    private Convertitore convertitore;

    public void setUp(){
        this.convertitore = new Convertitore();
    }

    public void test110(){
        assertEquals(110.0, convertitore.convertiInVotoLaurea(30, 110));
        assertEquals(66.0, convertitore.convertiInVotoLaurea(18, 110));
        assertEquals(102.66, convertitore.convertiInVotoLaurea(28, 110), 0.01);
    }

    public void test100(){
        assertEquals(100.0, convertitore.convertiInVotoLaurea(30, 100));
        assertEquals(60.0, convertitore.convertiInVotoLaurea(18, 100));
        assertEquals(93.33, convertitore.convertiInVotoLaurea(28, 100), 0.01);
    }

    public void test30(){
        assertEquals(30.0, convertitore.convertiIn30mi(110, 110));
        assertEquals(18.0, convertitore.convertiIn30mi(66, 110));
        assertEquals(27.81, convertitore.convertiIn30mi(102, 110), 0.01);
    }

}
