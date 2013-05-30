package com.example.jsonapp;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class detailActivity extends Activity {
	
   clsActivity objActivity=null;
   String jsonReturned="";
   ///Detail Actvity is meant for github testing 
   @Override
   protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitydetail);	
        Log.d("Activity Details","Launched");
        
        Bundle bundle = getIntent().getExtras();
        
        if (bundle.getString("operation").equalsIgnoreCase("put"))
        {
        	//Get the JSON from Bundle
        	jsonReturned = bundle.getString("GetById");
	      
	        //Map JSON to Activity
	        fpMapJSON();
	      
	        //Render UI  objActivity
		    SetTextValues();
        }
        
	    //Attach Click listeners
	    fpAttachclickListneres();
	}

   private void fpAttachclickListneres() {
		Button btnPost=(Button)findViewById(R.id.btnSave);
		btnPost.setOnClickListener( new OnClickListener(){
		    @Override
		    public void onClick(View v) {
				if (objActivity==null)
				{
					Log.d("POST___","Clicked");
					fpPostJSON();
				}
				else
				{
					Log.d("PUT___","Clicked");
					fpPutJSON();
				}	
			}
		});
	}

   private void fpMapJSON() 
	{
		try 
		{
			JSONObject jobj=new JSONObject(jsonReturned);
			objActivity=new clsActivity();
			objActivity.SetPropertiesFromJSON(jobj);
		} 
		catch (JSONException e) 
		{
			e.printStackTrace();
		}
   }

   private void fpPutJSON() 
   {
	   	new PutJSON().execute();
   }

   private void fpPostJSON() 
   {
   	   new PostJSON().execute();
   }
   
   private void fpSetObject(boolean op) {
		try
		{
			EditText txtValue; 
			CheckBox chkBill;
			String ActivityID="";
			if (op)
			{
			
				objActivity=new clsActivity(); 
				String id=UUID.randomUUID().toString();
				objActivity.SetActivity_ID(id);
				Log.d("Id",objActivity.GetActivity_ID());
			}
			
			txtValue=(EditText)findViewById(R.id.txtActivityCode);
			objActivity.SetActivityCode(txtValue.getText().toString());
			Log.d("CODE::::::",objActivity.GetActivityCode());
			ActivityID=txtValue.getText().toString();
			
			txtValue=(EditText)findViewById(R.id.txtActivitySub);
			objActivity.SetActivitySub(txtValue.getText().toString());
			ActivityID +=":"+ txtValue.getText().toString();
					
			txtValue=(EditText)findViewById(R.id.txtActivityDesc);
			objActivity.SetActivityDescription(txtValue.getText().toString());
			
			txtValue=(EditText)findViewById(R.id.txtActivityID);
			objActivity.SetActivityID(ActivityID);
			
			chkBill=(CheckBox)findViewById(R.id.chkActivityBillable);
			if ( chkBill.isChecked()==false)
			{
			   objActivity.SetActivityBillable(0);
			}
			else
		    {
				objActivity.SetActivityBillable(1);
		    }
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		
	}
 
   private void SetTextValues() {
		try
		{
			EditText txtValue; 
			CheckBox chkBill;
			txtValue=(EditText)findViewById(R.id.txtActivityID);
			txtValue.setText(String.valueOf(objActivity.GetActivityID()));
			
			txtValue=(EditText)findViewById(R.id.txtActivityCode);
			txtValue.setText(String.valueOf(objActivity.GetActivityCode()));
			
			txtValue=(EditText)findViewById(R.id.txtActivitySub);
			txtValue.setText(String.valueOf(objActivity.GetActivitySub()));
			
			txtValue=(EditText)findViewById(R.id.txtActivityDesc);
			txtValue.setText(String.valueOf(objActivity.GetActivityDescription()));
			
			chkBill=(CheckBox)findViewById(R.id.chkActivityBillable);
			if ( objActivity.GetActivityBillable()==0)
			{
				chkBill.setChecked(false);
			}
			else
		    {
				chkBill.setChecked(true);
		    }
			
		
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
  
   private void fpRefresh()
   {
   	     //Refresh Screen
	     finish();
		 //Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
		 //startActivity(myIntent);
   }
  
   class PutJSON extends AsyncTask<Void, Integer, Void>
   {
	     protected void onPreExecute() {
		  // update the UI immediately after the task is executed
		  super.onPreExecute();
		  Log.d("PutActvity","PreExecute");	
		  fpSetObject(false);
         }
	     
		@Override
		 protected Void doInBackground(Void... params) {
			clsJSON putjson=new clsJSON("Activity");
			putjson.PutActivityData(objActivity);
	        putjson=null;
		    return null;
		 }
	 
		 @Override
		 protected void onProgressUpdate(Integer... values) {
		  super.onProgressUpdate(values);
		  Log.d("PutActvity","Update");
		 }
	  
		 @Override
		 protected void onPostExecute(Void result) {
		  super.onPostExecute(result);
		  Log.d("PutActvity","Post");
		  fpRefresh();
	     }

		 private void fpRefresh()
		 {
			finish();
			//Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
			//startActivity(myIntent);
	     }
		
    }
    
   class PostJSON extends AsyncTask<Void, Integer, Void>
	{
	     protected void onPreExecute() {
		  // update the UI immediately after the task is executed
		  super.onPreExecute();
		  Log.d("PostActvity","PreExecute");	
		  fpSetObject(true);
		 }
	     
		 @Override
		 protected Void doInBackground(Void... params) {
			Log.d("PostActvity","BackGround");
			clsJSON postjson=new clsJSON("Activity");
			postjson.PostActivityData(objActivity);
			postjson=null;
		    return null;
		 }
	 
		 @Override
		 protected void onProgressUpdate(Integer... values) {
		  super.onProgressUpdate(values);
		  Log.d("PostActvity","Update");
		 }
	  
		 @Override
		 protected void onPostExecute(Void result) {
		  super.onPostExecute(result);
		  Log.d("PostActvity","Post");
		  fpRefresh();
	     }
   }
      
}
