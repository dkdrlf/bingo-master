package tabletest;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;


public class TableTest extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JScrollPane scrollPane;
	private JTable table;
	private JButton btnNewButton;
	private Object [][] tableData = new Object[0][0];
	private String [] tableColumns = {"SSN", "Name", "Address"};
	private TableModel tm;
	private JButton btnNewButton_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TableTest frame = new TableTest();
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
	public TableTest() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		tm = new TableModel(tableData, tableColumns);
		table = new JTable(tm);
		table.setFillsViewportHeight(true);
		table.addMouseListener(new TableEventHandler());
		
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.setColumnSelectionAllowed(true);
		(table.getTableHeader()).setReorderingAllowed(false);
		table.setCellSelectionEnabled(false);
		scrollPane.setViewportView(table);
		
		btnNewButton = new JButton("Press Me");
		contentPane.add(btnNewButton, BorderLayout.SOUTH);
		btnNewButton.addActionListener(this);
		
		btnNewButton_1 = new JButton("Get Data");
		contentPane.add(btnNewButton_1, BorderLayout.NORTH);
		btnNewButton_1.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source == btnNewButton) {
			Object tableData [][] = {{"abc", "def", "ghi"}};
			tm.setDataVector(tableData, tableColumns);
		} else {
			int index = table.getSelectedRow();
			String value = (String)table.getValueAt(index, 0);
			System.out.println(value);
		}
	}
	
	private class TableModel extends DefaultTableModel {
		
		public TableModel(Object [][] defaultRowData,Object [] defaultColumnNames){
			super.setDataVector(defaultRowData,defaultColumnNames);			 
    	}

		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return false;
		}		
	}
	
	public class TableEventHandler extends MouseAdapter {
		public void mouseClicked(MouseEvent me){
			
		}
	}
	

}
