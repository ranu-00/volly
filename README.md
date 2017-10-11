# volly
json post request using android volly
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
