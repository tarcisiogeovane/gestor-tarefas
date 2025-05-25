package br.com.gestortarefas.util;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@FacesConverter (value="localDateConverter", managed=true)
public class LocalDateConverter implements Converter <LocalDate>{
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    @Override
    public LocalDate getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        return LocalDate.parse(s, DATE_FORMAT);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, LocalDate localDate) {
        return localDate.format(DATE_FORMAT);
    }
}
