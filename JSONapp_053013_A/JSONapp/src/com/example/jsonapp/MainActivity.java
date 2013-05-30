package com.example.jsonapp;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


public class MainActivity extends Activity {

	String jsondata="";
	ArrayList<String> Ids;
	ArrayList<String> listValues;
	Context context;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
     
        context=this;
        fpGetJSON(0);    
        
        ///Post Button
        Button btnPost=(Button)findViewById(R.id.btnPost);
        btnPost.setOnClickListener( new OnClickListener(){
			@Override
			public void onClick(View v) {
				 Intent myIntent = new Intent(getApplicationContext(), detailActivity.class);
				 myIntent.putExtra("operation","post");
				 startActivity(myIntent);
			}
		});
        
        
        //Get list
        Button btnGetlist=(Button)findViewById(R.id.btnGetlist);
        btnGetlist.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v) {
        	    Log.d("Get List", "Pressed");
        		fpGetJSON(1);
        	}
        	
        });
        
        //Find
        Button btnFind=(Button)findViewById(R.id.btnGetID);
        btnFind.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v) {
        	    Log.d("Find", "Pressed");
        		fpGetJSON(2);
        	}
        	
        });
        
	}
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	   //Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.main, menu);
	        return true;
	}
	    
    private void fpGetJSON(int switchfilter) {
    	GetJSON Mylist=new GetJSON();
    	Mylist.filter=switchfilter;
    	Mylist.execute();
	}
 
    class GetJSON extends AsyncTask<Void, Integer, Void>
	{
    	 public int filter;
    	 public String filtervalues="Activity";
    	 
	     protected void onPreExecute() {
		  // update the UI immediately after the task is executed
		  super.onPreExecute();
		  
		  if (filter==1) //Get list by filter
		   {
		     
			 EditText txtfrom=(EditText)findViewById(R.id.txtFrom);
		     EditText txtTo=(EditText)findViewById(R.id.txtTo);
		     if ( ! (txtfrom.getText().toString().equalsIgnoreCase("") && txtTo.getText().toString().equalsIgnoreCase("")))
		     {  
		    	filtervalues +="?from=" + txtfrom.getText().toString() + "&to=";
		        filtervalues +=txtTo.getText().toString();
		     }
		   }
		  
		   else if(filter==2) //Get by Activity_ID
		   {
				 EditText txtfrom=(EditText)findViewById(R.id.txtFrom);
			     if ( ! (txtfrom.getText().toString().equalsIgnoreCase("")))
			    	filtervalues +="/" + txtfrom.getText().toString();
		
		   }  
		 }
	     
		 @Override
		 protected Void doInBackground(Void... params) {
		    fpJSONRetrieve();
			return null;
		 }
	 
		 @Override
		 protected void onProgressUpdate(Integer... values) {
		  super.onProgressUpdate(values);
		 }
	  
		 @Override
		 protected void onPostExecute(Void result) {
		  super.onPostExecute(result);
		  fpSetJSONData();
		 }

		 private void fpJSONRetrieve()
		 {
				clsJSON json=new clsJSON(filtervalues);
				jsondata=json.GetActvityData();
				json=null;
		 }

		 private void fpSetJSONData() 
		 {
			 
     		if (filter==2)
			{
					//finish();
					Intent myIntent = new Intent(context, detailActivity.class);
				    myIntent.putExtra("operation","put");
			     	myIntent.putExtra("GetById",jsondata);
			     	startActivity(myIntent);
			}
     		else
     		{
			    Ids=new ArrayList<String>();
			 	listValues=new ArrayList<String>();
				
			 	try {
	         
			     	     // Getting Array of Activity
			     	     JSONArray jsonActivities = new JSONArray(jsondata);
			     	     Ids.clear();
			     	     listValues.clear();
			     	    
			     	     // looping through All Activities
			     	     for(int i = 0; i < jsonActivities.length(); i++)
			     	        {
			     		 	   JSONObject jObj = jsonActivities.getJSONObject(i); 
			     		 	   Ids.add(jObj.getString("Activity_ID"));
			     		 	   listValues.add(jObj.getString("ActivityID") + "\n" + jObj.getString("ActivityDescription"));
			     		 	}
			     	 
			     	     // Getting adapter by passing data ArrayList
			     	     ListView list=(ListView)findViewById(R.id.mydataList);
			     	     customListView mylist=new customListView(context,Ids,listValues);
			     	     list.setAdapter(mylist);
			     	     mylist.notifyDataSetChanged();
			     	     list.invalidateViews();
			     	     list.setSelector( R.drawable.listselector);
			     	 
	             } 
	             catch (JSONException e) 
	             {
	                  e.printStackTrace();
	             }
     		}
	    }
	}	
 
    /*private void fpRefresh()
    {
    	 //Refresh Screen
	     //finish();
		 Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
		 startActivity(myIntent);
    }*/
}