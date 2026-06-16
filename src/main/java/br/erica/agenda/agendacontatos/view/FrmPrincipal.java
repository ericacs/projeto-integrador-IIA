package br.erica.agenda.agendacontatos.view;

import br.erica.agenda.agendacontatos.dao.ContatoDAO;
import br.erica.agenda.agendacontatos.model.Contato;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class FrmPrincipal extends JPanel {

    private JTextField txtId;
    private JTextField txtNome;
    private JTextField txtTelefone;
    private JTextField txtEmail;

    private JButton btnNovo;
    private JButton btnSalvar;
    private JButton btnAtualizar;
    private JButton btnRemover;
    private JButton btnBuscar;
    private JButton btnListar;

    private JTable tabelaContatos;
    private DefaultTableModel modeloTabela;

    private final ContatoDAO contatoDAO;

    public FrmPrincipal() {
        contatoDAO = new ContatoDAO();

        inicializarComponentes();
        configurarEventos();
        listarContatos();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel painelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 3, 3, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtId = new JTextField(20);
        txtId.setEditable(false);

        txtNome = new JTextField(20);
        txtTelefone = new JTextField(20);
        txtEmail = new JTextField(20);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        painelFormulario.add(new JLabel("ID:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        painelFormulario.add(txtId, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        painelFormulario.add(new JLabel("Nome:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        painelFormulario.add(txtNome, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        painelFormulario.add(new JLabel("Telefone:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        painelFormulario.add(txtTelefone, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        painelFormulario.add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        painelFormulario.add(txtEmail, gbc);

        JPanel painelBotoes = new JPanel(new GridLayout(1, 6, 5, 5));

        btnNovo = new JButton("Novo");
        btnSalvar = new JButton("Salvar");
        btnAtualizar = new JButton("Atualizar");
        btnRemover = new JButton("Remover");
        btnBuscar = new JButton("Buscar");
        btnListar = new JButton("Listar");

        painelBotoes.add(btnNovo);
        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnRemover);
        painelBotoes.add(btnBuscar);
        painelBotoes.add(btnListar);

        modeloTabela = new DefaultTableModel(
                new Object[]{"ID", "Nome", "Telefone", "Email"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaContatos = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabelaContatos);

        JPanel painelSuperior = new JPanel(new BorderLayout(5, 5));
        painelSuperior.add(painelFormulario, BorderLayout.CENTER);
        painelSuperior.add(painelBotoes, BorderLayout.SOUTH);

        add(painelSuperior, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void configurarEventos() {
        btnNovo.addActionListener(e -> limparCampos());

        btnSalvar.addActionListener(e -> salvarContato());

        btnAtualizar.addActionListener(e -> atualizarContato());

        btnRemover.addActionListener(e -> removerContato());

        btnBuscar.addActionListener(e -> buscarContato());

        btnListar.addActionListener(e -> listarContatos());

        tabelaContatos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                preencherCamposComLinhaSelecionada();
            }
        });
    }

    private void salvarContato() {
        if (!validarCampos()) {
            return;
        }

        Contato contato = new Contato(
                txtNome.getText(),
                txtTelefone.getText(),
                txtEmail.getText()
        );

        contatoDAO.adicionarContato(contato);

        JOptionPane.showMessageDialog(this, "Contato salvo com sucesso!");

        limparCampos();
        listarContatos();
    }

    private void atualizarContato() {
        if (txtId.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Selecione um contato para atualizar.");
            return;
        }

        if (!validarCampos()) {
            return;
        }

        Contato contato = new Contato(
                Integer.parseInt(txtId.getText()),
                txtNome.getText(),
                txtTelefone.getText(),
                txtEmail.getText()
        );

        contatoDAO.atualizarContato(contato);

        JOptionPane.showMessageDialog(this, "Contato atualizado com sucesso!");

        limparCampos();
        listarContatos();
    }

    private void removerContato() {
        if (txtId.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Selecione um contato para remover.");
            return;
        }

        int confirmacao = JOptionPane.showConfirmDialog(
                this,
                "Deseja realmente remover este contato?",
                "Confirmação",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacao == JOptionPane.YES_OPTION) {
            int id = Integer.parseInt(txtId.getText());

            contatoDAO.removerContato(id);

            JOptionPane.showMessageDialog(this, "Contato removido com sucesso!");

            limparCampos();
            listarContatos();
        }
    }

    private void buscarContato() {
        String nome = txtNome.getText();

        if (nome.isBlank()) {
            JOptionPane.showMessageDialog(this, "Digite um nome para buscar.");
            return;
        }

        Contato contato = contatoDAO.buscarContato(nome);

        modeloTabela.setRowCount(0);

        if (contato != null) {
            modeloTabela.addRow(new Object[]{
                    contato.getId(),
                    contato.getNome(),
                    contato.getTelefone(),
                    contato.getEmail()
            });

            preencherCampos(contato);
        } else {
            JOptionPane.showMessageDialog(this, "Contato não encontrado.");
        }
    }

    private void listarContatos() {
        ArrayList<Contato> contatos = contatoDAO.listarContatos();

        modeloTabela.setRowCount(0);

        for (Contato contato : contatos) {
            modeloTabela.addRow(new Object[]{
                    contato.getId(),
                    contato.getNome(),
                    contato.getTelefone(),
                    contato.getEmail()
            });
        }
    }

    private void preencherCamposComLinhaSelecionada() {
        int linhaSelecionada = tabelaContatos.getSelectedRow();

        if (linhaSelecionada >= 0) {
            txtId.setText(modeloTabela.getValueAt(linhaSelecionada, 0).toString());
            txtNome.setText(modeloTabela.getValueAt(linhaSelecionada, 1).toString());
            txtTelefone.setText(modeloTabela.getValueAt(linhaSelecionada, 2).toString());
            txtEmail.setText(modeloTabela.getValueAt(linhaSelecionada, 3).toString());
        }
    }

    private void preencherCampos(Contato contato) {
        txtId.setText(String.valueOf(contato.getId()));
        txtNome.setText(contato.getNome());
        txtTelefone.setText(contato.getTelefone());
        txtEmail.setText(contato.getEmail());
    }

    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");

        tabelaContatos.clearSelection();
        txtNome.requestFocus();
    }

    private boolean validarCampos() {
        if (txtNome.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Informe o nome.");
            txtNome.requestFocus();
            return false;
        }

        if (txtTelefone.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Informe o telefone.");
            txtTelefone.requestFocus();
            return false;
        }

        if (txtEmail.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Informe o email.");
            txtEmail.requestFocus();
            return false;
        }

        return true;
    }
}
