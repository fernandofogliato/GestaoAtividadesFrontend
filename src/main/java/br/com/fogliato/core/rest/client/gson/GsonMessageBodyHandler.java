package br.com.fogliato.core.rest.client.gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.fogliato.core.rest.client.gson.adapter.LocalDateAdapter;
import br.com.fogliato.core.rest.client.gson.adapter.LocalDateTimeAdapter;

/**
 * 
 * Provedor utilizado pelo RestClient para conversão de objetos para JSON e vice-versa. 
 * Sobrescreve o Jackson utilizado por padrão.
 * 
 * @author Fernando Fogliato
 *
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GsonMessageBodyHandler implements MessageBodyWriter<Object>, MessageBodyReader<Object> {

    private static final String UTF_8 = "UTF-8";
    private Gson gson;
    
    private void criarGson() {
    	
 		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
				   .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
		gsonBuilder.setPrettyPrinting();

		this.gson = gsonBuilder.serializeNulls().create();
    }

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return true;
    }

    @Override
    public long getSize(Object t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return -1;
    }

    @Override
    public void writeTo(Object object, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
            MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
            throws IOException, WebApplicationException {
    	
        OutputStreamWriter writer = new OutputStreamWriter(entityStream, UTF_8);

        try {
            Type jsonType;
            if(type.equals(genericType) || genericType == null) 
                jsonType = type;
            else 
                jsonType = genericType;
            
            if (gson == null)
            	criarGson();
            gson.toJson(object, jsonType, writer);
        } finally {
            writer.close();
        }
    }

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return true;
    }

    @Override
    public Object readFrom(Class<Object> type, Type genericType, Annotation[] annotations, MediaType mediaType,
            MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
            throws IOException, WebApplicationException {
    	
        InputStreamReader streamReader = new InputStreamReader(entityStream, UTF_8);

        try {
            Type jsonType;
            if (type.equals(genericType))
                jsonType = type;
            else
                jsonType = genericType;
            
            if (gson == null)
            	criarGson();
            return gson.fromJson(streamReader, jsonType);
        } finally {
            streamReader.close();
        }
    }

}
