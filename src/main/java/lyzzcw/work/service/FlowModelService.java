package lyzzcw.work.service;

import lombok.RequiredArgsConstructor;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author lzy
 * @version 1.0
 * Date: 2023/6/19 17:18
 * Description: No Description
 */
@Component
@RequiredArgsConstructor
public class FlowModelService {

    final RepositoryService repositoryService;

    public String deploy(){
        Deployment deployment = repositoryService.createDeployment()
                .category("demo")
                .name("travel_reimbursement_process_v1")
                .addClasspathResource("travel_reimbursement_process_v1.bpmn20.xml")
                .deploy();
        return deployment.getId();
    }

    public List<ProcessDefinition> list(){
        return repositoryService.createProcessDefinitionQuery()
                .list();
    }
}
