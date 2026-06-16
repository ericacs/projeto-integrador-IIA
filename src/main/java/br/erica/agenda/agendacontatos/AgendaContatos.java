/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package br.erica.agenda.agendacontatos;

import br.erica.agenda.agendacontatos.view.FrmPrincipal;

import javax.swing.*;

/**
 *
 * @author erica
 */
public class AgendaContatos {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException |
                 IllegalAccessException | UnsupportedLookAndFeelException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Não foi possível aplicar o tema Nimbus.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
        
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Agenda de Contatos");
            frame.setContentPane(new FrmPrincipal());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setSize(800, 500);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
