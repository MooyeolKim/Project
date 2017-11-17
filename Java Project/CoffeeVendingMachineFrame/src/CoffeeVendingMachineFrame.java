import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CoffeeVendingMachineFrame extends JFrame{
	CoffeeMachinePanel coffeeMachinePanel;
	
	public CoffeeVendingMachineFrame(){
		setTitle("Open Challenge 14");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		coffeeMachinePanel = new CoffeeMachinePanel();
		
		add(new TitlePanel(), BorderLayout.NORTH);
		add(coffeeMachinePanel, BorderLayout.CENTER);
		add(new ButtonPanel(), BorderLayout.SOUTH);
		
		setSize(450, 400);
		setResizable(false);
		setVisible(true);
	}
	
	class TitlePanel extends JPanel{
		JLabel titleMsg = new JLabel("Welcome, Hot Coffee !!");
		public TitlePanel(){
			titleMsg.setHorizontalAlignment(JLabel.CENTER);
			setBackground(Color.MAGENTA);
			add(titleMsg);
		}
	}
	
	class ButtonPanel extends JPanel{
		JButton[] coffeeButtons = new JButton[4];
		String[] names = {"Black Coffee", "Sugar Coffee", "Milk Coffee", "Reset"};
		
		public ButtonPanel(){
			for(int i=0 ; i<coffeeButtons.length ; i++){
				coffeeButtons[i] = new JButton(names[i]);
				add(coffeeButtons[i]);
				
				coffeeButtons[i].addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						coffeeMachinePanel.operate(e.getActionCommand());
					}
				});
			}
		}
	}
	
	class CoffeeMachinePanel extends JPanel{
		BoxLabel[] boxes = new BoxLabel[5];
		JLabel coffeeImageLabel;
		
		final int CUP = 0;
		final int COFFEE = 1;
		final int WATER = 2;
		final int SUGAR = 3;
		final int CREAM = 4;
		
		String[] boxNames = {"Cup", "Coffee", "Water", "Sugar", "Cream"};
		
		ImageIcon coffeeIcon = new ImageIcon("images/coffee.jpg");
		
		public CoffeeMachinePanel(){
			setLayout(null);
			
			for(int i=0 ; i<boxes.length ; i++){
				boxes[i] = new BoxLabel();
				boxes[i].setLocation(30+80*i, 10);
				boxes[i].setSize(40, 100);
				
				JLabel text = new JLabel(boxNames[i]);
				text.setLocation(30+80*i, 120);
				text.setSize(50, 30);
				
				add(boxes[i]);
				add(text);
			}
			
			coffeeImageLabel = new JLabel();
			coffeeImageLabel.setLocation(180, 200);
			coffeeImageLabel.setSize(coffeeIcon.getIconWidth(), coffeeIcon.getIconHeight());
			
			add(coffeeImageLabel);
		}
		
		public void operate(String cmd){
			if(cmd.equals("Black Coffee")){
				if(boxes[CUP].isEmpty() || boxes[COFFEE].isEmpty() || boxes[WATER].isEmpty()){
					error("부족한 것이 있습니다. 채워주세요.");
					return;
				} else{
					boxes[CUP].consume();
					boxes[COFFEE].consume();
					boxes[WATER].consume();
				}
			}
			else if(cmd.equals("Sugar Coffee")){
				if(boxes[CUP].isEmpty() || boxes[COFFEE].isEmpty() || boxes[WATER].isEmpty() || boxes[SUGAR].isEmpty()){
					error("부족한 것이 있습니다. 채워주세요.");
					return;
				} else{
					boxes[CUP].consume();
					boxes[COFFEE].consume();
					boxes[WATER].consume();
					boxes[SUGAR].consume();
				}
			}
			else if(cmd.equals("Milk Coffee")){
				if(boxes[CUP].isEmpty() || boxes[COFFEE].isEmpty() || boxes[WATER].isEmpty() || boxes[SUGAR].isEmpty() || boxes[CREAM].isEmpty()){
					error("부족한 것이 있습니다. 채워주세요.");
					return;
				}else{
					boxes[CUP].consume();
					boxes[COFFEE].consume();
					boxes[WATER].consume();
					boxes[SUGAR].consume();
					boxes[CREAM].consume();
				}
			}
			
			else if(cmd.equals("Reset")){
				boxes[CUP].reset();
				boxes[COFFEE].reset();
				boxes[WATER].reset();
				boxes[SUGAR].reset();
				boxes[CREAM].reset();
				repaint();
				return;
			}
			
			repaint();
			
			this.coffeeImageLabel.setIcon(coffeeIcon);
			JOptionPane.showMessageDialog(null, "뜨거워요, 즐거운 하루~~", "커피나왔습니다.", JOptionPane.INFORMATION_MESSAGE);
			coffeeImageLabel.setIcon(null);
		}
		
		public void error(String msg){
			JOptionPane.showMessageDialog(null, msg, "커피 자판기 경고",JOptionPane.WARNING_MESSAGE);
			return;
		}
	}
	
	class BoxLabel extends JLabel{
		final int MAXSIZE = 10;
		int currentSize;
		
		public BoxLabel(){
			currentSize = MAXSIZE;
		}
		boolean consume(){
			if(currentSize > 0){
				currentSize--;
				return true;
			}
			else
				return false;
		}
		
		void reset(){
			currentSize = MAXSIZE;
		}
		
		boolean isEmpty(){
			return currentSize == 0;
		}
		
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			g.setColor(Color.LIGHT_GRAY);
			int y = this.getHeight() - (currentSize*this.getHeight() / MAXSIZE);
			g.fillRect(0, y, this.getWidth(), this.getHeight() - y);
			
			g.setColor(Color.GRAY);
			g.drawRect(0, 0, this.getWidth()-1, this.getHeight()-1);
			
		}
	}
	
	public static void main(String [] args){
		new CoffeeVendingMachineFrame();
	}
}
