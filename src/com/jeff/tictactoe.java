package com.jeff;

import android.util.Log;
import android.app.Activity;
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
import com.jeff.GridButton;


public class tictactoe extends Activity implements View.OnClickListener {
	TableLayout tl;	
	TableRow[] tr;
	GridButton[][] grid;
	int gridSize = 3;
	int currentPlayer = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		tl = new TableLayout(this);//table layout
		
		FrameLayout.LayoutParams tlp = new FrameLayout.LayoutParams( ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.FILL_PARENT);
		
		tl.setLayoutParams(tlp);
		tl.setStretchAllColumns(true);
		
		setContentView(tl);
		
		//grid
		grid = new GridButton[gridSize][gridSize];
		tr = new TableRow[gridSize];
	
		//!! children must have layout params of their parent
		//layout params for rows
		TableLayout.LayoutParams rl = new TableLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT,1.0f);
		//layout for cells in the row
		TableRow.LayoutParams cl = new TableRow.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT,1.0f);

		//loop to create cells
		for (int i=0;i<=gridSize-1;i++) {
			tr[i] = new TableRow(this);	
			for (int j=0;j<=gridSize-1;j++) {
				grid[i][j] = new GridButton(this,i,j);
				grid[i][j].setTextSize(120);
				grid[i][j].setOnClickListener(this);
				tr[i].addView(grid[i][j],cl);
			}
			tl.addView(tr[i],rl);
		}
	}

	public void onClick(View v) {
		GridButton gb = (GridButton) v;
		//Log.i("TICTACTOE", "CLICKINFO: " + gb.row +  "," + gb.col);

		//already set
		if (gb.getText() != "") 
			return;

		if(currentPlayer == 1){ 
			grid[gb.row][gb.col].setText("X");	
			grid[gb.row][gb.col].setTextColor(Color.RED);	
			currentPlayer = 2;
		}else if(currentPlayer == 2) {
			grid[gb.row][gb.col].setTextColor(Color.BLUE);	
			grid[gb.row][gb.col].setText("O");	
			currentPlayer = 1;
		}


	}
}
