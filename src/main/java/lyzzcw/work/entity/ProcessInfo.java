package lyzzcw.work.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author lzy
 * @version 1.0
 * Date: 2023/6/16 15:44
 * Description: No Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class ProcessInfo {
    @ApiModelProperty(name="userId",value="用户id",required=true)
    private String userId;
    @ApiModelProperty(name="money",value="报销金额",required=true)
    private Integer money;
    @ApiModelProperty(name="description",value="描述")
    private String description;
}
