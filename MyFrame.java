/**
 * Implements JFrame and holds the MainFrame and Module
 *
 * @author Andrew Yu
 * @version 21 April 2019
 */

/*
I have not discussed the Java language code 
in my program with anyone other than my instructor 
or the teaching assistants assigned to this course.

I have not used Java language code obtained 
from another student, or any other unauthorized 
source, either modified or unmodified.

If any Java language code or documentation 
used in my program was obtained from another source, 
such as a text book or webpage, those have been 
clearly noted with a proper citation in the comments 
of my code.
*/

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.io.*; 
import java.util.*;
import java.lang.Object;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MyFrame extends JFrame{
	private int width;
	private int height;
	private static final String title = "WordMap";

	public MyFrame(int w, int h){
		width = w;
		height = h;
	}

	public void setUpFrame(){
		Container contentPane = getContentPane();
		MainFrame mf = new MainFrame(width, height);
		Module md = new Module(mf); 
		md.setUpPanel();

		setTitle(title);
		contentPane.add(mf, BorderLayout.CENTER);
		contentPane.add(md, BorderLayout.SOUTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack(); //automatically size
		setVisible(true);
	}
}