package bancopoo;

import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingConstants;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

class BancoPOO {

    protected static JFrame mainFrame; // Referência para a janela principal
    private static final boolean isSmallWindowOpen = false; // Verifica se uma janela menor está aberta
    private static SessionFactory sessionFactory;
    private static Session session;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BancoPOO::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        // Configuração da janela principal
        mainFrame = new JFrame("Interface Gráfica");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Fechar a sessão do Hibernate e encerrar a aplicação
                session.close();
                sessionFactory.close();
                System.exit(0);
            }
        });
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Criação e configuração da sessão do Hibernate
        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
        session = sessionFactory.openSession();

        // Painel principal dividido em duas partes
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Parte superior com os botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
           
        String[] buttonLabels = {"Cliente", "Fornecedor", "Funcionário", "Peça", "Venda"};
        String[] buttonIcons = {"src/resources/images/clientes.png", "src/resources/images/fornecedor.png", "src/resources/images/funcionario.png",
                "src/resources/images/produto.png", "src/resources/images/vendas.png"};

        for (int i = 0; i < buttonLabels.length; i++) {
            JButton button = createSquareButton(buttonIcons[i]);
            String label = buttonLabels[i];
            button.addActionListener(e -> {
                // Verifica se uma janela menor já está aberta
                if (!isSmallWindowOpen) {
                    if (label.equals("Cliente")) {
                        InterfaceCliente client = new InterfaceCliente(mainFrame, session);
                        client.show();
                    } else if (label.equals("Fornecedor")) {
                        InterfaceFornecedor forne = new InterfaceFornecedor(mainFrame, session);
                        forne.show();
                    } else if (label.equals("Funcionário")) {
                        InterfaceFuncionario func = new InterfaceFuncionario(mainFrame, session);
                        func.show();
                    } else if (label.equals("Peça")) {
                        InterfaceEstoque pec = new InterfaceEstoque(mainFrame, session);
                        pec.show();
                    }else if (label.equals("Venda")) {
                        InterfaceVenda venda = new InterfaceVenda(mainFrame, session);
                        venda.show();
                    }

                }
            });
            buttonPanel.add(button);
        }

        // Parte inferior com a foto da empresa
        JLabel companyLogo = new JLabel(new ImageIcon("caminho/para/imagem_empresa.png"));
        companyLogo.setHorizontalAlignment(SwingConstants.CENTER);

        // Adiciona os painéis no painel principal
        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(companyLogo, BorderLayout.CENTER);

        // Adiciona o painel principal na janela principal
        mainFrame.getContentPane().add(mainPanel);

        // Exibe a janela principal
        mainFrame.setVisible(true);
    }

    private static JButton createSquareButton(String iconPath) {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(100, 100));
        ImageIcon icon = new ImageIcon(iconPath);
        Image scaledImage = icon.getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH);
        button.setIcon(new ImageIcon(scaledImage));
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setFocusPainted(false);
        return button;
    }
}