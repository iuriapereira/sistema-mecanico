package bancopoo;

import banco.TbCidEst;
import banco.TbEstado;
import banco.TbLogradouro;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.DefaultComboBoxModel;
import javax.swing.text.MaskFormatter;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

class InterfaceInsereFornecedor extends JDialog {
    private final JFrame mainFrame;
    private JTextField nomeField;
    private JFormattedTextField documentoField;
    private JTextField fantasiaField;
    private JTextField ieField;
    private JFormattedTextField foneField;
    private JTextField emailField;
    private JTextField enderecoField;
    private JTextField bairroField;
    private JFormattedTextField cepField;
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
        JLabel fantasiaLabel = new JLabel("Nome Fantasia:");
        fantasiaField = new JTextField(20);
        JLabel ieLabel = new JLabel("Inscrição Estadual:");
        ieField = new JTextField(20);
        JLabel foneLabel = new JLabel("Fone:");
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(20);
        JLabel cepLabel = new JLabel("CEP:");
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
                String hql = "SELECT c.cepId FROM TbCidEst c WHERE c.tbCidade.cidDescricao = '" + listCidade.getSelectedItem() + "' AND c.tbEstado = '" + listEstado.getSelectedItem() + "'";
                Query query = session.createQuery(hql);

                String hql2 = "SELECT l.logId FROM TbLogradouro l WHERE l.logDescricao = '" + listLogradouro.getSelectedItem() + "'";
                Query query2 = session.createQuery(hql2);

                int logId = (int) query2.uniqueResult();
                int cidId = (int) query.uniqueResult();

                Transaction transaction = session.beginTransaction();
                try {

                    banco.TbBairro tbBairro = new banco.TbBairro();
                    tbBairro.setBaiDescricao(bairroField.getText());
                    session.save(tbBairro);

                    banco.TbEndPostal tbEndPostal = new banco.TbEndPostal();
                    tbEndPostal.setTbBairro(tbBairro);
                    tbEndPostal.setEndPNomerua(enderecoField.getText());
                    tbEndPostal.setEndPCep(cepField.getText());
                    Object log = session.load(TbLogradouro.class, logId);
                    tbEndPostal.setTbLogradouro((TbLogradouro) log);
                    Object cidest = session.load(TbCidEst.class, cidId);
                    tbEndPostal.setTbCidEst((TbCidEst) cidest);
                    session.save(tbEndPostal);

                    banco.TbEndereco tbendereco = new banco.TbEndereco();
                    tbendereco.setTbEndPostal(tbEndPostal);
                    tbendereco.setEndNumero(numeroField.getText());
                    tbendereco.setEndComplemento(complementoField.getText());
                    session.save(tbendereco);

                    banco.TbEntidade tbentidade = new banco.TbEntidade();
                    tbentidade.setEntCpfCnpj(documentoField.getText());
                    tbentidade.setTbEndereco(tbendereco);
                    tbentidade.setEntNome(nomeField.getText());
                    tbentidade.setEntNomeFantasia(fantasiaField.getText());
                    tbentidade.setEntRgIe(ieField.getText());
                    tbentidade.setEntFone(foneField.getText());
                    tbentidade.setEntEmail(emailField.getText());
                    tbentidade.setEntTipo("Juridica");
                    session.save(tbentidade);

                    banco.TbFornecedor tbfornecedor = new banco.TbFornecedor();
                    tbfornecedor.setTbEntidade(tbentidade);
                    session.save(tbfornecedor);

                    transaction.commit();
                    JOptionPane.showMessageDialog(null, "Fornecedor Inserido com Sucesso!");
                    dispose();

                } catch (HibernateException ex) {
                    transaction.rollback();
                    JOptionPane.showMessageDialog(null, "Ocorreu um erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
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
        // Adicione os componentes ao painel principal
        mainPanel.add(nomeLabel);
        mainPanel.add(nomeField);

        // CPF NA TELA
        mainPanel.add(documentoLabel);
        try {
            MaskFormatter mf = new MaskFormatter("##.###.###/####-##");
            documentoField = new JFormattedTextField(mf);
        } catch (ParseException ex) {
            Logger.getLogger(InterfaceInsereFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        mainPanel.add(documentoField);

        mainPanel.add(fantasiaLabel);
        mainPanel.add(fantasiaField);

        // RG/IE NA TELA
        mainPanel.add(ieLabel);
        // MaskFormatter para permitir apenas números
        MaskFormatter maskFormatter = null;
        try {
            maskFormatter = new MaskFormatter("##############");
            maskFormatter.setValidCharacters("0123456789"); // Permite apenas números
            maskFormatter.setPlaceholderCharacter('_'); // Define um caractere de espaço reservado

        } catch (ParseException e) {
            e.printStackTrace();
        }

        // JFormattedTextField usando o MaskFormatter
        JFormattedTextField ieField = new JFormattedTextField(maskFormatter);
        mainPanel.add(ieField);

        // TELEFONE NA TELA
        mainPanel.add(foneLabel);
        try {
            MaskFormatter mf = new MaskFormatter("(##) #####-####");
            foneField = new JFormattedTextField(mf);
        } catch (ParseException ex) {
            Logger.getLogger(InterfaceInsereFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        mainPanel.add(foneField);

        mainPanel.add(emailLabel);
        mainPanel.add(emailField);

        // CEP NA TELA
        mainPanel.add(cepLabel);
        try {
            MaskFormatter mf = new MaskFormatter("#####-###");
            cepField = new JFormattedTextField(mf);
        } catch (ParseException ex) {
            Logger.getLogger(InterfaceInsereFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    }
    
    public void showInterface() {
        // Exibe a janela menor
        setVisible(true);
    }
}