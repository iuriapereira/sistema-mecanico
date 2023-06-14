package bancopoo;

import banco.TbCargo;
import banco.TbCidEst;
import banco.TbEstado;
import banco.TbLogradouro;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

class InterfaceInsereFuncionario extends JDialog {

    private final JFrame mainFrame;
    private JTextField nomeField;
    private JFormattedTextField documentoField;
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
    private JRadioButton sexoMasculino;
    private JRadioButton sexoFeminino;
    private JRadioButton sexoOutros;
    private JFormattedTextField dataNascimentoField;
    private Session session;
    private final JTextField usuarioField;
    private final JPasswordField senhaField;
    private Object passwordCheckBox;

    public InterfaceInsereFuncionario(JFrame mainFrame, Session session) {
        this.mainFrame = mainFrame;
        this.session = session;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        setTitle("Inserindo dados do funcionário");
        setResizable(false);

        Font fonte = new Font("Times New Roman", Font.BOLD, 14);

        // CONEXÃO COM O BANCO TB_ESTADO
        Criteria estd = session.createCriteria(TbEstado.class);
        ArrayList<TbEstado> estado = (ArrayList<TbEstado>) estd.list();

        // COMBOBOX DO ESTADO
        JComboBox<String> listEstado = new JComboBox<>();
        DefaultComboBoxModel<String> est = new DefaultComboBoxModel<>();
        est.addElement("Selecione..."); // PALAVRA QUE VAI FICAR ANTES DE APARACER AS LITA DE TODOS OS ESTADOS
        listEstado.setModel(est);
        listEstado.setFont(fonte);
        for (TbEstado descricao : estado) {
            listEstado.addItem(descricao.getEstSigla());
        }

        // CONEXÃO COM O BANCO TB_CIDEST
        Criteria cid = session.createCriteria(TbCidEst.class);
        ArrayList<TbCidEst> cidade = (ArrayList<TbCidEst>) cid.list();

        // COMBOBOX DA CIDADE
        JComboBox<String> listCidade = new JComboBox<>();
        listCidade.setFont(fonte);

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
        logr.addElement("Selecione..."); // PALAVRA QUE VAI FICAR ANTES DE APARACER A LISTA DE TODOS OS ESTADOS
        listLogradouro.setModel(logr);
        listLogradouro.setFont(fonte);
        for (TbLogradouro descricao : logradouro) {
            listLogradouro.addItem(descricao.getLogDescricao());
        }

        // CONEXÃO COM O BANCO TB_CARGO
        Criteria cag = session.createCriteria(TbCargo.class);
        ArrayList<TbCargo> cargo = (ArrayList<TbCargo>) cag.list();
        // COMBOBOX DO CARGO
        JComboBox<String> listCargo = new JComboBox<>();
        DefaultComboBoxModel<String> carg = new DefaultComboBoxModel<>();
        carg.addElement("Selecione..."); // PALAVRA QUE VAI FICAR ANTES DE APARACER A LISTA DE TODOS OS CARGOS
        listCargo.setModel(carg);
        listCargo.setFont(fonte);
        for (TbCargo descricao : cargo) {
            listCargo.addItem(descricao.getCarDescricao());
        }

        // PAINEL DA JANELA MENOR
        JPanel mainPanel = new JPanel(null); // DEFINE O LAYOUT COMO NULL

        JLabel cargoLabel = new JLabel("Cargo:");
        cargoLabel.setFont(fonte);
        JLabel nomeLabel = new JLabel("Nome:");
        nomeLabel.setFont(fonte);
        nomeField = new JTextField(20);
        nomeField.setFont(fonte);
        JLabel sexoLabel = new JLabel("Sexo:");
        sexoLabel.setFont(fonte);
        JLabel documentoLabel = new JLabel("CPF:");
        documentoLabel.setFont(fonte);
        JLabel dataNascimentoLabel = new JLabel("Data de Nascimento:");
        dataNascimentoLabel.setFont(fonte);
        JLabel fantasiaLabel = new JLabel("Nome Fantasia:");
        fantasiaLabel.setFont(fonte);
        fantasiaField = new JTextField(20);
        fantasiaField.setFont(fonte);
        JLabel rgieLabel = new JLabel("RG:");
        rgieLabel.setFont(fonte);
        JLabel foneLabel = new JLabel("Fone:");
        foneLabel.setFont(fonte);
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(fonte);
        emailField = new JTextField(20);
        emailField.setFont(fonte);
        JLabel cepLabel = new JLabel("CEP:");
        cepLabel.setFont(fonte);
        JLabel logradouroLabel = new JLabel("Logradouro:");
        logradouroLabel.setFont(fonte);
        JLabel enderecoLabel = new JLabel("Endereço:");
        enderecoLabel.setFont(fonte);
        enderecoField = new JTextField(20);
        enderecoField.setFont(fonte);
        JLabel numeroLabel = new JLabel("Número:");
        numeroLabel.setFont(fonte);
        numeroField = new JTextField(20);
        numeroField.setFont(fonte);
        JLabel complementoLabel = new JLabel("Complemento:");
        complementoLabel.setFont(fonte);
        complementoField = new JTextField(20);
        complementoField.setFont(fonte);
        JLabel bairroLabel = new JLabel("Bairro:");
        bairroLabel.setFont(fonte);
        bairroField = new JTextField(20);
        bairroField.setFont(fonte);
        JLabel estadoLabel = new JLabel("Estado:");
        estadoLabel.setFont(fonte);
        JLabel cidadeLabel = new JLabel("Cidade:");
        cidadeLabel.setFont(fonte);
        // DADOS DE LOGIN DO FUNCIONÁRIO
        JLabel usuarioLabel = new JLabel("Usuário:");
        usuarioLabel.setFont(fonte);
        usuarioField = new JTextField(20);
        usuarioField.setFont(fonte);
        JLabel senhaLabel = new JLabel("Senha:");
        senhaLabel.setFont(fonte);
        senhaField = new JPasswordField(8);
        senhaField.setFont(fonte);

        JCheckBox mostrarSenha = new JCheckBox("Mostrar senha");
        mostrarSenha.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mostrarSenha.isSelected()) {
                    senhaField.setEchoChar('\u0000'); // Caractere de eco nulo para mostrar a senha
                } else {
                    senhaField.setEchoChar('*'); // Caractere de eco '*' para ocultar a senha
                }
            }
        });

        sexoMasculino = new JRadioButton("Masculino");
        sexoMasculino.setBounds(80, 80, 90, 20);
        sexoMasculino.setSelected(true);
        sexoMasculino.setFont(fonte);

        sexoFeminino = new JRadioButton("Feminino");
        sexoFeminino.setBounds(170, 80, 85, 20);
        sexoFeminino.setFont(fonte);

        sexoOutros = new JRadioButton("Outro");
        sexoOutros.setBounds(255, 80, 80, 20);
        sexoOutros.setFont(fonte);

        ButtonGroup tipoSexoGroup = new ButtonGroup();
        tipoSexoGroup.add(sexoMasculino);
        tipoSexoGroup.add(sexoFeminino);
        tipoSexoGroup.add(sexoOutros);

        // BOTÃO CADASTRAR
        JButton cadastrarButton = new JButton();
        ImageIcon cads = new ImageIcon("src/resources/images/salvar.png");
        Image scaledCads = cads.getImage().getScaledInstance(100, 30, Image.SCALE_SMOOTH);
        cadastrarButton.setIcon(new ImageIcon(scaledCads));
        cadastrarButton.setBounds(70, 620, 100, 30);
        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String hql = "SELECT c.cepId FROM TbCidEst c WHERE c.tbCidade.cidDescricao = '" + listCidade.getSelectedItem() + "' AND c.tbEstado = '" + listEstado.getSelectedItem() + "'";
                Query query = session.createQuery(hql);

                String hql2 = "SELECT l.logId FROM TbLogradouro l WHERE l.logDescricao = '" + listLogradouro.getSelectedItem() + "'";
                Query query2 = session.createQuery(hql2);

                String hql3 = "SELECT c.carId FROM TbCargo c WHERE c.carDescricao = '" + listCargo.getSelectedItem() + "'";
                Query query3 = session.createQuery(hql3);
                int logId = (int) query2.uniqueResult();
                int cidId = (int) query.uniqueResult();
                int CargoId = (int) query3.uniqueResult();

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
                    tbentidade.setEntRgIe(rgieField.getText());
                    tbentidade.setEntFone(foneField.getText());
                    tbentidade.setEntEmail(emailField.getText());

                    ButtonModel selectedButtonModel = tipoSexoGroup.getSelection();
                    if (selectedButtonModel == sexoMasculino.getModel()) {
                        tbentidade.setEntSexo("M");
                    } else if (selectedButtonModel == sexoFeminino.getModel()) {
                        tbentidade.setEntSexo("F");
                    } else if (selectedButtonModel == sexoOutros.getModel()) {
                        tbentidade.setEntSexo("Outros");
                    }
                    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                    Date data = formato.parse(dataNascimentoField.getText());
                    tbentidade.setEntDtNasc(data);
                    tbentidade.setEntTipo("Fisico");
                    session.save(tbentidade);

                    banco.TbFuncionario tbfuncionario = new banco.TbFuncionario();
                    tbfuncionario.setTbEntidade(tbentidade);
                    Object cargo = session.load(TbCargo.class, CargoId);
                    tbfuncionario.setTbCargo((TbCargo) cargo);
                    tbfuncionario.setFuncUsuario(usuarioField.getText());
                    tbfuncionario.setFuncSenha(senhaField.getText());
                    session.save(tbfuncionario);

                    transaction.commit();
                    JOptionPane.showMessageDialog(null, "Funcionario Inserido com Sucesso!");
                } catch (HibernateException ex) {
                    transaction.rollback();
                    JOptionPane.showMessageDialog(null, "Ocorreu um erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    session.clear();
                } catch (ParseException ex) {
                    Logger.getLogger(InterfaceInsereFuncionario.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        // BOTÃO LIMPAR
        JButton limparCampos = new JButton();
        limparCampos = new JButton();
        ImageIcon limp = new ImageIcon("src/resources/images/limpar.png");
        Image scaledLimpar = limp.getImage().getScaledInstance(100, 30, Image.SCALE_SMOOTH);
        limparCampos.setIcon(new ImageIcon(scaledLimpar));
        limparCampos.setBounds(200, 620, 100, 30);
        limparCampos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limparCampos();
                listEstado.setSelectedIndex(0);
                listLogradouro.setSelectedIndex(0);
                listCidade.setSelectedIndex(0);
                listCargo.setSelectedIndex(0);
            }
        });

        // Adicione os componentes ao painel principal
        mainPanel.add(cargoLabel);
        mainPanel.add(listCargo);

        mainPanel.add(nomeLabel);
        mainPanel.add(nomeField);

        mainPanel.add(sexoLabel);
        mainPanel.add(sexoMasculino);
        mainPanel.add(sexoFeminino);
        mainPanel.add(sexoOutros);

        // CPF NA TELA
        mainPanel.add(documentoLabel);
        try {
            MaskFormatter mf = new MaskFormatter("###.###.###-##");
            documentoField = new JFormattedTextField(mf);
        } catch (ParseException ex) {
            Logger.getLogger(InterfaceInsereFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        documentoField.setFont(fonte);
        mainPanel.add(documentoField);

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
        dataNascimentoField.setFont(fonte);
        mainPanel.add(dataNascimentoField);

        mainPanel.add(fantasiaLabel);
        mainPanel.add(fantasiaField);

        // RG/IE NA TELA
        mainPanel.add(rgieLabel);
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
        rgieField = new JFormattedTextField(maskFormatter);
        rgieField.setFont(fonte);
        mainPanel.add(rgieField);

        // TELEFONE NA TELA
        mainPanel.add(foneLabel);
        try {
            MaskFormatter mf = new MaskFormatter("(##) #####-####");
            foneField = new JFormattedTextField(mf);
        } catch (ParseException ex) {
            Logger.getLogger(InterfaceInsereFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        foneField.setFont(fonte);
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
        cepField.setFont(fonte);
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

        // DADOS DE LOGIN
        mainPanel.add(usuarioLabel);
        mainPanel.add(usuarioField);

        mainPanel.add(senhaLabel);
        mainPanel.add(senhaField);
        mainPanel.add(mostrarSenha);

        // botão para limpar
        mainPanel.add(cadastrarButton);
        mainPanel.add(limparCampos);

        // Define as coordenadas de posicionamento dos componentes
        int x = 10;
        int y = 20;
        int yGap = 30;
        int labelWidth = 150;
        int fieldWidth = 200;

        cargoLabel.setBounds(x, y, labelWidth, 20);
        listCargo.setBounds(x + labelWidth + 10, y, fieldWidth, 20);

        y += yGap;
        nomeLabel.setBounds(x, y, labelWidth, 20);
        nomeField.setBounds(x + labelWidth + 10, y, fieldWidth, 20);

        y += yGap;
        sexoLabel.setBounds(x, y, labelWidth, 20);

        y += yGap;
        documentoLabel.setBounds(x, y, labelWidth, 20);
        documentoField.setBounds(x + labelWidth + 10, y, 105, 20);

        y += yGap;
        dataNascimentoLabel.setBounds(x, y, labelWidth, 20);
        dataNascimentoField.setBounds(x + labelWidth + 10, y, 105, 20);

        y += yGap;
        fantasiaLabel.setBounds(x, y, labelWidth, 20);
        fantasiaField.setBounds(x + labelWidth + 10, y, fieldWidth, 20);

        y += yGap;
        rgieLabel.setBounds(x, y, labelWidth, 20);
        rgieField.setBounds(x + labelWidth + 10, y, 105, 20);

        y += yGap;
        foneLabel.setBounds(x, y, labelWidth, 20);
        foneField.setBounds(x + labelWidth + 10, y, 105, 20);

        y += yGap;
        emailLabel.setBounds(x, y, labelWidth, 20);
        emailField.setBounds(x + labelWidth + 10, y, fieldWidth, 20);

        y += yGap;
        cepLabel.setBounds(x, y, labelWidth, 20);
        cepField.setBounds(x + labelWidth + 10, y, 105, 20);

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

        y += yGap;
        usuarioLabel.setBounds(x, y, labelWidth, 20);
        usuarioField.setBounds(x + labelWidth + 10, y, fieldWidth, 20);

        y += yGap;
        senhaLabel.setBounds(x, y, labelWidth, 20);
        senhaField.setBounds(x + labelWidth + 10, y, fieldWidth, 20);

        y += yGap;
        mostrarSenha.setBounds(x + labelWidth + 10, y, fieldWidth, 20);

        // Defina o tamanho do painel principal
        mainPanel.setPreferredSize(new Dimension(380, 660));

        // Adicione o painel principal à janela de diálogo
        getContentPane().add(mainPanel);
        pack();
        setLocationRelativeTo(mainFrame);
    }

    private void limparCampos() {
        nomeField.setText("");
        documentoField.setText("");
        fantasiaField.setText("");
        rgieField.setText("");
        foneField.setText("");
        emailField.setText("");
        cepField.setText("");
        enderecoField.setText("");
        numeroField.setText("");
        complementoField.setText("");
        bairroField.setText("");
        dataNascimentoField.setText("");
        usuarioField.setText("");
        senhaField.setText("");
    }

    public void showInterface() {
        // Exibe a janela menor
        setVisible(true);
    }
}
