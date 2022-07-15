import com.lynn.dao.AssistantArticleMapper;
import com.lynn.dao.AssistantUserMapper;
import com.lynn.dao.ModelInforMapper;
import com.lynn.dao.ModelOptionMapper;
import com.lynn.pojo.AssistantArticle;
import com.lynn.pojo.AssistantUser;
import com.lynn.pojo.ModelInfor;
import com.lynn.pojo.ModelOption;
import com.lynn.util.HttpClientUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.*;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class ConnectTest {

    @Resource
    private AssistantUserMapper assistantUserMapper;
    @Resource
    private ModelInforMapper modelInforMapper;
    @Resource
    private AssistantArticleMapper assistantArticleMapper;
    @Resource
    private ModelOptionMapper modelOptionMapper;

    @Test
    public void findUser(){
        AssistantUser user = assistantUserMapper.findAdmin("lynn");
        System.out.println(user.getUser_id());
    }

    @Test
    public void findAllModel(){
        List<ModelInfor> ms = modelInforMapper.findAllModel();
        for (ModelInfor m : ms) {
            System.out.println(m.getPhone_model());
        }
    }

    @Test
    public void findAllArticle(){
        List<AssistantArticle> as = assistantArticleMapper.getAllArticle();
        for (AssistantArticle a : as) {
            System.out.println(a.getArticle_title());
        }
    }

    @Test
    public void findModelByOption(){
        ModelOption modelOption = new ModelOption();
//        modelOption.setOption_resolving_power("1440");
//        modelOption.setOption_weight_low(160);
//        modelOption.setOption_weight_high(190);
//        modelOption.setOption_battery_low(4500);
        modelOption.setOption_size_low(Float.parseFloat("6.4"));
        List<ModelInfor> ms = modelInforMapper.findModelByOption(modelOption);
        for (ModelInfor m : ms) {
            System.out.println(m.getPhone_model());
        }
    }

    @Test
    public void httpGetTest(){
        String result = HttpClientUtil.getInstance().getHttpResponse("updateModel");
        if(!result.equals("ERROR")) {
            System.out.println("python接口调用成功！");
        } else {
            System.out.println("python接口调用失败！");
        }
    }

    @Test
    public void getTopCharacter(){
        System.out.println(modelOptionMapper.getTopCharacter());
    }

    @Test
    public void getTopCamera(){
        System.out.println(modelOptionMapper.getTopCamera());
    }
}
