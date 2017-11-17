import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;


public class ImageDB extends JFrame implements ActionListener{
	JFileChooser fc;
	private JMenuItem save;
	private JMenuItem view;
	private JMenuItem exit;
	private JMenuBar menuBar;
	private JMenu menu;
	private JLabel imageLabel;
	private JLabel textLabel;
	private JButton prevButton;
	private JButton nextButton;
	private ImageIcon img=null;
	private int numberOfRecord;
	private Statement stmt = null;
	private Connection conn = null;
	private ResultSet viewRS = null;
	
	public ImageDB(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc", "root", "1234");
			stmt = conn.createStatement();
			ResultSet srs = stmt.executeQuery("select count(*) from images");
			srs.next();
			numberOfRecord = srs.getInt(1);
		} catch(ClassNotFoundException e){
			handleError(e.getMessage());
		} catch(SQLException e){
			handleError(e.getMessage());
		}
		
		setTitle("이미지 데이터 베이스");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		fc = new JFileChooser();
		
		menuBar = new JMenuBar();
		menu = new JMenu("메뉴");
		menuBar.add(menu);
		
		save = new JMenuItem("사진 저장");
		save.addActionListener(this);
		menu.add(save);
		
		view = new JMenuItem("모든 사진 보기");
		view.addActionListener(this);
		menu.add(view);
		
		menu = new JMenu("종료");
		exit = new JMenuItem("나가기");
		exit.addActionListener(this);
		menu.add(exit);
		menuBar.add(menu);
		setJMenuBar(menuBar);
		
		imageLabel = new JLabel();
		imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		textLabel = new JLabel();
		textLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		nextButton = new JButton("다음 사진 >>");
		nextButton.addActionListener(this);
		prevButton = new JButton("<< 이전 사진");
		prevButton.addActionListener(this);
		
		JScrollPane spane = new JScrollPane(imageLabel);
		add(textLabel, BorderLayout.NORTH);
		add(spane, BorderLayout.CENTER);
		JPanel panel = new JPanel();
		panel.setSize(50, 50);
		panel.add(prevButton);
		panel.add(nextButton);
		add(panel, BorderLayout.SOUTH);
		setSize(700, 700);
		setVisible(true);
		showPhotos();
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == exit){
			System.exit(0);
		} else if(e.getSource() == save){
			int returnVal = fc.showOpenDialog(this);
			if(returnVal == JFileChooser.APPROVE_OPTION){
				insertImage(fc.getSelectedFile());
			}
		} else if(e.getSource() == view){
			showPhotos();
		} else if(e.getSource() == prevButton){
			try{
				if(viewRS == null || !viewRS.previous()){
					imageLabel.setIcon(null);
					imageLabel.setText("사진 없음");
					JOptionPane.showMessageDialog(null, "이전 사진이 없습니다!!!", "처음 사진", JOptionPane.INFORMATION_MESSAGE);
					textLabel.setText(null);
				}else{
					Blob b = viewRS.getBlob("FILE");
					img = new ImageIcon(b.getBytes(1, (int)b.length()));
					imageLabel.setIcon(img);
					imageLabel.setText(null);
					textLabel.setText(viewRS.getString("FILENAME"));
				}
			}catch(SQLException el){
				handleError(el.getMessage());
			}
		}else if(e.getSource() == nextButton){
			try{
				if(viewRS == null || !viewRS.next()){
					imageLabel.setIcon(null);
					imageLabel.setText("사진 없음");
					JOptionPane.showMessageDialog(null, "다음 사진이 없습니다!!!", "마지막 사진", JOptionPane.INFORMATION_MESSAGE);
					textLabel.setText(null);
				}else{
					Blob b = viewRS.getBlob("FILE");
					img = new ImageIcon(b.getBytes(1, (int)b.length()));
					imageLabel.setIcon(img);
					imageLabel.setText(null);
					textLabel.setText(viewRS.getString("FILENAME"));
				}
			}catch(SQLException el){
				handleError(el.getMessage());
			}
		}
	}
	
	private void showPhotos(){
		try{
			viewRS = stmt.executeQuery("select * from images");
			if(viewRS.next()){
				Blob b = viewRS.getBlob("FILE");
				img = new ImageIcon(b.getBytes(1, (int)b.length()));
				imageLabel.setIcon(img);
				imageLabel.setText(null);
				textLabel.setText(viewRS.getString("FILENAME"));
			}else{
				imageLabel.setIcon(null);
				imageLabel.setText("사진 없음");
				textLabel.setText(null);
			}
		}catch(SQLException e){
			handleError(e.getMessage());
		}
	}
	
	private void handleError(String string){
		System.out.println(string);
		System.exit(1);
	}
	
	private void insertImage(File file){
		try{
			FileInputStream fln = new FileInputStream(file);
			PreparedStatement pstmt = conn.prepareStatement("insert into images(ID, FILENAME, FILE) value(?, ?, ?)");
			pstmt.setInt(1, numberOfRecord++);
			pstmt.setString(2, file.getName());
			pstmt.setBinaryStream(3, fln, (int)file.length());
			pstmt.executeUpdate();
			pstmt.close();
		} catch(Exception e){
			handleError(e.getMessage());
		}
	}
	
	private void deleteImage(){
		try{
			PreparedStatement pstmt = conn.prepareStatement("delete from images where");
			pstmt.setInt(1, numberOfRecord++);
			pstmt.setString(2, file.getName());
			pstmt.setBinaryStream(3, fln, (int)file.length());
			pstmt.executeUpdate();
			pstmt.close();
		} catch(Exception e){
			handleError(e.getMessage());
		}
	}
	
	public static void main(String [] args){
		ImageDB frame = new ImageDB();
	}
}
