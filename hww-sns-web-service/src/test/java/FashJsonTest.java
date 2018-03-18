import com.hww.base.util.BeanMapper;
import com.hww.framework.common.tool.JedisPoolUtil;
import com.hww.sns.common.entity.SnsTopic;
import redis.clients.jedis.Jedis;

import java.util.Map;

public class FashJsonTest {

    public static void main1(String[] args) {
            Jedis conn= JedisPoolUtil.getConnection();
            String skey="sns:val:set";
            conn.sadd(skey,"1");
            String val1=conn.spop(skey);
            String val2=conn.spop(skey);
            String hkey="sns:val:hash";
            SnsTopic snsTopic=new SnsTopic();
            Map<String,String> map= BeanMapper.mapBeanToStringMap(snsTopic);
            String result1= conn.hmset(hkey,map);
            String val3=conn.hget(hkey,"upNum");
            Long val4=conn.hincrBy(hkey,"upNum",1);
            conn.close();

        }

    public static void main(String[] args) {
        SnsTopic snsTopic=new SnsTopic();
        Map<String,String> map= BeanMapper.mapBeanToStringMap(snsTopic);
    }


    }

