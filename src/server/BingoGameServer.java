package server;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import data.User;

import javax.swing.JList;
import java.awt.Color;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
//<옆에있는걸 나도 쓰겠다. ,%t 시간타입을 받겠다
public class BingoGameServer extends JFrame implements Runnable {
	
	public JTextArea textArea;
	public JList list;
	
	public BingoGameServer() {
		
		setTitle("Bingo Game Server");
		this.setBounds(300, 250, 700, 500);
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JLabel lblbingoGameServer = new JLabel("■ Bingo Game Server Event Log View");
		lblbingoGameServer.setBounds(12, 10, 262, 29);
		getContentPane().add(lblbingoGameServer);
		
		JLabel lblConnectionlist = new JLabel("■ ConnectionList");
		lblConnectionlist.setBounds(404, 10, 262, 29);
		getContentPane().add(lblConnectionlist);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 49, 365, 403);
		getContentPane().add(scrollPane);
		
		textArea = new JTextArea();
		textArea.setForeground(Color.GREEN);
		textArea.setBackground(Color.BLACK);
		scrollPane.setViewportView(textArea);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(404, 49, 268, 403);
		getContentPane().add(scrollPane_1);
		
		list = new JList();
		scrollPane_1.setViewportView(list);
		this.setVisible(true);
		
		// TODO Auto-generated constructor stub
	}


		
	@Override
	public void run() {
		// TODO Auto-generated method stub
		ServerSocket serversocket;
		try {
			serversocket = new ServerSocket(7777);
			while(true)
			{
				Socket socket=serversocket.accept();
				BingoGameServerThread st=new BingoGameServerThread(this, socket);	
				Thread tt=new Thread(st);
				tt.start();
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		BingoGameServer s=new BingoGameServer();
		Thread t=new Thread(s);
		t.start();
		

	}
}
