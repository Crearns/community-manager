package com.cms.workSheet.feign;

import com.cms.common.common.ServerResponse;
import com.cms.common.entity.Community;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Creams
 */

@FeignClient("community-service")
public interface CommunityClient {

    @GetMapping("community/community")
    ServerResponse<List<Community>> community(@RequestParam("name") String name);

    @GetMapping("community/community-id")
    ServerResponse<List<Community>> communityId(@RequestParam("communityId") Integer communityId);

    @GetMapping("/memberShip")
    ServerResponse<Integer> memberShip(@RequestParam("userId") Long userId, @RequestParam("communityId") Integer communityId);

}