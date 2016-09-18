package com.revesoft.tcptest;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnsend;
    String input_text;
    EditText input, port;
    TextView serverResponse;
    String modifiedSentence;

    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input = (EditText) findViewById(R.id.et_text);
        btnsend = (Button) findViewById(R.id.btn_send);
        // btnsend.setOnClickListener(this);
        serverResponse = (TextView) findViewById(R.id.tv_response);
        TcpRequest tcpRequest = new TcpRequest();
        tcpRequest.execute(input_text);

    }

    @Override
    public void onClick(View view) {
        if (flag == false)
            flag = true;
        else flag = false;
        input_text = input.getText().toString();
        TcpRequest tcpRequest = new TcpRequest();
        tcpRequest.execute(input_text);
    }

    class TcpRequest extends AsyncTask<String, String, String> {
        Socket clientSocket = null;

        @Override
        protected String doInBackground(String... strings) {
            //                clientSocket = new Socket("192.168.31.0", 1000);

                /*int i=0;

              //  while (i++<3) {
                    clientSocket = new Socket("192.168.31.0", 1000);
                   // OutputStream socketOutputStream = clientSocket.getOutputStream();
                    DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
                    BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    // sentence = inFromUser.readLine();

                    outToServer.writeBytes(strings[0] + '\n');
                   // socketOutputStream.write(strings[0].getBytes());
                    modifiedSentence = inFromServer.readLine();
                    if (modifiedSentence != null)
                        Log.i("modifiedSentence: ", modifiedSentence);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            // some code #3 (Write your code here to run in UI thread)
                            serverResponse.append("\n" + modifiedSentence);

                        }
                    });
              //  }
*/
            /*final SocketAddress address = new InetSocketAddress("192.168.30.202", 30000);
            final Socket clientSocket = new Socket();
            try {
                clientSocket.connect(address, 4000);
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < 15; i++) {
                System.out.println("count"+ i);
                        Thread thread = new Thread() {
                            @Override
                            public void run() {
                                try {
                                    JSONObject json = new JSONObject();
                                    json.put("count","Nasir");
                                            //System.out.println("sending json.toString());



                                    DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
                                    //BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                                    outToServer.writeBytes(json.toString() + '\n');
                                    outToServer.flush();
                                } catch(Exception ex) {
                                    ex.printStackTrace();
                                }


                            }

                        };
                thread.start();
            }
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            try {
                clientRoutine();
            } catch (Exception e) {
            }
            return null;
        }

        void clientRoutine() throws Exception {
            Socket clientSocket = null;
            clientSocket = new Socket("192.168.30.202", 30000);
            OutputStream os = clientSocket.getOutputStream();
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            clientSocket.setTcpNoDelay(true);
            for (int j = 1; j <50 ; j++) {
            //    os.write(hexStringToByteArray("0x23,0x23,0x23,0x23"));
               // os.write(0x23);
               /* os.write(0x23);
                os.write(0x23);*/
                byte arr1[]=null;

                if ((j%2)==0) {
                     arr1 = "GET/clientCGI".getBytes();
                }
                else {
                     arr1 = "Post/clientCGI".getBytes();
                }

                byte arr2[]={0x0b,0x02,0x0d,0x18};
                byte[] arr3= concatenateByteArrays(arr1,arr2);
                byte [] arr4 = "Sample text from Nasir's Mobile".getBytes();
                byte [] arr5 = concatenateByteArrays(arr3,arr4);
                byte arr6[]={0x23,0x23,0x23,0x23};
                byte [] string = concatenateByteArrays(arr5,arr6);
                os.write(string);

                os.flush();




                modifiedSentence = String.valueOf(inFromServer.read());
                if (modifiedSentence == null)
                    Log.i("Reply: ", "NULL");
                else
                    Log.i("Reply",modifiedSentence);

                Thread.sleep(1000);
               /* os.write(("abcde: " + 2).getBytes());
                os.flush();
                os.write(("abcde: " + 3).getBytes());
                os.flush();
                os.write(("abcde: " + 4).getBytes());
                os.flush();
                os.write(("abcde: " + 5).getBytes());
                os.flush();*/
            }

            //  outToServer.close();
            clientSocket.close();
        }



    }

    byte[] concatenateByteArrays(byte[] a, byte[] b) {
        byte[] result = new byte[a.length + b.length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

}
