import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

public class SearchDelFrame extends JFrame{
	JTextField srchField = new JTextField(15);
	JButton srchBtn = new JButton("SEARCH");
	
	JTextField delField = new JTextField(15);
	JButton delBtn = new JButton("DEL");
	
	JTextArea textArea = new JTextArea(20, 25);
	
	public SearchDelFrame(String title){
		super(title);
		setBounds(100, 200, 330, 450);
		setLayout(new BorderLayout());
		Border border = BorderFactory.createEtchedBorder();
		
		Border srchBorder = BorderFactory.createTitledBorder(border, "Search");
		JPanel srchPanel = new JPanel();
		srchPanel.setBorder(srchBorder);
		srchPanel.setLayout(new FlowLayout());
		srchPanel.add(srchField);
		srchPanel.add(srchBtn);
		
		Border delBorder = BorderFactory.createTitledBorder(border, "Delete");
		JPanel delPanel = new JPanel();
		delPanel.setBorder(delBorder);
		delPanel.setLayout(new FlowLayout());
		delPanel.add(delField);
		delPanel.add(delBtn);
		
		JScrollPane scrollTextArea = new JScrollPane(textArea);
		Border textBorder = BorderFactory.createTitledBorder(border, "Information Board");
		scrollTextArea.setBorder(textBorder);
		
		add(srchPanel, BorderLayout.NORTH);
		add(delPanel, BorderLayout.SOUTH);
		add(scrollTextArea, BorderLayout.CENTER);
		

		srchBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String name = srchField.getText();
				PhoneBookManager manager = PhoneBookManager.createManagerInst();
				String srchResult = manager.searchData(name);
				
				if(srchResult == null){
					textArea.append("해당하는 데이터가 존재하지 않습니다.\n");
				} else{
					textArea.append("찾으시는 정보를 알려드립니다. \n");
					textArea.append(srchResult);
					textArea.append("\n");
				}
			}
		});

		delBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String name = delField.getText();
				PhoneBookManager manager = PhoneBookManager.createManagerInst();
				boolean isDeleted = manager.deleteData(name);
				if(isDeleted)
					textArea.append("데이터 삭제를 완료하였습니다. \n");
				else
					textArea.append("해당하는 데이터가 존재하지 않습니다. \n");
			}
		});
		
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}
}