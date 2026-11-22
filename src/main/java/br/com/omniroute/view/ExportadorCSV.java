
package br.com.omniroute.view;

import br.com.omniroute.model.Encomenda;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ExportadorCSV {

    public void exportar(List<Encomenda> lista, String caminho)
            throws IOException {

        try (FileWriter arquivo = new FileWriter(caminho)) {

            arquivo.write("ID;Cliente;Endereco;Numero;Bairro;Cidade;CEP;UF;Status\n");

            for (Encomenda e : lista) {

                arquivo.write(
                        e.getId() + ";"
                        + limpar(e.getCliente()) + ";"
                        + limpar(e.getEndereco()) + ";"
                        + limpar(e.getNumero()) + ";"
                        + limpar(e.getBairro()) + ";"
                        + limpar(e.getCidade()) + ";"
                        + limpar(e.getCep()) + ";"
                        + limpar(e.getUf()) + ";"
                        + limpar(e.getStatus()) + "\n"
                );
            }
        }
    }

    private String limpar(String texto) {
        if (texto == null) {
            return "";
        }

        return texto.replace(";", ",")
                    .replace("\n", " ")
                    .replace("\r", " ");
    }
}
