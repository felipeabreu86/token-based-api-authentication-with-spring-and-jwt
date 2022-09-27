package com.example.token.util;

import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ObjectNode;

public final class JsonUtil {

    private JsonUtil() {
        super();
    }

    /**
     * O ObjectMapper fornece funcionalidades para o mapeamento entre objetos
     * complexos e textos no formato Json
     */
    private static ObjectMapper mapper;
    static {
        mapper = new ObjectMapper();
    }

    /**
     * Verifica se o texto recebido por parâmetro apresenta um formato Json válido
     * 
     * @param json - String contendo um texto em formato json
     * @return true se é um formato Json válido ou false se for inválido
     */
    public static boolean isValidJson(String json) {
        
        boolean valid = false;
        
        if (json != null && !json.trim().isEmpty()) {
            try {
                mapper.readTree(json);
                valid = true;
            } catch (JsonProcessingException e) { }
        }
        
        return valid;
    }

    /**
     * Verifica se a String recebida por parâmetro está em um formato Array Json
     * válido, por exemplo, [{ "attribute" : "value" }]
     * 
     * @param json - String contendo um texto em formato json
     * @return true se for um formato válido de Array Json ou false se for inválido
     */
    public static boolean isJsonArray(String json) {
        
        boolean valid = false;
        
        try {
            if (json != null && !json.trim().isEmpty()) {
                JsonNode jsonNode = mapper.readTree(json);
                if (jsonNode.isArray()) {
                    valid = true;
                }
            }
        } catch (JsonProcessingException e) { }
        
        return valid;
    }

    /**
     * Converte o objeto Java recebido por parâmetro para o formato String Json
     * 
     * @param obj - Objeto Java
     * @return Opcional que pode conter o Objeto convertido para o formato String
     *         Json ou nulo em caso de erro ou de não possibilidade de conversão
     */
    public static Optional<String> toJson(Object obj) {
        
        String json = null;
        
        if (obj != null) {
            try {
                ObjectWriter ow = mapper.writer();
                json = ow.writeValueAsString(obj);
            } catch (JsonProcessingException e) { }
        }
        
        return Optional.ofNullable(json);
    }

    /**
     * Converte um texto no formato Json recebido por parâmetro em um objeto java do
     * tipo <T>
     * 
     * @param <T>       - Tipo genérico
     * @param json      - String contendo um texto em formato json
     * @param classType - Classe do tipo <T>
     * @return referência a um objeto <T> instanciado do conteúdo json ou null
     */
    public static <T> Optional<T> readValue(String json, Class<T> classType) {
        
        T response = null;
        
        try {
            if (json != null && !json.trim().isEmpty()) {
                response = mapper.readValue(json, classType);
            }
        } catch (JsonProcessingException e) { 
            System.out.println(e.getMessage());
        }
        
        return Optional.ofNullable(response);
    }

    /**
     * Converte um texto no formato Json recebido por parâmetro em um objeto java
     * Array do tipo <T>
     * 
     * @param <T>       - Tipo genérico
     * @param json      - String contendo um texto em formato json
     * @param classType - Classe do tipo <T>
     * @return referência a um objeto <T>[] instanciado do conteúdo json ou null
     */
    public static <T> Optional<T[]> readValues(String json, Class<T[]> classType) {
        
        T[] response = null;
        
        try {
            if (json != null && !json.trim().isEmpty()) {
                response = mapper.readValue(json, classType);
            }
        } catch (JsonProcessingException e) { }
        
        return Optional.ofNullable(response);
    }

    /**
     * Este método busca em uma String no formato Json a chave passada por parâmetro
     * e retorna o seu valor associado ou null
     * 
     * @param json - String contendo um texto em formato json
     * @param key  - chave do elemento
     * @return valor do elemento associado à chave
     */
    public static Optional<String> readValueByKey(String json, String key) {
        
        String elementValue = null;
        final Optional<ObjectNode> node = JsonUtil.readValue(json, ObjectNode.class);
        
        if (node.isPresent()) {
            ObjectNode objNode = node.get();
            
            if (objNode.has(key)) {
                elementValue = objNode.get(key).asText();
            }
        }
        
        return Optional.ofNullable(elementValue);
    }

}
