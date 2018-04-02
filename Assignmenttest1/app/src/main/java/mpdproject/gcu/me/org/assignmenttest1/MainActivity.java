//
//
// Starter code for the Mobile Platform Development Assignment
// Seesion 2017/2018
//
//

package mpdproject.gcu.me.org.assignmenttest1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private String url1="https://trafficscotland.org/rss/feeds/currentincidents.aspx";
    private String url2="https://trafficscotland.org/rss/feeds/roadworks.aspx";
    private String url3="https://trafficscotland.org/rss/feeds/plannedroadworks.aspx";
    private TextView urlInput;
    private Button current;
    private Button roadworks;
    private Button planned;
    private String result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        urlInput = (TextView)findViewById(R.id.urlInput);

        current = (Button)findViewById(R.id.current);
        current.setOnClickListener(this);

        roadworks = (Button) findViewById(R.id.roadworks);
        roadworks.setOnClickListener(this);

    } // End of onCreate

    @Override
    public void onClick(View aview)
    {
        if (aview == current.findViewById(R.id.current)) {
            startProgress1();
        }
        else if (aview == roadworks.findViewById(R.id.roadworks)) {
            startProgress2();
        }
    }

    public void startProgress1()
    {
        Toast.makeText(getApplicationContext(), "Current Incidents", Toast.LENGTH_LONG).show();
        urlInput.setText("Getting Current Incidents Information...");
        Log.e("INFO", "urlInput has been cleared" );
        Log.e("INFO", "Running with: " + url1);
        new Thread(new Task(url1)).start();
    } //

    public void startProgress2()
    {
        Toast.makeText(getApplicationContext(), "Roadworks", Toast.LENGTH_LONG).show();
        urlInput.setText("Getting Roadworks Information...");
        Log.e("INFO", "urlInput has been cleared" );
        Log.e("INFO", "Running with: " + url2);
        new Thread(new Task(url2)).start();
    } //

    // Need separate thread to access the internet resource over network
    // Other neater solutions should be adopted in later iterations.
    class Task implements Runnable
    {
    private String url;

        public Task(String aurl)
        {
            url = aurl;
        }
        @Override
        public void run()
        {

            URL aurl;
            URLConnection yc;
            BufferedReader in = null;
            String inputLine = "";


            Log.e("MyTag","in run");

            try
            {
                Log.e("MyTag","in try");
                aurl = new URL(url);
                yc = aurl.openConnection();
                in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                //
                // Throw away the first 2 header lines before parsing
                //
                //
                //
                while ((inputLine = in.readLine()) != null)
                {
                    result = result + inputLine;
                    Log.e("MyTag",inputLine);

                }
                in.close();
            }
            catch (IOException ae)
            {
                Log.e("MyTag", "ioexception");
            }

            //
            // Now that you have the xml data you can parse it
            //

            // Now update the TextView to display raw XML data
            // Probably not the best way to update TextView
            // but we are just getting started !

            MainActivity.this.runOnUiThread(new Runnable()
            {
                public void run() {
                    Log.d("UI thread", "I am the UI thread");
                    urlInput.setText(result);
                }
            });
        }

    }
}
