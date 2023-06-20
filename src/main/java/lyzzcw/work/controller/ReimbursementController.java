package lyzzcw.work.controller;

import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import liquibase.pro.packaged.T;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lyzzcw.work.entity.ProcessInfo;
import lyzzcw.work.entity.Result;
import org.flowable.engine.*;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lzy
 * @version 1.0
 * Date: 2023/6/16 15:48
 * Description: No Description
 */
@Api(tags = "出差报销流程")
@RestController
@RequestMapping("/demo")
@RequiredArgsConstructor
@Slf4j
public class ReimbursementController {
    /**
     * 从ProcessEngine类中可以得到各种服务，值得注意的是，这些服务可以被认为是单例模式。
     * RepositoryService是使用Flowable引擎时需要的第一个服务，它提供用于管理和操作部署和流程定义（BPMN ）的操作，主要负责一些静态的配置。
     * RuntimeService主要负责一些动态的任务，如启动一个新的实例，通常一个流程可以对应多个实例。它也用于检索和储存一些流程变量，比如在单向网关组件中需要传递之前的选择。
     * TaskService主要和分配给人的任务相关，比如将任务分配给用户。
     * IdentityService主要负责用户的管理。
     * FormService是一个可选的服务，主要负责开始表单和结束表单。
     * HistoryService保存历史信息。
     * DynamicBpmnService可以动态的添加新的流程。
     */
    final ProcessEngine processEngine;

    final RepositoryService repositoryService;

    final RuntimeService runtimeService;

    final TaskService taskService;


    /**
     * 添加报销
     */
    @ApiOperation(value = "添加报销")
    @PostMapping(value = "add")
    public Result<T> addExpense(@RequestBody ProcessInfo processInfo) {
        //启动流程
        Map<String, Object> map = Maps.newHashMap();
        map.put("userId", processInfo.getUserId());
        map.put("money",processInfo.getMoney());
        map.put("description",processInfo.getDescription());
        String executionId = "lyzzcw_work_20230616";
        ProcessInstance processInstance =
                runtimeService.startProcessInstanceByKey(executionId, map);
        return Result.ok(processInstance.getId());
    }

    /**
     * 获取审批管理列表
     */
    @ApiOperation(value = "获取审批管理列表")
    @GetMapping(value = "/list")
    public Result<List<Task>> list(String userId) {
        List<Task> tasks = taskService.createTaskQuery()
                .taskAssignee(userId)
                .orderByTaskCreateTime()
                .desc()
                .list();
        tasks.forEach(System.out::println);
        return Result.ok(tasks);
    }



    /**
     * 批准
     *
     * @param taskId 任务ID
     */
    @ApiOperation(value = "同意")
    @GetMapping(value = "apply")
    public Result<T> apply(String taskId) {
        Task task = taskService.createTaskQuery()
                .taskId(taskId)
                .singleResult();
        Assert.isNull(task,"流程不存在");
        //通过审核
        HashMap<String, Object> map = Maps.newHashMap();
        map.put("outcome", "同意");
        taskService.complete(taskId, map);
        return Result.ok("流程已通过");
    }

    /**
     * 拒绝
     */
    @ApiOperation(value = "驳回")
    @GetMapping(value = "reject")
    public Result<T> reject(String taskId) {
        Task task = taskService.createTaskQuery()
                .taskId(taskId)
                .singleResult();
        Assert.isNull(task,"流程不存在");
        Map<String, Object> map = Maps.newHashMap();
        map.put("outcome", "驳回");
        taskService.complete(taskId, map);
        return Result.ok("流程已驳回");
    }


}
