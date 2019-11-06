import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import com.mysql.cj.protocol.Message;
public class GoiMon extends Frame implements ActionListener
{
	Panel panelCenter = new Panel();
	Panel panelSub = new Panel(new GridLayout(4,2,10,10));
	Panel panelSouth = new Panel();
	Panel panelSpace = new Panel();
	Label lbSpace = new Label(" ");
	Label lbGoiMon = new Label("GOI MON",Label.CENTER);
	Label lbMaGoi = new Label("Luot goi:");
	Label lbBan = new Label("Ban:");
	Label lbDoUong = new Label("Do uong:");
	Label lbSoLuong = new Label("So luong:");
	TextField txtMaGoi = new TextField("");
	Choice chBan = new Choice();
	Choice chTenBan = new Choice();
	Choice chDoUong = new Choice();
	Choice chTenDoUong = new Choice();
	TextField txtSoLuong = new TextField();
	Button buttLuu = new Button("   Luu   ");
	Button buttThoat = new Button(" Thoat ");
	private Component frame;
	static Connection con;
	static Statement stmt;
	static ResultSet rs;
	public GoiMon(String title)
	{
		super(title);
		lbGoiMon.setFont(new Font("Tahoma",Font.BOLD,20));
		add(lbGoiMon,BorderLayout.NORTH);
		panelSub.add(lbMaGoi);
		panelSub.add(txtMaGoi);
		panelSub.add(lbBan);
		try
		{
			connect();
			rs = stmt.executeQuery("SELECT * FROM ban");
			rs.next();
			while(!rs.isAfterLast())
			{
				chBan.addItem(rs.getString(1));
				chTenBan.addItem(rs.getString(2));
				rs.next();
			}
		}
		catch(Exception e){}
		try
		{
			connect();
			rs = stmt.executeQuery("SELECT * FROM thucdon");
			rs.next();
			while(!rs.isAfterLast())
			{
				chDoUong.addItem(rs.getString(1));
				chTenDoUong.addItem(rs.getString(2));
				rs.next();
			}
		}
		catch(Exception e){}

		panelSub.add(chTenBan);
		panelSub.add(lbDoUong);
		panelSub.add(chTenDoUong);
		panelSub.add(lbSoLuong);
		panelSub.add(txtSoLuong);
		panelCenter.add(panelSub);
		panelSpace.add(lbSpace);
		panelCenter.add(panelSpace);
		add(panelCenter,BorderLayout.CENTER);
		panelSouth.add(buttLuu);
		add(panelSouth,BorderLayout.SOUTH);
		panelSouth.add(buttThoat);
		add(panelSouth,BorderLayout.SOUTH);
		buttLuu.addActionListener(this);
		buttThoat.addActionListener(this);
		setFont(new Font("Arial",Font.PLAIN,14));
		setLocation(180,50);
		setSize(500, 250);
		setResizable(false);
		setVisible(true);
	}
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource()==buttLuu)
			Luu();
		if(e.getSource() == buttThoat)
		{
			setVisible(false);
		}
	}
	public void Luu()
	{
		if(txtSoLuong.getText().equals("")){
			JOptionPane.showMessageDialog(frame, "Vui Long Nhap So Luong");
		}
		else {
		
		try
		{
			connect();
			rs = stmt.executeQuery("SELECT * FROM goimon");
			rs.moveToInsertRow();
			rs.updateLong(1, Long.parseLong(txtMaGoi.getText()));
			int n = chTenBan.getSelectedIndex();
			rs.updateString(2, chBan.getItem(n));
			int m = chTenDoUong.getSelectedIndex();
			rs.updateString(3,chDoUong.getItem(m));
			rs.updateLong(4, Long.parseLong(txtSoLuong.getText()));
			rs.insertRow();
			txtMaGoi.setText("");
			txtSoLuong.setText("");
			JOptionPane.showMessageDialog(frame, "Luu Thanh Cong");
		}
		catch(Exception e)
		{
			System.err.println("Error: "+e.toString());
		}
	}}
	public static Connection connect()
	{
		String dbUrl = "jdbc:mysql://localhost:3306/cafe";
		String dbClass = "com.mysql.cj.jdbc.Driver";

	try {
		Class.forName(dbClass);
		 con = DriverManager.getConnection(dbUrl, "root", "");
		 stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		 rs = stmt.executeQuery("SELECT * FROM goimon");
	} catch (Exception e) {
		e.printStackTrace();
	} return con;
	}
}
