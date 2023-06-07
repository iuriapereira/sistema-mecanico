package bancopoo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.hibernate.Session;


class InterfaceCliente {

    private final JFrame smallFrame;
    private final JFrame mainFrame;
    private static boolean isSmallWindowOpen = false;
    private String[] buttonLabels;
    private final Session session;

    public InterfaceCliente(JFrame mainFrame, Session session) {
        this.smallFrame = new JFrame("Clientes");
        this.mainFrame = mainFrame;
        this.session = session;
        
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
        JPanel smallPanel = new JPanel(new BorderLayout());

        // Parte superior com os botões flutuantes
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        String[] buttonLabels = {"Inserir", "Alterar", "Remover"};
        String[] buttonIcons = {"src/resources/images/inserir.png", "src/resources/images/alterar.png", "src/resources/images/excluir.png"};
        
        for (int i = 0; i < buttonLabels.length; i++) {
            JButton button = createSmallButton(buttonIcons[i]);
            String label = buttonLabels[i];
            button.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Verifica se uma janela menor já está aberta
                    if (!isSmallWindowOpen) {
                        if (label.equals("Inserir")) {
                            InterfaceInsereCliente inserir = new InterfaceInsereCliente(mainFrame);
                            inserir.showInterface();
                        } else if (label.equals("Alterar")) {
                            //InterfaceFornecedor forne = new InterfaceFornecedor(mainFrame);
                            //forne.show();
                        } else if (label.equals("Remover")) {
                            //InterfaceFuncionario func = new InterfaceFuncionario(mainFrame);
                            //func.show();
                        }
                    }
                }
                
            });
            buttonPanel.add(button);
        }

        // Parte inferior com a tabela
        JTable table = createTableFromDatabase();
        table.setEnabled(false); // Torna a tabela não editável

        // Adiciona os painéis no painel da janela menor
        smallPanel.add(buttonPanel, BorderLayout.NORTH);
        smallPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        // Adiciona o painel da janela menor na janela menor
        smallFrame.getContentPane().add(smallPanel);

        // Centraliza a janela menor em relação à janela principal
        int x = mainFrame.getX() + (mainFrame.getWidth() - smallFrame.getWidth()) / 2;
        int y = mainFrame.getY() + (mainFrame.getHeight() - smallFrame.getHeight()) / 2;
        smallFrame.setLocation(x, y);

        // Configura um listener para quando a janela menor for fechada
        smallFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                isSmallWindowOpen = false;
                // Habilita a janela principal
                mainFrame.setEnabled(true);
                mainFrame.requestFocus();
            }
        });
    }

    public void show() {
        // Exibe a janela menor
        smallFrame.setVisible(true);
    }

    private JButton createSmallButton(String iconPath) {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(50, 50));
        ImageIcon icon = new ImageIcon(iconPath);
        Image scaledImage = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        button.setIcon(new ImageIcon(scaledImage));
        button.setFocusPainted(false);
        return button;
    }

    private JTable createTableFromDatabase() {
        // Código para buscar os dados do banco de dados usando Hibernate

        // Dados da tabela
        String[] columnNames = {"Coluna 1", "Coluna 2", "Coluna 3"};
        Object[][] data = {
            {"Valor 1", "Valor 2", "Valor 3"},
            {"Valor 4", "Valor 5", "Valor 6"},
            {"Valor 7", "Valor 8", "Valor 9"}
        };

        // Modelo da tabela não editável
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true;
            }
        };

        // Cria a tabela
        JTable table = new JTable(model);
        table.setFillsViewportHeight(true);

        return table;
    }
}
