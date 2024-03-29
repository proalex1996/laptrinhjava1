import java.awt.*;
import java.awt.event.*;
public class MenuChinh
{
	public static void main(String[] args)
	{
		createMenu();
	}
	private static void createMenu()
	{
		final Frame fr = new Frame("Chuong trinh quan ly quan Cafe");
		fr.setLayout(new BorderLayout());
		MenuBar menu = new MenuBar();
		Menu menuFile = new Menu("File");
		Menu menuUpdate = new Menu("Cap Nhat");
		
		MenuItem trangThai = new MenuItem("Trang thai Ban");
		trangThai.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				new TrangThai("Trang Thai Ban");
			}
		});
		MenuItem goiMon = new MenuItem("Goi Mon");
		goiMon.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				new GoiMon("Goi Mon");
			}
		});
		MenuItem tinhTien = new MenuItem("Tinh Tien");
		tinhTien.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				new TinhTien("Tinh Tien");
			}
		});
		MenuItem thoat = new MenuItem("Thoat");
		thoat.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				System.exit(0);
			}
		});
		menuFile.add(trangThai);
		menuFile.add(goiMon);
		menuFile.add(tinhTien);
		menuFile.addSeparator();
		menuFile.add(thoat);
		//tao Menu Item cho Update
		MenuItem themBan = new MenuItem("Danh sach Ban");
		themBan.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				new ThemBan("Danh sach Ban");
			}
		});
		
		menuUpdate.add(themBan);
		//tao Menu Item cho Process
		//tao Menu Item cho Help
		

		menu.add(menuFile);
		menu.add(menuUpdate);
		MenuItem thayDoi  = new MenuItem("Thay Doi Mon");
		thayDoi.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				new ThemMon("Thay Doi Mon");
			}
		});
		
		
		fr.setMenuBar(menu);
		Label lb = new Label("CHUONG TRINH QUAN LY QUAN CAFE",Label.CENTER);
		lb.setFont(new Font("Times Roman",Font.BOLD,30));
		fr.add(lb,BorderLayout.CENTER);
		fr.setBounds(0,0, 800, 570);
		fr.setVisible(true);
		fr.setFont(new Font("Arial",Font.PLAIN,13));
		fr.addWindowListener(new WindowAdapter()
				{
					public void windowClosing(WindowEvent e)
					{
						System.exit(0);
					}	
				});
	}
}
