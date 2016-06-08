package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import javax.print.event.PrintEvent;
import com.sun.javafx.geom.transform.GeneralTransform3D;

import data.Data;
import data.GameRoom;
import data.User;

public class BingoGameServerThread implements Runnable {
	
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private boolean exit;
	private BingoGameServer parent;
	static ArrayList<User> connectedUserList = new ArrayList<>(); //������ ���ӵ� Ŭ���̾�Ʈ, �� Ŭ���̾�Ʈ�� ObjectOutputStream�� �����Ǿ� ����
	static HashMap<String, GameRoom> gameRoomList = new HashMap<>();
	private Data data;
	public BingoGameServerThread(){}
	public BingoGameServerThread(BingoGameServer parent, Socket client) {
		
		this.parent = parent;
		try {
			ois = new ObjectInputStream(client.getInputStream());
			oos = new ObjectOutputStream(client.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 서버에 발생되는 모든 이벤트 로그를 표시한다.
	 */
	public void printEventLog(String message)
	{
		String logMessage=String.format("[%tY-%<tm-%<td %<tH:%<tM:%<tS] %s%n",new Date(), message);
		parent.textArea.append(logMessage);
	}
	
	/**
	 *  서버에 접속된 사용자 목록을 보여준다
	 * @param userList
	 */
	public void updateConnectionList(ArrayList<User> userList)
	{
		parent.list.setListData(userList.toArray());
	}
	
	@Override
	public void run() {
		while(!exit){
			try {
				//data = (Data)ois.readObject();
				data = (Data)ois.readObject();
				
				switch(data.getCommand()){
					case Data.LOGIN: {
						User u=data.getUser();	
						u.setOos(oos);
						connectedUserList.add(u);
						printEventLog(u.getId()+"가접속했습니다");
						this.updateConnectionList(connectedUserList);
						data.setUserList(connectedUserList);
						data.setRoomList(gameRoomList);
						broadCasting();
					}
						break;
						
					case Data.MAKE_ROOM: {
						GameRoom gr=data.getGameRoom();
						User u=data.getUser();
						u.setState(User.READY);
						HashMap<String, User> h=new HashMap<>();
						h.put(u.getId(), u);
						gr.setUserList(h);
						gr.setNowUserNum(1);
						gameRoomList.put(gr.getTitle(), gr);
						this.find(gr.getRoomID(),gr);
						data.setUserList(connectedUserList);
						data.setRoomList(gameRoomList);
						broadCasting();
					}
						break;
						
					case Data.JOIN: {
						User u=(User)data.getUser();
						GameRoom g=(GameRoom)data.getGameRoom();
						u.setState(User.READY);
						findGame(g,u);
						data.setUserList(connectedUserList);
						data.setRoomList(gameRoomList);
						broadCasting();	
					}
						break;	
						
					case Data.CHAT_MESSAGE:
					case Data.SEND_WINNING_RESULT:
					case Data.GAME_READY:
						User u=data.getUser();
						for(GameRoom a:gameRoomList.values())
						{
							for(User t:a.getUserList().values())
							{
								if(t.getId().equals(u.getId()))
								{
									gameRoomList.get(a.getTitle()).getUserList().get(t.getId()).setState(User.DONE);
								}
							}
						}
						data.setUserList(connectedUserList);
						data.setRoomList(gameRoomList);
						broadCasting();
						break;
					case Data.GAME_START:
					case Data.SEND_BINGO_DATA: {
						sendDataRoommate(data.getGameRoom().getRoomID());
					}
						break;
					case Data.EXIT: {
						
					}
						break;
				}//switch
				
			} catch (IOException e) {
				exit = true;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}//while
	}//run
	
	/**
	 * ���� �濡 �ִ� �������� Data��ü ����
	 * */
	public void sendDataRoommate(String roomID){
		
	}
	public void findGame(GameRoom room,User user)
	{
		
		
		gameRoomList.get(room.getTitle()).setNowUserNum(gameRoomList.get(room.getTitle()).getNowUserNum()+1);
		gameRoomList.get(room.getTitle()).getUserList().put(user.getId(), user);
		room.setMaxUserNum(gameRoomList.get(room.getTitle()).getMaxUserNum());
		room.setNowUserNum(gameRoomList.get(room.getTitle()).getNowUserNum());
		room.setRoomID(gameRoomList.get(room.getTitle()).getRoomID());
		room.setUserList(gameRoomList.get(room.getTitle()).getUserList());
		for(int a=0;a<connectedUserList.size();a++)
		{
			if(connectedUserList.get(a).getId().equals(user.getId()))
			{
				String i_d=connectedUserList.get(a).getId();
				int privilege=User.NORMAL_PRIVILEGE;
				oos=connectedUserList.get(a).getOos();
				User u=new User(i_d, privilege);
				u.setOos(oos);
				u.setRoom(room);
				
				connectedUserList.remove(a);
				connectedUserList.add(u);
			}
		}
		
	}

	
	public void find(String id,GameRoom room)
	{
		for(int a=0;a<connectedUserList.size();a++)
		{
			if(connectedUserList.get(a).getId().equals(id))
			{
				String i_d=connectedUserList.get(a).getId();
				int privilege=User.HOST_PRIVILEGE;
				oos=connectedUserList.get(a).getOos();
				User u=new User(i_d, privilege);
				u.setOos(oos);
				u.setRoom(room);
				connectedUserList.remove(a);
				connectedUserList.add(u);
			}
		}
	}
	
	/**
	 * ��� �������� Data��ü ����
	 * */
	public void broadCasting(){
		for(User user : connectedUserList){
			try {
				user.getOos().writeObject(data);
				user.getOos().reset();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	

	public static void main(String[] args) {
		BingoGameServerThread b=new BingoGameServerThread();
		//b.printEventLog("dd");
	}
	
}
