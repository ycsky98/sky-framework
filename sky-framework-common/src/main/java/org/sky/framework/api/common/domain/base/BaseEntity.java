package org.sky.framework.api.common.domain.base;

import java.time.LocalDateTime;

/**
 * @author yangcong
 */
public class BaseEntity {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updateAt;

}
