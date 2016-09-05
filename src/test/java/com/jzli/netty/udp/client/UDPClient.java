package com.jzli.netty.udp.client;

import com.jzli.bean.User;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.LinkedList;
import java.util.List;

/**
 * =======================================================
 *
 * @Company 金色家网络科技有限公司-云存储业务部
 * @Date ：2016/9/5
 * @Author ：li.jinzhao
 * @Version ：0.0.1
 * @Description ：
 * ========================================================
 */
public class UDPClient {
    public static void main(String[] args) throws Exception {
        new UDPClient().test();
    }

    public void test() throws Exception {
        // 创建发送方的套接字，IP默认为本地，端口号随机
        DatagramSocket sendSocket = new DatagramSocket();

        // 确定要发送的消息：
        User user = new User(11, "李金钊");
        List<User> list = new LinkedList<>();
        list.add(user);
        Object obj = list;

        byte[] buf = object2Byte(obj);

        // 确定发送方的IP地址及端口号，地址为本地机器地址
        int port = 9999;
        InetAddress ip = InetAddress.getLocalHost();

        // 创建发送类型的数据报：
        DatagramPacket sendPacket = new DatagramPacket(buf, buf.length, ip,
                port);

        // 通过套接字发送数据：
        sendSocket.send(sendPacket);
        sendSocket.close();
    }


    private byte[] object2Byte(Object obj) throws Exception {
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        byte[] bytes = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            oos.flush();
            bytes = baos.toByteArray();
        } finally {
            if (oos != null) {
                oos.close();
            }

            if (baos != null) {
                baos.close();
            }
        }
        return bytes;
    }
}
