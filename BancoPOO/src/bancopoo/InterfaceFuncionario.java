package bancopoo;

import banco.TbFuncionario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

class InterfaceFuncionario {
    private final JFrame smallFrame;
    private final JFrame mainFrame;
    private static boolean isSmallWindowOpen = false;
    private final Session session;

    public InterfaceFuncionario(JFrame mainFrame, Session session) {
        this.smallFrame = new JFrame("Janela Menor");
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
        isSmallWindowOpen = true;

        // Desabilita a janela principal
        mainFrame.setEnabled(false);

        // Painel da janela menor
        JPanel smallPanel = new JPanel(new BorderLayout());

        // Parte superior com os botões flutuantes
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        String[] buttonNames = {"Botão 1", "Botão 2", "Botão 3"};
        String[] buttonIcons = {"caminho/para/imagem1.png", "caminho/para/imagem2.png", "caminho/para/imagem3.png"};

        for (int i = 0; i < buttonNames.length; i++) {
            JButton button = createSmallButton(buttonIcons[i]);
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
        button.setIcon(new ImageIcon(iconPath));
        button.setFocusPainted(false);
        return button;
    }

    private JTable createTableFromDatabase() {
        Criteria criteria = session.createCriteria(TbFuncionario.class);
        ArrayList<TbFuncionario> funcionarios = (ArrayList<TbFuncionario>) criteria.list();

        // Dados da tabela
        Object[][] data = new Object[funcionarios.size()][2];
        for (int i = 0; i < funcionarios.size(); i++) {
            TbFuncionario funcionario = funcionarios.get(i);
            data[i][0] = funcionario.getTbEntidade().getEntCpfCnpj();
            data[i][1] = funcionario.getTbEntidade().getEntNome();
            data[i][2] = funcionario.getTbEntidade().getEntNomeFantasia();
            data[i][3] = funcionario.getTbEntidade().getEntFone();
            data[i][4] = funcionario.getTbEntidade().getEntSexo();
            data[i][6] = funcionario.getTbEntidade().getEntEmail();
            data[i][7] = funcionario.getTbCargo().getCarDescricao();
            data[i][8] = funcionario.getFuncUsuario();
        }
        String[] columnNames = {"Cpf/Cnpj", "Nome", "Nome Fantasia", "Fone","Sexo", "Email","Cargo", "Login"};

        // Modelo da tabela não editável
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Cria a tabela
        JTable table = new JTable(model);
        table.setFillsViewportHeight(true);

        return table;
    }
}