/**
 * -Implements JComponent and handles all the graphics and rendering of the game
 	-Additionally, it also containts all pertinent variables and data needed during the runtime
 	-Main program which generates the random variables and keeps count of the score
 	-Handles real time updating of the scores and current word
 	-It also contains functions like returnIndex which returns which of the characters (NGons)
 		was clicked based on whether the (x,y) coordinate of the click was in range (given by Apothem) 
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

import java.util.Random;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.io.*; 
import java.util.*;
import java.lang.Object;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;


public class MainFrame extends JComponent{
	public int width;
	public int height;
	public Color bgColor;
	//end of standard variables

	public int refreshCount; //count of board refreshes
	public int mistakes; 	//count of wrong submissions
	public int clearCount; 
	public int score; 
	public String inFocus; //word currently formed by the user
	public String myMessage; //displays the game's remarks for the user's last action
	public int scoreInFocus; 
	public Stack <Integer> scoresAdded  = new Stack <Integer> (); 
	public boolean gameOver; 

	public HashMap <String, Integer> wordMap = new HashMap<>(); //load HashMap in WordMap Canvas
	public ArrayList <NGon> Letters = new ArrayList <NGon> (); 

	//generating associated character
	public int [] cCount = new int[27]; //26 = ' '
	public double [] cDist = new double[27];
	public int cTotal; 
	public int [] cGenerated = new int[27]; //set limit to two instances of a letter per board

	//static variables
	//we can change these gameplay variables easily 
	public static double horizontal = 6;
	public static double vertical = 3;
	public static int refreshLimit = 10;
	public static int mistakeLimit = 5; 
	public static int clearLimit = 60; 

	//Variables for usage statistics
	public HashMap <String, Integer> wordsFound = new HashMap <>();
	public int correctSubmissions;
	public int charClicks;
	



	public MainFrame (int w, int h){
		width = w;
		height = h;
		refreshCount = 0;
		mistakes = 0;
		charClicks = 0; 
		setPreferredSize(new Dimension(width, height));
		for(int i=0; i<27; i++){cDist[i]=0; cGenerated[i]=0; cCount[i]=0;}
		cTotal = 0;
		score = 0;
		clearCount = 0;
		scoreInFocus = 0; 
		inFocus = "";
		myMessage = "";
		gameOver = false;

		//usage statistics
		correctSubmissions = 0;
		charClicks = 0;

		
		String [] words = {"danaerys", "jon", "snow", "ghost", "arya", "stark", "tyrion", "nymeria", "unsullied", "lannister", "rob", "needle", "dragon", "ned", "throne", "game", "varys", "westeros", "essos", "daario", "sam", "wolf", "sansa", "braavos", "free", "dothraki"}; //initialize the string with the parsed values. current version of the program requires +- 50 values

		for(int i=0; i<words.length; i++){
			wordMap.put(words[i], 1); //puts the key and the value into the map
			for(int j=0; j<words[i].length(); j++){
				if(words[i].charAt(j)==' '){
					cCount[26]++;
				}
				else{
				cCount[words[i].charAt(j)-'a']++;
				cTotal++;
				} 
			}
		}

		for(int i=0; i<27; i++){
			cDist[i] = (0.15)*(1/27.0)+(0.85)*cCount[i]/cTotal; 
			//weighted distribution between its occuerence in the alphabet and its frequency in our Map
		}
		

		for(int i=1; i<26; i++){
			cDist[i]+=cDist[i-1]; 
			//the distribution is now cumulative
			
		}

		cDist[26] = 1; //force in order to eliminate rounding errors



		
		//create arraylist of letters
		//parameters 5 X 3
		double tempx, tempy; //coordinates of the (i,j)th NGon

		tempx = width / (5.0*1.5);
		tempy = 0 + height/ (3.0 * 1.5);
		for(int i=0; i<vertical; i++)for(int j=0; j<horizontal; j++){
			Random rand = new Random();
			int tempn = rand.nextInt(13)+4; //generates random number between 4 - 16 

			//generate the character associated from the frequency distribution of the words given
			Random rand1 = new Random();
			int tempn1 = rand.nextInt(27);
			while(cGenerated[tempn1]>2){
				tempn1 = rand.nextInt(27);
			}
			cGenerated[tempn1]++;

			if(tempn1 == 26){
				tempn1 = ' ' - 'a';
				//convert to space ASCII
			}




			Letters.add(new NGon(tempx + j*width/(5.0*1.5), tempy + i*height/(3.0*1.5), tempn, 6000, (char) ('a' + tempn1) ));
		}




	}

	public void reGenerateAll(){
		for(int i=0; i<27; i++){
			cGenerated[i] = 0; 
		}

		for(int i=0; i<Letters.size(); i++){
			Random rand = new Random();
			int tempn = rand.nextInt(13)+4; //generates random number between 4 - 16 

			//generate the character associated from the frequency distribution of the words given
			Random rand1 = new Random();
			int tempn1 = rand.nextInt(27);
			while(cGenerated[tempn1]>2){
				tempn1 = rand.nextInt(27);
			}
			cGenerated[tempn1]++;

			if(tempn1 == 26){
				tempn1 = ' ' - 'a';
				//convert to space ASCII
			}

			Letters.get(i).reGenerate(tempn, 6000, (char)('a' + tempn1));
		}
	}

	public void reGenerateSpecific(int a){
		//ath NGon gets regenerated
		Random rgrand = new Random();
			int rgn = rgrand.nextInt(13)+4; //generates random number between 4 - 16 

			//generate the character associated from the frequency distribution of the words given
			Random rgrand1 = new Random ();
			double pc1 = rgrand1.nextInt(11)/10.00000; 
			int rgn1 = 0;
			for(int j=0; j<27; j++){
				if(pc1<cDist[j]){
					rgn1 = j; 
					break;
				}
			}

		if(rgn1 == 26){
				rgn1 = ' ' - 'a';
				//convert to space ASCII
			}

		Letters.get(a).reGenerate(rgn, 6000, (char)('a' + rgn1));


	}





	protected void paintComponent (Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		Rectangle2D.Double background = new Rectangle2D.Double(0,0,width,height);
		g2d.setColor(bgColor);
		g2d.fill(background); 

		//fill in the blocks
		for(int i=0; i<Letters.size(); i++){
			Letters.get(i).draw(g); 
		}

		
		//superimpose word in focus 
		g.setColor(Color.YELLOW);
		g.drawString("Word: ", width/80, height/10);
		g.drawString(inFocus, width/20, height/10);
		g.drawString("Word Score: ",width/80, height/8);
		g.drawString(Integer.toString(scoreInFocus), width/10, height/8);
		g.drawString("Total Score: ", width/80, 2*height/8 - height/10);
		g.drawString(Integer.toString(score), width/10, 2*height/8 - height/10);

		g.drawString("Message Board: ", width/80, (int)9*height/10);
		g.drawString(myMessage, width/10, (int)9*height/10);
		g.drawString("Mistakes Left:  " + (mistakeLimit - mistakes), width/80, (int)9*height/10 - (height/8 - height/10));
		g.drawString("Refreshes Left: " + (refreshLimit - refreshCount), width/80, (int)9*height/10 - 2*(height/8 - height/10));
		g.drawString("Clears Left:    " + (clearLimit - clearCount), width/80, (int)9*height/10 - 3*(height/8 - height/10));

		//gameOver function
		if(gameOver){
			Rectangle2D.Double gameOverFrame = new Rectangle2D.Double(0,0,width,height);
			g2d.setColor(Color.BLACK);
			g2d.fill(background);

			//creates a regular polygon with score/2 sides to frame your game statistics
			NGon dashboard = new NGon(width/2, height/2, (int)score/2, (22/7.00)*height*height/4.00, ' ');
			dashboard.draw(g);

			g.setColor(Color.YELLOW);
			g.drawString("GAME OVER", width/2 - width/10, height/2);
			g.drawString("Total Score: " + Integer.toString(score), width/2 - width/10, height/2 + (height/8 - height/10));
			g.drawString("Total Words Found:  " + correctSubmissions, width/2 - width/10, height/2 + 2*(height/8 - height/10)); 
			g.drawString("Percentage Correct: " + 100.00 * correctSubmissions / (correctSubmissions + mistakes) + "%", width/2 - width/10, height/2 + 3*(height/8 - height/10)); 
			g.drawString("Characters Clicked: " + charClicks, width/2 - width/10, height/2 + 4*(height/8 - height/10)); 
		}
		
	}

	public void setBackgroundColor(Color newColor){
		bgColor = newColor; 
	}


	public NGon getLetter(int a){
		return Letters.get(a);
	}

	public int returnIndex(double x, double y){
		//this function returns which NGon in the array list was clicked. A value of -1 means user clicked into space
		//pseudo collision detection algorithm
		int index = -1;
		for(int i=0; i<Letters.size(); i++){
			double currentApothem = Letters.get(i).rApothem;
			if(Math.abs(Letters.get(i).x-x)<=currentApothem && Math.abs(Letters.get(i).y-y)<=currentApothem){
				index = i;
				break; //for sure no other becuase there is spacing and Apothem is less than max border (NGon's don't intersect)
			}
		}

		return index; 
	}



	






}