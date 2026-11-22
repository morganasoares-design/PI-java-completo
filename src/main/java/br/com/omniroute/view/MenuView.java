
package br.com.omniroute.view;

import javax.swing.*;
import java.awt.*;

public class MenuView extends JFrame {

    private final Color FUNDO = new Color(11, 18, 35);
    private final Color AZUL = new Color(20, 115, 230);
    private final Color LARANJA = new Color(255, 145, 45);
    private final Color TEXTO = new Color(240, 245, 255);

    public MenuView() {

        setTitle("OMNIRoute - Painel principal");
        setSize(850, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);

        JPanel principal = new JPanel(new BorderLayout());
        principal.setBackground(FUNDO);

        // CABEÇALHO
        JPanel cabecalho = new JPanel(new BorderLayout());
        cabecalho.setBackground(new Color(16, 28, 50));
        cabecalho.setBorder(
            BorderFactory.createEmptyBorder(25, 35, 25, 35)
        );

        JLabel logo = new JLabel("OMNI");
        logo.setForeground(AZUL);
        logo.setFont(new Font("Arial", Font.BOLD, 30));

        JLabel nome = new JLabel("Route");
        nome.setForeground(LARANJA);
        nome.setFont(new Font("Arial", Font.BOLD, 30));

        JPanel marca = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        marca.setOpaque(false);
        marca.add(logo);
        marca.add(nome);

        JLabel subtitulo = new JLabel(
            "GESTÃO INTELIGENTE DE ENTREGAS"
        );
        subtitulo.setForeground(TEXTO);
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 12));

        JPanel textos = new JPanel(new GridLayout(2, 1));
        textos.setOpaque(false);
        textos.add(marca);
        textos.add(subtitulo);

        cabecalho.add(textos, BorderLayout.WEST);

        // CONTEÚDO
        JPanel conteudo = new JPanel(new BorderLayout(15, 20));
        conteudo.setBackground(FUNDO);
        conteudo.setBorder(
            BorderFactory.createEmptyBorder(30, 35, 30, 35)
        );

        JLabel titulo = new JLabel("Painel principal");
        titulo.setForeground(TEXTO);
        titulo.setFont(new Font("Arial", Font.BOLD, 25));

        JLabel descricao = new JLabel(
            "Selecione uma funcionalidade para continuar."
        );
        descricao.setForeground(new Color(170, 185, 205));
        descricao.setFont(new Font("Arial", Font.PLAIN, 14));

        JPanel informacoes = new JPanel(new GridLayout(2, 1, 5, 5));
        informacoes.setOpaque(false);
        informacoes.add(titulo);
        informacoes.add(descricao);

        // BOTÕES DE ACESSO
        JPanel cards = new JPanel(new GridLayout(1, 2, 20, 20));
        cards.setOpaque(false);

        JButton encomendas = criarBotao(
            "ENCOMENDAS",
            "Cadastrar, consultar e organizar entregas"
        );

        JButton rotas = criarBotao(
            "ROTAS",
            "Criar e consultar rotas de entrega"
        );

        cards.add(encomendas);
        cards.add(rotas);

        conteudo.add(informacoes, BorderLayout.NORTH);
        conteudo.add(cards, BorderLayout.CENTER);

        // RODAPÉ
        JLabel rodape = new JLabel(
            "OMNIRoute | Sistema de gerenciamento de entregas",
            SwingConstants.CENTER
        );
        rodape.setForeground(new Color(130, 150, 175));
        rodape.setFont(new Font("Arial", Font.PLAIN, 12));
        rodape.setBorder(
            BorderFactory.createEmptyBorder(12, 10, 18, 10)
        );

        principal.add(cabecalho, BorderLayout.NORTH);
        principal.add(conteudo, BorderLayout.CENTER);
        principal.add(rodape, BorderLayout.SOUTH);

        add(principal);

        // AÇÕES
        encomendas.addActionListener(e ->
            new EncomendaView().setVisible(true)
        );

        rotas.addActionListener(e ->
            new RotaView().setVisible(true)
        );
    }

    private JButton criarBotao(String titulo, String descricao) {

        JButton botao = new JButton(
            "<html><center><b>" + titulo +
            "</b><br><br>" + descricao + "</center></html>"
        );

        botao.setFont(new Font("Arial", Font.PLAIN, 17));
        botao.setForeground(TEXTO);
        botao.setBackground(new Color(23, 42, 70));
        botao.setFocusPainted(false);
        botao.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AZUL, 2),
                BorderFactory.createEmptyBorder(35, 20, 35, 20)
            )
        );
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));

        botao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                botao.setBackground(new Color(30, 65, 105));
                botao.setBorder(
                    BorderFactory.createLineBorder(LARANJA, 2)
                );
            }

            public void mouseExited(java.awt.event.MouseEvent e) {
                botao.setBackground(new Color(23, 42, 70));
                botao.setBorder(
                    BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(AZUL, 2),
                        BorderFactory.createEmptyBorder(35, 20, 35, 20)
                    )
                );
            }
        });

        return botao;
    }
}
