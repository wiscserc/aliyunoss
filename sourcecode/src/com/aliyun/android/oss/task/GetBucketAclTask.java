/**
 * Copyright (c) 2012 The Wiseserc. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be
 * found in the LICENSE file.
 */
package com.aliyun.android.oss.task;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import com.aliyun.android.oss.OSSException;
import com.aliyun.android.oss.http.HttpMethod;
import com.aliyun.android.oss.http.OSSHttpTool;
import com.aliyun.android.oss.model.AccessLevel;
import com.aliyun.android.oss.xmlparser.GetBucketAclXmlParser;
import com.aliyun.android.util.Helper;

/**
 * 获取Bucket访问权限的任务
 * 
 * @author Michael
 */
public class GetBucketAclTask extends Task {
    /**
     * bucket名称
     */
    private String bucketName;

    /**
     * 构造新实例
     */
    public GetBucketAclTask(String bucketName) {
        super(HttpMethod.GET);
        this.bucketName = bucketName;
    }

    /**
     * 合法性验证
     */
    protected void checkArguments() {
        if (bucketName == null || bucketName.equals("")) {
            throw new IllegalArgumentException("bucketName not set");
        }
    }

    /*
     * (non-Javadoc) * @see
     * com.aliyun.android.oss.task.Task#generateHttpRequest()
     */
    @Override
    protected HttpUriRequest generateHttpRequest() {
        httpTool.setIsAcl(true);
        String resource = httpTool.generateCanonicalizedResource("/"
                + bucketName);;
        HttpGet httpGet = new HttpGet(OSS_END_POINT + resource);

        String dateStr = Helper.getGMTDate();
        String authorization = OSSHttpTool
                .generateAuthorization(accessId, accessKey,
                        httpMethod.toString(), "", "", dateStr, "", resource);
        httpGet.setHeader("Authorization", authorization);
        httpGet.setHeader("Date", dateStr);
        httpGet.setHeader("Host", OSS_HOST);

        return httpGet;
    }

    /**
     * 获取指定对象ACL信息* @return
     * 
     * @throws Exception
     * @throws
     * @throws
     */
    public AccessLevel getResult() throws OSSException {
        try {
            HttpResponse r = this.execute();
            GetBucketAclXmlParser parser = new GetBucketAclXmlParser();
            return parser.parse(r.getEntity().getContent());
        } catch (OSSException osse) {
            throw osse;
        } catch (Exception e) {
            throw new OSSException(e);
        } finally {
            this.releaseHttpClient();
        }
    }
}
