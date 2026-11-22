package br.com.omniroute.model;
import java.nio.file.*;import java.util.*;
public class ExportadorTXT implements Exportador {
 public void exportar(List<Encomenda> dados,String arquivo)throws Exception{
  StringBuilder b=new StringBuilder("OMNIRoute - Encomendas\n");
  for(Encomenda e:dados)b.append(e.getId()).append(" | ").append(e.getCliente()).append(" | ").append(e.getEndereco()).append(", ").append(e.getNumero()).append(" | ").append(e.getCidade()).append(" | ").append(e.getStatus()).append("\n");
  Files.writeString(Path.of(arquivo),b.toString());
 }
}
