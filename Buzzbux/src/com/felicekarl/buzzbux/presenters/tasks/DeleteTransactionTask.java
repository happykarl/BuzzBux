package com.felicekarl.buzzbux.presenters.tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.felicekarl.buzzbux.presenters.Network;

import android.os.AsyncTask;
/**
 * DeleteTransactionTask.
 * @author Karl
 *
 */
public class DeleteTransactionTask extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... params) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(params[0]);
		
        String jsonResult = "";
		
		/* add post data*/
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(params.length - 1);
        nameValuePairs.add(new BasicNameValuePair(Network.TAG_OMA_TRANSACTION_INDEX, params[1]));
        nameValuePairs.add(new BasicNameValuePair(Network.TAG_OMA_TRANSACTION_SIGN, params[2]));
        nameValuePairs.add(new BasicNameValuePair(Network.TAG_OMA_TRANSACTION_DIFF, params[3]));
        
        try {
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            jsonResult = inputStreamToString(response.getEntity().getContent()).toString();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonResult;
    }
    /**
	 * String builder from InputStream.
	 * @param is inputStream
	 * @return built String
	 */
    private StringBuilder inputStreamToString(InputStream is) {
        String rLine = "";
        StringBuilder answer = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
 
        try {
            while ((rLine = rd.readLine()) != null) {
                answer.append(rLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return answer;
    }
}
