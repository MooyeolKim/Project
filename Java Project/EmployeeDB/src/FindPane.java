import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;


public class FindPane extends JPanel implements ActionListener {
	private JPanel [] jp = new JPanel[6];
	private JLabel [] jl = new JLabel[5];
	private JTextField [] tf = new JTextField[5];
	private JButton okb;
	private JButton rsb;
		
	String [] caption = {"사 번 : ", "이 름 : ", "직 책 : ", "부 서 : ", "메 일 : "};
	
	public FindPane(){
		setLayout(new GridLayout(7, 1));
		EtchedBorder eb = new EtchedBorder();
		setBorder(eb);
		
		int size = caption.length;
		
		for(int i=0 ; i<size ; i++){
			jp[i] = new JPanel();
			jl[i] = new JLabel(caption[i]);
			tf[i] = new JTextField(15);
			jp[i].add(jl[i]);
			jp[i].add(tf[i]);
			add(jp[i]);
			
			tf[i].setEditable(false);
			if(i==0 || i==1)
				tf[i].setEditable(true);
		}
		
		jp[size] = new JPanel();
		okb = new JButton("사원조회");
		okb.addActionListener(this);
		rsb = new JButton("다시쓰기");
		rsb.addActionListener(this);
		jp[size].add(okb);
		jp[size].add(rsb);
		add(jp[size]);
	}
	
	public void actionPerformed(ActionEvent ae) {
		String ae_type = ae.getActionCommand();
		EmployeeVO evo = null;
		EmployeeDAO edvo = null;
		if(ae_type.equals(okb.getText())){
			try{
				edvo = new EmployeeDAO();
				String sno = tf[0].getText().trim();
				String sname = tf[1].getText().trim();
				
				if(!sno.equals("") && !sname.equals("")){
					int no = Integer.parseInt(sno);
					evo = edvo.getEmployeeCheck(no, sname);
				} else if(!sno.equals("") && sname.equals("")){
					int no = Integer.parseInt(sno);
					evo = edvo.getEmployeeNo(no);
				} else if(sno.equals("") && !sname.equals(""))
					evo = edvo.getEmployeeName(sname);
			}catch(Exception e){
				System.out.println("e = [" + e + "]");
			}
			
			if(evo != null){
				tf[0].setText(evo.getNo() + "");
				tf[1].setText(evo.getName());
				tf[2].setText(evo.getJobGrade());
				tf[3].setText(evo.getDepartment() + "");
				tf[4].setText(evo.getEmail());
			} else{
				JOptionPane.showMessageDialog(this, "검색 실패");
			}
		} else if(ae_type.equals(rsb.getText())){
			int size = caption.length;
			for(int i=0 ; i<size-1 ; i++){
				tf[i].setText("");
			}
		}
	}
}
