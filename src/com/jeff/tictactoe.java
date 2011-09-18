package com.jeff;

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


public class tictactoe extends Activity implements View.OnClickListener {
	TableLayout tl;	
	TableRow[] tr;
	Button[][] grid;
	int gridSize = 3;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		tl = new TableLayout(this);//table layout
		
		FrameLayout.LayoutParams tlp = new FrameLayout.LayoutParams( ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.FILL_PARENT);
		
		tl.setLayoutParams(tlp);
		tl.setStretchAllColumns(true);
		
		setContentView(tl);

		//grid
		grid = new Button[gridSize][gridSize];
		tr = new TableRow[gridSize];
		
		//layout params for rows
		TableLayout.LayoutParams rl = new TableLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT,1.0f);
		TableRow.LayoutParams cl = new TableRow.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT,1.0f);


		//loop to create cells
		for (int i=0;i<=gridSize-1;i++) {
			tr[i] = new TableRow(this);	
			for (int j=0;j<=gridSize-1;j++) {
				grid[i][j] = new Button(this);
				grid[i][j].setText(Integer.toString(i)+","+Integer.toString(j));
				tr[i].addView(grid[i][j],cl);
			}
			tl.addView(tr[i],rl);
		}
	}
	public void onClick(View v) {
	}
}
