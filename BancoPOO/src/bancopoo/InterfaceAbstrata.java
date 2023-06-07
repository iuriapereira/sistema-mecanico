package bancopoo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public abstract class InterfaceAbstrata {
    private final JFrame smallFrame;
    private final JFrame mainFrame;
    private static boolean isSmallWindowOpen = false;

    public InterfaceAbstrata(JFrame mainFrame, String smallWindowTitle) {
        this.smallFrame = new JFrame(smallWindowTitle);
        this.mainFrame = mainFrame;

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

    protected abstract JTable createTableFromDatabase();
}