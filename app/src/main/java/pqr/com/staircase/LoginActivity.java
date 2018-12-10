package pqr.com.staircase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class LoginActivity extends AppCompatActivity {

    EditText usernameText,passwordText;
    Button loginButton;
    String username,password;
    AgentLoginDetails agentLoginDetails;

    String app_url = "https://drive.google.com/uc?export=download&id=1DzKzrgadKIWgWmiW3t2fUCJh2ji2g9SS";

    String loginUrl =  "http://192.168.1.8:8000/api/agentLogin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);


        usernameText = findViewById(R.id.usernameText);
        passwordText = findViewById(R.id.passwordText);
        loginButton = findViewById(R.id.loginButton);
        agentLoginDetails = new AgentLoginDetails(LoginActivity.this);

        //downloadTask = new LoginActivity.DownloadTask();
        //new DownloadTask().execute(app_url);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                username = usernameText.getText().toString();
                password = passwordText.getText().toString();

                if(!username.equals("") && !password.equals(""))
                {
                    agentLoginDetails.getAgentDetails(username,password);

                }
                else
                {
                    Toast.makeText(LoginActivity.this,"Fill the fileds",Toast.LENGTH_LONG).show();
                }

            }
        });




    }



    private class DownloadTask extends AsyncTask<String,Integer,String>
    {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {

            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setTitle("Downloading in Progress");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMax(100);
            progressDialog.setProgress(0);
            progressDialog.show();


        }

        @Override
        protected String doInBackground(String... params) {

            String path = params[0];
            try {
                URL url = new URL(path);
                URLConnection urlConnection = url.openConnection();
                urlConnection.connect();
                int fileLength = urlConnection.getContentLength();

                File new_folder = new File(Environment.getExternalStorageDirectory()+"/Download/");

                if(!new_folder.exists())
                {
                    new_folder.mkdir();
                }

                File inputFile = new File(new_folder,"staircase.apk");

                InputStream inputStream = new BufferedInputStream(url.openStream(),1024);
                byte[] data = new byte[1024];

                int total = 0;
                int count = 0;


                OutputStream outputStream = new FileOutputStream(inputFile);

                while ((count = inputStream.read(data))!=-1)
                {
                    total += count;
                    outputStream.write(data,0,count);
                    int progress = (int)total*100/fileLength;
                    //Log.d("Progress",String.valueOf(progress));

                    publishProgress(progress);



                }
                inputStream.close();
                outputStream.close();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return "Download Complete";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

            progressDialog.setProgress(values[0]);
            //Log.d("Progress",String.valueOf(values[0]));


        }


        @Override
        protected void onPostExecute(String result) {


            progressDialog.hide();
            Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
            String path = Environment.getExternalStorageDirectory()+"/Download/staircase.apk";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(new File(path)),
                    "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }
    }





}
