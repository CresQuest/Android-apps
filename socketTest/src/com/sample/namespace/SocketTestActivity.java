package com.sample.namespace;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;



import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SocketTestActivity extends Activity {
	EditText textOut;
	TextView textIn;
	 public boolean isServer = true;
	 ServerSocket socket1 = null;
	 Socket socket = null;
	 DataOutputStream dataOutputStream = null;
	 DataInputStream dataInputStream = null;
	 private ServerThread mSecureAcceptThread;
	 Button buttonSend;
	 Button setServer;
	 Button connect;
	 
	 private ConnectThread mConnectThread;
	 /** Called when the activity is first created. */
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
	     super.onCreate(savedInstanceState);
	     setContentView(R.layout.main);
	  
	     textOut = (EditText)findViewById(R.id.textout);
	     buttonSend = (Button)findViewById(R.id.send);
	     textIn = (TextView)findViewById(R.id.textin);
	     buttonSend.setOnClickListener(buttonSendOnClickListener);
	     
	     setServer = (Button)findViewById(R.id.set_server);
	     setServer.setOnClickListener(buttonSendOnClickListener);
	     
	     connect = (Button)findViewById(R.id.connect_server);
	     connect.setOnClickListener(buttonSendOnClickListener);
	 }
	 Button.OnClickListener buttonSendOnClickListener
	 = new Button.OnClickListener(){

	public void onClick(View arg0) {
	 // TODO Auto-generated method stub
		 mSecureAcceptThread = new ServerThread();
		  mSecureAcceptThread.start();
	}};
	 Button.OnClickListener buttonSetServerClickListener
	 = new Button.OnClickListener(){

	public void onClick(View arg0) {
	 // TODO Auto-generated method stub
	 
	if(arg0 == buttonSend)
	{
	 try {
			 WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
			 List<WifiConfiguration> networks = wifiManager.getConfiguredNetworks();
			 int count = networks.size();
			 
			 
			 WifiInfo wifiInfo = wifiManager.getConnectionInfo();
			 int ipAddress = wifiInfo.getIpAddress();
			 WifiInfo info;
			 String ip_str1 = String.format("%d.%d.%d.%d",
					 (ipAddress & 0xff),
					 (ipAddress >> 8 & 0xff),
					 (ipAddress >> 16 & 0xff),
					 (ipAddress >> 24 & 0xff));
			 TextView ip_get = (TextView)findViewById(R.id.textin);
			 ip_get.setText(ip_str1);
		  
		  
	 }
	 finally{
		  if (socket != null){
		   try {
		    socket.close();
		   } catch (IOException e) {
		    // TODO Auto-generated catch block
			   e.printStackTrace();
		   }
		  }
	
		 }
	}
	
	 }
	 };
	public static String getLocalIpAddressString() {
		 try {
		     for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
		         NetworkInterface intf = en.nextElement();
		         for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
		             InetAddress inetAddress = enumIpAddr.nextElement();
		             if (!inetAddress.isLoopbackAddress()) {
		                 return inetAddress.getHostAddress().toString();
		             }
		         }
		     }
		 } catch (SocketException ex) {
		     Log.e("IPADDRESS", ex.toString());
		 }
		 return null;
}
	
	
	
	  /**
     * This thread runs while listening for incoming connections. It behaves
     * like a server-side client. It runs until a connection is accepted
     * (or until cancelled).
     */
    private class ServerThread extends Thread {
        // The local server socket
        private  ServerSocket mmServerSocket;
        public boolean cancel = false;

        public ServerThread() {
        	 EditText port = (EditText)findViewById(R.id.port);
        
        	try {
				mmServerSocket = new ServerSocket(Integer.parseInt(port.getText().toString()));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

        public void run() {
          
            Socket socket = null;
        	
            // Listen to the server socket if we're not connected
            while (!cancel) {
                try {
                    // This is a blocking call and will only return on a
                    // successful connection or an exception
                    socket = mmServerSocket.accept();
                } catch (IOException e) {
                  
                }

                // If a connection was accepted
                if (socket != null) {
                   Toast.makeText(getApplicationContext(), "Connection accepted", 100).show();
                    }
                }
            }
            

        }
    /**
     * This thread runs while listening for incoming connections. It behaves
     * like a server-side client. It runs until a connection is accepted
     * (or until cancelled).
     */
    private class ConnectThread extends Thread {
        // The local server socket
        private  Socket mmSocket;
        public boolean cancel = false;

        public ConnectThread() {
        	 EditText port = (EditText)findViewById(R.id.port);
        
        	try {
        		mmSocket = new Socket("192.168.43.27",8888);
        		Toast.makeText(getApplicationContext(), "Client socket created", 100).show();
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

        public void run() {
          
         if(mmSocket!= null)
         {
        	 try {
				mmSocket.connect(mmSocket.getRemoteSocketAddress());
				Toast.makeText(getApplicationContext(), "Client socket connected to server", 100).show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
         }
        }
            

        }
}