package bancopoo;

import banco.TbEstoque;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

class InterfaceEstoque extends InterfaceAbstrata {

    private final Session session;
    private String selectedPeca; // Variável para armazenar o CPF selecionado

    public InterfaceEstoque(JFrame mainFrame, Session session) {
        super(mainFrame, session);
        this.session = session;
    }

    @Override
    protected JTable createTable(Session session) {
        // Código para buscar os dados do banco de dados usando Hibernate
        String hql = "SELECT e.tbFornecedorHasPeca.tbPeca.peDescricao, e.estoQuantidade, e.estoValorUni, e.tbFornecedorHasPeca.tbPeca.peQuantMin, e.tbFornecedorHasPeca.tbFornecedor.tbEntidade.entNomeFantasia FROM TbEstoque e";
        Query query = session.createQuery(hql);

        String[] columnNames = {"Nome Produto", "Quantidade em Estoque", "Valor Unitário", "Quantidade Mínima", "Fornecedor"};

        // Modelo da tabela não editável
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        List<Object[]> results = query.list();
        for (Object[] result : results) {
            String nome = (String) result[0];
            float qtd_e = (float) result[1];
            Float valor = (Float) result[2];
            float qtd_m = (float) result[3];
            String fornecedor = (String) result[4];

            model.addRow(new Object[]{nome, qtd_e, valor, qtd_m, fornecedor}); // Adicione outras colunas conforme necessário
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
                        InterfaceInsereEstoque inserir = new InterfaceInsereEstoque(mainFrame, session);
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
                            selectedPeca = (String) table.getValueAt(selectedRow, 0);
                            Transaction transaction = session.beginTransaction();
                            try {
                                String hqlDeleteEstoque = "DELETE FROM TbEstoque e WHERE e.tbFornecedorHasPeca IN (SELECT fhp FROM TbFornecedorHasPeca fhp WHERE fhp.tbPeca IN (SELECT p FROM TbPeca p WHERE p.peDescricao = :descricao))";
                                Query deleteQueryEstoque = session.createQuery(hqlDeleteEstoque);
                                deleteQueryEstoque.setParameter("descricao", selectedPeca);
                                deleteQueryEstoque.executeUpdate();

                                String hqlDeleteFornecedorHasPeca = "DELETE FROM TbFornecedorHasPeca fhp WHERE fhp.tbPeca IN (SELECT p FROM TbPeca p WHERE p.peDescricao = :descricao)";
                                Query deleteQueryFornecedorHasPeca = session.createQuery(hqlDeleteFornecedorHasPeca);
                                deleteQueryFornecedorHasPeca.setParameter("descricao", selectedPeca);
                                deleteQueryFornecedorHasPeca.executeUpdate();

                                String hqlDeletePeca = "DELETE FROM TbPeca p WHERE p.peDescricao = :descricao";
                                Query deleteQueryPeca = session.createQuery(hqlDeletePeca);
                                deleteQueryPeca.setParameter("descricao", selectedPeca);
                                deleteQueryPeca.executeUpdate();
                                
                                transaction.commit();
                                JOptionPane.showMessageDialog(null, "Produto Removido");
                                updateTableData(model);
                            } catch (HibernateException ex) {
                                transaction.rollback();
                                JOptionPane.showMessageDialog(null, "Ocorreu um erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                });
            } else if (label.equals("Atualizar")) {
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        updateTableData(model);
                    }
                });
            }

            buttonPanel.add(button);
        }
        return table;
    }

    private void updateTableData(DefaultTableModel model) {
        String hql = "SELECT e.tbFornecedorHasPeca.tbPeca.peDescricao, e.estoQuantidade, e.estoValorUni, e.tbFornecedorHasPeca.tbPeca.peQuantMin, e.tbFornecedorHasPeca.tbFornecedor.tbEntidade.entNomeFantasia FROM TbEstoque e";
        Query query = session.createQuery(hql);

        List<Object[]> results = query.list();
        model.setRowCount(0); // Limpa os dados da tabela antes de atualizar
        for (Object[] result : results) {
            String nome = (String) result[0];
            float qtd_e = (float) result[1];
            float valor = (float) result[2];
            float qtd_m = (float) result[3];
            String fornecedor = (String) result[4];

            model.addRow(new Object[]{nome, qtd_e, valor, qtd_m, fornecedor});  // Adicione outras colunas conforme necessário
        }
    }
}
