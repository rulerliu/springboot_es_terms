package org.example;

import org.example.bean.ResourceInstanceQueryVO;
import org.example.service.TestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Unit test for simple App.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTest {

    @Autowired
    private TestService testService;

    @Test
    public void testSelect() {
        ResourceInstanceQueryVO queryVO = new ResourceInstanceQueryVO();
        queryVO.setSearchCriteria("busSysName,Virtual_Host_Name,Business_Private_IP,Business_Elastic_IP");
        // 1个人 2单位
        queryVO.setOtherCondition(1);
        long start = System.currentTimeMillis();
        testService.getCountMap(queryVO);
        System.out.println("个人查询耗时:" + (System.currentTimeMillis() - start));

        queryVO.setOtherCondition(2);
        long start2 = System.currentTimeMillis();
        testService.getCountMap(queryVO);
        System.out.println("单位查询耗时:" + (System.currentTimeMillis() - start2));
    }

}
