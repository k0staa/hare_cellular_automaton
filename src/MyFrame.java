/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author kostek
 */
public class MyFrame extends JFrame {
	Grid myGrid;

	BufferedImage image;
	JPanel jp_buttony;
	JButton start;
	JButton stop;
	JButton restart;
	JTextArea textArea;
	JPanel jp_putData;
	JTextField tf_amountCell;
	JTextField tf_amountPredator;
	JTextArea tx_data;
	JLabel l_amountCell;
	JLabel l_amountPredator;
	JTextField tf_amountResources;
	JLabel l_amountResources;

	public static final int X_SIZE = 840; // Size of graphing box in x
											// direction.
	public static final int Y_SIZE = 510; // Size of graphing box in y
											// direction.
	public static final int PADDING_SPACE = 0; // Space between screen edges and
												// coordinate system.

	public MyFrame() {
		// BorderLayout bl = new BorderLayout();
		// bl.setVgap(5);
		// this.setLayout(bl);
		image = new BufferedImage(X_SIZE, Y_SIZE, BufferedImage.TYPE_INT_RGB);

		myGrid = new Grid(X_SIZE, Y_SIZE, PADDING_SPACE, image);
		// myGrid.setAlignmentX(Component.LEFT_ALIGNMENT);

		this.add(myGrid, BorderLayout.CENTER);

		myGrid.repaint();

		// Panel do ustawien programu////////
		jp_putData = new JPanel();
		jp_putData.setLayout(new BoxLayout(jp_putData, BoxLayout.Y_AXIS));

		tf_amountCell = new JTextField();
		tf_amountCell.setText("15");
		tf_amountCell.setAlignmentX(Component.LEFT_ALIGNMENT);
		tf_amountCell.setMaximumSize(new Dimension(30, 20));
		tf_amountPredator = new JTextField();
		tf_amountPredator.setText("2");
		tf_amountPredator.setAlignmentX(Component.LEFT_ALIGNMENT);
		tf_amountResources = new JTextField();
		tf_amountResources.setText("10");
		tf_amountResources.setAlignmentX(Component.LEFT_ALIGNMENT);
		// TextArea opisujaca warunki doswiadczenia///
		String dataString = "Populacja Zajaca Szaraka\n"
				+ "Warunki biologiczne:\n" + "-czas zycia max. 13 lat;\n"
				+ "-12 dni plodnych w msc.;\n"
				+ "-osobnik plodny po 90 dniach;\n" + "-45 dni ciazy;\n"
				+ "-miot sklada sie z 2-5 osobnikow\n"
				+ " jednak nalezy przyjac 0-5;\n"
				+ "-pierwsze osobniki maja 90 dni;\n"
				+ "-ponizej 2 zasobow poczatek\n" + " wymierania.";
		System.out.println(dataString);
		tx_data = new JTextArea(dataString);

		tx_data.setAlignmentX(Component.LEFT_ALIGNMENT);

		tf_amountCell.setMaximumSize(new Dimension(30, 20));
		tf_amountPredator.setMaximumSize(new Dimension(30, 20));
		tf_amountResources.setMaximumSize(new Dimension(30, 20));
		tx_data.setMaximumSize(new Dimension(210, 200));
		l_amountCell = new JLabel("Ilosc zajecy:");
		l_amountCell.setAlignmentX(Component.LEFT_ALIGNMENT);
		l_amountPredator = new JLabel("Ilosc predatorow:");
		l_amountPredator.setAlignmentX(Component.LEFT_ALIGNMENT);
		l_amountResources = new JLabel("Ilosc zasobow:");
		l_amountResources.setAlignmentX(Component.LEFT_ALIGNMENT);

		jp_putData.add(l_amountCell);
		jp_putData.add(tf_amountCell);
		jp_putData.add(l_amountPredator);
		jp_putData.add(tf_amountPredator);
		jp_putData.add(l_amountResources);
		jp_putData.add(tf_amountResources);
		jp_putData.add(tx_data);
		// JTEXTAREA - do informacji odnosnie
		// rzutu//////////////////////////////////////
		textArea = myGrid.getTextArea();
		// //////BUTTONS////////////////////////////////
		jp_buttony = new JPanel();
		jp_buttony.setLayout(new FlowLayout());
		stop = new JButton("Stop");
		start = new JButton("Start");
		restart = new JButton("Restart Timer");

		jp_buttony.add(start);
		jp_buttony.add(restart);
		jp_buttony.add(stop);
		restart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("restart");

			}
		});
		stop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				myGrid.stopAnim();
			}
		});
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				myGrid.startAnim(Integer.parseInt(tf_amountCell.getText()),
						Integer.parseInt(tf_amountPredator.getText()),
						Integer.parseInt(tf_amountResources.getText()));

			}
		});
		start.setBounds(10, 30, 100, 50);
		stop.setBounds(10, 80, 100, 50);
		restart.setBounds(10, 130, 100, 50);

		// //DODANIE PANELI
		add(jp_buttony, BorderLayout.PAGE_START);
		add(textArea, BorderLayout.SOUTH);
		add(jp_putData, BorderLayout.WEST);

		// Screen resolution:
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width_screen = screenSize.getWidth();
		double height_screen = screenSize.getHeight() - 25;
		this.setSize((int) width_screen, (int) height_screen);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

}
