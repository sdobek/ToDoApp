package com.example.todoapp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;


public class ToDoActivity extends Activity {
	ArrayList<String> todoItems;
	ArrayAdapter<String> todoAdapter;
	ListView lvItems;
	EditText etNewItem;
	
	private final int REQUEST_CODE = 42;
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
        etNewItem = (EditText) findViewById(R.id.etNewItem);
        lvItems = (ListView) findViewById(R.id.lvItems);
        todoItems = new ArrayList<String>();
        readItems();
        todoAdapter = new ArrayAdapter<String>(getBaseContext(),
        		android.R.layout.simple_list_item_1, todoItems);
        lvItems.setAdapter(todoAdapter);
        setupListViewListener();
        setupEditListener();
    }

    private void setupListViewListener() {
		lvItems.setOnItemLongClickListener(new OnItemLongClickListener(){
			public boolean onItemLongClick(AdapterView<?> aView,
					View item, int pos, long id){
				todoItems.remove(pos);
				todoAdapter.notifyDataSetInvalidated();
				writeItems();
				return true;
			}
			
		});
	}
    
    private void setupEditListener() {
		lvItems.setOnItemClickListener(new OnItemClickListener(){
			public void onItemClick(AdapterView<?> aView,
					View item, int pos, long id){
				Intent i = new Intent(ToDoActivity.this, EditItemActivity.class);
				String toEdit = todoAdapter.getItem(pos);
				i.putExtra("itemText", toEdit);
				i.putExtra("pos", pos);
				startActivityForResult(i, REQUEST_CODE);
			}
			
		});
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.to_do, menu);
        return true;
    }
    
    public void addTodoItem(View v){
    	todoAdapter.add(etNewItem.getText().toString());
    	etNewItem.setText("");
    	writeItems();
    }
    
    private void readItems(){
    	File filesDir = getFilesDir();
    	File todoFile = new File(filesDir, "todo.txt");
    	try {
    		todoItems = new ArrayList<String>(FileUtils.readLines(todoFile));
    	}
    	catch (IOException e) {
    		todoItems = new ArrayList<String>();
    		e.printStackTrace();
    	}
    	
    }
    
    private void writeItems(){
    	File filesDir = getFilesDir();
    	File todoFile = new File(filesDir, "todo.txt");
    	try {
    		FileUtils.writeLines(todoFile, todoItems);
    	}
    	catch (IOException e) {
    		e.printStackTrace();
    	}
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
		  String itemText = data.getStringExtra("newText");
		  int position = data.getIntExtra("pos", 0);
		  todoItems.set(position, itemText);
	      lvItems.setAdapter(todoAdapter);
		  writeItems();
	  }
    } 
    
}
