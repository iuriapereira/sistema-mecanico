package bancopoo;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import javax.swing.*;
import javax.swing.text.NumberFormatter;
import org.hibernate.Session;

class InterfaceInsereServico extends JFrame {

    protected static boolean isSmallWindowOpen = false;
    private final JFrame smallFrame;
    private final JFrame mainFrame;
    private final JFormattedTextField valorServicoField;
    private final JTextField modeloField;
    private final JTextField marcaField;
    private final JTextField placaField;
    private final JFormattedTextField kmPercorridoField;
    private final JFormattedTextField valorKMField;
    private final JTextArea descricaoArea;
    private Session session;
    private JButton salvar;
    

    public InterfaceInsereServico(JFrame mainFrame, Session session,JButton salvar) {
        this.smallFrame = new JFrame("Inserindo Serviço"); // TELA ATUAL
        this.mainFrame = mainFrame; // TELA ANTERIOR
        this.session = session;
        this.salvar = salvar;
        
        // DEFININDO A DIMENSÃO DA JANELA ---------------------------------------------
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.width * 0.4);
        int height = (int) (screenSize.height * 0.6);
        smallFrame.setSize(width, height);
        smallFrame.setResizable(false);
        smallFrame.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        // ----------------------------------------------------------------------------

        // Verifica se a janela menor está aberta
        isSmallWindowOpen = false;

        // Painel da janela menor
        JPanel smallPanel = new JPanel();
        smallPanel.setLayout(new BoxLayout(smallPanel, BoxLayout.Y_AXIS));

        // Define o formato para números de ponto flutuante
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(Locale.US));
        decimalFormat.setParseBigDecimal(true);

        // Cria um NumberFormatter com o formato definido
        NumberFormatter formatter = new NumberFormatter(decimalFormat);
        formatter.setValueClass(Float.class);
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);
        
        Font fonte = new Font("Times New Roman", Font.BOLD, 14);
        
        // PAINEL DE DESCRIÇÃO E VALOR DO SERVIÇO --------------------
        JPanel valor = new JPanel(new FlowLayout(FlowLayout.LEFT));
        valor.setSize(new Dimension(10, 1));
        JLabel valorServicoLabel = new JLabel("Valor do Item");
        valorServicoLabel.setFont(fonte);
        valorServicoField = new JFormattedTextField(formatter);
        valorServicoField.setValue(0.00f);
        valorServicoField.setFont(fonte);
        valorServicoField.setPreferredSize(new Dimension(100, 30));

        JLabel descricaoLabel = new JLabel("Descrição Serviço:");
        descricaoLabel.setFont(fonte);
        descricaoArea = new JTextArea();
        descricaoArea.setFont(fonte);
        descricaoArea.setLineWrap(true); // Permite que o texto pule de linha automaticamente 
        descricaoArea.setWrapStyleWord(true); // Quebra a linha no espaço em branco mais próximo
        descricaoArea.setPreferredSize(new Dimension(358, 70));
        // ----------------------------------------------------------
        
        // PAINEL DE VEICULO -----------------------------------------
        JPanel veiculo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel modeloLabel = new JLabel("Modelo:");
        modeloLabel.setFont(fonte);
        modeloField = new JTextField();
        modeloField.setFont(fonte);
        modeloField.setPreferredSize(new Dimension(200, 30));

        JLabel marcaLabel = new JLabel("Marca:");
        marcaLabel.setFont(fonte);
        marcaField = new JTextField();
        marcaField.setFont(fonte);
        marcaField.setPreferredSize(new Dimension(200, 30));

        JLabel placaLabel = new JLabel("Placa:");
        placaLabel.setFont(fonte);
        placaField = new JTextField();
        placaField.setFont(fonte);
        placaField.setPreferredSize(new Dimension(100, 30));
        // ----------------------------------------------------------
        
        // PAINEL DE KM ---------------------------------------------
        JPanel km = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel kmPercorridoLabel = new JLabel("KM Percorrido:");
        kmPercorridoLabel.setFont(fonte);
        kmPercorridoField = new JFormattedTextField(formatter);
        kmPercorridoField.setValue(0.00f);
        kmPercorridoField.setFont(fonte);
        kmPercorridoField.setPreferredSize(new Dimension(100, 30));
        
        JLabel valorKMLabel = new JLabel("Valor por KM:");
        valorKMLabel.setFont(fonte);
        valorKMField = new JFormattedTextField(formatter);
        valorKMField.setValue(0.00f);
        valorKMField.setFont(fonte);
        valorKMField.setPreferredSize(new Dimension(100, 30));
        
        km.add(kmPercorridoLabel);
        km.add(kmPercorridoField);
        km.add(valorKMLabel);
        km.add(valorKMField);
        // ----------------------------------------------------------

        valor.add(valorServicoLabel);
        valor.add(valorServicoField);
        veiculo.add(descricaoLabel);
        veiculo.add(descricaoArea);
        veiculo.add(modeloLabel);
        veiculo.add(modeloField);
        veiculo.add(marcaLabel);
        veiculo.add(marcaField);
        veiculo.add(placaLabel);
        veiculo.add(placaField);
        
        // BOTÕES -----------------------------------------------------------------------
        
        // BOTÃO SALVAR ---------------------------------------
        ImageIcon slv = new ImageIcon("src/resources/images/salvar.png");
        Image scaledSlv = slv.getImage().getScaledInstance(100, 30, Image.SCALE_SMOOTH);
        salvar.setIcon(new ImageIcon(scaledSlv));
        salvar.setBounds(70, 540, 100, 40);
        // ----------------------------------------------------
        // BOTÃO LIMPAR ---------------------------------------
        JButton limpar = new JButton();
        ImageIcon lip = new ImageIcon("src/resources/images/limpar.png");
        Image scaledLip = lip.getImage().getScaledInstance(100, 30, Image.SCALE_SMOOTH);
        limpar.setIcon(new ImageIcon(scaledLip));
        limpar.setBounds(70, 540, 100, 40);
        // ----------------------------------------------------
        JPanel botao = new JPanel(new FlowLayout(FlowLayout.CENTER));
        botao.add(salvar);
        botao.add(limpar);
        // --------------------------------------------------------------------------------

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
        
        
        smallPanel.add(valor);
        smallPanel.add(veiculo);
        smallPanel.add(km);
        smallPanel.add(botao);
    }

    public void showInterface() {
        // Desabilita a janela anterior (mainFrame)
        mainFrame.setEnabled(false);
        // Exibe a janela atual (smallFrame)
        smallFrame.setVisible(true);
    }

    public Object[] getServicoInputs() {
        // Obtenha os valores dos inputs
        float valorServico = Float.parseFloat(valorServicoField.getText());
        String descricaoServico = descricaoArea.getText();
        String modelo = modeloField.getText();
        String marca = marcaField.getText();
        String placa = placaField.getText();
        float KmRodado = Float.parseFloat(kmPercorridoField.getText());
        float KmValor = Float.parseFloat(valorKMField.getText());

        // Crie um array com os valores
        Object[] servicoInputs = {"Serviço", descricaoServico, valorServico, 1, modelo, marca, placa, KmRodado, KmValor};
        return servicoInputs;
    }
}
