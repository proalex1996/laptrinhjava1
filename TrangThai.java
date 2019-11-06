import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.JOptionPane;
public class TrangThai extends Frame implements ActionListener
{
	int n=0;//dung de duyet cac ban
	Panel panelSouth = new Panel();
	Label lbChinh = new Label("Trang thai Ban",Label.CENTER);
	TextArea txaChinh = new TextArea();
	Button buttXem = new Button("   Xem   ");
	Button buttThoat = new Button("  Thoat  ");
	Choice chMaBan = new Choice();
	Choice chTenBan = new Choice();
	private Component frame;
	static Connection con;
	static Statement stmt;
	static ResultSet rs;
	public TrangThai(String title)
	{
		super(title);
		lbChinh.setFont(new Font("Tahoma",Font.BOLD,20));
		add(lbChinh,BorderLayout.NORTH);
		txaChinh.setEditable(false);
		txaChinh.setText("\tTen Ban\t\tTrang thai\n\n");
		add(txaChinh,BorderLayout.CENTER);
		panelSouth.add(buttXem);
		panelSouth.add(buttThoat);
		add(panelSouth,BorderLayout.SOUTH);
		buttXem.addActionListener(this);
		buttThoat.addActionListener(this);
		setFont(new Font("Arial",Font.PLAIN,14));
		setLocation(200,50);
		setSize(400, 400);
		setResizable(false);
		setVisible(true);
		try
		{
			connect();
			rs = stmt.executeQuery("SELECT * FROM ban");
			rs.next();
			while(!rs.isAfterLast())
			{
				chMaBan.addItem(rs.getString(1));
				chTenBan.addItem(rs.getString(2));
				n++;
				rs.next();
			}
		}
		catch(Exception e){}
	}
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource()==buttXem)
			Xem();
		if(e.getSource() == buttThoat)
		{
			setVisible(false);
		}
	}
	public void Xem()
	{
		try
		{
			connect();
			rs = stmt.executeQuery("SELECT IDban FROM goimon");
			for(int i = 0; i<n;i++)
			{
				String s = chMaBan.getItem(i);
				rs.first();
				boolean flag = false;
				while(!rs.isAfterLast())
				{
					if(rs.getString(1).equals(s))
					{
						flag = true;
						break;
					}
					rs.next();
				}
				if(flag == true)
					txaChinh.append("\t"+chTenBan.getItem(i)+"\t\t\tDa su dung");
				else
					txaChinh.append("\t"+chTenBan.getItem(i)+"\t\t\tChua su dung");
				txaChinh.append("\n");
			}
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(frame, "Tất Cả Các Bàn Điều Rỗng Vui Lòng Tạo Bàn Mới");
		}
	}
	public static Connection connect()
	{
	String dbUrl = "jdbc:mysql://localhost:3306/cafe";
	String dbClass = "com.mysql.cj.jdbc.Driver";

	try {
		Class.forName(dbClass);
		 con = DriverManager.getConnection(dbUrl, "root", "");
		stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	} catch (Exception e) {
		e.printStackTrace();
	} return con;
	}
}
