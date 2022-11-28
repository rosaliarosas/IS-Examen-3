package uia.com.inventarios;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SolicitudSalidaMaterial extends RegistroItem
{

    public SolicitudSalidaMaterial(RegistroItem sm) throws IOException {
        super();
    }

    public SolicitudSalidaMaterial(int id, String name, String categoria, String cantidad, String idPartida, String idSubpartida, String idCategoria)
    {
        super(id, name, categoria, cantidad, idPartida, idSubpartida, idCategoria);
    }

    public void serializa(String nombre) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(nombre), this);
    }


}