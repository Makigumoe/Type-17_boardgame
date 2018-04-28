package CodeGaming;

import com.alibaba.fastjson.JSON;
import org.msgpack.MessagePack;

import java.io.IOException;

public class json_test {

    public static void main(String args[]) throws IOException{
        int iter=1000000;
        empty_class ec = new empty_class();
        ec.name="hhh";
        long tmr_start=System.nanoTime();
        for (int i = 0; i < iter; i++) {
            String s = JSON.toJSONString(ec);
        }
        long tmr_end=System.nanoTime();
        System.out.println("FastJson:"+(tmr_end-tmr_start)/1000000000.0+"s");
        tmr_start=System.nanoTime();
        MessagePack msgP=new MessagePack();
        msgP.register(empty_class.class);
        for (int i = 0; i < iter; i++) {
            byte[] raw=msgP.write(ec);
        }
        tmr_end=System.nanoTime();
        System.out.println("MsgPack:"+(tmr_end-tmr_start)/1000000000.0+"s");
        System.out.println(JSON.toJSONString(ec).length());
        System.out.println(msgP.write(ec).length);
    }

}
