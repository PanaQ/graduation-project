package com.ly.control;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ly.control.R;
import com.ly.bean.ApplyBean;
import com.ly.bean.AttentionBean;
import com.ly.bean.FriendListBean;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.ExpandableListView.ExpandableListContextMenuInfo;
import android.widget.Toast;


public class LYFriendsActivity extends Activity {
	private	HashMap<String,Object> map;
	private ArrayList<String[]> list;
	private ArrayList<String[]> lista;
	private ArrayList<String[]> list2;
	private ArrayList<String[]> listb;
	private ArrayList<String[]> list1;
	private ArrayList<String[]> listc;
	private String ueid;
	private String aid;
	private String fid;
	List<Map<String, String>> child1,child2,child3;
	
	List<List<Map<String, String>>> childs;
	SimpleExpandableListAdapter adapter;
	List<Map<String, String>> groups;
	ExpandableListView elvExpandableListView;
	private String id;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friends);
		
		
		id=getIntent().getStringExtra("result");
		Thread t1= new Thread(r3);//  �����߳�
		Thread t2= new Thread(r4); //��ע���߳�
		Thread t3= new Thread(r1);//�ҵĺ�������
		t1.start();
		t2.start();
		t3.start();
		
		
	   elvExpandableList
	   View = (ExpandableListView) findViewById(R.id.list);
		// ����һ����Ŀ
		groups = new ArrayList<Map<String, String>>();
		// ��������һ����Ŀ����
		Map<String, String> group1 = new HashMap<String, String>();
		Map<String, String> group2 = new HashMap<String, String>();
		Map<String, String> group3 = new HashMap<String, String>();
		group1.put("group", "�ҵĺ�������");
		group2.put("group", "�ҵĺ���");
		group3.put("group", "�ҹ�ע����");
		groups.add(group1);
		groups.add(group2);
		groups.add(group3);
		child1 = new ArrayList<Map<String, String>>();
		child2 = new ArrayList<Map<String, String>>();  
		child3 = new ArrayList<Map<String, String>>();    
		
		childs = new ArrayList<List<Map<String, String>>>();
		childs.add(child1);
		childs.add(child2);
		childs.add(child3);


		/**
		 * ʹ��SimpleExpandableListAdapter��ʾExpandableListView 
		 * ����1.�����Ķ���Context
		 * ����2.һ����ĿĿ¼���� 
		 * ����3.һ����Ŀ��Ӧ�Ĳ����ļ� 
		 * ����4.fromto������map�е�key��ָ��Ҫ��ʾ�Ķ���
		 * ����5.�����4��Ӧ��ָ��Ҫ��ʾ��groups�е�id 
		 * ����6.������ĿĿ¼���� 
		 * ����7.������Ŀ��Ӧ�Ĳ����ļ�
		 * ����8.fromto������map�е�key��ָ��Ҫ��ʾ�Ķ��� 
		 * ����9.�����8��Ӧ��ָ��Ҫ��ʾ��childs�е�id
		 */
	     adapter = new SimpleExpandableListAdapter(
					this, groups, R.layout.groups, new String[] { "group" },
					new int[] { R.id.group }, childs, R.layout.childs,
					new String[] { "child" }, new int[] { R.id.child });
	     elvExpandableListView.setAdapter(adapter);
		 registerForContextMenu(elvExpandableListView);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		ExpandableListContextMenuInfo info = (ExpandableListContextMenuInfo) menuInfo;
		int type = ExpandableListView.getPackedPositionType(info.packedPosition);
		// �����������Ԫ��
		if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
			// ��index
			int groupPos = ExpandableListView.getPackedPositionGroup(info.packedPosition);
			/* 
			 * ��һ��int���͵�group ID���������������������Խ������˵����Ϊһ�飬�Ա���õ�����ķ�ʽ������Ĳ˵���ť��
			 * �ڶ���int���͵�item ID���������������Ŀ��š�
			 * ��������ǳ���Ҫ��һ��item ID��Ӧһ��menu�е�ѡ��ں���ʹ�ò˵���ʱ�򣬾Ϳ����item ID���ж���ʹ�õ����ĸ�ѡ�
			 * ������int���͵�order ID������������ǲ˵������ʾ˳��Ĭ����0����ʾ�˵�����ʾ˳����ǰ���add����ʾ˳������ʾ��
			 * ���ĸ�String���͵�title��������ʾѡ������ʾ�����֡�
			*/
	
			switch (groupPos) {
			// ����������
			case 0:
				menu.setHeaderTitle("�Ƿ�ͬ������");
				menu.add(0, 0, 0, "ͬ��");
				menu.add(0, 1, 0, "��ͬ��");
				break;

			// ������
			case 1:
				menu.setHeaderTitle("���Ѳ˵�");
				menu.add(0, 0, 0, "�鿴���ѵ����м���");
				menu.add(0, 1, 0, "�����ѷ�����Ϣ");
				menu.add(0, 2, 0, "�鿴��������");
				break;

			// ��ע����
			case 2:
				menu.setHeaderTitle("��ע�߲˵�");
				menu.add(0, 0, 0, "�鿴��ע�ߵ����м���");
				break;
			}

		}

		super.onCreateContextMenu(menu, v, menuInfo);

	}

	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		// ��ȡ�������б�ĸ�����Ϣ
		ExpandableListContextMenuInfo info = (ExpandableListContextMenuInfo) item.getMenuInfo();

		// ��ȡ���ͣ������ǩ��չ�ֲ˵����������ӱ�ǩ��չ�ֲ˵���
		int type = ExpandableListView.getPackedPositionType(info.packedPosition);
		// ������ӱ�ǩ��չ�ֲ˵�
		if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
			
			// ��ȡ��Index
			int groupPos = ExpandableListView.getPackedPositionGroup(info.packedPosition);
			// ��ȡ��Ԫ���ڵ�ǰ���Index
			int childPos = ExpandableListView.getPackedPositionChild(info.packedPosition);

			// �ж����
			switch (groupPos) {

	// ��������
			case 0:
				switch (item.getItemId()) {
				
				case 0:// ͬ�����Ϊ����
					Toast.makeText(this, "ͬ���Ϊ���� ", Toast.LENGTH_SHORT).show();
					aid = list2.get(item.getItemId())[0];
					ueid = list2.get(item.getItemId())[1];
					Thread t6 = new Thread(r2);
					t6.start();
					System.out.println("t6ִ�н���");
					resetms();//������������б�
					break;
					
				case 1:// ��ͬ�����Ϊ����
					Toast.makeText(this, "��ͬ���Ϊ���� ", Toast.LENGTH_SHORT).show();
					fid = list1.get(item.getItemId()-1)[0];
					aid = list2.get(item.getItemId()-1)[0];
					Thread t7 = new Thread(r6);
					t7.start();	
					resetms();//������������б�
					break;
				}
				break;
	// ������	
			case 1:
				switch (item.getItemId()) {
				case 0:// �鿴���м���
					Toast.makeText(this, "�鿴���Ѽ��� ", Toast.LENGTH_SHORT).show();
					ueid = list.get(childPos)[3];
					Intent intent =new Intent(LYFriendsActivity.this,LYFriendsMeActivity.class); 
					intent.putExtra("ueid", ueid);
					intent.putExtra("result", id);
					startActivity(intent);
					break;
				case 1:// ������Ϣ
					Toast.makeText(this, "������Ϣ", Toast.LENGTH_LONG).show();
					ueid = list.get(childPos)[3];
					Intent intent2 =new Intent(LYFriendsActivity.this,LYSendInfoActivity.class); 
					intent2.putExtra("ueid", ueid);
					intent2.putExtra("result", id);
					startActivity(intent2);
					break;
					
				case 2:// �鿴����
					Toast.makeText(this, "�鿴��������", Toast.LENGTH_LONG).show();
					ueid = list.get(childPos)[3];
					Intent intent3 =new Intent(LYFriendsActivity.this,LYFriendsInfoActivity.class); 
					intent3.putExtra("ueid", ueid);
					intent3.putExtra("result", id);
					startActivity(intent3);
					break;
				}

				break;
// ��ע����
			case 2:
				// �鿴��ע����Ϣ
				Toast.makeText(this, "�鿴��ע�߼���", Toast.LENGTH_SHORT).show();
				ueid = list.get(childPos)[3];
				Intent intent =new Intent(LYFriendsActivity.this,LYFriendsMeActivity.class); 
				intent.putExtra("ueid", ueid);
				intent.putExtra("result", id);
				startActivity(intent);
				break;
			}
			return true;
		}

		return false;
	}
	
	
	
	//�ҵĺ�������	  
	 Runnable r1 = new Runnable() {
						public void run() {
							try {
								URL url = new URL("http://192.168.21.12:8888/Ly/LYApplyServlet");
								HttpURLConnection con = (HttpURLConnection) url.openConnection();
								con.setDoInput(true);
								con.setDoOutput(true);
								con.setRequestMethod("POST");
								con.setRequestProperty("Content-Type", "text/xml");  							
								OutputStream out= con.getOutputStream();
								StringBuilder sb = new StringBuilder();
								sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"); 
								sb.append("<user>");
								sb.append("<uid>");					
								sb.append(id);
								sb.append("</uid>");
								sb.append("</user>");
								byte userXML[] = sb.toString().getBytes();
								out.write(userXML);
								
								if(con.getResponseCode()==HttpURLConnection.HTTP_OK)
								{
									InputStream in =con.getInputStream();
									ApplyBean flb= new ApplyBean();
									listb = flb.applys(in);
									Message msg = new Message();
									msg.obj=listb;
									handler1.sendMessage(msg);
								}

							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					};
					
					Handler  handler1=new  Handler()
					  {
						public void handleMessage(android.os.Message msg) {
							  child1.clear();
							  list2 =(ArrayList<String[]>) msg.obj;
							 Map<String, String> childdata =null;
							 if(list2.size()==0){}
							 else
							 {
							 for(int i=0;i<list2.size();i++){
							  childdata = new HashMap<String, String>();
							  childdata.put("child","�û�"+list2.get(i)[4]+"����������Ϊ����!" );
							  child1.add(childdata);
							 }
							  childs.add(child1);
							 } 
						  };
					  };
					  
					  
	//ͬ��Ӻ�����===ɾ������		  
	 Runnable r2 = new Runnable() {
							public void run() {
								try {
									URL url = new URL("http://192.168.21.12:8888/Ly/DeleteApplyServlet");
									HttpURLConnection con = (HttpURLConnection) url.openConnection();
									con.setDoInput(true);
									con.setDoOutput(true);
									con.setRequestMethod("POST");
									con.setRequestProperty("Content-Type", "text/xml");  
									OutputStream out= con.getOutputStream();
									StringBuilder sb = new StringBuilder();
									sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"); 
									sb.append("<user>");
									sb.append("<uid>");					
									sb.append(aid);
									sb.append("</uid>");
									sb.append("<hostid>");					
									sb.append(id);
									sb.append("</hostid>");
									sb.append("<otherid>");					
									sb.append(ueid);
									sb.append("</otherid>");
									sb.append("</user>");
									byte userXML[] = sb.toString().getBytes();
									out.write(userXML);
									if(con.getResponseCode()==HttpURLConnection.HTTP_OK)
									{
										
									}
								} catch (Exception e) {
									e.printStackTrace();
								}	
							}
						};
	
	
//  �����߳�
Runnable r3 = new Runnable() {
		public void run() {
			try {
				URL url = new URL("http://192.168.21.12:8888/Ly/FriendListServlet");
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setDoInput(true);
				con.setDoOutput(true);
				con.setRequestProperty("Content-Type", "text/xml");  
				con.setRequestMethod("POST");
				con.connect();//��ʼ��������
				OutputStream out= con.getOutputStream();
				StringBuilder sb = new StringBuilder();
				sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"); 
				sb.append("<user>");
				sb.append("<uid>");					
				sb.append(id);
				sb.append("</uid>");
				sb.append("</user>");
				
				byte userXML[] = sb.toString().getBytes();
				out.write(userXML);
				
				if(con.getResponseCode()==HttpURLConnection.HTTP_OK)
				{
					InputStream in =con.getInputStream();
					InputStreamReader read = new InputStreamReader(in);//
					BufferedReader bufferedReader = new BufferedReader(read);//
					String aa = bufferedReader.readLine();//��ȡ�� ���н���
					FriendListBean flb= new FriendListBean();
					list = flb.friendlist(aa);
					Message msg = new Message();
					msg.obj=list;
					h.sendMessage(msg);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	};
	Handler  h=new  Handler()
	  {
		 @SuppressWarnings("unchecked")
		public void handleMessage(Message msg) {
			
			 list1 =(ArrayList<String[]>) msg.obj;
			 Map<String, String> childdata =null;
			 if(list1.size()==0&&id==null){
				 Toast.makeText(getApplicationContext(), "δ��¼", Toast.LENGTH_SHORT).show();
			 }else{
				 for(int i=0;i<list1.size();i++){
					 childdata = new HashMap<String, String>();
					 childdata.put("child",list1.get(i)[4] );
					 child2.add(childdata);
					 }
				 for(int i=0;i<child2.size();i++){
						System.out.println( "===="+child2.get(i).get("child"));
					 }
					 childs.add(child2);
			 }
		  };
	  };
	
	  //  ��ע���߳�
	  Runnable r4 = new Runnable() {	public void run() {
				try {
					URL url = new URL("http://192.168.21.12:8888/Ly/AttentionServlet");
					HttpURLConnection con = (HttpURLConnection) url.openConnection();
					con.setDoInput(true);
					con.setDoOutput(true);
					con.setRequestMethod("POST");
					con.setRequestProperty("Content-Type", "text/xml");  
					OutputStream out= con.getOutputStream();
					StringBuilder sb = new StringBuilder();
					sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"); 
					sb.append("<user>");
					sb.append("<uid>");					
					sb.append(id);
					sb.append("</uid>");
					sb.append("</user>");
					
					byte userXML[] = sb.toString().getBytes();
					out.write(userXML);
					if(con.getResponseCode()==HttpURLConnection.HTTP_OK)
					{
						InputStream in =con.getInputStream();
						AttentionBean atb= new AttentionBean();
						lista = atb.attention(in);
						Message msg = new Message();
						msg.obj=lista;
						h4.sendMessage(msg);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		};
		
		
		Handler  h4=new  Handler()
		  {
			  public void handleMessage(android.os.Message msg) {
				 lista =(ArrayList<String[]>) msg.obj;
				 Map<String, String> childdata =null;
				 if(lista.size()==0){}
				 else{
				 for(int i=0;i<lista.size();i++){
				  childdata = new HashMap<String, String>();
				  childdata.put("child",lista.get(i)[4] );
				  child3.add(childdata);
				 }
				 for(int i=0;i<child3.size();i++){
					System.out.println( "===="+child3.get(i).get("child"));
				 }
				 childs.add(child3);
				 }
				 
			  };
		  };

	

						
//��ͬ��ӶԷ�Ϊ���ѣ�����ɾ��
	Runnable r6 = new Runnable() {
						public void run() {
									try {
										URL url = new URL("http://192.168.21.12:8888/Ly/DeleteFriendsServlet");
										HttpURLConnection con = (HttpURLConnection) url.openConnection();
										con.setDoInput(true);
										con.setDoOutput(true);
										con.setRequestMethod("POST");
										con.setRequestProperty("Content-Type", "text/xml");  
										OutputStream out= con.getOutputStream();
										StringBuilder sb = new StringBuilder();
										
										sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"); 
										sb.append("<user>");
										sb.append("<uid>");					
										sb.append(aid);
										sb.append("</uid>");
										sb.append("<fid>");					
										sb.append(fid);
										sb.append("</fid>");
										sb.append("</user>");
										
										byte userXML[] = sb.toString().getBytes();
										out.write(userXML);
										if(con.getResponseCode()==HttpURLConnection.HTTP_OK)
										{
											InputStream in =con.getInputStream();
										}
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							};
							
	/////������������б�		
							private void resetms() {
								try {
									URL url = new URL("http://192.168.21.12:8888/Ly/LYApplyServlet");
									HttpURLConnection con = (HttpURLConnection) url.openConnection();
									con.setDoInput(true);
									con.setDoOutput(true);
									con.setRequestMethod("POST");
									con.setRequestProperty("Content-Type", "text/xml");  
									OutputStream out= con.getOutputStream();
									StringBuilder sb = new StringBuilder();
									sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"); 
									sb.append("<user>");
									sb.append("<uid>");					
									sb.append(id);
									sb.append("</uid>");
									sb.append("</user>");
									
									byte userXML[] = sb.toString().getBytes();
									out.write(userXML);
									if(con.getResponseCode()==HttpURLConnection.HTTP_OK)
									{
										System.out.println("�����б�����");
										InputStream in =con.getInputStream();
									
										ApplyBean flb= new ApplyBean();
										
										listb = flb.applys(in);
										Message msg = new Message();
										msg.obj=listb;
										handler1.sendMessage(msg);
									}

								} catch (Exception e) {
									e.printStackTrace();
								}
							}
}
