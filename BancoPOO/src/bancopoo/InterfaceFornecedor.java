package bancopoo;

import banco.TbFornecedor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class InterfaceFornecedor extends InterfaceAbstrata {
    private Session session;
    private String selectedCPF;

    public InterfaceFornecedor(JFrame mainFrame, Session session) {
        super(mainFrame,session);
        this.session = session;
    }

    @Override
    protected JTable createTable(Session session) {
        // Código para buscar os dados do banco de dados usando Hibernate
        String hql = "SELECT f.tbEntidade.entCpfCnpj, f.tbEntidade.entNomeFantasia, f.tbEntidade.entFone, f.tbEntidade.entEmail FROM TbFornecedor f";
        Query query = session.createQuery(hql);

        String[] columnNames = {"Nome Fantasia", "Cpf/Cnpj", "Fone", "Email"};
        // Modelo da tabela não editável
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        List<Object[]> results = query.list();
        for (Object[] result : results) {
            String cnpj = (String) result[0];
            String nome = (String) result[1];
            String fone = (String) result[2];
            String email = (String) result[3];         
            
            model.addRow(new Object[]{nome, cnpj, fone,email}); // Adicione outras colunas conforme necessário
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
                        InterfaceInsereFornecedor inserir;
                        try {
                            inserir = new InterfaceInsereFornecedor(mainFrame, session);
                            inserir.showInterface();
                        } catch (ParseException ex) {
                            Logger.getLogger(InterfaceFornecedor.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
            } else if (label.equals("Alterar")) {
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int selectedRow = table.getSelectedRow();
                        selectedCPF = (String) table.getValueAt(selectedRow, 1);
                        InterfaceAlterarEntidade alterar;
                        try {
                            alterar = new InterfaceAlterarEntidade(mainFrame, session, selectedCPF, "FORN");
                            alterar.showInterface();
                        } catch (ParseException ex) {
                            Logger.getLogger(InterfaceCliente.class.getName()).log(Level.SEVERE, null, ex);
                        }
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
                                String hql = "DELETE FROM TbFornecedor f WHERE f.tbEntidade.entCpfCnpj = :cpf";
                                Query deleteQuery = session.createQuery(hql);
                                deleteQuery.setParameter("cpf", selectedCPF);
                                deleteQuery.executeUpdate();
                                transaction.commit();
                                updateTableData(model);
                                JOptionPane.showMessageDialog(null, "Forncedor Removido");
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
        String hql = "SELECT f.tbEntidade.entCpfCnpj, f.tbEntidade.entNomeFantasia, f.tbEntidade.entFone, f.tbEntidade.entEmail FROM TbFornecedor f";
        Query query = session.createQuery(hql);
        model.setRowCount(0); 
        List<Object[]> results = query.list();
        for (Object[] result : results) {
            String cnpj = (String) result[0];
            String nome = (String) result[1];
            String fone = (String) result[2];
            String email = (String) result[3];         
            
            model.addRow(new Object[]{nome, cnpj, fone,email});// Adicione outras colunas conforme necessário
        }
    }
}