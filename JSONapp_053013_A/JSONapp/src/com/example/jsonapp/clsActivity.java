package com.example.jsonapp;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class clsActivity {
	
	private String Activity_ID; 
	
	private String ActivityID;

	private String ActivityCode; 

	private String ActivitySub;
    
	private String ActivityDescription;
	
    private int ActivityBillable;
    
    public clsActivity()
    {
    	Activity_ID="";
    	ActivityID="";
    	ActivityCode="";
    	ActivitySub="";
    	ActivityDescription="";
    	ActivityBillable=0;
    }
    
    public clsActivity(JSONObject obj)
    {   
	   try 
    	{
			   Activity_ID=obj.getString("Activity_ID");
		       ActivityID=obj.getString("ActivityID");
    	       ActivityCode=obj.getString("ActivityCode");
    	       ActivitySub=obj.getString("ActivitySub");
    	       ActivityDescription=obj.getString("ActivityDescription");
    	       ActivityBillable=obj.getInt("ActivityBillable");
    	} 
    	catch (JSONException e) 
    	{
			e.printStackTrace();
		}
    }
    
    ////Get Methods
    public String GetActivity_ID() { return Activity_ID; }
    
    public String GetActivityID() { return ActivityID; }
    
    public String GetActivityCode() { return ActivityCode; }
    
    public String GetActivitySub() { return ActivitySub; }
    
    public String GetActivityDescription() { return ActivityDescription; }
    
    public int GetActivityBillable() { return ActivityBillable; }
    //////////////////////
    
    ////Set Methods
    public void SetActivity_ID(String act_ID) { Activity_ID=act_ID; }
    
    public void SetActivityID(String actID) { ActivityID=actID;    }
    
    public void SetActivityCode(String actCode) { ActivityCode=actCode;  }
    
    public void SetActivitySub(String actSub) {	ActivitySub=actSub;  }
    
    public void SetActivityDescription(String actDesc) { ActivityDescription=actDesc;}
    
    public void SetActivityBillable(int activityBillable) { ActivityBillable=activityBillable; }
    //////////////////////

    public void SetPropertiesFromJSON(JSONObject jsonActivity)
    {   
    	try 
    	{
    		   Log.d("JSONActivity","IN");
    		   Activity_ID = jsonActivity.getString("Activity_ID");
		       ActivityID = jsonActivity.getString("ActivityID");
    	       ActivityCode = jsonActivity.getString("ActivityCode");
    	       ActivitySub = jsonActivity.getString("ActivitySub");
    	       ActivityDescription = jsonActivity.getString("ActivityDescription");
    	       ActivityBillable = jsonActivity.getInt("ActivityBillable");
    	       Log.d("JSONActivity","OUT");
		
    	}
    	catch (Exception e) 
    	{
			e.printStackTrace();
		}
    	
    }
    
}
