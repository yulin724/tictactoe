package com.jeff;

import android.util.Log;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.AbsoluteLayout.*;
import android.widget.FrameLayout;
import android.graphics.Color;
import android.content.DialogInterface;
import com.jeff.GridButton;

/*
 * NOTES:
 * 	The output of the LOG statements goes to logcat which can be invoked using `adb logcat` in a shell 
 * 	while the device is running. 
 *
 *
 */
public class tictactoe extends Activity implements View.OnClickListener {
	TableLayout tl;	
	TableRow[] tr;
	GridButton[][] grid;
	int gridSize = 3;				//change this for some amusement (dont mind the font)
	int currentPlayer = 1;
	String winner;
	int moves = 0;					//used to check for a tie 
	AlertDialog ad;

	private static final String DEFAULT_TEXT = " "; //fixes a bug where buttons resize when the text changes
	
	/* TODO:
	 *		Alertbox when rotated leaks (see logcat output)
	 */
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (getLastNonConfigurationInstance() == null) {//if first time
			//set up main screen
			tl = new TableLayout(this);//table layout
			FrameLayout.LayoutParams tlp = new FrameLayout.LayoutParams( ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.FILL_PARENT);
			tl.setLayoutParams(tlp);
			tl.setStretchAllColumns(true);
			
			//table grids	
			tr = new TableRow[gridSize];
		
			//layout params for rows
			TableLayout.LayoutParams rl = new TableLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT,1.0f);
			//layout for cells in the row
			TableRow.LayoutParams cl = new TableRow.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT,1.0f);

			grid = new GridButton[gridSize][gridSize];
			//loop to create cells
			for (int i=0;i<gridSize;i++) {
				tr[i] = new TableRow(this);	
				for (int j=0;j<gridSize;j++) {
					grid[i][j] = new GridButton(this,i,j);
					grid[i][j].setOnClickListener(this);
					grid[i][j].setText(DEFAULT_TEXT);
					tr[i].addView(grid[i][j],cl);
				}
				tl.addView(tr[i],rl);
			}

		}else if (getLastNonConfigurationInstance() != null) {//on restore
			tl = (TableLayout) getLastNonConfigurationInstance();
		}

		setContentView(tl);

	}
	public void resetGrid() {
		moves = 0;
		for (int i=0;i<gridSize;i++) {
			for (int j=0;j<gridSize;j++) {
				grid[i][j].setText(DEFAULT_TEXT);
				grid[i][j].status = 0;
			}
		}

	}
	public void onClick(View v) {

		moves++;

		GridButton gb = (GridButton) v;
		//Log.i("TICTACTOE", "CLICKINFO: " + gb.row +  "," + gb.col);

		//make sure the button is not already set
		if (gb.getText() != DEFAULT_TEXT) 
			return;

		if(currentPlayer == 1){ 
			grid[gb.row][gb.col].status = 1;
			grid[gb.row][gb.col].setText("X");	
			grid[gb.row][gb.col].setTextColor(Color.RED);	
			currentPlayer = 2;
		}else if(currentPlayer == 2) {
			grid[gb.row][gb.col].status = -1;
			grid[gb.row][gb.col].setText("O");	
			grid[gb.row][gb.col].setTextColor(Color.BLUE);	
			currentPlayer = 1;
		}
		
		winner = checkVictory();
		if(!winner.equals("")) {
			showVictor(winner);
		}

		//Log.i("VICTORY_CHECK","WHO WINS: " + checkVictory());
	}
	public void showVictor(String victor) {
		ad = new AlertDialog.Builder(this).create();
		ad.setOwnerActivity(this);
		ad.setCancelable(false);
		ad.setTitle("GAME OVER");
		ad.setMessage(victor + " wins!");
		ad.setButton("Cool", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog,int which) {
				resetGrid();
			}
		});
		ad.show();
	}
	public String checkVictory() {
		int rowTotal = 0;
		int colTotal = 0;
		int backTotal = 0;
		int forwardTotal = 0;

		
		for (int i=0;i<gridSize;i++) { //row
			for (GridButton gb : grid[i]) {
				rowTotal += gb.status;		
			}
			for (int j=0;j<gridSize;j++) { //col
				colTotal += grid[j][i].status;
			}

			if (rowTotal == gridSize) {
				highlightVictory(i,"row");
				return "X";
			}else if(colTotal == gridSize) {
				highlightVictory(i,"col");
				return "X";
			}else if(rowTotal == -gridSize) {
				highlightVictory(i,"row");
				return "O";
			}else if(colTotal == -gridSize) {
				highlightVictory(i,"col");
				return "O";
			}
			
			//reset for next row
			rowTotal = colTotal = 0;
			
			//check back and forward diagonal
			backTotal += grid[i][i].status;	
			forwardTotal += grid[i][(gridSize-1)-i].status;
		}
		
		if (backTotal == gridSize) {
			highlightVictory(0,"bdiag");
			return "X";
		}else if(forwardTotal == gridSize) {
			highlightVictory(0,"fdiag");
			return "X";
		}else if(backTotal == -gridSize) {
			highlightVictory(0,"bdiag");
			return "O";
		}else if(forwardTotal == -gridSize) {
			highlightVictory(0,"fdiag");
			return "O";
		}

		//check for tie
		//Log.i("MOVES",""+moves);
		if (moves == gridSize * gridSize) {
			return "NO ONE"; //lazy way to shoe horn this function to work with showVictory	
		}
		return "";
	}
	public void highlightVictory(int element, String dir) {
		if (dir.equals("row")) {
			for (int i=0;i<gridSize;i++) { 
				grid[element][i].setTextColor(Color.YELLOW);
			}
		}else if(dir.equals("col")) {
			for (int i=0;i<gridSize;i++) { 
				grid[i][element].setTextColor(Color.YELLOW);
			}
		}else if(dir.equals("fdiag")) {
			for (int i=0;i<gridSize;i++) {
				grid[i][(gridSize-1)-i].setTextColor(Color.YELLOW);
			}
		}else if(dir.equals("bdiag")) {
			for (int i=0;i<gridSize;i++) {
				grid[i][i].setTextColor(Color.YELLOW);
			}
		}
	}
	//Save state on rotate,keyboard slide out, etc. This method only allows saving one variable. 
	//You can send an ArrayList full of objects however. The compiler complains a bit though. 
	@Override
	public Object onRetainNonConfigurationInstance() {
		if (tl != null) {//save table layout
			ViewGroup vg = (ViewGroup) tl.getParent();//probably not good
			vg.removeView(tl);
			return(tl);
		}
		return super.onRetainNonConfigurationInstance();
	}


}
