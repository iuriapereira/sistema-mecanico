package bancopoo;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class InterfaceInsereCliente extends JDialog {
    private final JFrame smallFrame;
    private final JFrame mainFrame;
    private JTextField nomeField;
    private JTextField documentoField;
    private JTextField fantasiaField;
    private JTextField rgieField;
    private JTextField foneField;
    private JTextField emailField;
    private JTextField enderecoField;
    private JTextField bairroField;
    private JCheckBox pessoaFisicaCheckbox;
    private JCheckBox pessoaJuridicaCheckbox;
    private JTextField dataNascimentoField;
    private boolean isSmallWindowOpen;

    public InterfaceInsereCliente(JFrame mainFrame) {
        this.smallFrame = new JFrame("Clientes");
        this.mainFrame = mainFrame;

        smallFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.width * 0.6);
        int height = (int) (screenSize.height * 0.6);
        smallFrame.setSize(width, height);
        smallFrame.setResizable(false);
        smallFrame.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);

        // Verifica se a janela menor está aberta
        isSmallWindowOpen = false;

        // Desabilita a janela principal
        mainFrame.setEnabled(false);
        // Painel da janela menor
        JPanel smallPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        JLabel nomeLabel = new JLabel("Nome:");
        nomeField = new JTextField(20);
        JLabel documentoLabel = new JLabel("Documento:");
        documentoField = new JTextField(20);
        JLabel fantasiaLabel = new JLabel("Nome Fantasia:");
        fantasiaField = new JTextField(20);
        JLabel rgieLabel = new JLabel("RG/Inscrição Estadual:");
        rgieField = new JTextField(20);
        JLabel foneLabel = new JLabel("Fone:");
        foneField = new JTextField(20);
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(20);
        JLabel enderecoLabel = new JLabel("Endereço:");
        enderecoField = new JTextField(20);
        JLabel bairroLabel = new JLabel("Bairro:");
        bairroField = new JTextField(20);
        JLabel dataNascimentoLabel = new JLabel("Data de Nascimento:");
        dataNascimentoField = new JTextField(20);
        dataNascimentoLabel.setVisible(false);
        dataNascimentoField.setVisible(false);

        pessoaFisicaCheckbox = new JCheckBox("Pessoa Física");
        pessoaFisicaCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pessoaJuridicaCheckbox.setSelected(false);
                dataNascimentoLabel.setVisible(true);
                dataNascimentoField.setVisible(true);
            }
        });

        pessoaJuridicaCheckbox = new JCheckBox("Pessoa Jurídica");
        pessoaJuridicaCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pessoaFisicaCheckbox.setSelected(false);
                dataNascimentoLabel.setVisible(false);
                dataNascimentoField.setVisible(false);
            }
        });

        JButton cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = nomeField.getText();
                String documento = documentoField.getText();
                String fantasia = fantasiaField.getText();
                String rgie = rgieField.getText();
                String fone = foneField.getText();
                String email = emailField.getText();
                String endereco = enderecoField.getText();
                String bairro = bairroField.getText();
                String dataNascimento = dataNascimentoField.getText();

                if (pessoaFisicaCheckbox.isSelected()) {
                    // Lógica para cadastrar pessoa física
                    JOptionPane.showMessageDialog(InterfaceInsereCliente.this, "Cadastro de pessoa física:\nNome: " + nome + "\nDocumento: " + documento + "\nFantasia: " + fantasia + "\nRG/IE: " + rgie + "\nFone: " + fone + "\nEmail: " + email + "\nEndereço: " + endereco + "\nBairro: " + bairro + "\nData de Nascimento: " + dataNascimento);
                } else if (pessoaJuridicaCheckbox.isSelected()) {
                    // Lógica para cadastrar pessoa jurídica
                    JOptionPane.showMessageDialog(InterfaceInsereCliente.this, "Cadastro de pessoa jurídica:\nNome: " + nome + "\nDocumento: " + documento + "\nFantasia: " + fantasia + "\nRG/IE: " + rgie + "\nFone: " + fone + "\nEmail: " + email + "\nEndereço: " + endereco + "\nBairro: " + bairro);
                } else {
                    JOptionPane.showMessageDialog(InterfaceInsereCliente.this, "Selecione o tipo de cliente (Pessoa Física ou Jurídica).");
                }
            }
        });

        constraints.gridx = 0;
        constraints.gridy = 0;
        smallPanel.add(pessoaFisicaCheckbox, constraints);
        constraints.gridx = 1;
        smallPanel.add(pessoaJuridicaCheckbox, constraints);
        constraints.gridx = 0;
        constraints.gridy = 1;
        smallPanel.add(nomeLabel, constraints);
        constraints.gridx = 1;
        smallPanel.add(nomeField, constraints);
        constraints.gridx = 0;
        constraints.gridy = 2;
        smallPanel.add(documentoLabel, constraints);
        constraints.gridx = 1;
        smallPanel.add(documentoField, constraints);
        constraints.gridx = 0;
        constraints.gridy = 3;
        smallPanel.add(fantasiaLabel, constraints);
        constraints.gridx = 1;
        smallPanel.add(fantasiaField, constraints);
        constraints.gridx = 0;
        constraints.gridy = 4;
        smallPanel.add(rgieLabel, constraints);
        constraints.gridx = 1;
        smallPanel.add(rgieField, constraints);
        constraints.gridx = 0;
        constraints.gridy = 5;
        smallPanel.add(foneLabel, constraints);
        constraints.gridx = 1;
        smallPanel.add(foneField, constraints);
        constraints.gridx = 0;
        constraints.gridy = 6;
        smallPanel.add(emailLabel, constraints);
        constraints.gridx = 1;
        smallPanel.add(emailField, constraints);
        constraints.gridx = 0;
        constraints.gridy = 7;
        smallPanel.add(enderecoLabel, constraints);
        constraints.gridx = 1;
        smallPanel.add(enderecoField, constraints);
        constraints.gridx = 0;
        constraints.gridy = 8;
        smallPanel.add(bairroLabel, constraints);
        constraints.gridx = 1;
        smallPanel.add(bairroField, constraints);
        constraints.gridx = 0;
        constraints.gridy = 9;
        smallPanel.add(dataNascimentoLabel, constraints);
        constraints.gridx = 1;
        smallPanel.add(dataNascimentoField, constraints);
        constraints.gridx = 0;
        constraints.gridy = 10;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        smallPanel.add(cadastrarButton, constraints);

        add(smallPanel);

        // Adiciona o painel da janela menor na janela menor
        smallFrame.getContentPane().add(smallPanel);

        // Centraliza a janela menor em relação à janela principal
        int x = mainFrame.getX() + (mainFrame.getWidth() - smallFrame.getWidth()) / 2;
        int y = mainFrame.getY() + (mainFrame.getHeight() - smallFrame.getHeight()) / 2;
        smallFrame.setLocation(x, y);
    }
    void showInterface() {
        smallFrame.setVisible(true);
    }
}
