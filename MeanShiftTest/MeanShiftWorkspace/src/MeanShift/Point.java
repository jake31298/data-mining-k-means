package MeanShift;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Point extends JPanel {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public void paintComponent(Graphics g)
	{
		//super.paintComponent(g);
		
		g.fillOval(350, 400, 10, 10);
		g.drawString("I LOVE YOU", 200, 200);
		
		
	}

}