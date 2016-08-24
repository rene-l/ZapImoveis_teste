package com.rliossi.apps.zapimoveis_teste;

import com.rliossi.apps.zapimoveis_teste.model.Anuncio;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Rene on 22/08/2016.
 */
public class Testes {

    @Test
    public void verificaSortingPadraoPorPreco(){
        Anuncio anuncio1 = new Anuncio();
        anuncio1.setPreco(100d);

        Anuncio anuncio2 = new Anuncio();
        anuncio2.setPreco(150d);

        Anuncio anuncio3 = new Anuncio();
        anuncio3.setPreco(130d);

        List<Anuncio> anuncios = new ArrayList<>();
        anuncios.add(anuncio2);//150d (0)
        anuncios.add(anuncio3);//130d (1)
        anuncios.add(anuncio1);//100d (2)

        Collections.sort(anuncios);

        assertEquals(100d, anuncios.get(0).getPreco());
        assertEquals(130d, anuncios.get(1).getPreco());
        assertEquals(150d, anuncios.get(2).getPreco());
    }
}
