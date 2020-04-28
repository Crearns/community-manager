package com.cms.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cms.common.common.ResponseCode;
import com.cms.common.common.ServerResponse;
import com.cms.common.entity.Community;
import com.cms.common.entity.SheetCatalog;
import com.cms.common.entity.Worksheet;
import com.cms.web.feign.CommunityClient;
import com.cms.web.feign.WorksheetClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Creams
 */

@RestController
@RequestMapping("office")
@Slf4j
public class OfficeController {
    @Autowired
    private WorksheetClient worksheetClient;

    @Autowired
    private CommunityClient communityClient;

    @PutMapping("/worksheet")
    public ServerResponse worksheet(@RequestParam("worksheetId") Integer worksheetId,
                                    @RequestParam("agree") Integer agree,
                                    @RequestParam("auditId") Long auditId,
                                    String reason) {
        int state = (agree == 1) ? 1 : -1;

        if (agree == 0) {
            worksheetClient.worksheetState(worksheetId, state, auditId, reason);
            return ServerResponse.createSuccessResponse();
        }


        Worksheet worksheet = worksheetClient.worksheet(worksheetId).getData();
        SheetCatalog sheetCatalog = SheetCatalog.valueOf(worksheet.getCatalog());

        switch (sheetCatalog){
            case COMMUNITY_FOUND:
                if (!JSON.isValid(worksheet.getRemark())) {
                    return ServerResponse.createFailureResponse("无法获得申请社团信息");
                }
                JSONObject object = JSON.parseObject(worksheet.getRemark());
                worksheetClient.worksheetState(worksheetId, state, auditId, "成功");
                int communityId = communityClient.community(worksheet.getName(), object.getByte("communityCatalog"), object.getString("description")).getData();
                return communityClient.member(communityId, worksheet.getSubmitUserId(), 1);
            case COMMUNITY_PARTICIPATION:
                Community community = communityClient.community(worksheet.getName()).getData().get(0);
                if (community == null) {
                    return ServerResponse.createFailureResponse("申请的社团已经不存在");
                }
                worksheetClient.worksheetState(worksheetId, state, auditId, "成功");
                communityClient.historyNum(community.getId());
                return communityClient.member(community.getId(), worksheet.getSubmitUserId(), 3);
            case COMMUNITY_ACTIVITY_APPLY:
            case COMMUNITY_CANCEL:
            case UNKNOWN:
            default:
                return ServerResponse.createFailureResponse(ResponseCode.NO_PERMISSION);
        }
    }
}
