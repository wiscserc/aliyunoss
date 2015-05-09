/**
 * Copyright (c) 2012 The Wiseserc. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be
 * found in the LICENSE file.
 */
package com.aliyun.android.oss.task;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;

import com.aliyun.android.oss.OSSException;
import com.aliyun.android.oss.http.HttpMethod;
import com.aliyun.android.oss.http.OSSHttpTool;
import com.aliyun.android.oss.model.AccessLevel;
import com.aliyun.android.util.Helper;

/**
 * 设置Bucket访问权限
 * 
 * @author Michael
 */
public class PutBucketAclTask extends Task {
    /**
     * Bucket名字
     */
    private String bucketName;

    /**
     * 访问权限
     */
    private AccessLevel accessLevel;

    /**
     * 构造新示例
     */
    public PutBucketAclTask(String bucketName, AccessLevel accessLevel) {
        super(HttpMethod.PUT);
        this.bucketName = bucketName;
        this.accessLevel = accessLevel;
    }

    /**
     * 合法性验证
     */
    protected void checkArguments() {
        if (bucketName == null || bucketName.equals("")) {
            throw new IllegalArgumentException("bucketName not set");
        }
        if (accessLevel == null) {
            throw new IllegalArgumentException("accessLevel not set");
        }
    }

    /**
     * 获取请求结果，如果成功返回true
     * 
     * @return
     * @throws OSSException
     */
    public boolean getResult() throws OSSException {
        try {
            HttpResponse r = this.execute();
            return r.getStatusLine().getStatusCode() == 200;
        } catch (OSSException osse) {
            throw osse;
        } finally {
            this.releaseHttpClient();
        }
    }

    /*
     * (non-Javadoc) * @see
     * com.aliyun.android.oss.task.Task#generateHttpRequest()
     */
    @Override
    protected HttpUriRequest generateHttpRequest() {
        String resource = httpTool.generateCanonicalizedResource("/"
                + bucketName);
        HttpPut httpPut = new HttpPut(OSS_END_POINT + resource);

        String dateStr = Helper.getGMTDate();
        String content = "PUT\n\n\n" + dateStr + "\n" + X_OSS_ACL + ":"
                + accessLevel.toString() + "\n" + resource;
        String authorization = OSSHttpTool.generateAuthorization(accessId,
                accessKey, content);
        httpPut.setHeader("Authorization", authorization);
        httpPut.setHeader(X_OSS_ACL, accessLevel.toString());
        httpPut.setHeader("Date", dateStr);
        httpPut.setHeader("Host", OSS_HOST);

        return httpPut;
    }
}
