package com.santos.joao.targetproplem;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;

/**
 * Created by Joao on 18/08/2015.
 */
public class Constants {
    public static final String BATIDA = "Batida";
    public static final String PASSEATA = "Passeata";
    public static final String DESVIO = "Desvio";
    public static final String BURACO = "Buraco";
    public static final String NONE = null;
}

class Tuple{
    public String name;

    public Tuple(){
        this.name = Constants.NONE;
    }

    public float getColorAviso() {
        switch (this.name) {
            case Constants.BATIDA: return BitmapDescriptorFactory.HUE_RED;
            case Constants.PASSEATA: return BitmapDescriptorFactory.HUE_YELLOW;
            case Constants.DESVIO: return BitmapDescriptorFactory.HUE_ORANGE;
            case Constants.BURACO: return BitmapDescriptorFactory.HUE_MAGENTA;
        }
        return -1;
    }

}
