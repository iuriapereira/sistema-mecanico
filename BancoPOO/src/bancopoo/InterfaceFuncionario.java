package bancopoo;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class InterfaceFuncionario extends InterfaceAbstrata {

    private final Session session;
    private String selectedCPF; // Variável para armazenar o CPF selecionado

    public InterfaceFuncionario(JFrame mainFrame, Session session) {
        super(mainFrame, session);
        this.session = session;
    }

    @Override
    protected JTable createTable(Session session) {
        String hql = "SELECT f.tbEntidade.entCpfCnpj, f.tbEntidade.entNome, f.tbEntidade.entNomeFantasia, f.tbEntidade.entFone, f.tbEntidade.entSexo, f.tbEntidade.entEmail, f.tbCargo.carDescricao, f.funcUsuario FROM TbFuncionario f";
        Query query = session.createQuery(hql);

        String[] columnNames = {"Cpf/Cnpj", "Nome", "Nome Fantasia", "Fone", "Sexo", "Email", "Cargo", "Login"};

        // Modelo da tabela não editável
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        List<Object[]> results = query.list();
        for (Object[] result : results) {
            String cpf = (String) result[0];
            String nome = (String) result[1];
            String nome_fantasia = (String) result[2];
            String fone = (String) result[3];
            String sexo = (String) result[4];
            String email = (String) result[5];
            String cargo = (String) result[6]; // Corrigido índice do array
            String login = (String) result[7]; // Corrigido índice do array

            model.addRow(new Object[]{cpf, nome, nome_fantasia, fone, sexo, email, cargo, login});
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
                        InterfaceInsereFuncionario inserir = new InterfaceInsereFuncionario(mainFrame, session);
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
                        if (selectedRow != -1) {
                            // Obtém o CPF da linha selecionada
                            selectedCPF = (String) table.getValueAt(selectedRow, 0);
                            Transaction transaction = session.beginTransaction();
                            try {
                                String hql = "DELETE FROM TbFuncionario f WHERE f.tbEntidade.entCpfCnpj = :cpf";
                                Query deleteQuery = session.createQuery(hql);
                                deleteQuery.setParameter("cpf", selectedCPF);
                                deleteQuery.executeUpdate();
                                transaction.commit();
                                JOptionPane.showMessageDialog(null, "Funcionario Removido");
                            } catch (HibernateException ex) {
                                transaction.rollback();
                                JOptionPane.showMessageDialog(null, "Ocorreu um erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                });
            }else if(label.equals("Atualizar")){
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        updateTableData(model); 
                    }
                });
            }

            buttonPanel.add(button);
        }
    
        Font fonte = new Font("Times New Roman", Font.ROMAN_BASELINE, 14);
        table.setFont(fonte);
        return table;
    }
     private void updateTableData(DefaultTableModel model) {
        String hql = "SELECT f.tbEntidade.entCpfCnpj, f.tbEntidade.entNome, f.tbEntidade.entNomeFantasia, f.tbEntidade.entFone, f.tbEntidade.entSexo, f.tbEntidade.entEmail, f.tbCargo.carDescricao, f.funcUsuario FROM TbFuncionario f";
        Query query = session.createQuery(hql);

        List<Object[]> results = query.list();
        model.setRowCount(0); // Limpa os dados da tabela antes de atualizar
        for (Object[] result : results) {
            String cpf = (String) result[0];
            String nome = (String) result[1];
            String nome_fantasia = (String) result[2];
            String fone = (String) result[3];
            String sexo = (String) result[4];
            String email = (String) result[5];
            String cargo = (String) result[6]; // Corrigido índice do array
            String login = (String) result[7]; // Corrigido índice do array

            model.addRow(new Object[]{cpf, nome, nome_fantasia, fone, sexo, email, cargo, login});// Adicione outras colunas conforme necessário
        }
    }
}

