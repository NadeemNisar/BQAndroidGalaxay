package com.example.jsonapp;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

public class customListView extends BaseAdapter {
	 
	   private final Context context;
	   private ArrayList<String> Ids;
   	   private ArrayList<String> listValues;
   	   private int itemPosition;
   	
   	   class DeleteTask extends AsyncTask<Void, Integer, Void>
	   {
	     protected void onPreExecute() {
		  // update the UI immediately after the task is executed
		  super.onPreExecute();
		  Log.d("DeleteTask","PreExecute");	
		 }
	     
		 @Override
		 protected Void doInBackground(Void... params) {
		
			 try 
			 {
			     //Delete an entry on clicking button
				 clsJSON delJson=new clsJSON("/Activity/"+ Ids.get(itemPosition));
				 delJson.DeleteActivityData();
				 delJson=null;
				 
				 //Refresh Screen
			     ((Activity) context).finish();
				 Intent myIntent = new Intent(context, MainActivity.class);
				 ((Activity) context).startActivity(myIntent);
			 }
			
		     catch (Exception e) 
			 {
				e.printStackTrace();
			 }
		     return null;
		 }
	 
		 
		 
		 @Override
		 protected void onProgressUpdate(Integer... values) {
		  super.onProgressUpdate(values);
		 }
	  
		 @Override
		 protected void onPostExecute(Void result) {
		  super.onPostExecute(result);
		 }
		 
       }
   	   
   	   class DetailsTask extends AsyncTask<Void, Integer, Void>
	   {
	     protected void onPreExecute() {
		  // update the UI immediately after the task is executed
		  super.onPreExecute();
		 }
	     
		 @Override
		 protected Void doInBackground(Void... params) {
		
			 try 
			 {
					String jsonRetAct="";
					clsJSON detailJson=new clsJSON("/Activity/"+ Ids.get(itemPosition));
					jsonRetAct=detailJson.GetActvityData();
					Log.d("GetById",jsonRetAct);
					detailJson=null;
				
					 Intent myIntent = new Intent(context, detailActivity.class);
					 myIntent.putExtra("operation","put");
					 myIntent.putExtra("GetById",jsonRetAct);
					 ((Activity) context).startActivity(myIntent);
				 } 
				 
				 catch (Exception e) 
				 {
					e.printStackTrace();
				 }
				
			 return null;
		 }
         
		 protected void onProgressUpdate(Integer... values) {
		  super.onProgressUpdate(values);
		 }
	  
		 @Override
		 protected void onPostExecute(Void result) {
		  super.onPostExecute(result);
		 }
		 
	   }
	 
       public customListView(Context context, ArrayList<String> ids,ArrayList<String> values) {
			
			this.context = context;
			this.Ids=ids;
			this.listValues=values;
			
		}
	 
	   @Override
	   public View getView(final int position, View convertView, ViewGroup parent) {
			
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.listview, parent, false);
        	
			TextView textView = (TextView) rowView.findViewById(R.id.lbldata);
			ImageButton btnDel = (ImageButton) rowView.findViewById(R.id.btndelete);
			ImageButton btnDetails = (ImageButton) rowView.findViewById(R.id.btndetails); 
			
			textView.setText(listValues.get(position));
			
			btnDel.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					itemPosition=position;
					new DeleteTask().execute();
				}
			});
			
			btnDetails.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					itemPosition=position;
					new DetailsTask().execute();
				}
			});
			
	 		return rowView;
		}

	   @Override
	   public int getCount() {
			// TODO Auto-generated method stub
			return Ids.size();
		}

	   @Override
	   public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
	   public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}
		
}



