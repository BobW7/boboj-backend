package com.bob.boboj.common;

import java.io.Serializable;
import lombok.Data;

/**
 * 删除请求
 *
 * @author BobW7
 * @from BobW7
 */
@Data
public class DeleteRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}