package com.jzli.netty.udp.handler;

import com.jzli.bean.User;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
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
public class UDPServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {


    protected void messageReceived(ChannelHandlerContext ctx,
                                   DatagramPacket packet) throws Exception {

        ByteBuf buf = packet.copy().content();
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);

        Object o = bytes2Object(req);

        if (o instanceof List) {
            List list = (List) o;
            for (Object obj : list) {
                if (obj instanceof User) {
                    User user = (User) obj;
                    System.out.println(user.getId() + "\t" + user.getName());
                }
            }
        }

//        String body = new String(req, "UTF-8");
//        System.out.println(body);

    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
    }

    private Object bytes2Object(byte[] bytes) throws Exception {
        Object obj = null;
        ObjectInputStream ois = null;
        ByteArrayInputStream bis = null;
        try {
            bis = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bis);
            obj = ois.readObject();
        } finally {
            if (ois != null) {
                ois.close();
            }

            if (bis != null) {
                bis.close();
            }
        }
        return obj;
    }
}
