package xyz.ly11.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.ly11.domain.SimpleUser;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author 29794
 * @date 2/12/2021 12:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchedulingFormDTO implements Serializable {
    private static final long serialVersionUID = -4242239100358851701L;

    private SimpleUser simpleUser;

    private String beginDate;

    private List<SchedulingData> data;

    @Data
    public static class SchedulingData implements Serializable {

        private static final long serialVersionUID = -7557967418428133152L;

        private Long userId;

        private Long deptId;

        private String subsectionType;

        private Collection<String> schedulingType;

    }

}