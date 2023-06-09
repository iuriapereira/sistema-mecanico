package bancopoo;

import static bancopoo.InterfaceAbstrata.isSmallWindowOpen;
import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


class BancoPOO {

    protected static JFrame mainFrame; // Referência para a janela principal
    private static final boolean isSmallWindowOpen = false; // Verifica se uma janela menor está aberta
    private static Configuration configuration; 
    private static SessionFactory sessionFactory;
    private static Session session;
    private static JLabel diagnosticLabel;
    private static JButton tentarConexaoButton;
    private static JButton loginButton;
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(BancoPOO::createAndShowLoginFrame);
    }
    
    private static void createAndShowLoginFrame(){
        //Criando uma mainFrame que não possui relação direta com a mainFrame do programa
        mainFrame = new JFrame("Login");
        mainFrame.setSize(600, 350);
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo(null);
        
        //Instaciando todos os atribudos da frame de login
        tentarConexaoButton = new JButton();
        diagnosticLabel = new JLabel("Tentando conexão com o servidor...");
        JLabel logo = new JLabel();
        JLabel usuarioLabel = new JLabel("Usuario:");
        JTextField usuarioTextField = new JTextField();
        JLabel senhaLabel = new JLabel("Senha:");
        JPasswordField senhaTextField = new JPasswordField();
        loginButton = new JButton();
        JLabel checkPasswordLabel = new JLabel("Mostrar senha");
        JCheckBox passwordCheckBox = new JCheckBox();

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Definindo a operação padrão ao finalizar o programa
        mainFrame.setLayout(null); // Definindo o layout
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Fechar a sessão do Hibernate e encerrar a aplicação
                session.close();
                sessionFactory.close();
                System.exit(0);
            }
        });

        //Criando evendo para o botão de mostrar senha, alterando o Echo do TextField
        passwordCheckBox.addActionListener(e -> {
            if (!isSmallWindowOpen) {
                if(passwordCheckBox.isSelected()){
                    senhaTextField.setEchoChar('\0');
                } else{
                    senhaTextField.setEchoChar('*');
                }
            }
        });
        
        //Instanciando a logo do programa
        ImageIcon icon = new ImageIcon("src/resources/images/logo.png");
        Image scaledLogo = icon.getImage().getScaledInstance(270, 153, Image.SCALE_SMOOTH);
        logo.setIcon(new ImageIcon(scaledLogo));
        
        //Setando os atributos do botão de login
        loginButton.setText("Login");
        loginButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        loginButton.setHorizontalTextPosition(SwingConstants.CENTER);
        loginButton.setFocusPainted(false);
        loginButton.setVisible(false);
        
        //Setando os atributos do botão de testar conexão
        tentarConexaoButton.setText("Testar conexão");
        tentarConexaoButton.setFocusPainted(false);
        tentarConexaoButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        tentarConexaoButton.setHorizontalTextPosition(SwingConstants.CENTER);
        tentarConexaoButton.setEnabled(false);
        tentarConexaoButton.addActionListener(e -> {
            realizarConexao();
        });
        
        //Deixando o texto do diagnósitico no centro
        diagnosticLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        //Adicionando evento para o botão login
        loginButton.addActionListener(e -> {
            if (session.isConnected()) {
                //Consulto no banco de dados se as credenciais informadas existem.
                String hql = "SELECT fu.funcUsuario, fu.funcSenha FROM TbFuncionario fu WHERE fu.funcUsuario = :usuario AND fu.funcSenha = :senha";
                Query query = session.createQuery(hql);
                char[] password = senhaTextField.getPassword();
                String usuario = usuarioTextField.getText();
                query.setParameter("usuario", usuario);
                query.setParameter("senha", String.valueOf(password));
                Object result = query.uniqueResult();
                
                //Retornando erro de login para o dianóstico
                if (result == null){
                    System.out.println("nulo");
                    diagnosticLabel.setForeground(Color.red);
                    diagnosticLabel.setText("Credenciais inválidas!");
                } else{
                    //Fechando a janela de login e abrindo o programa.
                    mainFrame.dispose();
                    SwingUtilities.invokeLater(BancoPOO::createAndShowGUI);
                }
            }
        });
        
        //Setando a posição de todos os elementos
        logo.setBounds(165, 13, 270, 153);
        usuarioLabel.setBounds(115, 180, 150, 20);
        usuarioTextField.setBounds(175, 180, 250, 20);
        senhaLabel.setBounds(115, 210, 150, 20);
        senhaTextField.setBounds(175, 210, 250, 20);
        passwordCheckBox.setBounds(171, 240, 20, 20);
        checkPasswordLabel.setBounds(195, 240, 100, 20);
        loginButton.setBounds(350, 240, 70, 22);
        tentarConexaoButton.setBounds(290, 240, 130, 22);
        diagnosticLabel.setBounds(200, 270, 200, 20);       
        
        //Adicionando todos os elementos na mainFrame
        mainFrame.add(logo);
        
        mainFrame.add(usuarioLabel);
        mainFrame.add(usuarioTextField);
        mainFrame.add(senhaLabel);
        mainFrame.add(senhaTextField);
        mainFrame.add(loginButton);
        
        mainFrame.add(passwordCheckBox);
        mainFrame.add(checkPasswordLabel);
        
        mainFrame.add(loginButton);
        
        mainFrame.add(tentarConexaoButton);
        mainFrame.add(diagnosticLabel);
        // Exibe a janela principal
        mainFrame.setVisible(true);
        
        //Crio uma nova Thread para verifica a conexão com o banco, com isso a janela é primeiramente renderizada e posteriormente é realizado a conexão
        new Thread(BancoPOO::realizarConexao).start();
    }

    private static void createAndShowGUI() {
        //Configuração da janela principal
        mainFrame = new JFrame("Interface Gráfica");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //Fechar a sessão do Hibernate e encerrar a aplicação
                session.close();
                sessionFactory.close();
                System.exit(0);
            }
        });
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Criação e configuração da sessão do Hibernate
        // Painel principal dividido em duas partes
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Parte superior com os botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
           
        String[] buttonLabels = {"Cliente", "Fornecedor", "Funcionário", "Peça", "Serviço", "Venda"};
        String[] buttonIcons = {"src/resources/images/clientes.png", "src/resources/images/fornecedor.png", "caminho/para/imagem3.png",
                "src/resources/images/produto.png", "src/resources/images/servico.png", "src/resources/images/venda.png"};

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
                    } else if (label.equals("Venda")) {
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
        //Método para a criação de botões
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
    
    private static void realizarConexao() {
        tentarConexaoButton.setEnabled(false);
        try{
            // Criação e configuração da sessão do Hibernate
            configuration = new Configuration().configure();;
            sessionFactory = configuration.buildSessionFactory();;
            session = sessionFactory.openSession();
        } catch (HibernateException e){
            //Habilitando o botão de testar a conexão, e retornando o diagnóstico
            tentarConexaoButton.setEnabled(true);
            diagnosticLabel.setText("Falha na conexão!");
            diagnosticLabel.setForeground(Color.RED);
        } finally {
            //Se a sessão estiver conectada, informo sucesso, desabilito o botão de tentar conexão e deixo visivel o botão de login.
            if (session.isConnected()) {
                diagnosticLabel.setText("Conexão realizada com sucesso!");
                diagnosticLabel.setForeground(Color.GREEN);
                tentarConexaoButton.setVisible(false); // Desabilitar o botão de testar conexão
                loginButton.setVisible(true);
            }
        }
    }
}

