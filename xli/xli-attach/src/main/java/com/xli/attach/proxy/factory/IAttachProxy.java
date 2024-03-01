package com.xli.attach.proxy.factory;

import cn.hutool.core.util.StrUtil;
import com.xli.attach.frameattachconfig.entity.FrameAttachConfig;
import com.xli.attach.frameattachinfo.entity.FrameAttachInfo;
import com.xli.attach.frameattachstorage.entity.FrameAttachStorage;
import com.xli.attach.proxy.factory.impl.AttachForDBImpl;
import com.xli.attach.proxy.service.IAttach;

public class IAttachProxy implements IAttach {

    private IAttach attachImpl;

    private FrameAttachConfig config;

    public IAttachProxy() {
        this("DB");
    }

    public IAttachProxy(String storageName) {
        if (StrUtil.isBlank(storageName)) {
            config = new FrameAttachConfig();
        } else if ("DB".equals(storageName)) {
            config = new FrameAttachConfig();
            this.attachImpl = new AttachForDBImpl();
        }
        if (this.attachImpl == null) {
            //默认DB实现
            this.attachImpl = new AttachForDBImpl();
        }
    }

    public IAttach getAttachImpl() {
        return this.attachImpl;
    }

    @Override
    public boolean insert(FrameAttachStorage frameAttachStorage) {
        return this.attachImpl.insert(frameAttachStorage);
    }

    @Override
    public boolean delete(FrameAttachInfo frameAttachInfo) {
        return this.attachImpl.delete(frameAttachInfo);
    }

    @Override
    public FrameAttachStorage getAttach(FrameAttachInfo frameAttachInfo) {
        return this.attachImpl.getAttach(frameAttachInfo);
    }
}
