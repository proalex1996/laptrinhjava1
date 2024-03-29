import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.JOptionPane;
class TinhTien extends Frame implements ActionListener
{
	long sum;
	int n;
	GridBagLayout gb = new GridBagLayout();
	GridBagConstraints gbc = new GridBagConstraints();
	TextArea taChinh = new TextArea();
	Label lbTinhTien = new Label("TINH TIEN");
	Label lbNhap = new Label("Nhap Ma Ban:                 ");
	Label lbTong = new Label("Tong so tien:                ");
	Choice chNhap = new Choice();
	Button buttTinh = new Button("Tinh");
	Button buttTrangThai = new Button("Tra Ban");
	Button buttThoat = new Button("Thoat");
	Label lbKqua = new Label();
	private Component frame;
	static Connection con;
	static Statement stmt;
	static ResultSet rs;
	public TinhTien(String title)
	{
		super(title);
		setLayout(gb);
		taChinh.setEditable(false);
		try
		{
			connect();
			rs = stmt.executeQuery("SELECT IDban FROM ban");
			rs.next();
			while(!rs.isAfterLast())
			{
				chNhap.addItem(rs.getString(1));
				rs.next();
			}
		}
		catch(Exception e){}
		gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5,5,5,5);
        lbTinhTien.setFont(new Font("Tahoma",Font.BOLD,20));
        addComponent(lbTinhTien, 1, 2, 1, 1);
        addComponent(lbNhap, 2, 1, 1, 1);
        addComponent(chNhap, 2, 2, 1, 1);
        addComponent(lbTong, 3, 1, 1, 1);
        addComponent(lbKqua, 3, 2, 1, 1);
        addComponent(taChinh, 4, 1, 6, 4);
        addComponent(buttTinh, 10, 1, 1, 1);
        addComponent(buttTrangThai, 10, 2, 1, 1);
        addComponent(buttThoat, 10, 3, 1, 1);
        buttTinh.addActionListener(this);
        buttTrangThai.addActionListener(this);
        buttThoat.addActionListener(this);
        setFont(new Font("Arial",Font.PLAIN,14));
        setLocation(200,50);
        pack();
        setVisible(true);
		setResizable(false);
	}
	public void addComponent(Component c, int row, int col, int nrow, int ncol)
    {
         gbc.gridy = row;
         gbc.gridx = col;
         gbc.gridheight = nrow;
         gbc.gridwidth = ncol;
         gb.setConstraints(c,gbc);
         add(c);
    }
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource() == buttTinh)
			tong();
		if(ae.getSource() == buttThoat)
			setVisible(false);
		if(ae.getSource() == buttTrangThai)
			xoa();
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
	public void tong()
	{
		String s = chNhap.getItem(chNhap.getSelectedIndex());
		try
		{
			connect();
		   rs = stmt.executeQuery("SELECT goimon.IDban, ThucDon.DonGia, goimon.SoLuong, ThucDon.DonGia*goimon.SoLuong,thucdon.DoUong AS Tong   FROM thucdon INNER JOIN (ban INNER JOIN goimon ON ban.IDban =goimon.IDban) ON thucdon.IDdouong = goimon.IDdouong");
			rs.next();
			taChinh.setText("Ten Do Uong\t\tDon Gia\t\tSo Luong\n\n");
			while(!rs.isAfterLast())
			{
				if(rs.getString(1).equals(s))
				{
					sum += (rs.getLong(4));
					taChinh.append("  "+rs.getString(5)+"\t\t");
					taChinh.append("  "+rs.getString(2)+"\t\t\t");
					taChinh.append("  "+rs.getString(3));
					taChinh.append("\n");
				}
				rs.next();
			}
		}
		catch(SQLException e)
		{
			System.err.println("Error:" +e.getMessage());
		}
		lbKqua.setText(Long.toString(sum));
		sum=0;
		//xoa tat ca cac ban ghi do trong Goi_mon
	}
	public void xoa()
	{
		String s = chNhap.getItem(chNhap.getSelectedIndex());
		try
		{
			rs = stmt.executeQuery("SELECT IDban FROM ban");
			n=1;
			rs.absolute(n);
			while(!rs.isAfterLast())
			{
				if(rs.getString(1).equals(s))
				{
					rs.deleteRow();
					n--;
				}JOptionPane.showMessageDialog(frame, "Đã Thanh Toán");
				rs.absolute(++n);
			}
		}
		catch(SQLException se)
		{
			System.err.println("Error: "+se.getMessage());
		}
	}
	/*public static void main(String[]a)
	{
		new TinhTien("Tinh Tien");
	}*/
}
