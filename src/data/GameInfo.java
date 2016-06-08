package data;

import java.io.Serializable;
import java.util.Scanner;

public class GameInfo implements Serializable {
	Scanner sc=new Scanner(System.in);
	
	private String keyword; //본인이 선택하거나, 상대방이 선택한 빙고 단어
	//private String bingoKeywords [][] = new String[5][5]; //입력한 25개의 빙고 단어 저장
	private String bingoKeywords[][]={{"1","2","3","4","5"},
									  {"6","7","8","9","10"},
									  {"11","12","13","14","15"},
									  {"16","17","18","19","20"},
									  {"21","22","23","24","25"}};
	
	
	private int bingoResult [][] = new int[5][5];	//빙고 현황(결과)를 담는 배열, 내가 선택하거나 상태방이 선택한 빙고 단어와 일치되는 위치의 값을 1로 초기화 한다.
	//private int bingoResult[][]={{1,1,1,1,1},{0,0,0,0,0},{0,0,0,0,0},{0,0,0,0,0},{0,0,0,0,0}};
	private int x, y; //상대방이 선택한 빙고 단어가 내가 입력한 빙고 단어와 일치하는 것이 발견될 경우 해당 단어의 배열(5X5)상의 위치 값
	private User user; //자기자신에 대한 정보를 갖는 User
	int c=0;//빙고체크변수
	public String[][] getBingoKeyword() {
		return bingoKeywords;
	}

	public void setBingoKeywords(String[][] bingoKeyword) {
		this.bingoKeywords = bingoKeyword;
	}
	
	/**
	 * 인자로 전달된 빙고 단어를 가지고 빙고 결과를 담는 배열을 1로 초기화 한다.
	 * 사용자가 입력한 빙고 단어와 일치하는 단어가 있으면 bingoResult 배열의 해당 위치값을 1로 초기화 하고, 해당 배열의 위치 값으로 x,y 변수를 초기화 한다.
	 * gui빨간색으로 그리기위해 위치값을 기억해놈
	 * 매개변수로 전달된 단어와 일치하는 단어가 있으면 true를, 그렇지 않으면 false를 반환한다.
	 * */
	public boolean markBingoResult(String keyword){
		boolean result = false;
		
		for(int i=0;i<5;i++)
		{
			for(int a=0;a<5;a++)
			{
				if(bingoKeywords[i][a].equals(keyword))
				{
					x=i;
					y=a;
					
					bingoResult[i][a]=1;
					if(c!=checkBingo())
					{
						System.out.println("!빙고!="+checkBingo());
						c++;
					}					
				}
			}
		}

		return result;
	}
	
	/**
	 * 빙고 결과를 담는 배열로부터 빙고 개수(가로,세로,대각선 연속하여 5개의 빙고단어를 맞춘 개수) 계산하여 반환한다.
	 * */
	public int checkBingo(){
		int bingoNum = 0;
		int cnt_1=0;
		int cnt_2=0;
		int cnt_3=0;
		int cnt_4=0;
		for(int a=0;a<5;a++)
		{
			for(int b=0;b<5;b++)
			{
				if(bingoResult[a][b]==1)
				{
					cnt_1++;
					if(b==4)
					{
						if(cnt_1==5)
						{
							bingoNum++;
							cnt_1=0;
							//System.out.println("빙고가로1!"+bingoNum);
						}
					}
					
				}
				
				if(bingoResult[b][a]==1)
				{
					cnt_2++;
					if(b==4)
					{
						if(cnt_2==5)
						{
							bingoNum++;
							cnt_2=0;
							//System.out.println("빙고세로2!"+bingoNum);
						}
					}
				}
				if(a==b)
				{
					if(bingoResult[a][b]==1)
					{
						cnt_3++;
						if(cnt_3==5)
						{
							bingoNum++;
							//System.out.println("빙고대각선3!"+bingoNum);
						}
					}
					
				}
				if(a+b==4)
				{
					if(bingoResult[a][b]==1)
					{
						cnt_4++;
						if(cnt_4==5) 
						{
							bingoNum++;
							//System.out.println("빙고대각선4!"+bingoNum);
						}
					}
				}
				
			}
		}
	
		return bingoNum;
		
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public String getKeyword() {
		return keyword;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public int[][] getBingoResult() {
		return bingoResult;
	}
	
	public static void main(String[] args) {
		GameInfo g=new GameInfo();
		//g.sc.nextLine();
		
		//System.out.println(g.checkBingo());
		while(true)
		{
			System.out.println("숫자를 입력하세요");
			g.markBingoResult(g.sc.next());
		}
		
	}

	
}
