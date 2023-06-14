package bancopoo;

import banco.TbFornecedor;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.DefaultComboBoxModel;
import javax.swing.text.NumberFormatter;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

class InterfaceInsereEstoque extends JDialog {

    private final JFrame mainFrame;
    private final JTextField descricaoField;
    private final JFormattedTextField minimoField;
    private final JFormattedTextField maximoField;
    private final JFormattedTextField custoField;
    private final JFormattedTextField lucroField;
    private final JFormattedTextField finalField;
    private Session session;

    public InterfaceInsereEstoque(JFrame mainFrame, Session session) {
        this.mainFrame = mainFrame;
        this.session = session;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        setTitle("Inserindo Produto");
        setResizable(false);
        
        Font fonte = new Font("Times New Roman", Font.BOLD, 16);
        
        // COMBOBOX DO TIPO DE UNIDADE DO PRODUTO
        JComboBox<String> listUnidade = new JComboBox<>();
        DefaultComboBoxModel<String> prod = new DefaultComboBoxModel<>();
        prod.addElement("Selecione..."); // PALAVRA QUE VAI FICAR ANTES DE APARACER A LISTA DE TODOS OS ESTADOS
        prod.addElement("UN");
        prod.addElement("KG");
        prod.addElement("PC");
        prod.addElement("MT");
        listUnidade.setFont(fonte);
        listUnidade.setModel(prod);

        // CONEXÃO COM O BANCO TB_FORNECEDOR
        Criteria estd = session.createCriteria(TbFornecedor.class);
        ArrayList<TbFornecedor> fornecedor = (ArrayList<TbFornecedor>) estd.list();
        // COMBOBOX DO FORNECEDOR
        JComboBox<String> listFornecedor = new JComboBox<>();
        DefaultComboBoxModel<String> forn = new DefaultComboBoxModel<>();
        forn.addElement("Selecione..."); // PALAVRA QUE VAI FICAR ANTES DE APARACER AS LITA DE TODOS OS ESTADOS
        listFornecedor.setModel(forn);
        for (TbFornecedor descricao : fornecedor) {
            listFornecedor.addItem(descricao.getTbEntidade().getEntNome());
        }
        listFornecedor.setFont(fonte);

        // Define o formato para números de ponto flutuante
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        decimalFormat.setParseBigDecimal(true);

        // Cria um NumberFormatter com o formato definido
        NumberFormatter formatter = new NumberFormatter(decimalFormat);
        formatter.setValueClass(Float.class);
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);

        // Cria os campos de entrada formatados
        minimoField = new JFormattedTextField(formatter);
        minimoField.setFont(fonte);
        maximoField = new JFormattedTextField(formatter);
        maximoField.setFont(fonte);
        custoField = new JFormattedTextField(formatter);
        custoField.setFont(fonte);
        lucroField = new JFormattedTextField(formatter);
        lucroField.setFont(fonte);
        finalField = new JFormattedTextField(formatter);
        finalField.setFont(fonte);
        JLabel fornecedorLabel = new JLabel("Fornecedor");
        fornecedorLabel.setFont(fonte);

        // PAINEL DA JANELA MENOR
        JPanel mainPanel = new JPanel(null); // DEFINE O LAYOUT COMO NULL

        JLabel unidadeLabel = new JLabel("Unidade");
        unidadeLabel.setFont(fonte);
        JLabel descricaoLabel = new JLabel("Descrição do Produto");
        descricaoLabel.setFont(fonte);
        descricaoField = new JTextField(20);
        descricaoField.setFont(fonte);
        JLabel minimoLabel = new JLabel("Estoque Mínimo"); // float
        minimoLabel.setFont(fonte);
        JLabel maximoLabel = new JLabel("Estoque Atual"); // float
        maximoLabel.setFont(fonte);
        JLabel custoLabel = new JLabel("Valor Custo"); // float
        custoLabel.setFont(fonte);
        JLabel lucroLabel = new JLabel("Margem Lucro(%)"); // float 
        lucroLabel.setFont(fonte);
        JLabel finalLabel = new JLabel("VALOR FINAL:"); // float
        finalLabel.setFont(fonte);

        custoField.addPropertyChangeListener("value", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                atualizarValorTotal();
            }
        });

        // Define um ouvinte de eventos para o campo "lucroField"
        lucroField.addPropertyChangeListener("value", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                atualizarValorTotal();
            }
        });

        // BOTÃO CADASTRAR
        JButton cadastrar = new JButton();
        ImageIcon cads = new ImageIcon("src/resources/images/salvar.png");
        Image scaledCads = cads.getImage().getScaledInstance(100, 30, Image.SCALE_SMOOTH);
        cadastrar.setIcon(new ImageIcon(scaledCads));
        cadastrar.setBounds(70, 270, 100, 30);
        cadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Transaction transaction = session.beginTransaction();
                try {
                    String hql = "SELECT f.forId FROM TbFornecedor f WHERE f.tbEntidade.entNome = '" + listFornecedor.getSelectedItem() + "'";
                    Query query = session.createQuery(hql);

                    int fornId = (int) query.uniqueResult();

                    banco.TbPeca tbpeca = new banco.TbPeca();
                    tbpeca.setPeDescricao(descricaoField.getText());
                    tbpeca.setPeQuantMin(Float.parseFloat(finalField.getText().replace(",", ".")));
                    session.save(tbpeca);

                    Object forne = session.load(TbFornecedor.class, fornId);
                    banco.TbFornecedorHasPeca tbfornecedor = new banco.TbFornecedorHasPeca();
                    tbfornecedor.setTbFornecedor((TbFornecedor) forne);
                    tbfornecedor.setTbPeca(tbpeca);
                    session.save(tbfornecedor);

                    banco.TbEstoque tbestoque = new banco.TbEstoque();
                    tbestoque.setTbFornecedorHasPeca(tbfornecedor);
                    tbestoque.setEstoMedida((String) listUnidade.getSelectedItem());
                    tbestoque.setEstoMargeLucro(Float.parseFloat(lucroField.getText().replace(",", ".")));
                    tbestoque.setEstoQuantidade(Float.parseFloat(finalField.getText().replace(",", ".")));
                    tbestoque.setEstoValorUni(Float.parseFloat(finalField.getText().replace(",", ".")));
                    session.save(tbestoque);

                    transaction.commit();
                    JOptionPane.showMessageDialog(null, "Peça Inserida no Estoque com Sucesso!");
                    dispose();

                } catch (HibernateException ex) {
                    transaction.rollback();
                    JOptionPane.showMessageDialog(null, "Ocorreu um erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    session.clear();
                }
            }
        });

        // BOTÃO LIMPAR
        JButton limparCampos = new JButton();
        limparCampos = new JButton();
        ImageIcon limp = new ImageIcon("src/resources/images/limpar.png");
        Image scaledLimpar = limp.getImage().getScaledInstance(100, 30, Image.SCALE_SMOOTH);
        limparCampos.setIcon(new ImageIcon(scaledLimpar));
        limparCampos.setBounds(200, 270, 100, 30);
        limparCampos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limparCampos();
                listUnidade.setSelectedIndex(0);
            }
        });

        // Define as coordenadas de posicionamento dos componentes
        int x = 10;
        int y = 20;
        int yGap = 30;
        int labelWidth = 150;
        int fieldWidth = 200;

        unidadeLabel.setBounds(x, y, labelWidth, 20);
        listUnidade.setBounds(x + labelWidth + 10, y, fieldWidth, 20);

        y += yGap;
        descricaoLabel.setBounds(x, y, labelWidth, 20);
        descricaoField.setBounds(x + labelWidth + 10, y, fieldWidth, 20);

        y += yGap;
        minimoLabel.setBounds(x, y, labelWidth, 20);
        minimoField.setBounds(x + labelWidth + 10, y, fieldWidth, 20);

        y += yGap;
        maximoLabel.setBounds(x, y, labelWidth, 20);
        maximoField.setBounds(x + labelWidth + 10, y, fieldWidth, 20);

        y += yGap;
        custoLabel.setBounds(x, y, labelWidth, 20);
        custoField.setBounds(x + labelWidth + 10, y, fieldWidth, 20);

        y += yGap;
        lucroLabel.setBounds(x, y, labelWidth, 20);
        lucroField.setBounds(x + labelWidth + 10, y, fieldWidth, 20);

        y += yGap;
        finalLabel.setBounds(x, y, labelWidth, 20);
        finalField.setBounds(x + labelWidth + 10, y, fieldWidth, 20);

        y += yGap;
        fornecedorLabel.setBounds(x, y, labelWidth, 20);
        listFornecedor.setBounds(x + labelWidth + 10, y, fieldWidth, 20);

        // Adicione os componentes ao painel principal
        mainPanel.add(unidadeLabel);
        mainPanel.add(listUnidade);

        mainPanel.add(descricaoLabel);
        mainPanel.add(descricaoField);

        mainPanel.add(minimoLabel);
        mainPanel.add(minimoField);
        minimoField.setValue(0.00f);
        minimoField.setSize(70, 21);

        mainPanel.add(maximoLabel);
        mainPanel.add(maximoField);
        maximoField.setValue(0.00f);
        maximoField.setSize(70, 21);

        mainPanel.add(custoLabel);
        mainPanel.add(custoField);
        custoField.setValue(0.00f);
        custoField.setSize(70, 21);

        mainPanel.add(lucroLabel);
        mainPanel.add(lucroField);
        lucroField.setValue(0.00f);
        lucroField.setSize(70, 21);

        mainPanel.add(finalLabel);
        mainPanel.add(finalField);
        finalField.setEditable(false);
        finalField.setValue(0.00f);
        finalField.setSize(70, 21);

        // botão para limpar
        mainPanel.add(cadastrar);
        mainPanel.add(limparCampos);

        mainPanel.add(fornecedorLabel);
        mainPanel.add(listFornecedor);

        // Defina o tamanho do painel principal
        mainPanel.setPreferredSize(new Dimension(380, 320));

        // Adicione o painel principal à janela de diálogo
        getContentPane().add(mainPanel);
        pack();
        setLocationRelativeTo(mainFrame);
    }

    private void limparCampos() {
        descricaoField.setText("");
        minimoField.setValue(0.00f);
        maximoField.setValue(0.00f);
        custoField.setValue(0.00f);
        lucroField.setValue(0.00f);
        finalField.setValue(0.00f);
    }

    public void showInterface() {
        // Exibe a janela menor
        setVisible(true);
    }

    private void atualizarValorTotal() {
        Float custo = null;
        if (custoField.getValue() != null) {
            custo = (Float) custoField.getValue();
        }

        Float lucro = null;
        if (lucroField.getValue() != null) {
            lucro = (Float) lucroField.getValue();
        }

        if (custo != null && lucro != null) {
            Float valorTotal = custo + (custo * lucro / 100);
            finalField.setValue(valorTotal);
        }
    }
}
