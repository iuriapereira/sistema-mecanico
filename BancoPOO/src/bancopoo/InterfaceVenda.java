package bancopoo;

import banco.TbVenPeca;
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

class InterfaceVenda extends InterfaceAbstrata {

    private final Session session;

    public InterfaceVenda(JFrame mainFrame, Session session) {
        super(mainFrame, session);
        this.session = session;
    }

    @Override
    protected JTable createTable(Session session) {

        String hql = "SELECT v.venId, v.tbCliente.tbEntidade.entNome, v.venData, v.tbTipoPagamento.tpDescricao, "
                + "(SELECT SUM(s.vsValorServico) FROM TbVendaSer s WHERE s.tbVenda = v), "
                + "(SELECT SUM(p.vpQuantidade * p.tbEstoque.estoValorUni) FROM TbVenPeca p WHERE p.tbVenda = v) "
                + "FROM TbVenda v";
        Query query = session.createQuery(hql);
        String[] columnNames = {"ID", "Nome Cliente", "Valor Total", "Data", "Tipo Pagamento"};

        // Modelo da tabela não editável
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        List<Object[]> results = query.list();
        for (Object[] result : results) {
            int id = (int) result[0];
            String nome = (String) result[1];
            java.sql.Timestamp timestamp = (java.sql.Timestamp) result[2];
            String pagamento = (String) result[3];
            Double totalSer = ((Number) result[4]).doubleValue();
            Double totalPec = ((Number) result[5]).doubleValue();
            // Verificar se totalSer é null e atribuir zero como valor padrão
            if (totalSer == null) {
                totalSer = 0.0;
            }

            // Verificar se totalPec é null e atribuir zero como valor padrão
            if (totalPec == null) {
                totalPec = 0.0;
            }
            Double valorTotal = totalSer + totalPec;

            model.addRow(new Object[]{id, nome, valorTotal, timestamp, pagamento});
        }

        // Cria a tabela
        JTable table = new JTable(model);
        table.setFillsViewportHeight(true);
        // Parte superior com os botões flutuantes

        JButton button = createSmallButton(buttonIcons[0]);
        JButton button2 = createSmallButton(buttonIcons[3]);
        String label = buttonLabels[0];
        String label2 = buttonLabels[3];

        if (label.equals("Inserir")) {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    InterfaceInsereCliente inserir = new InterfaceInsereCliente(mainFrame, session);
                    inserir.showInterface();
                }
            });
        } else if (label2.equals("Atualizar")) {
            button2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updateTableData(model);
                }
            });
        }

        buttonPanel.add(button);
        buttonPanel.add(button2);

        return table;
    }

    private void updateTableData(DefaultTableModel model) {
        String hql = "SELECT v.venId, v.tbCliente.tbEntidade.entNome, v.venData, v.tbTipoPagamento.tpDescricao, "
                + "(SELECT SUM(s.vsValorServico) FROM TbVendaSer s WHERE s.tbVenda = v), "
                + "(SELECT SUM(p.vpQuantidade * p.tbEstoque.estoValorUni) FROM TbVenPeca p WHERE p.tbVenda = v) "
                + "FROM TbVenda v";
        Query query = session.createQuery(hql);

        List<Object[]> results = query.list();
        model.setRowCount(0); // Limpa os dados da tabela antes de atualizar
        for (Object[] result : results) {
            int id = (int) result[0];
            String nome = (String) result[1];
            java.sql.Timestamp timestamp = (java.sql.Timestamp) result[2];
            String pagamento = (String) result[3];
            Double totalSer = ((Number) result[4]).doubleValue();
            Double totalPec = ((Number) result[5]).doubleValue();
            // Verificar se totalSer é null e atribuir zero como valor padrão
            if (totalSer == null) {
                totalSer = 0.0;
            }

            // Verificar se totalPec é null e atribuir zero como valor padrão
            if (totalPec == null) {
                totalPec = 0.0;
            }
            Double valorTotal = totalSer + totalPec;

            model.addRow(new Object[]{id, nome, valorTotal, timestamp, pagamento});
        }
    }
}
