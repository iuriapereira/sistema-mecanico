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
        super(mainFrame,session);
        this.session = session;
    }

    @Override
    protected JTable createTable(Session session) {
        
    String hql = "SELECT v.venId, SUM(vp.vpQuantidade * e.estoValorUni) + vs.vsValorServico, v.venData, v.tbTipoPagamento " +
             "FROM TbVenda v " +
             "JOIN v.tbVenPecas vp " +
             "JOIN vp.tbEstoque e " +
             "JOIN v.tbVendaSers vs " +
             "GROUP BY v.venId, v.venData, v.tbTipoPagamento";

        Query query = session.createQuery(hql);
        String[] columnNames = {"ID", "Nome Cliente", "Valor Total", "Data","Tipo Pagamento"};

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
            float valor = (float) result[2];
            String data = (String) result[3];
            String pagamento = (String) result[4];
            
            model.addRow(new Object[]{id, nome,valor,data,pagamento}); // Adicione outras colunas conforme necessário
        }         
                // Parte superior com os botões flutuantes
        for (int i = 0; i < buttonLabels.length; i++) {
            JButton button = createSmallButton(buttonIcons[i]);
            String label = buttonLabels[i];
            button.addActionListener(new ActionListener(){
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
