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
	//결과값을 출력할 EditText

	EditText editText;

	String xml; //다운로드된 xml문서



	    @Override

	    public void onCreate(Bundle savedInstanceState) {

	    super.onCreate(savedInstanceState);

	        setContentView(R.layout.activity_main);

	        editText = (EditText)findViewById(R.id.imageView1);

	        

	    }

	    //버튼을 눌렀을때 실행되는 메소드 

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

	    //서버에서 읽어오기 위한 스트림 객체

	    InputStreamReader isr = new InputStreamReader(conn.getInputStream());

	    //줄단위로 읽어오기 위해 BufferReader로 감싼다.

	    BufferedReader br = new BufferedReader(isr);

	    //반복문 돌면서읽어오기

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

	    //결과값 출력해보기

	    //editText.setText(sBuffer.toString());

	    xml = sBuffer.toString(); //결과값 변수에 담기

	    }catch (Exception e) {

	// TODO: handle exception

	    Log.e("다운로드 중 에러 발생",e.getMessage());

	    

	}

	    parse();

	    

	    }

	    

	    //xml파싱하는 메소드

	    public void parse(){

	    try{

	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

	    DocumentBuilder documentBuilder = factory.newDocumentBuilder();

	    //xml을 InputStream형태로 변환

	    InputStream is = new ByteArrayInputStream(xml.getBytes());

	    //document와 element 는 w3c dom에 있는것을 임포트 한다.

	    Document doc = documentBuilder.parse(is);

	    Element element = doc.getDocumentElement();

	    //읽어올 태그명 정하기

	    NodeList items = element.getElementsByTagName("tmx");

	    //읽어온 자료의 수

	    int n = items.getLength();

	    //자료를 누적시킬 stringBuffer 객체

	    StringBuffer sBuffer = new StringBuffer();

	    //반복문을 돌면서 모든 데이터 읽어오기

	    for(int i=0 ; i < n ; i++){

	    //읽어온 자료에서 알고 싶은 문자열의 인덱스 번호를 전달한다.

	    Node item = items.item(i);

	    Node text = item.getFirstChild();

	    //해당 노드에서 문자열 읽어오기

	    String itemValue = text.getNodeValue();

	    sBuffer.append("최대온도:"+itemValue+"\r\n");

	    

	     }

	    //읽어온 문자열 출력해보기

	    editText.setText(sBuffer.toString());

	    

	    }catch (Exception e) {

	// TODO: handle exception

	    Log.e("파싱 중 에러 발생", e.getMessage());

	}

	    }

	
	
}
