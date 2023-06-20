package lyzzcw.work.listener;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.engine.impl.context.Context;
import org.flowable.task.service.delegate.DelegateTask;

import java.util.Map;

/**
 * @author lzy
 * @version 1.0
 * Date: 2023/6/16 16:22
 * Description: 经理流程审批结果监听
 */
public class ManagerTaskHandler implements TaskListener {


    private final RuntimeService runtimeService = Context.getProcessEngineConfiguration().getRuntimeService();

    @Override
    public void notify(DelegateTask delegateTask) {
        Map<String, Object> map = runtimeService.getVariables(delegateTask.getExecutionId());
        Object outcome = map.get("outcome");
        if ("驳回".equals(outcome)) {
            System.out.println("经理驳回流程了！");
        } else {
            System.out.println("经理同意流程了！");
        }
    }

}
