package client;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import data.Data;
import data.GameRoom;
import data.User;

public class GameLobbyUI extends JFrame implements ActionListener {
	
	private Object [][] tableData = new Object[0][0];
	private String [] tableColumns = {"방제목", "빙고주제", "인원"};

	JButton btn_create;
	JButton btn_exit;
	JButton join;
	
	JLabel person_num;
	private static GameLobbyUI gl=new GameLobbyUI();
	JList list;
	BingoGameClient client;
	private JTable table_1;
	DefaultTableModel tmodel;
	MakeRoomUI m;
	
	public static GameLobbyUI getGL()
	{
		return gl;
	}

	
		private GameLobbyUI() {
		setTitle("빙고게임 대기실");
		// TODO Auto-generated constructor stub
		this.setBounds(300, 300, 580, 349);
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("GO! GO! Bingo Game");
		lblNewLabel.setFont(new Font("Dialog", Font.PLAIN, 39));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 0, 585, 53);
		getContentPane().add(lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 56, 327, 214);
		getContentPane().add(scrollPane);
		
		
		table_1 = new JTable();
		scrollPane.setViewportView(table_1);
		tmodel=new DefaultTableModel();
		tmodel.setColumnIdentifiers(tableColumns);
		table_1.setModel(tmodel);
		
		JLabel lblNewLabel_1 = new JLabel("접속자 목록");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(349, 57, 202, 15);
		getContentPane().add(lblNewLabel_1);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(349, 82, 202, 163);
		getContentPane().add(scrollPane_1);
		
		list = new JList();
		scrollPane_1.setViewportView(list);
		
		btn_create = new JButton("방만들기");
		btn_create.setBounds(147, 278, 97, 23);
		btn_create.addActionListener(this);
		getContentPane().add(btn_create);
		
		btn_exit = new JButton("종료하기");
		btn_exit.setBounds(355, 278, 97, 23);
		getContentPane().add(btn_exit);
		
		JLabel person_num = new JLabel("접속인원:00명");
		person_num.setFont(new Font("굴림", Font.PLAIN, 13));
		person_num.setHorizontalAlignment(SwingConstants.CENTER);
		person_num.setBounds(376, 255, 141, 15);
		getContentPane().add(person_num);
		
		join = new JButton("입장");
		join.setBounds(250, 278, 97, 23);
		join.addActionListener(this);
		getContentPane().add(join);
		this.setVisible(true);
	}
		
	public void setBingGameClient(BingoGameClient client){
		this.client = client;
	}
	
	public void updateUserList(ArrayList<User> userList){
		Object a_userList [] = userList.toArray();
		list.setListData(a_userList);
	}
	public void updateTableList(HashMap<String, GameRoom> hmap)
	{
		tmodel=new DefaultTableModel();
		tmodel.setColumnIdentifiers(tableColumns);
		table_1.setModel(tmodel);
		for(GameRoom g: hmap.values())
		{
			String title=g.getTitle();
			String theme=g.getTheme();
			int num=g.getNowUserNum();
			Object obj[]={title,theme,num};
			tmodel.addRow(obj);
		}
	}
		
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source == btn_create) {
			m=new MakeRoomUI();
		}
		if(source ==join)
		{
			String title=(String)tmodel.getValueAt(table_1.getSelectedRow(),0);
			String theme=(String)tmodel.getValueAt(table_1.getSelectedRow(),1);
			int maxUserNum=(int)tmodel.getValueAt(table_1.getSelectedRow(),2);
			Data d=new Data(Data.JOIN);
			User u=new User(client.user.getId(), client.user.getPrivilege());
			
			GameRoom g=new GameRoom(client.user.getId(), title, theme, maxUserNum);
			d.setUser(u);
			d.setGameRoom(g);
			
			try {
				client.oos.writeObject(d);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
