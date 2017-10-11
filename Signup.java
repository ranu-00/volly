package com.ranu.saloon;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.ranu.saloon.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
 
import org.json.JSONException;
import org.json.JSONObject;

public class Signup extends Activity implements OnClickListener {
 private static final String REGISTER_URL = "http://greenox.in/bookApi/sendOTP.php";
	  
	EditText edtxtName;
	EditText edtxtEmail;
	EditText edtxtPassword;
	EditText edtxtConfirmPassword;
	EditText lastName;
	EditText alertText;
	Button btnSignup;
	Button ok;
	final Activity registerActivity = this;
	final Context registerContext = this;
	String name;
	String email;
	String pass;
	String lastname;
	String phoneNum;
	String otp;
	String status;
	DataAccess dao;
	SPAccess spa;
	Button login;
	String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup);
		
		dao = new DataAccess(this);
		spa = new SPAccess(this);
		
		edtxtName = (EditText)findViewById(R.id.fullname_txt);		
		edtxtEmail = (EditText)findViewById(R.id.email_txt);		
		edtxtPassword = (EditText)findViewById(R.id.password_txt);		
		edtxtConfirmPassword = (EditText) findViewById(R.id.edtxtConfirmPassword);
		lastName = (EditText) findViewById(R.id.lastName);
		btnSignup = (Button) findViewById(R.id.signup);
		login = (Button) findViewById(R.id.login);
		
		
		login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//Intent reviewAttemptIntent = new Intent(Signup.this, Login.class);
				
				//startActivity(reviewAttemptIntent);
				Intent loginPage = new Intent(Signup.this, Login.class);
				startActivity(loginPage);
			}
		});
		
		btnSignup.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		boolean UserStatus = validateSignupInput();
		myTextDialog();
		
		if(UserStatus == false) {
		Intent loginPage = new Intent(Signup.this, Home.class);
		 startActivity(loginPage);
			finish();
			
		}
		
		else {
			//Do nothing
		}
	}
	
	
	
	/*
	 Checks if all the fields required for sign up are entered or not.
	 */
	private boolean validateSignupInput() {
		
		boolean valid = true;
		
		name = edtxtName.getText().toString();
		pass = edtxtPassword.getText().toString();
		email = edtxtEmail.getText().toString();
		lastname = lastName.getText().toString();
		phoneNum = edtxtConfirmPassword.getText().toString();
		//final String password = edtxtPassword.getText().toString().trim();
		
		if(name.trim().equals("") || pass.equals("") || email.equals("") ) {
			valid = false;
			Toast.makeText(getApplicationContext(), "Please enter all the details properly", Toast.LENGTH_SHORT).show();
		}
		else if(!email.matches(emailPattern)){
			Toast.makeText(getApplicationContext(), "Please use valid email", Toast.LENGTH_SHORT).show();	
			
		}
		
		else{
			
			valid = true;
			registerUser();
			
		}
		
		return valid;
	}
    private void registerUser(){
    	
    	JSONObject obj = new JSONObject();
    	try {
			obj.put("mobile_no", "1");
			//obj.put("name", "myname");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	

    	
    	JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST,REGISTER_URL,obj,
    	    new Response.Listener<JSONObject>() {
    	        @Override
    	        public void onResponse(JSONObject response) {
    	             try {
						System.out.println(response.getString("otp"));
						otp = response.getString("otp");
						alertText.setText(response.getString("otp"));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    	            // hideProgressDialog();
    	             
    	        }
    	    },
    	    new Response.ErrorListener() {
    	        @Override
    	        public void onErrorResponse(VolleyError error) {
    	           //  hideProgressDialog();
    	        }
    	    });
    	RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsObjRequest);

    }
    protected void myTextDialog()
	{
	final Dialog dialog = new Dialog(this);
	dialog.setContentView(R.layout.layout_dialogbox);
	dialog.setTitle("Text Dialog");
	 ok = (Button)dialog.findViewById(R.id.buttonOK);
	 alertText = (EditText)dialog.findViewById(R.id.editText1);
	ok.setOnClickListener(new View.OnClickListener() {
	 
	@Override
	public void onClick(View v) {
		String s = alertText.getText().toString();
		
		if(s.equals(otp)){
		
	    registration();	
		
		}else {
			Intent loginPage = new Intent(Signup.this, Signup.class);
			startActivity(loginPage);
			finish();
			
			
		}
		dialog.dismiss();
	 
	}
	});
	dialog.show();
	 
	}
    private void registration(){

    	
    
    	
       String URL = "http://greenox.in/bookApi/registration.php?first_name="+name+"&last_name="+lastname+"&email_id="+email+"&password="+pass+"&mobile_no="+phoneNum+"&requestby=vendor&platform=android&gender=1";
    	
    	JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST,URL,null,
    	    new Response.Listener<JSONObject>() {
    	        @Override
    	        public void onResponse(JSONObject response) {
    	            
					try {
						status = response.getString("status");
						JSONObject object = response.getJSONObject("data");
						String userId = object.getString("user_id");
						System.out.print(userId);
						System.out.println(response.getString("status"));
						if(status == "true"){
						 addEntry(userId,name, email, pass);
						}else if((status == "false")){
							 System.out.println(response);
	Toast.makeText(getApplicationContext(), "zxzk"+response.getString("msg"), Toast.LENGTH_SHORT).show();		

						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    	             
    	        }
    	    },
    	    new Response.ErrorListener() {
    	        @Override
    	        public void onErrorResponse(VolleyError error) {
    	           //  hideProgressDialog();
    	        }
    	    });
    	RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsObjRequest);

    
    	
    }
	/*
	 If user already exists with the email id entered, error message is displayed. If not, User gets added to the database.
	 */
	private void addEntry(String id,String name, String email, String pass) {	
		long userId = 0;
		int user_id =  Integer.parseInt(id);
		boolean check = dao.CheckIfUserAlreadyExist(email);
		if(check == true) {
			Toast.makeText(this.getApplicationContext(), "User with this email id already exist", Toast.LENGTH_SHORT).show();
		}
		else {
			
			userId = dao.insertUser(user_id,name,email,pass);
			//int id =  userId;
			
			Intent loginPage = new Intent(Signup.this, Home.class);
			startActivity(loginPage);
			finish();
			spa.saveId(user_id);
		}
	}
}