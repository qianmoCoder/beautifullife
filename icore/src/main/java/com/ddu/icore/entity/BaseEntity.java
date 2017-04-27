/*
 * @项目名称: ETCP停车
 * @文件名称: BaseEntity.java
 * @Copyright: 2016 悦畅科技有限公司. All rights reserved.
 * 注意：本内容仅限于悦畅科技有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */

package com.ddu.icore.entity;

/*
 * @项目名称: ETCP停车
 * @文件名称: BaseEntity.java
 * @Copyright: 2016 悦畅科技有限公司. All rights reserved.
 * 注意：本内容仅限于悦畅科技有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */

public class BaseEntity {

    private int code;
    private String message = "null";


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public boolean isSuccess() {
        return this.code == 0;
    }
}
