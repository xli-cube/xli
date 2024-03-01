package com.xli.attach.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.xli.attach.frameattachinfo.entity.FrameAttachInfo;
import com.xli.attach.frameattachstorage.entity.FrameAttachStorage;
import com.xli.attach.proxy.factory.IAttachProxy;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class AttachService {

    private String storageDsName;

    public FrameAttachInfo insert(FrameAttachInfo frameAttachInfo, InputStream content) {
        IAttachProxy iAttachProxy = null;
        if (StrUtil.isNotBlank(storageDsName)) {
            iAttachProxy = new IAttachProxy(storageDsName);
        } else {
            iAttachProxy = new IAttachProxy("DB");
            frameAttachInfo.setStorage_type("DB");
        }
        FrameAttachStorage frameAttachStorage = new FrameAttachStorage();
        frameAttachStorage.setId(frameAttachInfo.getId());
        frameAttachStorage.setGroup_id(frameAttachInfo.getGroup_id());
        frameAttachStorage.setFile_name(frameAttachInfo.getFile_name());
        frameAttachStorage.setFile_content(content);
        frameAttachStorage.setFile_type(frameAttachInfo.getFile_type());
        if (frameAttachInfo.getFile_size() != null && frameAttachInfo.getFile_size() != 0) {
            frameAttachStorage.setFile_size(frameAttachInfo.getFile_size());
        } else {
            int size;
            InputStream in;
            try {
                in = frameAttachStorage.getFile_content();
                size = in.available();
                frameAttachStorage.setFile_size((long) size);
            } catch (IOException e) {
                log.error("文件流异常", e);
            }
        }
        iAttachProxy.insert(frameAttachStorage);
        frameAttachInfo.setHash_code(IdUtil.fastSimpleUUID());
        return frameAttachInfo;
    }

    public boolean delete(FrameAttachInfo frameAttachInfo) {
        IAttachProxy iAttachProxy = new IAttachProxy(frameAttachInfo.getStorage_type());
        return iAttachProxy.delete(frameAttachInfo);
    }

    public FrameAttachStorage getAttachStorage(FrameAttachInfo frameAttachInfo) {
        IAttachProxy iAttachProxy = new IAttachProxy(frameAttachInfo.getStorage_type());
        return iAttachProxy.getAttach(frameAttachInfo);
    }
}
