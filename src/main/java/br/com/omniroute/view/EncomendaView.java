
package br.com.omniroute.view;

import br.com.omniroute.model.Encomenda;
import br.com.omniroute.model.ExportadorCSV;
import br.com.omniroute.model.dao.EncomendaDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.List;

public class EncomendaView extends JFrame {

    private final EncomendaDAO dao = new EncomendaDAO();

    private final JTextField cliente = new JTextField();
    private final JTextField endereco = new JTextField();
    private final JTextField numero = new JTextField();
    private final JTextField bairro = new JTextField();
    private final JTextField cidade = new JTextField();
    private final JTextField cep = new JTextField();
    private final JTextField uf = new JTextField();

    private final JComboBox<String> status = new JComboBox<>(
            new String[]{"Pendente", "Entregue", "Não Entregue"}
    );

    private final DefaultTableModel modelo = new DefaultTableModel(
            new String[]{
                "ID", "Cliente", "Endereço", "Número",
                "Bairro", "Cidade", "CEP", "UF", "Status"
            }, 0) {

        @Override
        public boolean isCellEditable(int linha, int coluna) {
            return false;
        }
    };

    private final JTable tabela = new JTable(modelo);

    private int id = 0;

    public EncomendaView() {

        setTitle("OMNIRoute - Cadastro de Encomendas");
        setSize(1100, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel formulario = new JPanel(new GridLayout(8, 2, 6, 6));

        formulario.setBorder(
                BorderFactory.createTitledBorder("Dados da encomenda")
        );

        formulario.add(new JLabel("Cliente"));
        formulario.add(cliente);

        formulario.add(new JLabel("Endereço"));
        formulario.add(endereco);

        formulario.add(new JLabel("Número"));
        formulario.add(numero);

        formulario.add(new JLabel("Bairro"));
        formulario.add(bairro);

        formulario.add(new JLabel("Cidade"));
        formulario.add(cidade);

        formulario.add(new JLabel("CEP"));
        formulario.add(cep);

        formulario.add(new JLabel("UF"));
        formulario.add(uf);

        formulario.add(new JLabel("Status"));
        formulario.add(status);

        JButton salvar = new JButton("Salvar / Atualizar");
        JButton novo = new JButton("Novo");
        JButton excluir = new JButton("Excluir");
        JButton exportar = new JButton("Exportar CSV");

        JPanel botoes = new JPanel();

        botoes.add(salvar);
        botoes.add(novo);
        botoes.add(excluir);
        botoes.add(exportar);

        tabela.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        tabela.getSelectionModel().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()
                    && tabela.getSelectedRow() >= 0) {

                int linha = tabela.convertRowIndexToModel(
                        tabela.getSelectedRow()
                );

                id = Integer.parseInt(
                        modelo.getValueAt(linha, 0).toString()
                );

                cliente.setText(valor(linha, 1));
                endereco.setText(valor(linha, 2));
                numero.setText(valor(linha, 3));
                bairro.setText(valor(linha, 4));
                cidade.setText(valor(linha, 5));
                cep.setText(valor(linha, 6));
                uf.setText(valor(linha, 7));

                status.setSelectedItem(valor(linha, 8));
            }
        });

        salvar.addActionListener(e -> salvar());
        novo.addActionListener(e -> limpar());
        excluir.addActionListener(e -> excluir());
        exportar.addActionListener(e -> exportar());

        add(formulario, BorderLayout.NORTH);
        add(new JScrollPane(tabela), BorderLayout.CENTER);
        add(botoes, BorderLayout.SOUTH);

        carregar();
    }

    private String valor(int linha, int coluna) {

        Object valor = modelo.getValueAt(linha, coluna);

        return valor == null ? "" : valor.toString();
    }

    private Encomenda ler() {

        return new Encomenda(
                cliente.getText().trim(),
                endereco.getText().trim(),
                numero.getText().trim(),
                bairro.getText().trim(),
                cidade.getText().trim(),
                cep.getText().trim(),
                uf.getText().trim().toUpperCase(),
                (String) status.getSelectedItem()
        );
    }

    private boolean validar() {

        if (cliente.getText().trim().isEmpty()
                || endereco.getText().trim().isEmpty()
                || numero.getText().trim().isEmpty()
                || bairro.getText().trim().isEmpty()
                || cidade.getText().trim().isEmpty()
                || cep.getText().trim().isEmpty()
                || uf.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Preencha todos os campos."
            );

            return false;
        }

        if (uf.getText().trim().length() != 2) {

            JOptionPane.showMessageDialog(
                    this,
                    "Informe a UF com duas letras. Exemplo: SC."
            );

            return false;
        }

        return true;
    }

    private void salvar() {

        if (!validar()) {
            return;
        }

        try {

            if (id == 0) {
                dao.inserir(ler());
            } else {
                dao.atualizar(id, ler());
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Encomenda salva com sucesso!"
            );

            limpar();
            carregar();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao salvar: " + e.getMessage()
            );
        }
    }

    private void excluir() {

        if (id == 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Selecione uma encomenda na tabela."
            );

            return;
        }

        int resposta = JOptionPane.showConfirmDialog(
                this,
                "Deseja excluir esta encomenda?",
                "Confirmar exclusão",
                JOptionPane.YES_NO_OPTION
        );

        if (resposta == JOptionPane.YES_OPTION) {

            try {

                dao.excluir(id);

                JOptionPane.showMessageDialog(
                        this,
                        "Encomenda excluída com sucesso!"
                );

                limpar();
                carregar();

            } catch (Exception e) {

                JOptionPane.showMessageDialog(
                        this,
                        "Erro ao excluir: " + e.getMessage()
                );
            }
        }
    }

    private void carregar() {

        modelo.setRowCount(0);

        try {

            List<Encomenda> lista = dao.listar();

            for (Encomenda e : lista) {

                modelo.addRow(new Object[]{
                    e.getId(),
                    e.getCliente(),
                    e.getEndereco(),
                    e.getNumero(),
                    e.getBairro(),
                    e.getCidade(),
                    e.getCep(),
                    e.getUf(),
                    e.getStatus()
                });
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao carregar encomendas: " + e.getMessage()
            );
        }
    }

    private void limpar() {

        id = 0;

        cliente.setText("");
        endereco.setText("");
        numero.setText("");
        bairro.setText("");
        cidade.setText("");
        cep.setText("");
        uf.setText("");

        status.setSelectedIndex(0);

        tabela.clearSelection();
    }

    private void exportar() {

        try {

            List<Encomenda> lista = dao.listar();

            JFileChooser seletor = new JFileChooser();

            seletor.setSelectedFile(
                    new File("OMNIRoute_encomendas.csv")
            );

            if (seletor.showSaveDialog(this)
                    == JFileChooser.APPROVE_OPTION) {

                String caminho = seletor.getSelectedFile()
                        .getAbsolutePath();

                new ExportadorCSV().exportar(lista, caminho);

                JOptionPane.showMessageDialog(
                        this,
                        "Arquivo CSV exportado com sucesso!"
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao exportar: " + e.getMessage()
            );
        }
    }
}
