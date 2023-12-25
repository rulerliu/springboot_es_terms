package org.example.service;

import org.example.bean.ResourceInstanceQueryVO;

import java.util.Map;

/**
 * @Author ：liuwq
 * @Date ：Created in 2023/12/22 17:58
 * @Description：
 * @Version : v1.0
 * @Copyright : Powerd By Winhong Tec Inc On 2023. All rights reserved.
 */

public interface TestService {

    public Map<String, Map<String, Long>> getCountMap(ResourceInstanceQueryVO query);
}
