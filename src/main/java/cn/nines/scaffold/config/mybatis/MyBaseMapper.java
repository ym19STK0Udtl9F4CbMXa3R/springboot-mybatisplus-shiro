package cn.nines.scaffold.config.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author nines
 * @since 2019-12-03
 */
public interface MyBaseMapper<T> extends BaseMapper<T> {

    /**
     * 如果要自动填充，@{@code Param}(xx) xx参数名必须是 list/collection/array 3个的其中之一
     * 批量添加
     * @param batchList list
     * @return boolean
     */
    boolean insertAllBatch(@Param("list") List<T> batchList);

    /**
     * 批量修改
     * @param batchList list
     * @return boolean
     */
    boolean updateAllBatchById(@Param("list") List<T> batchList);

}
