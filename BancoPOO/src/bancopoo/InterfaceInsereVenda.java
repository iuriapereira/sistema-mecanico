package bancopoo;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.NumberFormatter;
import org.hibernate.Query;
import org.hibernate.Session;

class InterfaceInsereVenda extends JFrame {
    protected static boolean isSmallWindowOpen = false;
    private final JFrame smallFrame;
    private final JFrame mainFrame;
    private final JFormattedTextField valorItemField;
    private final JFormattedTextField descontoField;
    private final JFormattedTextField totalVendaField;
    private Session session;
    protected String[] buttonLabels = {"Inserir Produto", "Alterar Produto", "Inserir Serviço", "Alterar Serviço"};
    protected String[] buttonIcons = {"src/resources/images/inserirproduto.png", "src/resources/images/alterarproduto.png", "src/resources/images/inserirservico.png","src/resources/images/alterarservico.png"};
    protected JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    
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
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        Font fonte = new Font("Comic Sans MS", Font.BOLD, 14);
        JLabel valorItemLabel = new JLabel("Valor do Item");
        valorItemField = new JFormattedTextField(formatter);
        valorItemField.setValue(0.00f);
        valorItemField.setFont(fonte);
        valorItemField.setPreferredSize(new Dimension(100, 30));
        
        Font fonte1 = new Font("Comic Sans MS", Font.BOLD, 14);
        JLabel descontoLabel = new JLabel("Desconto");
        descontoField = new JFormattedTextField(formatter);
        descontoField.setValue(0.00f);
        descontoField.setFont(fonte1);
        descontoField.setPreferredSize(new Dimension(100, 30));
        
        Font fonte2 = new Font("Comic Sans MS", Font.BOLD, 18);
        JLabel totalVendaLabel = new JLabel("TOTAL R$");
        totalVendaField = new JFormattedTextField(formatter);
        totalVendaField.setValue(0.00f);
        totalVendaField.setFont(fonte2);
        totalVendaField.setPreferredSize(new Dimension(200, 50));
        
        
        topPanel.add(valorItemLabel);
        topPanel.add(valorItemField);
        topPanel.add(descontoLabel);
        topPanel.add(descontoField);
        topPanel.add(totalVendaLabel);
        topPanel.add(totalVendaField);
        
        smallPanel.add(topPanel, BorderLayout.WEST);
        
        // Parte inferior com a tabela
        JTable table = createTable(session);
        table.setEnabled(true); // Torna a tabela não editável
        
        // Adiciona os painéis no painel da janela menor
        smallPanel.add(buttonPanel);
        smallPanel.add(new JScrollPane(table), BorderLayout.NORTH);
        
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
    
    protected JTable createTable(Session session) {
        // Código para buscar os dados do banco de dados usando Hibernate
        String hql = "SELECT cli.cliId, cli.tbEntidade.entNome, cli.tbEntidade.entCpfCnpj, cli.tbEntidade.entFone, cli.tbEntidade.entSexo, cli.tbEntidade.entEmail, cli.tbEntidade.entTipo FROM TbCliente cli";
        Query query = session.createQuery(hql);

        String[] columnNames = {"ID", "Nome", "Cpf/Cnpj", "Fone", "Sexo", "Email", "Tipo"};

        // Modelo da tabela não editável
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        java.util.List<Object[]> results = query.list();
        for (Object[] result : results) {
            int id = (int) result[0];
            String nome = (String) result[1];
            String cpf = (String) result[2];
            String fone = (String) result[3];
            String sexo = (String) result[4];
            String email = (String) result[5];
            String tipo = (String) result[6];

            model.addRow(new Object[]{id, nome, cpf, fone, sexo, email, tipo}); // Adicione outras colunas conforme necessário
        }

        // Cria a tabela
        JTable table = new JTable(model);
        table.setFillsViewportHeight(true);
        // Parte superior com os botões flutuantes
        for (int i = 0; i < buttonLabels.length; i++) {
            JButton button = createSmallButton(buttonIcons[i]);
            String label = buttonLabels[i];

            if (label.equals("Inserir")) {
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        InterfaceInsereCliente inserir = new InterfaceInsereCliente(mainFrame, session);
                        inserir.showInterface();
                    }
                });
            } else if (label.equals("Alterar")) {
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Implemente a lógica para a ação de alteração
                        // ...
                    }
                });
            } else if (label.equals("Remover")) {
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int selectedRow = table.getSelectedRow();

                        // Verifica se uma linha está selecionada
                        /*if (selectedRow != -1) {
                            // Obtém o CPF da linha selecionada
                            selectedCPF = (String) table.getValueAt(selectedRow, 2);
                            Transaction transaction = session.beginTransaction();
                            try {
                                String hql = "DELETE FROM TbCliente c WHERE c.tbEntidade.entCpfCnpj = :cpf";
                                Query deleteQuery = session.createQuery(hql);
                                deleteQuery.setParameter("cpf", selectedCPF);
                                deleteQuery.executeUpdate();
                                transaction.commit();
                                JOptionPane.showMessageDialog(null, "Cliente Removido");
                                updateTableData(model);
                            } catch (HibernateException ex) {
                                transaction.rollback();
                                JOptionPane.showMessageDialog(null, "Ocorreu um erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                            }
                        }*/
                    }
                });
            } else if(label.equals("Atualizar")){
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //updateTableData(model); 
                    }
                });
            }
            buttonPanel.add(button);
        }

        return table;
    }
}