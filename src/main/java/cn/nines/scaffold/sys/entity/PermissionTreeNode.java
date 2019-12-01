package cn.nines.scaffold.sys.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: PermissionTreeNode
 * @Description: 权限树节点
 * @author: Nines
 * @date: 2019年12月01日 23:23
 */
@Data
public class PermissionTreeNode implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 节点ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 节点名称
     */
    private String label;

    /**
     * 子节点
     */
    private List<PermissionTreeNode> children;

    public PermissionTreeNode() {
    }

    public PermissionTreeNode(Long id, String label, List<PermissionTreeNode> children) {
        this.id = id;
        this.label = label;
        this.children = children;
    }
}
