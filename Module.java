/**
 * -Extends JPanel and handles user interaction.
 	-Main Features include Button listener which implements the submit, clear, refresh and clear all. 
 	-Submit checks if the current word is in the hashmap, if it is, score is added, word is removed from hashmap WordMap
 		and added to the HashMap WordsFound
 	-Mouselistener handles the selection of letters based on the (x,y) coordinate of the click and if it is inside range
 		defined by the NGons apothem. 
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

public class Module extends JPanel{
	private MainFrame mf;
	private JButton [] b;


	public Module (MainFrame mf){
		String [] s = {"Submit", "Clear", "Refresh", "Clear All", "End Game"};
		b = new JButton[s.length];

//problem with JButton wait lang
		for(int i=0; i<s.length; i++){
			String temp = s[i];
			b[i] = new JButton(temp); 
		}

		this.mf = mf; 
	}

	public void setUpPanel(){
		setLayout(new GridLayout(1,3));
		for(int i=0; i<b.length; i++){
			add(b[i]);
		}
		 

		ButtonListener buttonListener = new ButtonListener();

		for(int i=0; i<b.length; i++){
			b[i].addActionListener(buttonListener);
		}


		//add mouse listener to frame
		
		MyMouse m = new MyMouse ();
		mf.addMouseMotionListener(m);
		mf.addMouseListener(m);
		

	}

	private class ButtonListener implements ActionListener{
		//override

		public void actionPerformed (ActionEvent ae){
			Object o = ae.getSource();
			
			if(o==b[0]){
				if(mf.wordMap.containsKey(mf.inFocus)){
					mf.wordMap.remove(mf.inFocus);
					mf.wordsFound.put(mf.inFocus, 1); 
					mf.correctSubmissions ++;
					mf.score += mf.scoreInFocus;

					mf.myMessage = ("Score! " + "Word: " + mf.inFocus + ", Score: " + mf.scoreInFocus);

					mf.scoresAdded.clear(); 
					mf.scoreInFocus = 0; 
					mf.inFocus = ""; 
				}
				else if(mf.wordsFound.containsKey(mf.inFocus)){
					mf.myMessage = ("Word " + mf.inFocus + " has already been submitted"); 
					mf.scoresAdded.clear(); 
					mf.scoreInFocus = 0; 
					mf.inFocus = ""; 
					
				}
				else{
					if(mf.mistakes < mf.mistakeLimit){
						mf.mistakes++;
						mf.myMessage = "Sorry, your Word isn't part of this game!";
					}
					else{
						//if mistakes reaches a certain number call game over frame
						mf.myMessage = "Mistake Limit Reached! [Game Over]";
						mf.gameOver = true;

					}
				}
			}

			else if(o==b[1]){
				if(mf.inFocus.length()>0){
					if(mf.clearCount < mf.clearLimit){
					mf.clearCount ++;
					mf.inFocus = mf.inFocus.substring(0,mf.inFocus.length()-1);
					mf.scoreInFocus -= mf.scoresAdded.pop(); //removes last element and subtracts it from score

					}

					else{
					mf.myMessage = "Clear Limit Reached"; 
					}
				}
				
			}

			else if(o==b[2]){
				//your word in focus stays at it is
				if(mf.refreshCount<mf.refreshLimit){
					mf.refreshCount++;
					mf.myMessage = ("Number of refreshes used: " + mf.refreshCount);
					mf.reGenerateAll();
				}
				else{
					mf.myMessage = "Refresh limit reached.";
				}
			}

			else if(o == b[3]){
				if(mf.inFocus.length()>0){
					if(mf.clearCount + mf.inFocus.length() <= mf.clearLimit){
					mf.clearCount += mf.inFocus.length(); 
					mf.scoreInFocus = 0; 
					mf.inFocus = ""; 
					}
					else{
						mf.myMessage = "Clear Limit Reached"; 
					}
				}
			}

			else if(o == b[4]){
				mf.gameOver = true; 
				//just ends the game prematurely 
			}

			mf.repaint(); 
		}	
	}




	//source: https://www.geeksforgeeks.org/mouselistener-mousemotionlistener-java/
	private class MyMouse implements MouseMotionListener, MouseListener {

		MyMouse(){

		}


		public void mouseDragged(MouseEvent e) 
    		{ 
         

    		} 
  

    public void mouseMoved(MouseEvent e) 
    		{ 

    		}	 
  
    // MouseListener events 
  	

    // this function is invoked when the mouse is pressed 
    public void mousePressed(MouseEvent e) 
    		{ 

    		} 
  
    // this function is invoked when the mouse is released 
    public void mouseReleased(MouseEvent e) 
    		{ 

    		} 
  
    // this function is invoked when the mouse exits the component 
    public void mouseExited(MouseEvent e) 
    		{ 

    		} 
  
    // this function is invoked when the mouse enters the component 
    public void mouseEntered(MouseEvent e) 
    		{ 

    		} 
  
    // this function is invoked when the mouse is pressed or released 
    public void mouseClicked(MouseEvent e) 
    		{ 

    		//calls function that determines which NGon in the arrayList was clicked based on e.getX() and e.getY()
    		int k = mf.returnIndex(e.getX(), e.getY());

    		if(k>=0){
    			mf.charClicks++; 

		    	mf.inFocus += mf.getLetter(k).getChar(); //this returns the getX()th NGon and gets the character it contains to add to inFocus.
		    	mf.scoresAdded.push(mf.getLetter(k).getScore());
		    	mf.scoreInFocus += mf.getLetter(k).getScore(); 
		    	mf.reGenerateSpecific(k); //calls regenerate specific function on that specific NGon. 
		        mf.repaint(); 
		    	}
    		} 


		} 


//end of module
	}





