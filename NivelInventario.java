package uia.com.inventarios;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class NivelInventario extends PartidaInventario{

    //private HashMap<String, HashMap<String, PartidaInventario>> reporteNIvelInventario = new HashMap<String, HashMap<String, PartidaInventario>>();


    public NivelInventario() {
        super();
    }

    public InfoItem agrega(String idPartida, String descPartida, String idSubpartida, String descSubpartida, String idCat, String descCat, String cantidad, Lote lote)
    {
        InfoItem item = null;

        if(this.getItems().get(idPartida) != null) {
            if (this.getItems().get(idPartida).getItems().get(idSubpartida) != null)
            {
                if (this.getItems().get(idPartida).getItems().get(idSubpartida).getItems().get(idCat) == null)
                {
                    String ubicP = String.valueOf(Integer.parseInt(idPartida)/100);
                    char ubicS = (char) (65 + (Integer.parseInt(idSubpartida)/100)/10);
                    String ubicC = String.valueOf((Integer.parseInt(idCat)-Integer.parseInt(idSubpartida)));
                    CategoriaInventario cat = new CategoriaInventario("Categoria", idCat, descCat, "SinEstatus", cantidad, "SinPosicion");
                    this.getItems().get(idPartida).getItems().get(idSubpartida).getItems().put(idCat, cat);

                    for(int i=0; i<Integer.parseInt(cantidad); i++)
                    {
                        String ubic = ubicP+ubicS+ubicC+String.valueOf(i);
                        String idItem = idCat +  "-" + String.valueOf(i);
                        item = new InfoItem("Item", idItem, descCat, "SinEstatus", "1", ubic);
                        item.setLote(lote);
                        this.getItems().get(idPartida).getItems().get(idSubpartida).getItems().get(idCat).getItems().put(idItem, item);
                    }
                }
            } else {
                SubpartidaInventario subpartida = new SubpartidaInventario("Subpartida", idSubpartida, descSubpartida,  "SinEstatus", "", "");
                this.getItems().get(idPartida).getItems().put(idPartida, subpartida);
            }
        }
        else
        {
            PartidaInventario partida = new PartidaInventario("Partida", idPartida, descPartida, "", "SinEstatus", "");
            SubpartidaInventario subpartida = new SubpartidaInventario("Subpartida", idSubpartida, descSubpartida, "SinEstatus", "", "");

            String ubicP = String.valueOf(Integer.parseInt(idPartida)/100);
            char ubicS = (char) (65 + (Integer.parseInt(idSubpartida)/100)/10);
            String ubicC = String.valueOf((Integer.parseInt(idCat)-Integer.parseInt(idSubpartida)));
            this.getItems().put(idPartida, partida);
            partida.getItems().put(idSubpartida, subpartida);

            CategoriaInventario cat = new CategoriaInventario("Categoria", idCat, descCat, "SinEstatus", cantidad, "");
            this.getItems().get(idPartida).getItems().get(idSubpartida).getItems().put(idCat, cat);

            for(int i=0; i<Integer.parseInt(cantidad); i++)
            {
                String ubic = ubicP+ubicS+ubicC+String.valueOf(i);
                String idItem = idCat +  "-" + String.valueOf(i);
                item = new InfoItem("Item", idItem, descCat, "SinEstatus", "1", ubic);
                item.setLote(lote);
                this.getItems().get(idPartida).getItems().get(idSubpartida).getItems().get(idCat).getItems().put(idItem, item);
            }
        }

        return item;
    }

    public void serializa() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("ReporteNivelInventario-9.json"), this);
    }

    public List<InfoItem> busca(int id, String name, String descCat, String cantidad, String idPartida, String idSubpartida, String idCat)
    {
        if(this.getItems().get(idPartida) != null)
        {
            if (this.getItems().get(idPartida).getItems().get(idSubpartida) != null)
            {
                if (this.getItems().get(idPartida).getItems().get(idSubpartida).getItems().get(idCat) != null)
                {
                    CategoriaInventario cat = (CategoriaInventario) this.getItems().get(idPartida).getItems().get(idSubpartida).getItems().get(idCat);

                    if(Integer.parseInt(cat.getCantidad()) >= Integer.parseInt(cantidad))
                    {
                        return cat.getItems(cantidad);
                    }
                }
            }
        }
        return null;
    }

    public void print()
    {
        if (this.getItems() != null)
        {
            System.out.println("----- Items List -----");
            for (Map.Entry<String, InfoItem> item : getItems().entrySet())
            {
                PartidaInventario nodo = (PartidaInventario) item.getValue();
                nodo.print();
            }
        }
    }


}
