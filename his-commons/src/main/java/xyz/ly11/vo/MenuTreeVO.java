package xyz.ly11.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author by 29794
 * @date 2020/10/9 1:07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuTreeVO {

    private String id;

    /**
     * 菜单表中的url
     */
    private String serPath;

    /**
     * 是否需要展示
     */
    private boolean show = true;

    public MenuTreeVO(String id, String serPath) {
        this.id = id;
        this.serPath = serPath;
    }

}
