package com.example.token.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class CastUtils {

    private CastUtils() {
        super();
    }

    /**
     * Realiza a conversão de uma coleção genérica para uma lista da Classe do tipo
     * T passada por parâmetro.
     * 
     * @param <T>           - tipo genérico que representa a classe.
     * @param clazz         - classe do tipo T a ser utilizada na lista genérica.
     * @param rawCollection - coleção genérica a ser convertida.
     * @return lista do tipo T.
     */
    public static <T> List<T> castList(Class<? extends T> clazz, Collection<?> rawCollection) {
        List<T> result = new ArrayList<>(rawCollection.size());
        for (Object o : rawCollection) {
            try {
                result.add(clazz.cast(o));
            } catch (ClassCastException e) {
                System.out.println(e.getMessage());
            }
        }
        return result;
    }

}
