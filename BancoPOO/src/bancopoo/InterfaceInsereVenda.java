package bancopoo;

import banco.TbCliente;
import banco.TbEstado;
import banco.TbEstoque;
import banco.TbTipoPagamento;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.NumberFormatter;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

class InterfaceInsereVenda extends JFrame {

    protected static boolean isSmallWindowOpen = false;
    private final JFrame smallFrame;
    private final JFrame mainFrame;
    private final JFormattedTextField valorItemField;
    private final JFormattedTextField descontoField;
    private final JFormattedTextField totalVendaField;
    private Session session;
    protected String[] buttonLabels = {"Inserir Produto", "Inserir Serviço", "Excluir", "Concluir Venda"};
    protected String[] buttonIcons = {"src/resources/images/inserirproduto.png", "src/resources/images/inserirservico.png",
        "src/resources/images/excluir.png", "src/resources/images/finalizar.png"};
    protected JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    private banco.TbVenda tbvenda;
    private ArrayList<Object[]> vendaItems = new ArrayList<>();
    private DefaultTableModel model;
    private JComboBox<String> listCliente;
    private JComboBox<String> listPagamento;

    public InterfaceInsereVenda(JFrame mainFrame, Session session) {
        this.smallFrame = new JFrame("Inserindo Vendas"); // TELA ATUAL
        this.mainFrame = mainFrame; // TELA ANTERIOR
        this.session = session;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.width * 0.6);
        int height = (int) (screenSize.height * 0.6);
        smallFrame.setSize(width, height);
        smallFrame.setResizable(false);
        smallFrame.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);

        // Verifica se a janela menor está aberta
        isSmallWindowOpen = false;

        // Painel da janela menor
        JPanel smallPanel = new JPanel();
        smallPanel.setLayout(new BoxLayout(smallPanel, BoxLayout.Y_AXIS));

        // Define o formato para números de ponto flutuante
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        decimalFormat.setParseBigDecimal(true);

        // Cria um NumberFormatter com o formato definido
        NumberFormatter formatter = new NumberFormatter(decimalFormat);
        formatter.setValueClass(Float.class);
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);

        // Adiciona os componentes acima da tabela
        JPanel valores = new JPanel(new FlowLayout(FlowLayout.CENTER));

        Font fonte = new Font("Times New Roman", Font.BOLD, 14);
        JLabel valorItemLabel = new JLabel("Valor do Item");
        valorItemField = new JFormattedTextField(formatter);
        valorItemField.setValue(0.00f);
        valorItemField.setFont(fonte);
        valorItemField.setPreferredSize(new Dimension(100, 30));

        Font fonte1 = new Font("Times New Roman", Font.BOLD, 14);
        JLabel descontoLabel = new JLabel("Desconto");
        descontoField = new JFormattedTextField(formatter);
        descontoField.setValue(0.00f);
        descontoField.setFont(fonte1);
        descontoField.setPreferredSize(new Dimension(100, 30));

        Font fonte2 = new Font("Times New Roman", Font.BOLD, 18);
        JLabel totalVendaLabel = new JLabel("TOTAL R$");
        totalVendaField = new JFormattedTextField(formatter);
        totalVendaField.setValue(0.00f);
        totalVendaField.setFont(fonte2);
        totalVendaField.setEditable(false);
        totalVendaField.setPreferredSize(new Dimension(200, 50));

        valores.add(valorItemLabel);
        valores.add(valorItemField);
        valores.add(descontoLabel);
        valores.add(descontoField);
        valores.add(totalVendaLabel);
        valores.add(totalVendaField);

        valorItemField.addPropertyChangeListener("value", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                atualizarValorTotal();
            }
        });

        // Define um ouvinte de eventos para o campo "lucroField"
        descontoField.addPropertyChangeListener("value", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                atualizarValorTotal();
            }
        });

        // DEFINOÇÕES PARA O COMBOBOX ---------------------------------------------
        Font box = new Font("Times New Roman", Font.BOLD, 18);
        JPanel comboBox = new JPanel(new FlowLayout(FlowLayout.CENTER));
        // CONEXÃO COM O BANCO TB_CLIENTE
        String hql = "SELECT cli.tbEntidade.entNome FROM TbCliente cli";
        Query query = session.createQuery(hql);
        java.util.List<String> clientes = (java.util.List<String>) query.list();

        /*Criteria cli = session.createCriteria(TbCliente.class);
         ArrayList<TbCliente> cliente = (ArrayList<TbCliente>) cli.list();*/
        // COMBOBOX DO CLIENTE
        listCliente = new JComboBox<>();
        DefaultComboBoxModel<String> model2 = new DefaultComboBoxModel<>();
        model2.addElement("Selecione..."); // PALAVRA QUE VAI FICAR ANTES DE APARACER AS LITA DE TODOS OS ESTADOS
        for (String cliente : clientes) {
            model2.addElement(cliente);
        }
        //ArrayList<TbEstado> estado = (ArrayList<TbEstado>) estd.list();

        listCliente.setModel(model2);

        /*
         DefaultComboBoxModel<String> clie = new DefaultComboBoxModel<>();
         clie.addElement("Inserir Cliente...");
         listCliente.setModel(clie);
         for (TbCliente descricao : cliente) {
         listCliente.addItem(decricao.getTbEntidade().getEntNome());
         }
         listCliente.setFont(box);*/
        comboBox.add(listCliente);

        // CONEXÃO COM O BANCO TB_TIPOPAGAMENTO
        Criteria pgm = session.createCriteria(TbTipoPagamento.class);
        ArrayList<TbTipoPagamento> pagamento = (ArrayList<TbTipoPagamento>) pgm.list();
        // COMBOBOX DO PAGAMENTO
        listPagamento = new JComboBox<>();
        DefaultComboBoxModel<String> pagm = new DefaultComboBoxModel<>();
        pagm.addElement("Tipo de Pagamento...");
        listPagamento.setModel(pagm);
        for (TbTipoPagamento desc : pagamento) {
            listPagamento.addItem(desc.getTpDescricao());
        }
        listPagamento.setFont(box);
        comboBox.add(listPagamento);

        // ------------------------------------------------------------------------
        // TABELA -----------------------------------------------------------------
        JTable table = createTable(session, tbvenda);
        table.setEnabled(true); // Torna a tabela não editável
        // -----------------------------------------------------------------------

        // Adiciona os painéis no painel da janela menor
        smallPanel.add(comboBox);
        smallPanel.add(buttonPanel);
        smallPanel.add(new JScrollPane(table), BorderLayout.NORTH);
        smallPanel.add(valores);

        // Adiciona o painel da janela menor na janela menor
        smallFrame.getContentPane().add(smallPanel);

        // Centraliza a janela menor em relação à janela principal
        int x = mainFrame.getX() + (mainFrame.getWidth() - smallFrame.getWidth()) / 2;
        int y = mainFrame.getY() + (mainFrame.getHeight() - smallFrame.getHeight()) / 2;
        smallFrame.setLocation(x, y);

        // Configura um listener para quando a janela menor for fechada
        smallFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                isSmallWindowOpen = false;
                // Habilita a janela principal
                mainFrame.setEnabled(true);
                mainFrame.requestFocus();
            }
        });
    }

    public void showInterface() {
        // Desabilita a janela anterior (mainFrame)
        mainFrame.setEnabled(false);
        // Exibe a janela atual (smallFrame)
        smallFrame.setVisible(true);
    }

    // FAZ O CALCULO DO VALOR TOTAL
    private void atualizarValorTotal() {
        Float custo = null;
        if (valorItemField.getValue() != null) {
            custo = (Float) valorItemField.getValue();
        }

        Float desconto = null;
        if (descontoField.getValue() != null) {
            desconto = (Float) descontoField.getValue();
        }

        if (custo != null && desconto != null) {
            Float valorTotal = custo - desconto;
            totalVendaField.setValue(valorTotal);
        }
    }

    // TABELA PARA INSERIR OS DADOS DA VENDA
    protected JButton createSmallButton(String iconPath) {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(150, 50));
        ImageIcon icon = new ImageIcon(iconPath);
        Image scaledImage = icon.getImage().getScaledInstance(120, 45, Image.SCALE_SMOOTH);
        button.setIcon(new ImageIcon(scaledImage));
        button.setFocusPainted(false);
        return button;
    }

    protected JTable createTable(Session session, banco.TbVenda tbvenda) {
        // Criação da tabela e modelo
        String[] columnNames = {"Tipo", "Descrição", "Valor Serviço/Peça", "Quantidade",};
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0 || column == 1 || column == 2) { // Impede a edição das colunas "Descrição" e "Valor"
                    return false;
                }
                return true; // Permite a edição das outras colunas
            }
        };
        String[] columnNames2 = {"Nome Produto", "Quantidade em Estoque", "Valor Unitário", "Quantidade Mínima", "Fornecedor"};
        DefaultTableModel model2 = new DefaultTableModel(columnNames2, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        // Cria a tabela
        JTable table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.getModel().addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent e) {
                int row = e.getFirstRow();
                int column = e.getColumn();
                if (row >= 0 && column >= 0) {
                    Object novoValor = model.getValueAt(row, column);
                    vendaItems.get(row)[column] = novoValor;
                }
            }
        });
        // Parte superior com os botões flutuantes
        for (int i = 0; i < buttonLabels.length; i++) {
            JButton button = createSmallButton(buttonIcons[i]);
            String label = buttonLabels[i];

            if (label.equals("Inserir Produto")) {
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            // Criação da janela com a tabela
                            JDialog dialog = new JDialog(mainFrame, "Selecione um produto");
                            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                            dialog.setSize(600, 400);

                            InterfaceEstoque tabela = new InterfaceEstoque(mainFrame, session);
                            tabela.updateTableData(model2);
                            // Criação da tabela dentro do dialog
                            JTable dialogTable = new JTable(model2);
                            JScrollPane dialogScrollPane = new JScrollPane(dialogTable);
                            dialog.add(dialogScrollPane);

                            // Adiciona o MouseListener para capturar o clique duplo na tabela
                            dialogTable.addMouseListener(new MouseInputAdapter() {
                                public void mouseClicked(MouseEvent e) {
                                    if (e.getClickCount() == 2) { // Verifica se foi um clique duplo
                                        int selectedRow = dialogTable.getSelectedRow();
                                        if (selectedRow != -1) {
                                            String selectedProduct = (String) dialogTable.getValueAt(selectedRow, 0);
                                            Float selectedProduct_valor = (Float) dialogTable.getValueAt(selectedRow, 3);
                                            Object[] vendaItem = {"Produto", selectedProduct, selectedProduct_valor, 0};
                                            vendaItems.add(vendaItem);
                                            atualizarTabela();

                                            // Feche a janela de seleção do produto
                                            dialog.dispose();
                                        }
                                    }
                                }
                            });

                            dialog.setVisible(true);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Ocorreu um erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
            } else if (label.equals("Inserir Serviço")) {
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JButton salvar = new JButton();
                        InterfaceInsereServico inserir = new InterfaceInsereServico(mainFrame, session, salvar);
                        inserir.showInterface();
                        salvar.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                vendaItems.add(inserir.getServicoInputs());
                                atualizarTabela();
                            }
                        });
                    }
                });
            } else if (label.equals("Excluir")) {
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int indiceSelecionado = table.getSelectedRow();
                        if (indiceSelecionado >= 0) {
                            vendaItems.remove(indiceSelecionado);
                            atualizarTabela();
                        }
                    }
                });
            } else if (label.equals("Concluir Venda")) {
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (listCliente.getSelectedItem() == "Inserir Cliente..." || listPagamento.getSelectedItem() == "Tipo de Pagamento...") {
                            JOptionPane.showMessageDialog(null, "Informe um Cliente/Pagamento");
                        } else {
                            InsereBanco();
                        }
                    }
                });
            }
            buttonPanel.add(button);
        }

        return table;
    }

    public void atualizarTabela() {
        model.setRowCount(0); // Limpa o modelo da tabela

        for (Object[] item : vendaItems) {
            model.addRow(item); // Adiciona cada item ao modelo da tabela
        }
    }

    public void adicionarServico(Object[] servicoInputs) {
        vendaItems.add(servicoInputs);
        atualizarTabela();
    }

    private void InsereBanco() {
        Transaction transaction = session.beginTransaction();
        String hql2 = "SELECT c.cliId FROM TbCliente c WHERE c.tbEntidade.entNome = '" + listCliente.getSelectedItem() + "'";
        Query query2 = session.createQuery(hql2);
        int CliId = (int) query2.uniqueResult();

        String hql3 = "SELECT p.tpId FROM TbTipoPagamento p WHERE p.tpDescricao = '" + listPagamento.getSelectedItem() + "'";
        Query query3 = session.createQuery(hql3);
        int PagId = (int) query3.uniqueResult();

        banco.TbVenda tbven = new banco.TbVenda();
        Object cliente = session.load(TbCliente.class, CliId);
        tbven.setTbCliente((TbCliente) cliente);
        Object pagamento = session.load(TbTipoPagamento.class, PagId);
        tbven.setTbTipoPagamento((TbTipoPagamento) pagamento);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        tbven.setVenData(timestamp);
        session.save(tbven);
        for (Object[] item : vendaItems) {
            if (item[0] == "Produto") {
                try {
                    String hql = "SELECT e.estoId FROM TbEstoque e WHERE e.tbFornecedorHasPeca.tbPeca.peDescricao = '" + item[1] + "'";
                    Query query = session.createQuery(hql);
                    int EstoqueId = (int) query.uniqueResult();

                    banco.TbVenPeca tbvenpeca = new banco.TbVenPeca();
                    Object estoque = session.load(TbEstoque.class, EstoqueId);
                    tbvenpeca.setTbEstoque((TbEstoque) estoque);
                    tbvenpeca.setTbVenda(tbven);
                    tbvenpeca.setVpQuantidade((Integer) item[3]);
                    session.save(tbvenpeca);

                } catch (HibernateException ex) {
                    transaction.rollback();
                    JOptionPane.showMessageDialog(null, "Ocorreu um erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                try {

                } catch (HibernateException ex) {
                    transaction.rollback();
                    JOptionPane.showMessageDialog(null, "Ocorreu um erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        transaction.commit();
    }
}
