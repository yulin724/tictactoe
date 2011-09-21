package com.jeff;

import android.widget.Button;
import android.content.Context;

public class GridButton extends Button {
	//getter and setter methods be damned
	public int status;	
	public int row;	//position in the grid, zero is first
	public int col;

	public GridButton(Context con,int row, int col) {
		super(con);
		status = 0;
		this.row = row;
		this.col = col;
	}
}
