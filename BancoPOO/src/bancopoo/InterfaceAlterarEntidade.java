package bancopoo;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class InterfaceAlterarEntidade<T> extends JDialog{
    private JFrame panelFrame;
    private JTextField nomeField;
    private JFormattedTextField documentoField = new JFormattedTextField();
    private JTextField fantasiaField;
    private JFormattedTextField rgieField;
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
    private JFormattedTextField dataNascimentoField;
    private JRadioButton pessoaFisicaRadioButton;
    private JRadioButton pessoaJuridicaRadioButton;
    private JRadioButton sexoMasculino;
    private JRadioButton sexoFeminino;
    private JRadioButton sexoOutros;
    private Session session;
    private String bairroId;
    private String endPId;
    private String endId;
    private String cpfCnpj;
    private SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
    
    public InterfaceAlterarEntidade(JFrame panelFrame, Session session, String cpf, String tipo) throws ParseException{
        this.panelFrame = panelFrame;
        this.session = session;
        
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setTitle("Alterando dados do cliente");
        
        Font fonte = new Font("Times New Roman", Font.ROMAN_BASELINE, 14);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                // Habilita panelFrame
                panelFrame.setEnabled(true);
                panelFrame.toFront();
                
            }
        });
        
        // CONEXÃO COM O BANCO TB_ESTADO
        
        String hql = "SELECT est.estSigla FROM TbEstado est";
        Query query = session.createQuery(hql);
        List<String> estados = (List<String>) query.list();
        
        // COMBOBOX DO ESTADO
        JComboBox<String> listEstado = new JComboBox<>();
        DefaultComboBoxModel<String> est = new DefaultComboBoxModel<>();
        est.addElement("Selecione...");
        listEstado.setModel(est); 
        for (String estado : estados) {
            listEstado.addItem(estado);
        }
        
        // COMBOBOX DA CIDADE
        JComboBox<String> listCidade = new JComboBox<>();
        
        listEstado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateEstado(listEstado, listCidade);
            }
        });
        
        // CONEXÃO COM O BANCO TB_LOGRADOURO
        hql = "SELECT log.logDescricao FROM TbLogradouro log";
        query = session.createQuery(hql);
        java.util.List<String> logradouros = (java.util.List<String>) query.list();
        
        // COMBOBOX DO LOGRADOURO
        JComboBox<String> listLogradouro = new JComboBox<>();
        DefaultComboBoxModel<String> logr = new DefaultComboBoxModel<>();
        logr.addElement("Selecione..."); // PALAVRA QUE VAI FICAR ANTES DE APARACER A LISTA DE TODOS OS ESTADOS
        listLogradouro.setModel(logr); 
        for (String logradouro : logradouros) {
            logr.addElement(logradouro);
        }
        listLogradouro.setModel(logr);
        
        JPanel mainPanel = new JPanel(null); // Define o layout como null

        JLabel nomeLabel = new JLabel("Nome:"); 
        nomeLabel.setFont(fonte);
        nomeField = new JTextField(20);
        nomeField.setFont(fonte);
        JLabel sexoLabel = new JLabel("Sexo:");
        sexoLabel.setFont(fonte);
        JLabel documentoLabel = new JLabel();
        documentoLabel.setFont(fonte);
        JLabel dataNascimentoLabel = new JLabel("Data de Nascimento:");
        JLabel fantasiaLabel = new JLabel("Nome Fantasia:");
        fantasiaField = new JTextField(20);
        JLabel rgieLabel = new JLabel("RG/Inscrição Estadual:");
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
        
        

        pessoaFisicaRadioButton = new JRadioButton("Pessoa Física");
        pessoaFisicaRadioButton.setBounds(10, 10, 150, 20);
        pessoaFisicaRadioButton.setSelected(true);

        pessoaJuridicaRadioButton = new JRadioButton("Pessoa Jurídica");
        pessoaJuridicaRadioButton.setBounds(170, 10, 150, 20);
        
        ButtonGroup tipoClienteGroup = new ButtonGroup();
        tipoClienteGroup.add(pessoaFisicaRadioButton);
        tipoClienteGroup.add(pessoaJuridicaRadioButton);
        
        sexoMasculino = new JRadioButton("Masculino");
        sexoMasculino.setBounds(80, 70, 90, 20);
        sexoMasculino.setSelected(true);

        sexoFeminino = new JRadioButton("Feminino");
        sexoFeminino.setBounds(170, 70, 80, 20);
        
        sexoOutros = new JRadioButton("Outro");
        sexoOutros.setBounds(250, 70, 60, 20);
        
        ButtonGroup tipoSexoGroup = new ButtonGroup();
        tipoSexoGroup.add(sexoMasculino);
        tipoSexoGroup.add(sexoFeminino);
        tipoSexoGroup.add(sexoOutros);

        JButton alterarButton = new JButton();
        ImageIcon cads = new ImageIcon("src/resources/images/salvar.png");
        Image scaledCads = cads.getImage().getScaledInstance(100, 30, Image.SCALE_SMOOTH);
        alterarButton.setIcon(new ImageIcon(scaledCads));
        alterarButton.addActionListener(new ActionListener() {
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
                    String dataNascimento = new String();
                    if(dataNascimentoField.getValue() == null){
                        dataNascimento = null;
                    } else {
                        String data = dataNascimentoField.getText();
                        dataNascimento = "'" + data.substring(6, 10) + "-" + data.substring(3, 5) + "-" + data.substring(3, 5) + "'";
                    }
                    String sexo = new String();
                    if(sexoFeminino.isSelected()){
                        sexo = "F";
                    }else if(sexoMasculino.isSelected()){
                        sexo = "M";
                    }else if(sexoOutros.isSelected()){
                        sexo = "Outros";
                    }else {
                        sexo = null;
                    }
                    
                    // Consulta que ira atualizar a entidade
                    String hqlUpEntidade = "UPDATE TbEntidade ent set ent.entNome = '" +  nomeField.getText() + "', " +
                                                   "ent.entNomeFantasia = '" +  fantasiaField.getText() + "', " +
                                                   "ent.entRgIe = '" +  rgieField.getText() + "', " +
                                                   "ent.entFone = '" +  foneField.getText() + "', " +
                                                   "ent.entEmail = '" +  emailField.getText() + "', " +
                                                   "ent.entSexo = '" +  sexo + "', " +
                                                   "ent.entDtNasc = " + dataNascimento + " "+
                                                   "WHERE ent.entCpfCnpj = '" +  cpfCnpj + "'";
                    
                    // Consulta que ira atualizar o endereço
                    String hqlUpEndereco = "UPDATE TbEndereco ende SET ende.endNumero = '" + numeroField.getText() + "', " +
                                                   "ende.endComplemento = '" + complementoField.getText() + "' " +
                                                   "WHERE ende.endId = '" + endId + "'";
                    
                    // Consulta que ira atualizar o endereço postal
                    String hqlUpEndPostal = "UPDATE TbEndPostal endP SET endP.tbLogradouro = '" + logId + "', " +
                                                   "endP.endPNomerua = '" + enderecoField.getText() + "', " +
                                                   "endP.endPCep = '" + cepField.getText() + "', " +
                                                   "endP.tbCidEst = '" + cidId + "' " +
                                                   "WHERE endP.endPId = '" + endPId + "'";
                    
                    // Consulta que ira atualizar o bairro
                    String hqlUpBairro = "UPDATE TbBairro bai SET baiDescricao = '" + bairroField.getText() + "' WHERE baiId = '" + bairroId +"'";
                            
                    //Criando as querys
                    Query updateQueryEntidade = session.createQuery(hqlUpEntidade);
                    Query updateQueryEndereco = session.createQuery(hqlUpEndereco);
                    Query updateQueryEndPostal = session.createQuery(hqlUpEndPostal);
                    Query updateQueryBairro = session.createQuery(hqlUpBairro);
                    
                    //Executando as querys
                    updateQueryEntidade.executeUpdate();
                    updateQueryEndereco.executeUpdate();
                    updateQueryEndPostal.executeUpdate();
                    updateQueryBairro.executeUpdate();
                    
                    // Commit caso tudo ocorra bem
                    transaction.commit();
                    JOptionPane.showMessageDialog(null, "Cleinte Alterado com Sucesso!");
                    dispose();

                } catch (HibernateException ex) {
                    transaction.rollback();
                    JOptionPane.showMessageDialog(null, "Ocorreu um erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        if(tipo.equals("F")){
            JButton alterarFuncionario = new JButton();
            alterarFuncionario.addActionListener(e -> {
                InterfaceAlterarCredenciaisFuncionario alterar;
                    alterar = new InterfaceAlterarCredenciaisFuncionario(this, session, cpf);
                    alterar.showInterface();
            });
            
            alterarButton.setBounds(70, 540, 100, 30);
            alterarFuncionario.setBounds(200, 540, 100, 30);

            mainPanel.add(alterarFuncionario);
            mainPanel.add(alterarButton);
        } else {
            alterarButton.setBounds(140, 540, 100, 30);
            mainPanel.add(alterarButton);
        }
        
        pessoaJuridicaRadioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    MaskFormatter mf = new MaskFormatter("##.###.###/####-##");
                    documentoField.setFormatterFactory(new DefaultFormatterFactory(mf));
                } catch (ParseException ex) {
                    Logger.getLogger(InterfaceInsereFuncionario.class.getName()).log(Level.SEVERE, null, ex);
                }
                mainPanel.remove(documentoField); // Remova o documentoField existente do mainPanel
                mainPanel.add(documentoField); // Adicione o documentoField atualizado ao mainPanel
                mainPanel.revalidate(); // Revalide o mainPanel para atualizar a exibição
                mainPanel.repaint(); // Repinte o mainPanel para atualizar a exibição
                documentoLabel.setText("CNPJ:");
                dataNascimentoField.setEnabled(false);
                dataNascimentoField.setText("");
                sexoMasculino.setEnabled(false);
                sexoFeminino.setEnabled(false);
                sexoOutros.setEnabled(false);
            }
        });

        pessoaFisicaRadioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    MaskFormatter mf = new MaskFormatter("###.###.###-##");
                    documentoField.setFormatterFactory(new DefaultFormatterFactory(mf));
                } catch (ParseException ex) {
                    Logger.getLogger(InterfaceInsereFuncionario.class.getName()).log(Level.SEVERE, null, ex);
                }
                mainPanel.remove(documentoField); // Remova o documentoField existente do mainPanel
                mainPanel.add(documentoField); // Adicione o documentoField atualizado ao mainPanel
                mainPanel.revalidate(); // Revalide o mainPanel para atualizar a exibição
                mainPanel.repaint(); // Repinte o mainPanel para atualizar a exibição
                documentoLabel.setText("CPF:");
                dataNascimentoField.setEnabled(true);
                sexoMasculino.setEnabled(true);
                sexoFeminino.setEnabled(true);
                sexoOutros.setEnabled(true);
            }
        });
        
        
        
        // Adicione os componentes ao painel principal
        mainPanel.add(pessoaFisicaRadioButton);
        mainPanel.add(pessoaJuridicaRadioButton);

        mainPanel.add(nomeLabel);
        mainPanel.add(nomeField);
        
        mainPanel.add(documentoLabel);

        mainPanel.add(sexoLabel);
        mainPanel.add(sexoMasculino);
        mainPanel.add(sexoFeminino);
        mainPanel.add(sexoOutros);

        mainPanel.add(fantasiaLabel);
        mainPanel.add(fantasiaField);

        // RG/IE NA TELA
        mainPanel.add(rgieLabel);
        
        
        // JFormattedTextField usando o MaskFormatter
        
        MaskFormatter rgie = new MaskFormatter("##############"); // MaskFormatter para permitir apenas números
        rgieField = new JFormattedTextField();
        
        mainPanel.add(rgieField);

        // DATA DE NASCIMENTO NA TELA 
        mainPanel.add(dataNascimentoLabel);
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            MaskFormatter maskFormatter = new MaskFormatter("##/##/####");
            maskFormatter.setPlaceholderCharacter('_');
            JFormattedTextField.AbstractFormatter formatter = new JFormattedTextField.AbstractFormatter() {
                @Override
                public Object stringToValue(String text) throws ParseException {
                    return dateFormat.parseObject(text);
                }

                @Override
                public String valueToString(Object value) throws ParseException {
                    if (value instanceof java.util.Date) {
                        return dateFormat.format(value);
                    }
                    return "";
                }
            };
            dataNascimentoField = new JFormattedTextField(formatter);
            dataNascimentoField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(maskFormatter));
        } catch (ParseException ex) {
            Logger.getLogger(InterfaceInsereFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        mainPanel.add(dataNascimentoField);
        
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
        

        // Define as coordenadas de posicionamento dos componentes
        int x = 10;
        int y = 40;
        int yGap = 30;
        int labelWidth = 150;
        int fieldWidth = 200;

        nomeLabel.setBounds(x, y, labelWidth, 20);
        nomeField.setBounds(x + labelWidth + 10, y, fieldWidth, 20);
        
        y += yGap;
        sexoLabel.setBounds(x, y, labelWidth, 20);
        
        y += yGap;
        documentoLabel.setBounds(x, y, labelWidth, 20);
        documentoField.setBounds(x + labelWidth + 10, y, fieldWidth, 20);
        
        y += yGap;
        dataNascimentoLabel.setBounds(x, y, labelWidth, 20);
        dataNascimentoField.setBounds(x + labelWidth + 10, y, fieldWidth, 20);
        
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
        pessoaFisicaRadioButton.doClick();

        // Obtendo e inserindo todos os dados do cliente
        hql = "SELECT ent.entTipo, ent.entNome, ent.entNomeFantasia, ent.entSexo, ent.entCpfCnpj, "
                + "ent.entDtNasc, ent.entRgIe, ent.entFone, ent.entEmail, ent.tbEndereco.tbEndPostal.endPCep, "
                + "ent.tbEndereco.tbEndPostal.tbLogradouro.logDescricao, ent.tbEndereco.tbEndPostal.endPNomerua, "
                + "ent.tbEndereco.endNumero, ent.tbEndereco.endComplemento, ent.tbEndereco.tbEndPostal.tbBairro.baiDescricao, "
                + "ent.tbEndereco.tbEndPostal.tbCidEst.tbEstado.estSigla, ent.tbEndereco.tbEndPostal.tbCidEst.tbCidade.cidDescricao, "
                + "ent.tbEndereco.endId, ent.tbEndereco.tbEndPostal.endPId, ent.tbEndereco.tbEndPostal.tbBairro.baiId "
                + "FROM TbEntidade ent WHERE ent.entCpfCnpj = '" + cpf + "'";
        
        query = session.createQuery(hql);
        List<Object[]> results = query.list();
        for (Object[] result : results) {
            if(result[0].toString().equals("Fisico")){
                pessoaFisicaRadioButton.doClick();
            }else{
                pessoaJuridicaRadioButton.doClick();
            }
            pessoaJuridicaRadioButton.setEnabled(false);
            pessoaFisicaRadioButton.setEnabled(false);
            
            nomeField.setText(result[1].toString());
            fantasiaField.setText(result[2].toString());
  
            if(result[3] == null){
                sexoMasculino.setEnabled(false);
                sexoFeminino.setEnabled(false);
                sexoOutros.setEnabled(false);
            } else if(result[3].equals("M")){
                sexoMasculino.doClick();
            } else if(result[3].equals("F")){
                sexoFeminino.doClick();
            } else{
                sexoOutros.doClick();
            }
            documentoField.setText(result[4].toString());
            documentoField.setEditable(false);
            cpfCnpj = result[4].toString();
            
            if(result[5] == null){
                dataNascimentoField.setEditable(false);
            } else {
                Date data = (Date) result[5];
                String dataContent = formato.format(data);
                dataNascimentoField.setText(dataContent.replace("-", ""));
            }

            rgieField.setText(result[6].toString().replace("_", ""));
            foneField.setText(result[7].toString());
            emailField.setText(result[8].toString());
            cepField.setText(result[9].toString());
            listLogradouro.setSelectedItem(result[10]);
            enderecoField.setText(result[11].toString());
            numeroField.setText(result[12].toString());
            complementoField.setText(result[13].toString());
            bairroField.setText(result[14].toString());
            listEstado.setSelectedItem(result[15]);
            listCidade.setSelectedItem(result[16]);
            endId = result[17].toString();
            endPId = result[18].toString();
            bairroId = result[19].toString();
        }
        
        // Defina o tamanho do painel principal
        mainPanel.setPreferredSize(new Dimension(380, 580));

        // Adicione o painel principal à janela de diálogo
        getContentPane().add(mainPanel);
        pack();
        setLocationRelativeTo((Component) panelFrame);
    }
    
    private void updateEstado(JComboBox listEstado, JComboBox listCidade){
                String selectedEstado = (String) listEstado.getSelectedItem();
                // Obter as cidades correspondentes ao estado selecionado
                String hql = "SELECT ce.tbCidade.cidDescricao FROM TbCidEst ce WHERE ce.tbEstado.estSigla = '" + selectedEstado + "'";
                Query query = session.createQuery(hql);
                List<String> cidades = (List<String>) query.list();

                DefaultComboBoxModel<String> modelEst = new DefaultComboBoxModel<>();
                modelEst.addElement("Selecione..."); // PALAVRA QUE VAI FICAR ANTES DE APARACER AS LITA DE TODOS OS ESTADOS
                for (String cidade : cidades) {
                    modelEst.addElement(cidade);
                }
                //ArrayList<TbEstado> estado = (ArrayList<TbEstado>) estd.list();
                
                listCidade.setModel(modelEst);
                

                // Atualizar a interface
                revalidate();
                repaint();
    }
    
    public void showInterface() {
        // Exibe a janela menor
        panelFrame.setEnabled(false);
        setVisible(true);
    }
}
