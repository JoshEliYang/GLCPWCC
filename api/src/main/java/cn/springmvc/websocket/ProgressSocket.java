package cn.springmvc.websocket;

import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.alibaba.fastjson.JSON;

import cn.springmvc.model.TaskResponse;

@ServerEndpoint(value = "/progress")
public class ProgressSocket {

	// 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
	private static int onlineCount = 0;

	// concurrent包的线程安全Set，用来存放每个客户端对应的ProgressSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
	private static CopyOnWriteArraySet<ProgressSocket> webSocketSet = new CopyOnWriteArraySet<ProgressSocket>();

	// 与某个客户端的连接会话，需要通过它来给客户端发送数据
	private Session session;

	@OnOpen
	public void onOpen(Session session) {
		this.session = session;
		webSocketSet.add(this); // 加入set中
		addOnlineCount(); // 在线数加1
		System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
	}

	@OnClose
	public void onClose() {
		webSocketSet.remove(this); // 从set中删除
		subOnlineCount(); // 在线数减1
		System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
	}

	@OnMessage
	public void onMessage(String message, Session session) {
		System.out.println("来自客户端的消息:" + message);

		// 群发消息
		for (ProgressSocket item : webSocketSet) {
			try {
				item.sendMessage(message);
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
		}
	}

	@OnError
	public void onError(Session session, Throwable error) {
		System.out.println("发生错误");
		error.printStackTrace();
	}

	public void sendMessage(String message) throws IOException {
		this.session.getBasicRemote().sendText(message);
	}

	public static synchronized int getOnlineCount() {
		return onlineCount;
	}

	public static synchronized void addOnlineCount() {
		ProgressSocket.onlineCount++;
	}

	public static synchronized void subOnlineCount() {
		ProgressSocket.onlineCount--;
	}

	/**
	 * broadcast to all client
	 * 
	 * @param message
	 */
	public static synchronized void broadcast(TaskResponse response) {
		Iterator<ProgressSocket> socketIt = webSocketSet.iterator();
		while (socketIt.hasNext()) {
			ProgressSocket item = socketIt.next();
			try {
				item.sendMessage(JSON.toJSONString(response));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
