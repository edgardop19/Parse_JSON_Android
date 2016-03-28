package inducesmile.com.parsejsoninandroid;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.JsonReader;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private ListView songList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        songList = (ListView)findViewById(R.id.listView);
        AsynDataClass jsonAsync = new AsynDataClass();
        jsonAsync.execute("");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class AsynDataClass extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub

            HttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());
            HttpPost httpPost = new HttpPost("http://toscanyacademy.com/blog/download/content.json");

            String jsonResult = "";

            try {
                HttpResponse response = httpClient.execute(httpPost);
                jsonResult = inputStreamToString(response.getEntity().getContent()).toString();
                System.out.println("Returned Json object " + jsonResult.toString());

            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return jsonResult;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            System.out.println("Resulted Value: " + result);
            List<ItemObject> parsedObject = returnParsedJsonObject(result);

            CustomAdapter jsonCustomAdapter = new CustomAdapter(MainActivity.this, parsedObject);
            songList.setAdapter(jsonCustomAdapter);
        }

        private StringBuilder inputStreamToString(InputStream is) {
            String rLine = "";
            StringBuilder answer = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            try {
                while ((rLine = br.readLine()) != null) {
                    answer.append(rLine);
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return answer;
        }
    }

    private List<ItemObject> returnParsedJsonObject(String result){

        List<ItemObject> jsonObject = new ArrayList<ItemObject>();
        JSONObject resultObject = null;
        JSONArray jsonArray = null;
        ItemObject newItemObject = null;

        try {
            resultObject = new JSONObject(result);
            System.out.println("Testing the water " + resultObject.toString());
            jsonArray = resultObject.optJSONArray("African_Music");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject jsonChildNode = null;
            try {
                jsonChildNode = jsonArray.getJSONObject(i);
                String songName = jsonChildNode.getString("song_name");
                String songYear = jsonChildNode.getString("song_id");
                String songAuthor = jsonChildNode.getString("artist_name");
                newItemObject = new ItemObject(songName, songYear, songAuthor);
                jsonObject.add(newItemObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }

}
