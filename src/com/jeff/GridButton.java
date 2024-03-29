package com.jeff;

import android.widget.Button;
import android.content.Context;
import android.util.TypedValue;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.view.Display;
import android.view.WindowManager;
import android.view.Surface;

public class GridButton extends Button {
	//getter and setter methods be damned
	public int status; //holds variable indicating which player has pressed the button	
	public int row;	//position in the grid, zero is first
	public int col;
	Display d;


	public GridButton(Context con,int row, int col) {
		super(con);
		status = 0;
		this.setTypeface(Typeface.MONOSPACE);//lazy fix to bug where in the button would become larger than its neighbors when it was selected
		this.row = row;
		this.col = col;
		this.d = ((WindowManager) con.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();//consider moving this to parent

	}
	@Override
	public void onDraw(Canvas c) {
		super.onDraw(c);
		
		int ort = d.getRotation();

		if (ort == Surface.ROTATION_90 || ort == Surface.ROTATION_270) {//landscape
			this.setTextSize(TypedValue.COMPLEX_UNIT_FRACTION,55);
		}else {//portrait
			this.setTextSize(TypedValue.COMPLEX_UNIT_FRACTION,80);
		}
	}
}
