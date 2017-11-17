import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MonsterGameFrame extends JFrame{
	JPanel gamePanel = new GamePanel('q', 200);
	/*static int tomCatch;
	static int jerryEscape;
	static JLabel scoreLabel;
	static JLabel catchTime;
	static JLabel escapeTime;
	static int score = jerryEscape*100 - tomCatch*100;*/
	
	public MonsterGameFrame(){
		setTitle("Åè°úÁ¦¸® °ÔÀÓ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setContentPane(gamePanel);
		
		setSize(1300, 800);
		setVisible(true);
		
		gamePanel.requestFocus();
	}
	
	class GamePanel extends JPanel{
		char quitChar;
		long tomDelay;
		JLabel jerry;
		JLabel tom;
		JLabel home;
		
		final int AVATAR_MOVE = 10;
		
		public GamePanel(char quitChar, long tomDelay){
			this.quitChar = quitChar;
			this.tomDelay = tomDelay;
						
			ImageIcon tomImg = new ImageIcon("images/Tom.png");
			ImageIcon jerryImg = new ImageIcon("images/jerry.png");
			//ImageIcon homeImg = new ImageIcon("imgaes/home.png");
						
			jerry = new JLabel(jerryImg);
			tom = new JLabel(tomImg);
			home = new JLabel("home");
			//home = new JLabel(homeImg);
						
			setLayout(null);
			addKeyListener(new MyKeyListener());
			
			home.setLocation((int)(Math.random()*900), (int)(Math.random()*700));
			home.setSize(50, 50);
			home.setOpaque(true);
			home.setBackground(Color.BLUE);
			home.setFont(new Font("Arial", Font.ITALIC, 20));
			home.setHorizontalAlignment(home.CENTER);
			home.setVisible(false);
			add(home);
			
			jerry.setLocation(50, 50);
			jerry.setSize(40, 40);
			add(jerry);
			
			tom.setLocation(200, 5);
			tom.setSize(40, 40);
			tom.setForeground(Color.BLUE);
			add(tom);
			
			MonsterThread th = new MonsterThread(tom, jerry, home, tomDelay);
			th.start();
		}
		
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			ImageIcon backGround = new ImageIcon("images/BackGround.jpg");
			g.drawImage(backGround.getImage(), 0, 0, this);
		}
		
		class MyKeyListener extends KeyAdapter{
			public void keyPressed(KeyEvent e){
				JPanel panel = (JPanel)e.getSource();
				if(e.getKeyChar() == quitChar){
					System.exit(0);
				}
				int keyCode = e.getKeyCode();
				int x=jerry.getX(), y=jerry.getY();
				
				switch(keyCode){
				case KeyEvent.VK_UP:
					x = jerry.getX();
					y = jerry.getY()-AVATAR_MOVE;
					break;
				case KeyEvent.VK_DOWN:
					x =	jerry.getX();
					y = jerry.getY()+AVATAR_MOVE;
					break;
				case KeyEvent.VK_LEFT:
					x = jerry.getX()-AVATAR_MOVE;
					y = jerry.getY();
					break;
				case KeyEvent.VK_RIGHT:
					x = jerry.getX()+AVATAR_MOVE;
					y = jerry.getY();
					break;
				}
				if(x < 0) x = 0;
				if(x+10 > panel.getWidth()-150) x -= AVATAR_MOVE;
				if(y < 0) y = 0;
				if(y+40 > panel.getHeight()) y -= AVATAR_MOVE;
				jerry.setLocation(x, y);
				jerry.getParent().repaint();
			}
		}
	}
	
	/*public static void score(){
		scoreLabel = new JLabel();
		catchTime = new JLabel();
		escapeTime = new JLabel();
					
		scoreLabel.setText("Á¡¼ö : " + score);
		scoreLabel.setFont(new Font("Gothic", Font.PLAIN, 20));
		scoreLabel.setLocation(1100, 100);
		
		catchTime.setText("ÀâÈùÈ½¼ö : " + tomCatch);
		catchTime.setFont(new Font("Gothic", Font.PLAIN, 20));
		catchTime.setLocation(1100, 200);
		
		escapeTime.setText("µµ¸ÁÄ£ È½¼ö : " + jerryEscape);
		escapeTime.setFont(new Font("Gothic", Font.PLAIN, 20));	
		escapeTime.setLocation(1100, 300);
	}*/
	
	class MonsterThread extends Thread{
		JLabel from;
		JLabel to;
		JLabel home;
		long tomDelay;
		final int MONSTER_MOVE = 5;
					
		MonsterThread(JLabel from, JLabel to, JLabel home, long tomDelay){
			this.from = from;
			this.to = to;
			this.home = home;
			this.tomDelay = tomDelay;
		}
		
		public void run(){
			int x = from.getX(), y = from.getY();
			
			while(true){
				if(to.getX() < from.getX())
					x = from.getX() - MONSTER_MOVE;
				else
					x = from.getX() + MONSTER_MOVE;
				if(to.getY() < from.getY())
					y = from.getY() - MONSTER_MOVE;
				else
					y = from.getY() + MONSTER_MOVE;
				
				from.setLocation(x, y);
				from.getParent().repaint();
				
				try{
					sleep(tomDelay);
					if(tomDelay == 40){
						tomDelay = 40;
						home.setVisible(true);
						if(((to.getX() >= home.getX())&&(to.getX() <= home.getX()+50))
								&&((to.getY() >= home.getY())&&(to.getY() <= home.getY()+50))){
								tomDelay = 200;
								//jerryEscape++;
								home.setVisible(false);
								home.setLocation((int)(Math.random()*900), (int)(Math.random()*700));
						}
					} else{
						tomDelay--;
					}
					if((from.getX()+20 >= to.getX() && from.getX()+20 <= to.getX()+40) &&
							(from.getY()+20 >= to.getY() && from.getY()+20 <= to.getY() + 40)){
						//tomCatch++;
						tomDelay = 200;
						from.setLocation((int)(Math.random()*900), (int)(Math.random()*700));
					}
				} catch(InterruptedException e){
					return;
				}
			}
		}
	}
	
	public static void main(String [] args){
		new MonsterGameFrame();
	}
}
