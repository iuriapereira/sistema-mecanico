package bancopoo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class InterfaceInsereCliente extends JDialog {
    private final JFrame mainFrame;
    private JTextField nomeField;
    private JTextField documentoField;
    private JTextField fantasiaField;
    private JTextField rgieField;
    private JTextField foneField;
    private JTextField emailField;
    private JTextField enderecoField;
    private JTextField bairroField;
    private JRadioButton pessoaFisicaRadioButton;
    private JRadioButton pessoaJuridicaRadioButton;
    private JTextField dataNascimentoField;
    private JButton limparCamposButton;

    public InterfaceInsereCliente(JFrame mainFrame) {
        this.mainFrame = mainFrame;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);

        // Painel da janela menor
        JPanel mainPanel = new JPanel(null); // Define o layout como null

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

        pessoaFisicaRadioButton = new JRadioButton("Pessoa Física");
        pessoaFisicaRadioButton.setBounds(10, 10, 150, 20);
        pessoaFisicaRadioButton.setSelected(true);

        pessoaJuridicaRadioButton = new JRadioButton("Pessoa Jurídica");
        pessoaJuridicaRadioButton.setBounds(170, 10, 150, 20);

        ButtonGroup tipoClienteGroup = new ButtonGroup();
        tipoClienteGroup.add(pessoaFisicaRadioButton);
        tipoClienteGroup.add(pessoaJuridicaRadioButton);

        JButton cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.setBounds(10, 320, 300, 30);
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

                if (pessoaFisicaRadioButton.isSelected()) {
                    // Lógica para cadastrar pessoa física
                    JOptionPane.showMessageDialog(InterfaceInsereCliente.this, "Cadastro de pessoa física:\nNome: " + nome + "\nDocumento: " + documento + "\nFantasia: " + fantasia + "\nRG/IE: " + rgie + "\nFone: " + fone + "\nEmail: " + email + "\nEndereço: " + endereco + "\nBairro: " + bairro + "\nData de Nascimento: " + dataNascimento);
                } else if (pessoaJuridicaRadioButton.isSelected()) {
                    // Lógica para cadastrar pessoa jurídica
                    JOptionPane.showMessageDialog(InterfaceInsereCliente.this, "Cadastro de pessoa jurídica:\nNome: " + nome + "\nDocumento: " + documento + "\nFantasia: " + fantasia + "\nRG/IE: " + rgie + "\nFone: " + fone + "\nEmail: " + email + "\nEndereço: " + endereco + "\nBairro: " + bairro);
                } else {
                    JOptionPane.showMessageDialog(InterfaceInsereCliente.this, "Selecione o tipo de cliente (Pessoa Física ou Jurídica).");
                }
            }
        });
        
        pessoaJuridicaRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dataNascimentoField.setEnabled(false); // Desativa a caixa de texto de Data de Nascimento
                dataNascimentoField.setText("");
            }
        });
        
        pessoaFisicaRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dataNascimentoField.setEnabled(true); // Desativa a caixa de texto de Data de Nascimento
            }
        });
        
        limparCamposButton = new JButton("Limpar");
        limparCamposButton.setBounds(10, 360, 300, 30);
        limparCamposButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limparCampos();
            }
        });

        // Define as coordenadas de posicionamento dos componentes
        int x = 10;
        int y = 40;
        int yGap = 30;
        int labelWidth = 150;
        int fieldWidth = 200;

        nomeLabel.setBounds(x, y, labelWidth, 20);
        nomeField.setBounds(x + labelWidth + 10, y, fieldWidth, 20);

        y += yGap;
        documentoLabel.setBounds(x, y, labelWidth, 20);
        documentoField.setBounds(x + labelWidth + 10, y, fieldWidth, 20);

        y += yGap;
        fantasiaLabel.setBounds(x, y, labelWidth, 20);
        fantasiaField.setBounds(x + labelWidth + 10, y, fieldWidth, 20);

        y += yGap;
        rgieLabel.setBounds(x, y, labelWidth, 20);
        rgieField.setBounds(x + labelWidth + 10, y, fieldWidth, 20);

        y += yGap;
        foneLabel.setBounds(x, y, labelWidth, 20);
        foneField.setBounds(x + labelWidth + 10, y, fieldWidth, 20);

        y += yGap;
        emailLabel.setBounds(x, y, labelWidth, 20);
        emailField.setBounds(x + labelWidth + 10, y, fieldWidth, 20);

        y += yGap;
        enderecoLabel.setBounds(x, y, labelWidth, 20);
        enderecoField.setBounds(x + labelWidth + 10, y, fieldWidth, 20);

        y += yGap;
        bairroLabel.setBounds(x, y, labelWidth, 20);
        bairroField.setBounds(x + labelWidth + 10, y, fieldWidth, 20);

        y += yGap;
        dataNascimentoLabel.setBounds(x, y, labelWidth, 20);
        dataNascimentoField.setBounds(x + labelWidth + 10, y, fieldWidth, 20);

        // Adicione os componentes ao painel principal
        mainPanel.add(pessoaFisicaRadioButton);
        mainPanel.add(pessoaJuridicaRadioButton);
        mainPanel.add(nomeLabel);
        mainPanel.add(nomeField);
        mainPanel.add(documentoLabel);
        mainPanel.add(documentoField);
        mainPanel.add(fantasiaLabel);
        mainPanel.add(fantasiaField);
        mainPanel.add(rgieLabel);
        mainPanel.add(rgieField);
        mainPanel.add(foneLabel);
        mainPanel.add(foneField);
        mainPanel.add(emailLabel);
        mainPanel.add(emailField);
        mainPanel.add(enderecoLabel);
        mainPanel.add(enderecoField);
        mainPanel.add(bairroLabel);
        mainPanel.add(bairroField);
        mainPanel.add(dataNascimentoLabel);
        mainPanel.add(dataNascimentoField);
        mainPanel.add(cadastrarButton);
        mainPanel.add(limparCamposButton);

        // Defina o tamanho do painel principal
        mainPanel.setPreferredSize(new Dimension(380, 420));

        // Adicione o painel principal à janela de diálogo
        getContentPane().add(mainPanel);
        pack();
        setLocationRelativeTo(mainFrame);
    }

    void showInterface() {
        setVisible(true);
    }

    private void limparCampos() {
        nomeField.setText("");
        documentoField.setText("");
        fantasiaField.setText("");
        rgieField.setText("");
        foneField.setText("");
        emailField.setText("");
        enderecoField.setText("");
        bairroField.setText("");
        dataNascimentoField.setText("");
    }
}
