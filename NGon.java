/**
 * -Implements the NGon data structure, which builds an N sided regular polygon
 	-This is done by finding the Apothem based on the given area and (x,y) coordinates of the NGon's center
 	-from here, the location of its vertices can be calculated and the object is filled
 	-Additionaly, the NGon also contains parameters letter (randomly generated and according to freq. dist)
 		and score (randomly generated and based on number of sides)
 	-Color is dynamically assigned based on a function based on N 
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


//nGon is both a shape and a data structure holding its score and its letter
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.io.*; 
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.Math; 


public class NGon{
public int n; //number of sides of the nGon
public double size; //if n is large, area is boun
public double x,y; //location of the nGon's center
public char c; //letter associated with the nGon instance

public double s;
public double rApothem;

	public NGon (double x, double y, int n, double size, char c){
		this.x = x;
		this.y = y;
		this.size = size; 
		this.n = n; 
		this.c = c;
	}

	public void reGenerate (int n, double size, char c){
		//regenerate always on the same initial point
		this.size = size; 
		this.n = n; 
		this.c = c;
	}


	public void draw(Graphics g){
		//math
		Graphics2D g2d = (Graphics2D) g;
		s = Math.sqrt(2*size*Math.tan(Math.toRadians(180.0/n))/n);  
		rApothem = s/(2*Math.tan(Math.toRadians(180.0/n))); 


		Path2D.Double edge = new Path2D.Double();
		
		double mx = x - s/2.0;
		double my = y - rApothem;
		edge.moveTo(x, y - rApothem);
		for(int i=0; i<n; i++){

			mx = mx + s*Math.cos(Math.toRadians(i*360.0/n));
			my = my + s*Math.sin(Math.toRadians(i*360.0/n)); 
			edge.lineTo(mx,my);
		}
		
		if(n<=16){
		g2d.setColor(new Color((int)Math.floor((210*(n/16.0))), 45, 210 - (int)Math.floor((210*(n/16.0))))); //based on RGB indicator of certain n 
		}
		else{
			g2d.setColor(Color.BLUE); 
		}

		g2d.fill(edge);

		String temp = c + ""; 
		g.setColor(Color.BLACK);
		g.drawString(temp,(int)x, (int)y);

	}

	public int getScore(){
		return n;
	}

	public char getChar(){
		return c; 
	}

	
}