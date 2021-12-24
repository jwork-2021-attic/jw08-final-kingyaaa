package marshalling;

import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;
import io.netty.handler.codec.marshalling.DefaultMarshallerProvider;
import io.netty.handler.codec.marshalling.DefaultUnmarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallingDecoder;
import io.netty.handler.codec.marshalling.MarshallingEncoder;
import io.netty.handler.codec.marshalling.UnmarshallerProvider;

public class MarshallingCodeFactory {

    /**
     * 创建Jboss Marshalling解码器MarshallingDecoder
     * @return MarshallingDecoder
     */
    public static MarshallingDecoder buildMarshallingDecoder(){

        //首先通过Marshalling工具类的静态方法获取Marshalling实例对象 参数为serial标识创建的是java序列化工厂对象
        final MarshallerFactory marshallerFactory =         Marshalling.getProvidedMarshallerFactory("serial");

        //创建MarshallingConfiguration对象，配置版本号为5
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);

        //根据marshallerFactory和configuration创建provider
        UnmarshallerProvider provider = new DefaultUnmarshallerProvider(marshallerFactory, configuration);

        //构建Netty的marshallingDecoder对象，两个参数分别为provider和单个消息序列化后的最大长度
        MarshallingDecoder decoder = new MarshallingDecoder(provider, 1024*1024*1);

        return decoder;
    }

    /**
     * 创建Jboss Marshalling编码器MarshallingEncoder
     * @return MarshallingDecoder
     */
    public static MarshallingEncoder buildMarshallingEncoder(){

        //首先通过Marshalling工具类的静态方法获取Marshalling实例对象 参数为serial标识创建的是java序列化工厂对象
        final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");

        //创建MarshallingConfiguration对象，配置版本号为5
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);

        //根据marshallerFactory和configuration创建provider
        MarshallerProvider provider = new DefaultMarshallerProvider(marshallerFactory, configuration);

        //构建Netty的marshallingDecoder对象，用于将实现序列化接口的POJO对象序列化成二进制数组
        MarshallingEncoder encoder = new MarshallingEncoder(provider);

        return encoder;
    }
}