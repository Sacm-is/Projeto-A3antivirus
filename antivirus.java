import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class antivirus extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable resultTable;
	private DefaultTableModel tableModel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					antivirus frame = new antivirus();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public antivirus() {
		setTitle("Antivirus");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnScan = new JButton("Scan");
		btnScan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(() -> {
	                String caminhoInicial = "C:\\Users\\942422760"; // Caminho inicial
	                String[] nomeArquivos = {
	                    "script-aula-teste.bat",
	                    "script-aula.bat",
	                    "execucao-aula-teste.exe",
	                    "execucao-aula.exe"
	                };

	                List<File> retornoScan = scanFiles(new File(caminhoInicial), nomeArquivos);

	                // Atualiza a tabela com os resultados
	                SwingUtilities.invokeLater(() -> {
	                    tableModel.setRowCount(0); // Limpa a tabela
	                    if (!retornoScan.isEmpty()) {
	                        for (File file : retornoScan) {
	                            tableModel.addRow(new Object[] { file.getName(), file.getAbsolutePath() });
	                        }
	                    } else {
	                        JOptionPane.showMessageDialog(null, "Nenhum arquivo encontrado.", "Resultados do Scan", JOptionPane.INFORMATION_MESSAGE);
	                    }
	                });
	            }).start();
			}
		});
		btnScan.setFont(new Font("Segoe UI Symbol", Font.BOLD, 15));
		btnScan.setBounds(311, 62, 117, 50);
		contentPane.add(btnScan);
		
		tableModel = new DefaultTableModel(new Object[][] {}, new String[] { "Nome do arquivo", "Caminho completo" });
        resultTable = new JTable(tableModel);
        resultTable.getColumnModel().getColumn(0).setPreferredWidth(150);
        resultTable.getColumnModel().getColumn(1).setPreferredWidth(400);
		
		JScrollPane scrollPane = new JScrollPane(resultTable);
        scrollPane.setBounds(50, 150, 634, 250); // Ajuste de posição e tamanho
        contentPane.add(scrollPane);
    }
	
	
	private List<File> scanFiles(File diretorio, String[] nomesArquivos) {
        List<File> ArquivosScan = new ArrayList<>();

        try {
            if (diretorio.isDirectory()) {
                File[] files = diretorio.listFiles();

                if (files != null) {
                    for (File file : files) {
                        if (file.isDirectory()) {
                        	ArquivosScan.addAll(scanFiles(file, nomesArquivos)); // Busca recursiva
                        } else {
                            for (String targetFileName : nomesArquivos) {
                                if (file.getName().equalsIgnoreCase(targetFileName)) {
                                	ArquivosScan.add(file);
                                    break; // Encerra busca para o nome atual
                                }
                            }
                        }
                    }
                }
            }
        } catch (SecurityException e) {
            // Ignorar pastas sem permissão
            System.err.println("Sem permissão para acessar: " + diretorio.getAbsolutePath());
        }

        return ArquivosScan;
    }
}