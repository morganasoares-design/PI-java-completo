
package br.com.omniroute.view;

import br.com.omniroute.model.dao.*;
import br.com.omniroute.model.Encomenda;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.*;

public class RotaView extends JFrame {

    private final JTextField partida = new JTextField();
    private final JTextField chegada = new JTextField();

    private final JPanel checks = new JPanel();
    private final java.util.List<JCheckBox> boxes = new ArrayList<>();

    private final DefaultTableModel m = new DefaultTableModel(
        new String[]{"ID", "Partida", "Chegada", "Encomendas"}, 0
    ) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    private final JTable tabela = new JTable(m);

    public RotaView() {

        setTitle("OMNIRoute - Rotas");
        setSize(850, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel top = new JPanel(new GridLayout(2, 2, 5, 5));
        top.setBorder(BorderFactory.createTitledBorder("Nova rota"));

        top.add(new JLabel("Partida"));
        top.add(partida);
        top.add(new JLabel("Chegada"));
        top.add(chegada);

        checks.setLayout(new BoxLayout(checks, BoxLayout.Y_AXIS));

        carregarEncomendas();

        JButton criar = new JButton("Cadastrar rota");
        JButton atualizar = new JButton("Atualizar lista");
        JButton excluir = new JButton("Excluir rota");

        JPanel botoes = new JPanel();
        botoes.add(criar);
        botoes.add(atualizar);
        botoes.add(excluir);

        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        criar.addActionListener(e -> criar());
        atualizar.addActionListener(e -> {
            carregar();
            carregarEncomendas();
        });
        excluir.addActionListener(e -> excluirRota());

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(checks), BorderLayout.WEST);
        add(new JScrollPane(tabela), BorderLayout.CENTER);
        add(botoes, BorderLayout.SOUTH);

        carregar();
    }

    private void carregarEncomendas() {

        checks.removeAll();
        boxes.clear();

        try {
            for (Encomenda e : new EncomendaDAO().listar()) {

                JCheckBox cb = new JCheckBox(
                    "#" + e.getId() + " " +
                    e.getCliente() + " - " +
                    e.getEndereco()
                );

                cb.putClientProperty("id", e.getId());

                boxes.add(cb);
                checks.add(cb);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                this, "Erro ao carregar encomendas: " + e.getMessage()
            );
        }

        checks.revalidate();
        checks.repaint();
    }

    private void criar() {

        if (partida.getText().isBlank() ||
            chegada.getText().isBlank()) {

            JOptionPane.showMessageDialog(
                this, "Informe partida e chegada."
            );
            return;
        }

        java.util.List<Integer> ids = new ArrayList<>();

        for (JCheckBox b : boxes) {
            if (b.isSelected()) {
                ids.add((Integer) b.getClientProperty("id"));
            }
        }

        if (ids.isEmpty()) {
            JOptionPane.showMessageDialog(
                this, "Selecione pelo menos uma encomenda."
            );
            return;
        }

        try {
            new RotaDAO().criar(
                partida.getText().trim(),
                chegada.getText().trim(),
                ids
            );

            JOptionPane.showMessageDialog(
                this, "Rota cadastrada com sucesso!"
            );

            partida.setText("");
            chegada.setText("");

            for (JCheckBox b : boxes) {
                b.setSelected(false);
            }

            carregar();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                this, "Erro ao cadastrar rota: " + e.getMessage()
            );
        }
    }

    private void carregar() {

        m.setRowCount(0);

        try {
            for (String[] r : new RotaDAO().listar()) {
                m.addRow(r);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                this, "Erro ao carregar rotas: " + e.getMessage()
            );
        }
    }

    private void excluirRota() {

        int linha = tabela.getSelectedRow();

        if (linha == -1) {
            JOptionPane.showMessageDialog(
                this, "Selecione uma rota na tabela para excluir."
            );
            return;
        }

        int id = Integer.parseInt(
            m.getValueAt(linha, 0).toString()
        );

        int confirmacao = JOptionPane.showConfirmDialog(
            this,
            "Deseja realmente excluir a rota " + id + "?\n" +
            "Os vínculos com as encomendas serão removidos, " +
            "mas as encomendas continuarão cadastradas.",
            "Confirmar exclusão",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (confirmacao != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            new RotaDAO().excluir(id);

            JOptionPane.showMessageDialog(
                this, "Rota excluída com sucesso!"
            );

            carregar();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                this, "Erro ao excluir rota: " + e.getMessage()
            );
        }
    }
}
