/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bancopoo;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Iuri Pereira
 */
public class ClienteJuridico {
    private final JFrame smallFrame;
    private final JFrame mainFrame;
    private static boolean isSmallWindowOpen = false;

    public ClienteJuridico(JFrame mainFrame) {
        this.smallFrame = new JFrame("Inserir Cliente");
        this.mainFrame = mainFrame;

        smallFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.width * 0.6);
        int height = (int) (screenSize.height * 0.6);
        smallFrame.setSize(width, height);
        smallFrame.setResizable(false);
        smallFrame.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);

        // Verifica se a janela menor está aberta
        isSmallWindowOpen = true;

        // Desabilita a janela principal
        mainFrame.setEnabled(false);

        // Painel da janela menor
        JPanel smallPanel = new JPanel(new BorderLayout());

        // Parte superior com os campos de entrada e botão de inserir
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel lblNome = new JLabel("Nome do Cliente:");
        JTextField txtNome = new JTextField(20);
        
        JLabel lblFantasia = new JLabel("Nome Fantasia:");
        JTextField txtFantasia = new JTextField(20);
        
        JLabel lblCPFCNPJ = new JLabel("CPF/CNPJ:");
        JTextField txtCPFCNPJ = new JTextField(20);
        
        JLabel lblRGIE = new JLabel("RG/Inscrição Estadual:");
        JTextField txtRGIE = new JTextField(20);
        
        JLabel lblFone = new JLabel("Fone:");
        JTextField txtFone = new JTextField(20);
        
        JLabel lblEmail = new JLabel("Email:");
        JTextField txtEmail = new JTextField(20);
        
        JLabel lblEndereco = new JLabel("Endereco:");
        JTextField txtEndereco = new JTextField(20);
        
        JLabel lblBairro = new JLabel("Bairro:");
        JTextField txtBairro = new JTextField(20);
        
        
        
        JButton btnInserir = new JButton("Inserir");
        btnInserir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = txtNome.getText();

                // Aqui você pode realizar o processamento dos dados inseridos, como salvá-los em um banco de dados

                // Exemplo de exibição dos dados inseridos:
                JOptionPane.showMessageDialog(smallFrame, "Nome do Cliente: " + nome);

                // Limpa o campo de entrada
                txtNome.setText("");
            }
        });

        inputPanel.add(lblNome);
        inputPanel.add(txtNome);
        inputPanel.add(lblFantasia);
        inputPanel.add(txtFantasia);
        inputPanel.add(lblCPFCNPJ);
        inputPanel.add(txtCPFCNPJ);
        inputPanel.add(lblRGIE);
        inputPanel.add(txtRGIE);
        inputPanel.add(lblFone);
        inputPanel.add(txtFone);
        inputPanel.add(lblEmail);
        inputPanel.add(txtEmail);
        inputPanel.add(lblEndereco);
        inputPanel.add(txtEndereco);
        inputPanel.add(lblBairro);
        inputPanel.add(txtBairro);
        inputPanel.add(btnInserir);

        // Adiciona os painéis no painel da janela menor
        smallPanel.add(inputPanel, BorderLayout.NORTH);

        // Adiciona o painel da janela menor na janela menor
        smallFrame.getContentPane().add(smallPanel);

        // Centraliza a janela menor em relação à janela principal
        int x = mainFrame.getX() + (mainFrame.getWidth() - smallFrame.getWidth()) / 2;
        int y = mainFrame.getY() + (mainFrame.getHeight() - smallFrame.getHeight()) / 2;
        smallFrame.setLocation(x, y);

        // Configura um listener para quando a janela menor for fechada
        smallFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                isSmallWindowOpen = false;
                // Habilita a janela principal
                mainFrame.setEnabled(true);
                mainFrame.requestFocus();
            }
        });
    }

    public void show() {
        // Exibe a janela menor
        smallFrame.setVisible(true);
    }
}
