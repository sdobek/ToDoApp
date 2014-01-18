package com.example.todoapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends Activity {
	EditText toEditItem;
	int position;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
		
		String itemText = getIntent().getStringExtra("itemText");
		position = getIntent().getIntExtra("pos", 0);
		
		toEditItem = (EditText) findViewById(R.id.toEditItem);
		toEditItem.setText(itemText);
	}
	
	public void returnEditChanges(View v){
		Intent editI = new Intent();
		editI.putExtra("pos", position);
		editI.putExtra("newText", toEditItem.getText().toString());
		
		setResult(RESULT_OK, editI);
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_item, menu);
		return true;
	}

}
