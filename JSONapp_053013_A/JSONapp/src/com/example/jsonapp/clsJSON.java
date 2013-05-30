package com.example.jsonapp;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import android.util.Log;

public class clsJSON {
	
	// Call BQE API  to make request
	private static String url = "";

	// activity JSONArray
	JSONArray activities = null;
    JSONObject json = null;
    
	public clsJSON(String list)
	{ 
		url="http://192.168.13.22/BQEGalaxyApi/api/" + list;
	}
	
	//Method to prepare JSONObject from Activity
	private void fpPrepareJSON(clsActivity ObjSend,boolean op)
	{
	   try 
	    {
	    	json = new JSONObject();
			json.put("Activity_ID",ObjSend.GetActivity_ID());
	   	    json.put("ActivityCode",ObjSend.GetActivityCode());
			json.put("ActivityDescription",ObjSend.GetActivityDescription());
			json.put("ActivitySub",ObjSend.GetActivitySub());
			json.put("ActivityID",ObjSend.GetActivityID());
			json.put("ActivityBillable",ObjSend.GetActivityBillable());

			if (op)
			   json.put("UpdateList","");  //ActivityCode,ActivityDescription,ActivitySub,ActivityBillable"); //
		} 
	    catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Method to Get activity list from Cloud and by activityID or activity_ID
	public String GetActvityData()
	{
		String result = null;
		
		//Step 1: Initialize HTTP CLient
		HttpClient  httpClient=new DefaultHttpClient();
    	
    	//Step 2: Ready the HTTP Get Request
    	HttpGet httpGetRequest=new HttpGet();
    	try {
			httpGetRequest.setURI(new URI(url));
			httpGetRequest.setHeader("Accept", "application/json");
			httpGetRequest.setHeader("Authorization","Basic U2hhZmF0OlRlc3Q6NzE5QTRFQTUtQkY1OS00QkE3LUFBRDktNDc3N0Y4MkVFNTNFOjYzMTZBNDk2LUYzRUYtNEI5Qy1CQkQxLUI1MDQ1RDk2MTVDQjo=");
		} 
    	catch (URISyntaxException e1) {
    		e1.printStackTrace();
		}
    	
        //Step 3:Execute the Request and Set Response Object
    	HttpResponse response = null;
    	try {
    		 response = httpClient.execute(httpGetRequest);
		} catch (ClientProtocolException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}

    	//Step 4: Extract data entity from response object
    	HttpEntity entity=response.getEntity();
    	if (entity!=null)
    	{
    		
			try {
				result = EntityUtils.toString(entity);
			} catch (ParseException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
	     }
    	
    	 Log.d("Activity Returned:",result);
		 return result;
	}
	
	//Method to delete an activity in a cloud
	public int DeleteActivityData()
	{
		//Step 1: Initialize HTTP CLient
		HttpClient  httpClient=new DefaultHttpClient();
    	
    	//Step 2: Ready the HTTP Get Request
    	HttpDelete httpDeleteRequest=new HttpDelete();
    	
    	try {
    		httpDeleteRequest.setURI(new URI(url));
    		httpDeleteRequest.setHeader("Accept", "application/json");
    		//httpDeleteRequest.setHeader("Host","192.168.13.17");
    		httpDeleteRequest.setHeader("Authorization","Basic U2hhZmF0OlRlc3Q6NzE5QTRFQTUtQkY1OS00QkE3LUFBRDktNDc3N0Y4MkVFNTNFOjYzMTZBNDk2LUYzRUYtNEI5Qy1CQkQxLUI1MDQ1RDk2MTVDQjo=");
	    	Log.d("JSon","Get request");
		} 
    	catch (URISyntaxException e1) {
    		Log.d("JSon","Get request Error");
			e1.printStackTrace();
		}
    	
    	//Step 3:Execute the Request and Set Response Object
    	HttpResponse response = null;
    	try {
    		 Log.d("JSon","response start");
			 response = httpClient.execute(httpDeleteRequest);
			 Log.d("Response Status: ",String.valueOf(response.getStatusLine().getStatusCode()));
			 Log.d("JSon","response");

		} catch (ClientProtocolException e1) {
			Log.d("JSon","response Error in Protocol");
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			Log.d("JSon","response IOException");
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e){
			Log.d("JSON","General Response Exception : " + e.getMessage());
			e.printStackTrace();
		}
    	
    	if (response.getStatusLine().getStatusCode()==204)
    	    return 1;
    	else
    	    return -1;
	}

    //Method to post activity into a cloud
	public void PostActivityData(clsActivity postActivity)
	{         
	    try 
	    {  
	    	 Log.d("POST ACTIVITY","Function");
	    	 //Step 1: Create JSON OBject Here
	    	 fpPrepareJSON(postActivity,false);
	    	 
	    	 //Step 2:Instantiate Client object
			 HttpClient client = new DefaultHttpClient();
			
			 //Step 3:Prepare HTTPPOST request
			 HttpPost postMethod = new HttpPost(url);
			 postMethod.addHeader("Content-Type", "application/json");
			 postMethod.addHeader("Accept", "application/json");
			 postMethod.addHeader("Authorization","Basic U2hhZmF0OlRlc3Q6NzE5QTRFQTUtQkY1OS00QkE3LUFBRDktNDc3N0Y4MkVFNTNFOjYzMTZBNDk2LUYzRUYtNEI5Qy1CQkQxLUI1MDQ1RDk2MTVDQjo=");
			 postMethod.setEntity(new StringEntity(json.toString(), HTTP.UTF_8));
	         Log.d("POST","2"); 
	         
	         //Step 4:Execute HTTPPOST request
	         HttpResponse response = client.execute(postMethod);
		     
	         // 200 type response.
	         if (response.getStatusLine().getStatusCode() >= HttpStatus.SC_OK &&
	         response.getStatusLine().getStatusCode() < HttpStatus.SC_MULTIPLE_CHOICES)
	         {
	               //Handle OK response etc
	               Log.d("HTTPPOST Request Executed","Successfully");	
	         }
		 } 
		 catch (Exception e) 
		 {
		     e.printStackTrace();
	     }
		 
	}
	
	//Method to put activity to a cloud
	public void PutActivityData(clsActivity putActivity)
	{
		 try 
		 {
			Log.d("PUT","Called"); 
		     
			//Step 1: Create JSON OBject 
			fpPrepareJSON(putActivity,true);
		    
		    //Step 2:Instantiate Client object
		    HttpClient client = new DefaultHttpClient();
		    
		    //Step 3:Prepare HTTPPUT request
 			HttpPut put= new HttpPut(url);
	        put.addHeader("Content-Type", "application/json");
            put.addHeader("Accept", "application/json");
            put.addHeader("Authorization","Basic U2hhZmF0OlRlc3Q6NzE5QTRFQTUtQkY1OS00QkE3LUFBRDktNDc3N0Y4MkVFNTNFOjYzMTZBNDk2LUYzRUYtNEI5Qy1CQkQxLUI1MDQ1RDk2MTVDQjo=");
            put.setEntity(new StringEntity(json.toString(), HTTP.UTF_8));
            
            //Step 4:Execute HTTPPUT request
            HttpResponse response = client.execute(put);
		    
            // 200 type response.
            if (response.getStatusLine().getStatusCode() >= HttpStatus.SC_OK &&
            response.getStatusLine().getStatusCode() < HttpStatus.SC_MULTIPLE_CHOICES)
            {
               //Handle OK response etc
               Log.d("HTTPPut Request Executed","Successfully");	
            }
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		
  }

}


	

