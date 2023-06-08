package bancopoo;

import banco.TbCargo;
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

class InterfaceInsereFuncionario extends JDialog {

    private final JFrame mainFrame;
    private JTextField nomeField;
    private JTextField documentoField;
    private JTextField fantasiaField;
    private JTextField rgieField;
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
    private JRadioButton sexoMasculino;
    private JRadioButton sexoFeminino;
    private JRadioButton sexoOutros;
    private JTextField dataNascimentoField;
    private JButton limparCamposButton;
    private Session session;
    private final JTextField usuarioField;
    private final JPasswordField senhaField;

    public InterfaceInsereFuncionario(JFrame mainFrame, Session session) {
        this.mainFrame = mainFrame;
        this.session = session;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        setTitle("Inserindo dados do funcionário");

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
        logr.addElement("Selecione..."); // PALAVRA QUE VAI FICAR ANTES DE APARACER A LISTA DE TODOS OS ESTADOS
        listLogradouro.setModel(logr);
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
        for (TbCargo descricao : cargo) {
            listCargo.addItem(descricao.getCarDescricao());
        }

        // PAINEL DA JANELA MENOR
        JPanel mainPanel = new JPanel(null); // DEFINE O LAYOUT COMO NULL

        JLabel cargoLabel = new JLabel("Cargo:");
        JLabel nomeLabel = new JLabel("Nome:");
        nomeField = new JTextField(20);
        JLabel sexoLabel = new JLabel("Sexo:");
        JLabel documentoLabel = new JLabel("CPF:");
        documentoField = new JTextField(20);
        JLabel dataNascimentoLabel = new JLabel("Data de Nascimento:");
        dataNascimentoField = new JTextField(20);
        JLabel fantasiaLabel = new JLabel("Nome Fantasia:");
        fantasiaField = new JTextField(20);
        JLabel rgieLabel = new JLabel("RG:");
        rgieField = new JTextField(20);
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
        // DADOS DE LOGIN DO FUNCIONÁRIO
        JLabel usuarioLabel = new JLabel("Usuário:");
        usuarioField = new JTextField(20);
        JLabel senhaLabel = new JLabel("Senha:");
        senhaField = new JPasswordField(8);

        sexoMasculino = new JRadioButton("Masculino");
        sexoMasculino.setBounds(80, 80, 90, 20);
        sexoMasculino.setSelected(true);

        sexoFeminino = new JRadioButton("Feminino");
        sexoFeminino.setBounds(170, 80, 80, 20);

        sexoOutros = new JRadioButton("Outro");
        sexoOutros.setBounds(250, 80, 60, 20);

        ButtonGroup tipoSexoGroup = new ButtonGroup();
        tipoSexoGroup.add(sexoMasculino);
        tipoSexoGroup.add(sexoFeminino);
        tipoSexoGroup.add(sexoOutros);

        // BOTÃO CADASTRAR
        JButton cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.setBounds(70, 590, 100, 30);
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

                    ButtonModel selectedButtonModel = tipoClienteGroup.getSelection();
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
                } catch (HibernateException ex) {
                    transaction.rollback();
                    JOptionPane.showMessageDialog(null, "Ocorreu um erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                } catch (ParseException ex) {
                    Logger.getLogger(InterfaceInsereFuncionario.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    );
        
        // BOTÃO LIMPAR
        limparCamposButton  = new JButton("Limpar");

    limparCamposButton.setBounds (

    200, 590, 100, 30);
    limparCamposButton.addActionListener ( 
        new ActionListener() {
            @Override
        public void actionPerformed
        (ActionEvent e
        
            ) {
                limparCampos();
            listEstado.setSelectedIndex(0);
            listLogradouro.setSelectedIndex(0);
            listCidade.setSelectedIndex(0);
            listCargo.setSelectedIndex(0);
        }
    }
    );

        // Define as coordenadas de posicionamento dos componentes
        int x = 10;
    int y = 20;
    int yGap = 30;
    int labelWidth = 150;
    int fieldWidth = 200;

    cargoLabel.setBounds (x, y, labelWidth, 

    20);
    listCargo.setBounds (x 
    + labelWidth + 10, y, fieldWidth, 20);
        
        y += yGap ;

    nomeLabel.setBounds (x, y, labelWidth, 

    20);
    nomeField.setBounds (x 
    + labelWidth + 10, y, fieldWidth, 20);
        
        y += yGap ;

    sexoLabel.setBounds (x, y, labelWidth, 
    20);

        y += yGap ;

    documentoLabel.setBounds (x, y, labelWidth, 

    20);
    documentoField.setBounds (x 
    + labelWidth + 10, y, fieldWidth, 20);
        
        y += yGap ;

    dataNascimentoLabel.setBounds (x, y, labelWidth, 

    20);
    dataNascimentoField.setBounds (x 
    + labelWidth + 10, y, fieldWidth, 20);
        
        y += yGap ;

    fantasiaLabel.setBounds (x, y, labelWidth, 

    20);
    fantasiaField.setBounds (x 
    + labelWidth + 10, y, fieldWidth, 20);

        y += yGap ;

    rgieLabel.setBounds (x, y, labelWidth, 

    20);
    rgieField.setBounds (x 
    + labelWidth + 10, y, fieldWidth, 20);

        y += yGap ;

    foneLabel.setBounds (x, y, labelWidth, 

    20);
    foneField.setBounds (x 
    + labelWidth + 10, y, fieldWidth, 20);

        y += yGap ;

    emailLabel.setBounds (x, y, labelWidth, 

    20);
    emailField.setBounds (x 
    + labelWidth + 10, y, fieldWidth, 20);

        y += yGap ;

    cepLabel.setBounds (x, y, labelWidth, 

    20);
    cepField.setBounds (x 
    + labelWidth + 10, y, fieldWidth, 20);

        y += yGap ;

    logradouroLabel.setBounds (x, y, labelWidth, 

    20);
    listLogradouro.setBounds (x 
    + labelWidth + 10, y, fieldWidth, 20);

        y += yGap ;

    enderecoLabel.setBounds (x, y, labelWidth, 

    20);
    enderecoField.setBounds (x 
    + labelWidth + 10, y, fieldWidth, 20);

        y += yGap ;

    numeroLabel.setBounds (x, y, labelWidth, 

    20);
    numeroField.setBounds (x 
    + labelWidth + 10, y, fieldWidth, 20);

        y += yGap ;

    complementoLabel.setBounds (x, y, labelWidth, 

    20);
    complementoField.setBounds (x 
    + labelWidth + 10, y, fieldWidth, 20);

        y += yGap ;

    bairroLabel.setBounds (x, y, labelWidth, 

    20);
    bairroField.setBounds (x 
    + labelWidth + 10, y, fieldWidth, 20);

        y += yGap ;

    estadoLabel.setBounds (x, y, labelWidth, 

    20);
    listEstado.setBounds (x 
    + labelWidth + 10, y, fieldWidth, 20);
        
        y += yGap ;

    cidadeLabel.setBounds (x, y, labelWidth, 

    20);
    listCidade.setBounds (x 
    + labelWidth + 10, y, fieldWidth, 20);
        
        y += yGap ;

    usuarioLabel.setBounds (x, y, labelWidth, 

    20);
    usuarioField.setBounds (x 
    + labelWidth + 10, y, fieldWidth, 20);
        
        y += yGap ;

    senhaLabel.setBounds (x, y, labelWidth, 

    20);
    senhaField.setBounds (x 

    + labelWidth + 10, y, fieldWidth, 20);
        
        // Adicione os componentes ao painel principal
    mainPanel.add (cargoLabel);

    mainPanel.add (listCargo);

    mainPanel.add (nomeLabel);

    mainPanel.add (nomeField);

    mainPanel.add (sexoLabel);

    mainPanel.add (sexoMasculino);

    mainPanel.add (sexoFeminino);

    mainPanel.add (sexoOutros);

    mainPanel.add (documentoLabel);

    mainPanel.add (documentoField);

    mainPanel.add (dataNascimentoLabel);

    mainPanel.add (dataNascimentoField);

    mainPanel.add (fantasiaLabel);

    mainPanel.add (fantasiaField);

    mainPanel.add (rgieLabel);

    mainPanel.add (rgieField);

    mainPanel.add (foneLabel);

    mainPanel.add (foneField);

    mainPanel.add (emailLabel);

    mainPanel.add (emailField);

    mainPanel.add (cepLabel);

    mainPanel.add (cepField);

    mainPanel.add (logradouroLabel);

    mainPanel.add (listLogradouro);

    mainPanel.add (enderecoLabel);

    mainPanel.add (enderecoField);

    mainPanel.add (numeroLabel);

    mainPanel.add (numeroField);

    mainPanel.add (complementoLabel);

    mainPanel.add (complementoField);

    mainPanel.add (bairroLabel);

    mainPanel.add (bairroField);

    // Adiciona o JComboBox ao JFrame
    mainPanel.add (estadoLabel);

    mainPanel.add (listEstado);

    mainPanel.add (cidadeLabel);

    mainPanel.add (listCidade);

    // DADOS DE LOGIN
    mainPanel.add (usuarioLabel);

    mainPanel.add (usuarioField);

    mainPanel.add (senhaLabel);

    mainPanel.add (senhaField);

    // botão para limpar
    mainPanel.add (cadastrarButton);

    mainPanel.add (limparCamposButton);

    // Defina o tamanho do painel principal
    mainPanel.setPreferredSize (

    new Dimension(380, 630));

        // Adicione o painel principal à janela de diálogo
    getContentPane()

    .add(mainPanel);
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
