import javax.swing.JFrame;
import javax.swing.JTabbedPane;


public class MainFrame extends JFrame{
	private JTabbedPane tp;
	private AddPane ap;
	private FindPane fp;
	private TotalPane tpa;
	
	public MainFrame(){
		tp = new JTabbedPane();
		ap = new AddPane();
		fp = new FindPane();
		tpa = new TotalPane();
		
		tp.addTab("사원정보입력" , ap);
		tp.addTab("사원정보조회", fp);
		tp.addTab("사원전체보기", tpa);
		
		getContentPane().add(tp);
		setTitle("사원관리(JDBC 버전)");
		
		pack();
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String [] args){
		new MainFrame();
	}
}
