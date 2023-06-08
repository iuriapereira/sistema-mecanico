package bancopoo;

import banco.TbCidEst;
import banco.TbEstado;
import banco.TbLogradouro;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.DefaultComboBoxModel;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;

class InterfaceInsereFornecedor extends JDialog {
    private final JFrame mainFrame;
    private JTextField nomeField;
    private JTextField documentoField;
    private JTextField fantasiaField;
    private JTextField ieField;
    private JTextField foneField;
    private JTextField emailField;
    private JTextField enderecoField;
    private JTextField bairroField;
    private JTextField cepField;
    private JTextField logradouroField;
    private JTextField numeroField;
    private JTextField complementoField;
    private JTextField cidadeField;
    private JTextField estadoField;
    private JRadioButton pessoaFisicaRadioButton;
    private JRadioButton pessoaJuridicaRadioButton;
    private JTextField dataNascimentoField;
    private JButton limparCamposButton;
    private Session session;
    

    public InterfaceInsereFornecedor(JFrame mainFrame, Session session) {
        this.mainFrame = mainFrame;
        this.session = session;
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        setTitle("Inserindo dados do fornecedor");
        
        // CONEXÃO COM O BANCO TB_ESTADO
        Criteria estd = session.createCriteria(TbEstado.class);
        ArrayList<TbEstado> estado = (ArrayList<TbEstado>) estd.list();
        
        // COMBOBOX DO ESTADO
        JComboBox<String> listEstado = new JComboBox<>();
        DefaultComboBoxModel<String> est = new DefaultComboBoxModel<>();
        est.addElement("Selecione..."); // PALAVRA QUE VAI FICAR ANTES DE APARACER AS LITA DE TODOS OS ESTADOS
        listEstado.setModel(est); 
        for (TbEstado descricao : estado) {
            listEstado.addItem(descricao.getEstSigla());
        }
        
        // CONEXÃO COM O BANCO TB_CIDEST
        Criteria cid = session.createCriteria(TbCidEst.class);
        ArrayList<TbCidEst> cidade = (ArrayList<TbCidEst>) cid.list();
        
        // COMBOBOX DA CIDADE
        JComboBox<String> listCidade = new JComboBox<>();
        
        listEstado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedEstado = (String) listEstado.getSelectedItem();

                // Obter as cidades correspondentes ao estado selecionado
                String hql = "SELECT ce.tbCidade.cidDescricao FROM TbCidEst ce WHERE ce.tbEstado.estSigla = '" + selectedEstado + "'";
                Query query = session.createQuery(hql);
                List<String> cidades = (List<String>) query.list();

                DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
                model.addElement("Selecione..."); // PALAVRA QUE VAI FICAR ANTES DE APARACER AS LITA DE TODOS OS ESTADOS
                for (String cidade : cidades) {
                    model.addElement(cidade);
                }
                ArrayList<TbEstado> estado = (ArrayList<TbEstado>) estd.list();
                
                listCidade.setModel(model);
                

                // ATUALIZAR A INTERFACE
                revalidate();
                repaint();
            }
        });
           
        // CONEXÃO COM O BANCO TB_LOGRADOURO
        Criteria log = session.createCriteria(TbLogradouro.class);
        ArrayList<TbLogradouro> logradouro = (ArrayList<TbLogradouro>) log.list();
        // COMBOBOX DO LOGRADOURO
        JComboBox<String> listLogradouro = new JComboBox<>();
        DefaultComboBoxModel<String> logr = new DefaultComboBoxModel<>();
        logr.addElement("Selecione..."); // PALAVRA QUE VAI FICAR ANTES DE APARACER AS LITA DE TODOS OS ESTADOS
        listLogradouro.setModel(logr); 
        for (TbLogradouro descricao : logradouro) {
            listLogradouro.addItem(descricao.getLogDescricao());
        }
        
        // PAINEL DA JANELA MENOR
        JPanel mainPanel = new JPanel(null); // DEFINE O LAYOUT COMO NULL
        
        JLabel nomeLabel = new JLabel("Nome:");
        nomeField = new JTextField(20);
        JLabel documentoLabel = new JLabel("CNPJ:");
        documentoField = new JTextField(20);
        JLabel fantasiaLabel = new JLabel("Nome Fantasia:");
        fantasiaField = new JTextField(20);
        JLabel ieLabel = new JLabel("Inscrição Estadual:");
        ieField = new JTextField(20);
        JLabel foneLabel = new JLabel("Fone:");
        foneField = new JTextField(20);
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(20);
        JLabel cepLabel = new JLabel("CEP:");
        cepField = new JTextField(20);
        JLabel logradouroLabel = new JLabel("Logradouro:");
        JLabel enderecoLabel = new JLabel("Endereço:");
        enderecoField = new JTextField(20);
        JLabel numeroLabel = new JLabel("Número:");
        numeroField = new JTextField(20);
        JLabel complementoLabel = new JLabel("Complemento:");
        complementoField = new JTextField(20);
        JLabel bairroLabel = new JLabel("Bairro:");
        bairroField = new JTextField(20);
        JLabel estadoLabel = new JLabel("Estado:");
        JLabel cidadeLabel = new JLabel("Cidade:");

        JButton cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.setBounds(70, 500, 100, 30);
        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nomeField == null || documentoField == null || dataNascimentoField == null || fantasiaField == null || ieField == null || foneField == null || emailField == null || cepField == null || enderecoField == null || numeroField == null || complementoField == null || bairroField == null) {
                    // Lógica para cadastrar pessoa física
                    JOptionPane.showMessageDialog(InterfaceInsereFornecedor.this, "Preencha todos os campos corretamente!");
                } else if(listEstado.getModel() == null || listCidade.getModel() == null || listLogradouro.getModel() == null) {
                    JOptionPane.showMessageDialog(InterfaceInsereFornecedor.this, "Selecione os campos!");
                }else {
                    JOptionPane.showMessageDialog(InterfaceInsereFornecedor.this, "Fornecedor inserido com sucesso!");
                }
            }
        });

        limparCamposButton = new JButton("Limpar");
        limparCamposButton.setBounds(200, 500, 100, 30);
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
        ieLabel.setBounds(x, y, labelWidth, 20);
        ieField.setBounds(x + labelWidth + 10, y, fieldWidth, 20);

        y += yGap;
        foneLabel.setBounds(x, y, labelWidth, 20);
        foneField.setBounds(x + labelWidth + 10, y, fieldWidth, 20);

        y += yGap;
        emailLabel.setBounds(x, y, labelWidth, 20);
        emailField.setBounds(x + labelWidth + 10, y, fieldWidth, 20);

        y += yGap;
        cepLabel.setBounds(x, y, labelWidth, 20);
        cepField.setBounds(x + labelWidth + 10, y, fieldWidth, 20);

        y += yGap;
        logradouroLabel.setBounds(x, y, labelWidth, 20);
        listLogradouro.setBounds(x + labelWidth + 10, y, fieldWidth, 20);

        y += yGap;
        enderecoLabel.setBounds(x, y, labelWidth, 20);
        enderecoField.setBounds(x + labelWidth + 10, y, fieldWidth, 20);

        y += yGap;
        numeroLabel.setBounds(x, y, labelWidth, 20);
        numeroField.setBounds(x + labelWidth + 10, y, fieldWidth, 20);

        y += yGap;
        complementoLabel.setBounds(x, y, labelWidth, 20);
        complementoField.setBounds(x + labelWidth + 10, y, fieldWidth, 20);

        y += yGap;
        bairroLabel.setBounds(x, y, labelWidth, 20);
        bairroField.setBounds(x + labelWidth + 10, y, fieldWidth, 20);

        y += yGap;
        estadoLabel.setBounds(x, y, labelWidth, 20);
        listEstado.setBounds(x + labelWidth + 10, y, fieldWidth, 20);
        
        y += yGap;
        cidadeLabel.setBounds(x, y, labelWidth, 20);
        listCidade.setBounds(x + labelWidth + 10, y, fieldWidth, 20);

        // Adicione os componentes ao painel principal
        mainPanel.add(nomeLabel);
        mainPanel.add(nomeField);

        mainPanel.add(documentoLabel);
        mainPanel.add(documentoField);

        mainPanel.add(fantasiaLabel);
        mainPanel.add(fantasiaField);

        mainPanel.add(ieLabel);
        mainPanel.add(ieField);

        mainPanel.add(foneLabel);
        mainPanel.add(foneField);

        mainPanel.add(emailLabel);
        mainPanel.add(emailField);

        mainPanel.add(cepLabel);
        mainPanel.add(cepField);

        mainPanel.add(logradouroLabel);
        mainPanel.add(listLogradouro);

        mainPanel.add(enderecoLabel);
        mainPanel.add(enderecoField);

        mainPanel.add(numeroLabel);
        mainPanel.add(numeroField);

        mainPanel.add(complementoLabel);
        mainPanel.add(complementoField);

        mainPanel.add(bairroLabel);
        mainPanel.add(bairroField);
        
        // Adiciona o JComboBox ao JFrame
        mainPanel.add(estadoLabel);
        mainPanel.add(listEstado);
        
        mainPanel.add(cidadeLabel);
        mainPanel.add(listCidade);
        
        // botão para limpar
        mainPanel.add(cadastrarButton);
        mainPanel.add(limparCamposButton);
        
        
        // Defina o tamanho do painel principal
        mainPanel.setPreferredSize(new Dimension(380, 550));

        // Adicione o painel principal à janela de diálogo
        getContentPane().add(mainPanel);
        pack();
        setLocationRelativeTo(mainFrame);
    }

    private void limparCampos() {
        nomeField.setText("");
        documentoField.setText("");
        fantasiaField.setText("");
        ieField.setText("");
        foneField.setText("");
        emailField.setText("");
        cepField.setText("");
        enderecoField.setText("");
        numeroField.setText("");
        complementoField.setText("");
        bairroField.setText("");
        dataNascimentoField.setText("");
    }
    
    public void showInterface() {
        // Exibe a janela menor
        setVisible(true);
    }
}
