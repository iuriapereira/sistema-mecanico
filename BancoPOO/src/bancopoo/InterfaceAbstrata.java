package bancopoo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.hibernate.Session;

public abstract class InterfaceAbstrata {
    private final JFrame smallFrame;
    protected final JFrame mainFrame;
    protected static boolean isSmallWindowOpen = false;
    private Session session;
    protected String[] buttonLabels = {"Inserir", "Alterar", "Remover"};
    protected String[] buttonIcons = {"src/resources/images/inserir.png", "src/resources/images/alterar.png", "src/resources/images/excluir.png"};
    protected JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

    public InterfaceAbstrata(JFrame mainFrame, Session session) {
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

        
       
        // Parte inferior com a tabela
        JTable table = createTable(session);
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

    protected JButton createSmallButton(String iconPath) {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(50, 50));
        ImageIcon icon = new ImageIcon(iconPath);
        Image scaledImage = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        button.setIcon(new ImageIcon(scaledImage));
        button.setFocusPainted(false);
        return button;
    }

    protected abstract JTable createTable(Session session);
}