package br.com.fogliato.view.utils;

import java.util.ResourceBundle;

public class BundleUtils {

    public String getDescricaoEnum(Enum<?> enumObject) {
        ResourceBundle labels = ResourceBundle.getBundle("br.com.fogliato.view.bundle.bundle");
        return labels.getString("enum.".concat(enumObject.toString()));
    }
    
    public String getDescricaoEnum(String tipo) {
        ResourceBundle labels = ResourceBundle.getBundle("br.com.fogliato.view.bundle.bundle");
        return labels.getString("enum.".concat(tipo));
    }
}
