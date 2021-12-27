package response;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import marshalling.MarshallingCodeFactory;
import org.junit.Test;

import static org.junit.Assert.*;

public class LoginResponseTest {

    @Test
    public void encoderTest(){
        LoginResponse msg = new LoginResponse(1);
        EmbeddedChannel embeddedChannel = new EmbeddedChannel();
        embeddedChannel.pipeline().addLast(MarshallingCodeFactory.buildMarshallingEncoder());
        assertTrue(embeddedChannel.writeOutbound(msg));
        assertTrue(embeddedChannel.finish());
        //读取数据
        Object o = embeddedChannel.readOutbound();
        //System.out.println(o);
    }
    @Test
    public void getId() {
    }

    @Test
    public void setId() {
    }
}