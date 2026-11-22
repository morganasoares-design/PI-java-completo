package br.com.omniroute.model;
import java.util.List;
public interface Exportador { void exportar(List<Encomenda> dados,String arquivo) throws Exception; }
