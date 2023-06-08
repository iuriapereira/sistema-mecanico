package bancopoo;

import banco.TbFuncionario;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;

class InterfaceFuncionario extends InterfaceAbstrata {

    private final Session session;

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
            String cargo = (String) result[5];
            String login = (String) result[5];

            model.addRow(new Object[]{cpf, nome, nome_fantasia, fone, sexo, email, cargo, login}); // Adicione outras colunas conforme necessário
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
                            InterfaceInsereFuncionario inserir = new InterfaceInsereFuncionario(mainFrame, session);
                            inserir.showInterface();
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
