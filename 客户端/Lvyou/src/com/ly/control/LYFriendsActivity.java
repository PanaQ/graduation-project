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
		Thread t1= new Thread(r3);//  朋友线程
		Thread t2= new Thread(r4); //关注者线程
		Thread t3= new Thread(r1);//我的好友申请
		t1.start();
		t2.start();
		t3.start();
		
		
	   elvExpandableList
	   View = (ExpandableListView) findViewById(R.id.list);
		// 创建一级条目
		groups = new ArrayList<Map<String, String>>();
		// 创建两个一级条目标题
		Map<String, String> group1 = new HashMap<String, String>();
		Map<String, String> group2 = new HashMap<String, String>();
		Map<String, String> group3 = new HashMap<String, String>();
		group1.put("group", "我的好友申请");
		group2.put("group", "我的好友");
		group3.put("group", "我关注的人");
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
		 * 使用SimpleExpandableListAdapter显示ExpandableListView 
		 * 参数1.上下文对象Context
		 * 参数2.一级条目目录集合 
		 * 参数3.一级条目对应的布局文件 
		 * 参数4.fromto，就是map中的key，指定要显示的对象
		 * 参数5.与参数4对应，指定要显示在groups中的id 
		 * 参数6.二级条目目录集合 
		 * 参数7.二级条目对应的布局文件
		 * 参数8.fromto，就是map中的key，指定要显示的对象 
		 * 参数9.与参数8对应，指定要显示在childs中的id
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
		// 如果类型是子元素
		if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
			// 组index
			int groupPos = ExpandableListView.getPackedPositionGroup(info.packedPosition);
			/* 
			 * 第一个int类型的group ID参数，代表的是组概念，你可以将几个菜单项归为一组，以便更好的以组的方式管理你的菜单按钮。
			 * 第二个int类型的item ID参数，代表的是项目编号。
			 * 这个参数非常重要，一个item ID对应一个menu中的选项。在后面使用菜单的时候，就靠这个item ID来判断你使用的是哪个选项。
			 * 第三个int类型的order ID参数，代表的是菜单项的显示顺序。默认是0，表示菜单的显示顺序就是按照add的显示顺序来显示。
			 * 第四个String类型的title参数，表示选项中显示的文字。
			*/
	
			switch (groupPos) {
			// 好友申请组
			case 0:
				menu.setHeaderTitle("是否同意申请");
				menu.add(0, 0, 0, "同意");
				menu.add(0, 1, 0, "不同意");
				break;

			// 好友组
			case 1:
				menu.setHeaderTitle("好友菜单");
				menu.add(0, 0, 0, "查看好友的所有记忆");
				menu.add(0, 1, 0, "给好友发送消息");
				menu.add(0, 2, 0, "查看好友资料");
				break;

			// 关注者组
			case 2:
				menu.setHeaderTitle("关注者菜单");
				menu.add(0, 0, 0, "查看关注者的所有记忆");
				break;
			}

		}

		super.onCreateContextMenu(menu, v, menuInfo);

	}

	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		// 获取可收缩列表的附加信息
		ExpandableListContextMenuInfo info = (ExpandableListContextMenuInfo) item.getMenuInfo();

		// 获取类型（在组标签上展现菜单，还是在子标签上展现菜单）
		int type = ExpandableListView.getPackedPositionType(info.packedPosition);
		// 如果在子标签上展现菜单
		if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
			
			// 获取组Index
			int groupPos = ExpandableListView.getPackedPositionGroup(info.packedPosition);
			// 获取子元素在当前组的Index
			int childPos = ExpandableListView.getPackedPositionChild(info.packedPosition);

			// 判定组别
			switch (groupPos) {

	// 好友申请
			case 0:
				switch (item.getItemId()) {
				
				case 0:// 同意加我为好友
					Toast.makeText(this, "同意加为好友 ", Toast.LENGTH_SHORT).show();
					aid = list2.get(item.getItemId())[0];
					ueid = list2.get(item.getItemId())[1];
					Thread t6 = new Thread(r2);
					t6.start();
					System.out.println("t6执行结束");
					resetms();//处理过后重置列表
					break;
					
				case 1:// 不同意加我为好友
					Toast.makeText(this, "不同意加为好友 ", Toast.LENGTH_SHORT).show();
					fid = list1.get(item.getItemId()-1)[0];
					aid = list2.get(item.getItemId()-1)[0];
					Thread t7 = new Thread(r6);
					t7.start();	
					resetms();//处理过后重置列表
					break;
				}
				break;
	// 好友组	
			case 1:
				switch (item.getItemId()) {
				case 0:// 查看所有记忆
					Toast.makeText(this, "查看好友记忆 ", Toast.LENGTH_SHORT).show();
					ueid = list.get(childPos)[3];
					Intent intent =new Intent(LYFriendsActivity.this,LYFriendsMeActivity.class); 
					intent.putExtra("ueid", ueid);
					intent.putExtra("result", id);
					startActivity(intent);
					break;
				case 1:// 发送消息
					Toast.makeText(this, "发送消息", Toast.LENGTH_LONG).show();
					ueid = list.get(childPos)[3];
					Intent intent2 =new Intent(LYFriendsActivity.this,LYSendInfoActivity.class); 
					intent2.putExtra("ueid", ueid);
					intent2.putExtra("result", id);
					startActivity(intent2);
					break;
					
				case 2:// 查看资料
					Toast.makeText(this, "查看好友资料", Toast.LENGTH_LONG).show();
					ueid = list.get(childPos)[3];
					Intent intent3 =new Intent(LYFriendsActivity.this,LYFriendsInfoActivity.class); 
					intent3.putExtra("ueid", ueid);
					intent3.putExtra("result", id);
					startActivity(intent3);
					break;
				}

				break;
// 关注者组
			case 2:
				// 查看关注者消息
				Toast.makeText(this, "查看关注者记忆", Toast.LENGTH_SHORT).show();
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
	
	
	
	//我的好友申请	  
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
							  childdata.put("child","用户"+list2.get(i)[4]+"想申请您加为好友!" );
							  child1.add(childdata);
							 }
							  childs.add(child1);
							 } 
						  };
					  };
					  
					  
	//同意加好友了===删除申请		  
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
	
	
//  朋友线程
Runnable r3 = new Runnable() {
		public void run() {
			try {
				URL url = new URL("http://192.168.21.12:8888/Ly/FriendListServlet");
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setDoInput(true);
				con.setDoOutput(true);
				con.setRequestProperty("Content-Type", "text/xml");  
				con.setRequestMethod("POST");
				con.connect();//开始连接请求
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
					String aa = bufferedReader.readLine();//获取到 进行解析
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
				 Toast.makeText(getApplicationContext(), "未登录", Toast.LENGTH_SHORT).show();
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
	
	  //  关注者线程
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

	

						
//不同意加对方为好友，进行删除
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
							
	/////处理过后重置列表		
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
										System.out.println("进行列表重置");
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
