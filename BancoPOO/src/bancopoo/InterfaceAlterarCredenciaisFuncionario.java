package bancopoo;

import java.awt.Font;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class InterfaceAlterarCredenciaisFuncionario extends JDialog{
    private JButton salvarAlteracoes;
    private JLabel usuarioLabel;
    private JLabel senhaLabel;
    private JLabel checkPasswordLabel;
    private JLabel cargoLabel;
    private JTextField usuarioField;
    private JPasswordField senhaTextField;
    private JCheckBox passwordCheckBox;
    private JDialog OverlayFrame;
    private Font fonte = new Font("Times New Roman", Font.ROMAN_BASELINE, 14);
    
    public InterfaceAlterarCredenciaisFuncionario(JDialog OverlayFrame, Session session, String cpf) {
        this.OverlayFrame = OverlayFrame;
        setSize(320, 240); 
        setTitle("Alterar credenciais funcionário");
        setResizable(false);
        setLocationRelativeTo(OverlayFrame);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); //Definindo a operação padrão ao finalizar o programa
        setLayout(null); // Definindo o layout
        
        // COMBOBOX DO CARGO ---------------------------------------------------
        String hqlCargos = "SELECT car.carDescricao FROM TbCargo car";
        Query query = session.createQuery(hqlCargos);
        List<String> cargos = (List<String>) query.list();
        JComboBox<String> listCargo = new JComboBox<>();
        DefaultComboBoxModel<String> carg = new DefaultComboBoxModel<>();
        carg.addElement("Selecione..."); // PALAVRA QUE VAI FICAR ANTES DE APARACER A LISTA DE TODOS OS CARGOS
        listCargo.setFont(fonte);
        for (String cargo : cargos) {
            carg.addElement(cargo);
        };
        listCargo.setModel(carg);
        
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                // Habilita o overlay
                OverlayFrame.setEnabled(true);
                OverlayFrame.requestFocus();
            }
        });
        
        usuarioLabel = new JLabel("Usuário:");
        senhaLabel = new JLabel("Senha:");
        cargoLabel = new JLabel("Cargo:");
        usuarioField = new JTextField();
        senhaTextField = new JPasswordField();
        checkPasswordLabel = new JLabel("Mostrar senha");
        passwordCheckBox = new JCheckBox();
        salvarAlteracoes = new JButton();
        
        passwordCheckBox.addActionListener(e -> {
            if (passwordCheckBox.isSelected()) {
                senhaTextField.setEchoChar('\0');
            } else {
                senhaTextField.setEchoChar('*');
            }
        });
        
        salvarAlteracoes.addActionListener(e -> {
            String hqlCargoId = "SELECT c.carId FROM TbCargo c WHERE c.carDescricao = '" + listCargo.getSelectedItem() + "'";
            Query queryGetCargoId = session.createQuery(hqlCargoId);
            
            Transaction transaction = session.beginTransaction();
            try{
                int cargoId = (int) queryGetCargoId.uniqueResult();
                char[] password = senhaTextField.getPassword();
                String hqlUpdateFuncionario = "UPDATE TbFuncionario fu SET fu.funcUsuario = '" + usuarioField.getText() + "', fu.funcSenha = '" + String.valueOf(password) +"', fu.tbCargo = '" + cargoId + "' WHERE fu.tbEntidade = '" + cpf + "'";
                Query queryUpdateFuncionario = session.createQuery(hqlUpdateFuncionario);
                
                queryUpdateFuncionario.executeUpdate();
                
                transaction.commit();
                JOptionPane.showMessageDialog(null, "Credenciais alteradas com sucesso!");
                dispose();
            } catch (HibernateException ex) {
                    transaction.rollback();
                    JOptionPane.showMessageDialog(null, "Ocorreu um erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    session.clear(); // LIMPA O BUFFER QUE FOI ARMAZENADO QUANDO CLICADO EM SALVAR
                } catch (NullPointerException ex) {
                    // ERRO PARA CASO NÃO PREENCHA TODOS OS CAMPOS
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
                }
            
            
        });
        
        //Setando a posição de todos os elementos
        cargoLabel.setBounds(20, 20, 100, 20);
        listCargo.setBounds(80, 20, 200, 20);
        usuarioLabel.setBounds(20, 50, 100, 20);
        usuarioField.setBounds(80, 50, 200, 20);
        senhaLabel.setBounds(20, 80, 100, 20);
        senhaTextField.setBounds(80, 80, 200, 20);
        passwordCheckBox.setBounds(76, 110, 20, 20);
        checkPasswordLabel.setBounds(100, 110, 100, 20);
        salvarAlteracoes.setBounds(110, 150, 100, 30);
        
        String hql = "SELECT fu.funcUsuario, fu.funcSenha, fu.tbCargo.carDescricao FROM TbFuncionario fu WHERE fu.tbEntidade.entCpfCnpj = :cpf";
        query = session.createQuery(hql);
        System.out.println(cpf);
        query.setParameter("cpf", cpf);
        List<Object[]> results = query.list();
        for(Object[] dado : results){
            System.out.println(dado[0].toString() + " " + dado[1].toString());
            usuarioField.setText(dado[0].toString());
            senhaTextField.setText(dado[1].toString());
            listCargo.setSelectedItem(dado[2]);
        }
        
        add(usuarioLabel);
        add(senhaLabel);
        add(usuarioField);
        add(senhaTextField);
        add(passwordCheckBox);
        add(checkPasswordLabel);
        add(listCargo);
        add(cargoLabel);
        add(salvarAlteracoes);
    }
    
    public void showInterface(){
        OverlayFrame.setEnabled(false);
        setVisible(true);
    }
}