package com.example.sss;
//test
import android.app.Activity;
import java.io.BufferedReader;




import java.io.ByteArrayInputStream;

import java.io.InputStream;

import java.io.InputStreamReader;

import java.net.HttpURLConnection;

import java.net.URL;




import javax.xml.parsers.DocumentBuilder;

import javax.xml.parsers.DocumentBuilderFactory;




import org.w3c.dom.Document;

import org.w3c.dom.Element;

import org.w3c.dom.Node;

import org.w3c.dom.NodeList;




import android.os.Bundle;

import android.util.Log;

import android.view.View;

import android.widget.EditText;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	protected void onCreate1(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		startActivity(new Intent(this,Splash.class));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	//������� ����� EditText

	EditText editText;

	String xml; //�ٿ�ε�� xml����



	    @Override

	    public void onCreate(Bundle savedInstanceState) {

	    super.onCreate(savedInstanceState);

	        setContentView(R.layout.activity_main);

	        editText = (EditText)findViewById(R.id.imageView1);

	        

	    }

	    //��ư�� �������� ����Ǵ� �޼ҵ� 

	    public void down(View v){

	    StringBuffer sBuffer = new StringBuffer();

	    try{

	    String urlAddr = " http://www.kma.go.kr/weather/forecast/mid-term-xml.jsp?stnId=109 ";

	    //String urlAddr = "http://naver.com";

	    URL url = new URL(urlAddr);

	    HttpURLConnection conn = (HttpURLConnection)url.openConnection();

	    if(conn != null){

	    conn.setConnectTimeout(20000);

	    conn.setUseCaches(false);

	    if(conn.getResponseCode()==HttpURLConnection.HTTP_OK){

	    //�������� �о���� ���� ��Ʈ�� ��ü

	    InputStreamReader isr = new InputStreamReader(conn.getInputStream());

	    //�ٴ����� �о���� ���� BufferReader�� ���Ѵ�.

	    BufferedReader br = new BufferedReader(isr);

	    //�ݺ��� ���鼭�о����

	    while(true){

	    String line = br.readLine();

	    if(line==null){

	    break;

	    }

	    sBuffer.append(line);

	    }

	    br.close();

	    conn.disconnect();

	    }

	    }

	    //����� ����غ���

	    //editText.setText(sBuffer.toString());

	    xml = sBuffer.toString(); //����� ������ ���

	    }catch (Exception e) {

	// TODO: handle exception

	    Log.e("�ٿ�ε� �� ���� �߻�",e.getMessage());

	    

	}

	    parse();

	    

	    }

	    

	    //xml�Ľ��ϴ� �޼ҵ�

	    public void parse(){

	    try{

	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

	    DocumentBuilder documentBuilder = factory.newDocumentBuilder();

	    //xml�� InputStream���·� ��ȯ

	    InputStream is = new ByteArrayInputStream(xml.getBytes());

	    //document�� element �� w3c dom�� �ִ°��� ����Ʈ �Ѵ�.

	    Document doc = documentBuilder.parse(is);

	    Element element = doc.getDocumentElement();

	    //�о�� �±׸� ���ϱ�

	    NodeList items = element.getElementsByTagName("tmx");

	    //�о�� �ڷ��� ��

	    int n = items.getLength();

	    //�ڷḦ ������ų stringBuffer ��ü

	    StringBuffer sBuffer = new StringBuffer();

	    //�ݺ����� ���鼭 ��� ������ �о����

	    for(int i=0 ; i < n ; i++){

	    //�о�� �ڷῡ�� �˰� ���� ���ڿ��� �ε��� ��ȣ�� �����Ѵ�.

	    Node item = items.item(i);

	    Node text = item.getFirstChild();

	    //�ش� ��忡�� ���ڿ� �о����

	    String itemValue = text.getNodeValue();

	    sBuffer.append("�ִ�µ�:"+itemValue+"\r\n");

	    

	     }

	    //�о�� ���ڿ� ����غ���

	    editText.setText(sBuffer.toString());

	    

	    }catch (Exception e) {

	// TODO: handle exception

	    Log.e("�Ľ� �� ���� �߻�", e.getMessage());

	}

	    }

	
	
}
