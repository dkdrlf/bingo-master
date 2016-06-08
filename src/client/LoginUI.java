package client;

import data.Data;
import data.User;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.sun.corba.se.spi.orbutil.fsm.Action;

import data.User;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class LoginUI extends JFrame implements ActionListener{
	private JTextField textField;
	JButton btnNewButton;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	
	public LoginUI() {
		getContentPane().setBackground(Color.PINK);
		setTitle("LoginUI");
		
		// TODO Auto-generated constructor stub
		this.setBounds(200, 200, 200, 248);
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		btnNewButton = new JButton("BingoGame Login");
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBackground(Color.BLACK);
		btnNewButton.setBounds(12, 10, 160, 45);
		btnNewButton.addActionListener(this);
		getContentPane().add(btnNewButton);
		
		JLabel label = new JLabel("아이디");
		label.setBounds(12, 80, 48, 15);
		getContentPane().add(label);
		
		textField = new JTextField();
		textField.setBounds(56, 77, 116, 21);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton_1 = new JButton("퇴장");
		btnNewButton_1.setBounds(21, 145, 63, 23);
		getContentPane().add(btnNewButton_1);
		
		JButton button = new JButton("닫기");
		button.setBounds(96, 145, 63, 23);
		getContentPane().add(button);
		this.setVisible(true);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object obj=e.getSource();
		
		if(obj==btnNewButton)
		{
			String id = textField.getText();
			User user = new User(id, User.NORMAL_PRIVILEGE);
			this.setVisible(false);
			BingoGameClient client = new BingoGameClient(user);
		}
		
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LoginUI l=new LoginUI();
		
	
	}
}
