package com.xli.attach.service.impl;

import com.xli.attach.frameattachinfo.entity.FrameAttachInfo;
import com.xli.attach.frameattachinfo.service.IFrameAttachInfoService;
import com.xli.attach.service.IAttachService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Slf4j
@Component
public class AttachServiceImpl implements IAttachService {

    @Autowired
    IFrameAttachInfoService iFrameAttachInfoService;

    @Override
    public boolean insert(FrameAttachInfo frameAttachInfo, InputStream content) {
        if (content != null) {
            //先用代理模式把文件流保存下来
            new AttachService().insert(frameAttachInfo, content);
        }
        //再把附件基本信息入库
        return iFrameAttachInfoService.insert(frameAttachInfo);
    }

    @Override
    public boolean delete(Long id) {
        FrameAttachInfo frameAttachInfo = iFrameAttachInfoService.find(id);
        if (frameAttachInfo != null) {
            //先用代理模式把文件流删了
            new AttachService().delete(frameAttachInfo);
            //再把附件基本信息删了
            return iFrameAttachInfoService.delete(frameAttachInfo.getId());
        }
        return false;
    }

    @Override
    public FrameAttachInfo getAttach(Long id) {
        return iFrameAttachInfoService.find(id);
    }

    @Override
    public FrameAttachInfo getAttachStorage(Long id) {
        FrameAttachInfo frameAttachInfo = iFrameAttachInfoService.find(id);
        if (frameAttachInfo != null) {
            //先用代理模式把文件流查出来
            frameAttachInfo.setFrameAttachStorage(new AttachService().getAttachStorage(frameAttachInfo));
        }
        return frameAttachInfo;
    }

    @Override
    public List<FrameAttachInfo> getAttachListByGroupId(Long groupId) {
        return iFrameAttachInfoService.findByGroupId(groupId);
    }

    @Override
    public List<FrameAttachInfo> getAttachStorageByGroupId(Long groupId) {
        List<FrameAttachInfo> frameAttachInfoList = iFrameAttachInfoService.findByGroupId(groupId);
        for (FrameAttachInfo frameAttachInfo : frameAttachInfoList) {
            frameAttachInfo.setFrameAttachStorage(new AttachService().getAttachStorage(frameAttachInfo));
        }
        return frameAttachInfoList;
    }
}
