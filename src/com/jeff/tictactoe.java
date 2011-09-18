package com.jeff;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.AbsoluteLayout.*;

public class tictactoe extends Activity implements View.OnClickListener {
	TableLayout t;	
	TableRow[] tr;
	Button[][] grid;
	int gridSize = 3;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		t = new TableLayout(this);//table layout
		t.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
		setContentView(t);

		//init *one change
		grid = new Button[gridSize][gridSize];
		tr = new TableRow[gridSize];
			
		for (int i=0;i<=gridSize-1;i++) {
			tr[i] = new TableRow(this);	
			//tr[i].setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
			tr[i].setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,40));
			for (int j=0;j<=gridSize-1;j++) {
				grid[i][j] = new Button(this);
				grid[i][j].setText(Integer.toString(i)+","+Integer.toString(j));
				tr[i].addView(grid[i][j]);
			}
			t.addView(tr[i]);
		}
	}
	public void onClick(View v) {
	}
}
