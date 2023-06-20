package lyzzcw.work.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lyzzcw.work.entity.Result;
import lyzzcw.work.service.FlowModelService;
import org.flowable.engine.repository.ProcessDefinition;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lzy
 * @version 1.0
 * Date: 2023/6/19 17:16
 * Description: No Description
 */
@RestController
@RequestMapping("flow")
@RequiredArgsConstructor
@Api(tags = "流程发布管理")
public class FlowModelController {

    final FlowModelService flowModelService;

    @PostMapping("deploy")
    @ApiOperation("发布")
    public Result deploy() {
        return Result.ok(flowModelService.deploy());
    }


    @PostMapping("list")
    @ApiOperation("查看")
    public Result<List<ProcessDefinition>> list() {
        return Result.ok(flowModelService.list());
    }
}
