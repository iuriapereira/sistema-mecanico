package bancopoo;

import banco.TbVenPeca;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;

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
        // Parte superior com os botões flutuantes
        for (int i = 0; i < buttonLabels.length; i++) {
            JButton button = createSmallButton(buttonIcons[i]);
            String label = buttonLabels[i];
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Verifica se uma janela menor já está aberta
                    if (!isSmallWindowOpen) {
                        if (label.equals("Inserir")) {
                            //InterfaceInsereCliente inserir = new InterfaceInsereCliente(mainFrame);
                            //inserir.showInterface();
                        } else if (label.equals("Alterar")) {
                            //InterfaceFornecedor forne = new InterfaceFornecedor(mainFrame);
                            //forne.show();
                        } else if (label.equals("Remover")) {
                            //InterfaceFuncionario func = new InterfaceFuncionario(mainFrame);
                            //func.show();
                        }
                    }
                }

            });
            buttonPanel.add(button);
        }

        // Cria a tabela
        JTable table = new JTable(model);
        table.setFillsViewportHeight(true);

        return table;
    }
}
