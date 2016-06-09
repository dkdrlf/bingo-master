package client;

import javax.swing.JFrame;
import java.awt.CardLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.plaf.synth.SynthSplitPaneUI;
import javax.swing.table.DefaultTableModel;

import com.sun.javafx.css.StyleCache.Key;

import data.Data;
import data.GameRoom;
import data.User;

import javax.swing.JTable;
import javax.swing.JTextArea;


public class GameRoomUI extends JFrame implements ActionListener,KeyListener {
	JTextField a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15,a16,a17,a18,a19,a20,a21,a22,a23,a24,a25,a26;
	JButton b1,b2,b3,b4,b5,b6,b7,b8,b9,b10,b11,b12,b13,b14,b15,b16,b17,b18,b19,b20,b21,b22,b23,b24,b25;

	JTextField[] jt_a={a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15,a16,a17,a18,a19,a20,a21,a22,a23,a24,a25,a26};
	JButton[] btn_b={b1,b2,b3,b4,b5,b6,b7,b8,b9,b10,b11,b12,b13,b14,b15,b16,b17,b18,b19,b20,b21,b22,b23,b24,b25};
	public JTable table;
	DefaultTableModel dt=new DefaultTableModel();
	String column[]={"차례","ID","상태","빙고"};
	public JTextField textField;
	JButton ready;
	JButton exit;
	CardLayout c;
	JPanel panel;
	JLabel lb_title;
	JTextArea textArea;
	private JLabel lblNewLabel_2;
	private static GameRoomUI grui=new GameRoomUI();
	private int time = 30;
	
	public static GameRoomUI getGameRoomUI()
	{
		return grui;
	}

	private GameRoomUI() {
		setTitle("빙고게임 창");
		// TODO Auto-generated constructor stub
		this.setBounds(300, 300, 600, 500);
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		panel = new JPanel();
		panel.setBounds(0, 30, 351, 299);
		getContentPane().add(panel);
		c=new CardLayout(0, 0);
		panel.setLayout(c);
		JPanel panel_1 = new JPanel();
		panel.add(panel_1, "name_1925665454453");
		panel_1.setLayout(new GridLayout(5,5));
		
		HashSet<Integer> sh = new HashSet<>();
		while(true){
			int n = (int)(Math.random()*50)+1;
			sh.add(n);
			if(sh.size() == 25) break;
		}
		
		ArrayList<Integer> rn = new ArrayList<>();
		for(Integer ii : sh){
			rn.add(ii);
		}
		
		Collections.shuffle(rn);
		
		for(int a=0;a<25;a++)
		{
			jt_a[a]=new JTextField(Integer.toString(rn.get(a)));
			panel_1.add(jt_a[a]);
		}
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2, "name_2895533320205");
		panel_2.setLayout(new GridLayout(5,5));
		
		for(int a=0;a<25;a++)
		{
			btn_b[a]=new JButton();
			panel_2.add(btn_b[a]);
		}
		
		lb_title = new JLabel();
		lb_title.setBounds(0, 0, 204, 30);
		getContentPane().add(lb_title);
		
		JLabel lblNewLabel_1 = new JLabel("참가자");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(351, 30, 233, 30);
		getContentPane().add(lblNewLabel_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(351, 61, 233, 255);
		getContentPane().add(scrollPane);
		
		table = new JTable();
		table.setModel(dt);
		scrollPane.setViewportView(table);
		
		lblNewLabel_2 = new JLabel("제한시간 :00초");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(351, 316, 233, 23);
		getContentPane().add(lblNewLabel_2);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(0, 329, 351, 97);
		getContentPane().add(scrollPane_1);
		
		textArea = new JTextArea();
		scrollPane_1.setViewportView(textArea);
		
		textField = new JTextField();
		textField.setBounds(0, 431, 351, 21);
		getContentPane().add(textField);
		textField.setColumns(10);
		textField.addKeyListener(this);
		
		ready = new JButton("준비완료");
		ready.setBounds(361, 349, 97, 23);
		ready.addActionListener(this);
		getContentPane().add(ready);
		
		exit = new JButton("방나가기");
		exit.setBounds(475, 349, 97, 23);
		exit.addActionListener(this);
		getContentPane().add(exit);
		
		
		setVisible(true);
	}
	
	public void setTable(HashMap<String, User> u)
	{
		Boolean b=true;
		int cnt=0;
		dt=new DefaultTableModel();
		dt.setColumnIdentifiers(column);
		for(User user :u.values())
		{
			Object[] obj={"X",user.getId(),user.getState(),"X"};
			dt.addRow(obj);
			cnt++;
			
		}
		table.setModel(dt);
		for(int a=0;a<cnt;a++)
		{
			if(dt.getValueAt(a,2).equals(User.READY))
			{
				b=false;
			}
		}
		if(b)
		{
			JOptionPane.showConfirmDialog(this, "게임이 시작되었습니다", "게임시작", JOptionPane.PLAIN_MESSAGE);
			new Thread(new Timer()).start();
		}
	}
	public void setMessage(Data data)
	{
		textArea.append(data.getUser().getId()+":"+data.getMessage()+"\n");
	}
	
	private class Timer implements Runnable {

		@Override
		public void run() {
			while(true){
				time--;
				lblNewLabel_2.setText("제한시간: "+time);
				if(time == 0){
					break;
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object ob=e.getSource();
		Boolean b=true;
		if(ob==ready)
		{
			out:
			for(int a=0;a<25;a++)
			{
				if(jt_a[a].getText().equals(""))
				{
					JOptionPane.showConfirmDialog(this, "값을 다 입력하세요", "확인", JOptionPane.PLAIN_MESSAGE);
					b=false;
					break;
				}
				else
				{
					for(int c=a+1;c<25;c++)
					{
						if(jt_a[a].getText().equals(jt_a[c].getText()))
						{
							JOptionPane.showConfirmDialog(this, "중복된 값을 입력하지 마세요", "확인", JOptionPane.PLAIN_MESSAGE);
							b=false;
							break out;
						}
					}
				}
				btn_b[a].setText(jt_a[a].getText());
			}
			if(b==true)
			{
				c.previous(panel);
				GameLobbyUI g=GameLobbyUI.getGL();
		
				User u=new User(g.client.user.getId(), g.client.user.getPrivilege());
				Data d=new Data(Data.GAME_READY);
				d.setUser(u);
				try {
					g.client.oos.writeObject(d);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		}
		else if(ob==exit)
		{
			this.setVisible(false);
		}
	}
	

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.VK_ENTER==e.getKeyCode())
		{
			Data d=new Data(Data.CHAT_MESSAGE);
			GameLobbyUI gl=GameLobbyUI.getGL();
			User u=new User(gl.client.user.getId(), gl.client.user.getPrivilege());
			d.setMessage(textField.getText());
			d.setUser(u);
			GameRoom g=new GameRoom("", lb_title.getText(), "", 1);
			d.setGameRoom(g);
			try {
				gl.client.oos.writeObject(d);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			textField.setText("");
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public static void main(String[] args) {
		new GameRoomUI();
	}
	

}
