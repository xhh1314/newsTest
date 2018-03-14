import com.hww.sns.SnsWebServiceApplication;
import com.hww.sns.common.dto.HBaseSnsQueryDto;
import com.hww.sns.common.vo.SnsTopicVo;
import com.hww.sns.webservice.service.SnsTopicService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = SnsWebServiceApplication.class)
public class LumbdaTest {

    @Autowired
    private SnsTopicService topicService;

    @Test
    public void test1(){
        HBaseSnsQueryDto snsQueryDto=new HBaseSnsQueryDto();
        snsQueryDto.setMemberId(28245006095626240L);
        List<SnsTopicVo> topicVos= topicService.loadMyTopicList(snsQueryDto);
        Stream<SnsTopicVo> stream= topicVos.parallelStream().filter(val -> val.getForumId()==1).sorted(Comparator.comparing(SnsTopicVo::getCreateTime));
        stream.forEach(val-> System.out.println(val));
    }

}
