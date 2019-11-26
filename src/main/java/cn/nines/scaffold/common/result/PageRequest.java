package cn.nines.scaffold.common.result;

import lombok.Data;

/**
 * @ClassName: PageRequest
 * @Description: 分页参数
 * @author: Nines
 * @date: 2019年11月26日 14:17
 */
@Data
public class PageRequest {

    private String searchText;
    private int current;
    private int size;

    public PageRequest() {
    }

    public PageRequest(String searchText, int current, int size) {
        this.searchText = searchText;
        this.current = current;
        this.size = size;
    }
}
