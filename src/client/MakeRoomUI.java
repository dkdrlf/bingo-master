package client;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JTextField;

import data.Data;
import data.GameRoom;
import data.User;

import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.Color;

public class MakeRoomUI extends JFrame implements ActionListener {
	private JTextField textField;
	private JTextField textField_1;
	JButton btnNewButton;
	JButton button;
	JComboBox comboBox;
	private Object [][] tableData = new Object[0][0];
	private String [] tableColumns = {"방제목", "빙고주제", "인원"};
	GameRoomUI g;
	
	public MakeRoomUI() {
		getContentPane().setBackground(new Color(100, 149, 237));
		setTitle("빙고게임 방 만들기");
		this.setBounds(300, 300, 229, 209);
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("방제목");
		lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 13));
		lblNewLabel.setBounds(12, 23, 66, 23);
		getContentPane().add(lblNewLabel);
		
		JLabel label = new JLabel("빙고주제");
		label.setFont(new Font("굴림", Font.PLAIN, 13));
		label.setBounds(12, 56, 66, 23);
		getContentPane().add(label);
		
		JLabel label_1 = new JLabel("참가인원");
		label_1.setFont(new Font("굴림", Font.PLAIN, 13));
		label_1.setBounds(12, 89, 66, 23);
		getContentPane().add(label_1);
		
		textField = new JTextField();
		textField.setBounds(79, 24, 116, 21);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(79, 56, 116, 21);
		getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		comboBox = new JComboBox();
		comboBox.setBounds(79, 90, 73, 21);
		comboBox.addItem("사용자수를 선택하세요");
		comboBox.addItem("1");
		comboBox.addItem("2");
		comboBox.addItem("3");
		comboBox.addItem("4");
		comboBox.addItem("5");
		getContentPane().add(comboBox);
		
		
		JLabel lblNewLabel_1 = new JLabel("명");
		lblNewLabel_1.setFont(new Font("굴림", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(161, 93, 34, 15);
		getContentPane().add(lblNewLabel_1);
		
		btnNewButton = new JButton("만들기");
		btnNewButton.setFont(new Font("굴림", Font.PLAIN, 13));
		btnNewButton.setBounds(22, 138, 82, 23);
		getContentPane().add(btnNewButton);
		btnNewButton.addActionListener(this);
		
		button = new JButton("취소");
		button.setFont(new Font("굴림", Font.PLAIN, 13));
		button.setBounds(113, 138, 66, 23);
		getContentPane().add(button);
		this.setVisible(true);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object obj=e.getSource();
		
		if(obj==btnNewButton)
		{
			String name=textField.getText();
			String subject=textField_1.getText();
			String num=(String) comboBox.getSelectedItem();	
			this.setVisible(false);
			GameLobbyUI g=GameLobbyUI.getGL();
			GameRoom gr=new GameRoom(g.client.user.getId(), name, subject, Integer.parseInt(num));
			User u=new User(g.client.user.getId(),User.HOST_PRIVILEGE);
			BingoGameClient bc=g.client;
			Data d=new Data(Data.MAKE_ROOM);
			d.setGameRoom(gr);
			d.setUser(u);
			try {
				bc.oos.writeObject(d);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}	
}
