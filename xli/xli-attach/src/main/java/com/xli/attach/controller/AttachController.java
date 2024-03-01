package com.xli.attach.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.xli.attach.frameattachinfo.entity.FrameAttachInfo;
import com.xli.attach.frameattachinfo.entity.dto.FrameAttachInfoDTO;
import com.xli.attach.frameattachinfo.entity.mapper.IFrameAttachInfoMapper;
import com.xli.attach.frameattachinfo.entity.vo.FrameAttachInfoVO;
import com.xli.attach.service.IAttachService;
import com.xli.dto.result.ResultVO;
import com.xli.dto.result.status.Status;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/attach")
public class AttachController {

    /**
     * 分片临时目录
     */
    private static final String sliceUploadPath = "";

    /**
     * 文件上传默认分片大小（KB）
     */
    public static int sliceSize = 128 * 1024;

    @Autowired
    private IAttachService iAttachService;

    @PostMapping(value = "/chunkInit")
    public ResultVO<FrameAttachInfoVO> chunkInit(@RequestBody FrameAttachInfoDTO dto) {
        //每个附件都有唯一的attachId。
        //初始化附件时，先根据attachId生成唯一的临时目录，接下来的所有分片都要提交到这个目录下
        String attachId = IdUtil.getSnowflakeNextIdStr();
        String groupId = dto.getGroupId();

        if (StrUtil.isBlank(groupId)) {
            //每条业务数据都有唯一的groupId（例如身份证附件，附件图片有国徽面和头像面。这两个附件的groupId一样但是attachId不一样）
            //提交的附件若无groupId，说明页面刚打开，且没有关联业务数据，此时应返回groupId
            groupId = IdUtil.getSnowflakeNextIdStr();
        }

        //拼接临时文件名（fileMD5_attachId.~Temp）
        String tempFileName = getTempFileName(attachId);
        //拼接临时文件信息（fileMD5_attachId.~uif）
        String tempFileInfoName = getTempFileInfoName(attachId);
        //创建临时文件对象
        File tempFile = getFile(tempFileName);
        File tempInfoFile = getFile(tempFileInfoName);

        boolean initSucc = false;
        if (!tempFile.exists()) {
            try {
                initSucc = tempFile.createNewFile();
                if (initSucc) {
                    initSucc = tempInfoFile.createNewFile();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (!initSucc) {
                log.error("未能成功创建保存文件:" + tempFile.getPath() + " 或者或者未能成功创建信息文件:" + tempInfoFile.getPath());
            }
        } else {
            initSucc = true;
        }
        if (initSucc) {
            FrameAttachInfoVO vo = new FrameAttachInfoVO();
            vo.setId(attachId);
            vo.setGroupId(groupId);
            vo.setFileName(dto.getFileName());
            vo.setFileSize(dto.getFileSize());
            return new ResultVO<>(Status.SUCCESS, "初始化成功", vo);
        }
        return new ResultVO<>(Status.FAILED, "初始化失败");
    }

    @PostMapping(value = "/chunkUpload")
    public ResultVO<String> chunkUpload(@RequestParam("fileName") String fileName,
                                        @RequestParam("fileSize") String fileSize,
                                        @RequestParam("fileSize") String fileMD5,
                                        @RequestParam("fileBlockSqNo") String fileBlockSqNo,
                                        @RequestParam("attachId") String attachId,
                                        @RequestParam("chunk") MultipartFile chunk) throws IOException {
        String tempFileName = getTempFileName(attachId);
        String tempFileInfoName = getTempFileInfoName(attachId);

        File tempFile = getFile(tempFileName);
        File tempInfoFile = getFile(tempFileInfoName);

        boolean isWriteFileOk = false;
        if (tempFile.exists() && tempInfoFile.exists()) {
            //临时文件必须同时存在
            // 先写入temp文件
            int blockSize = chunk.getBytes().length;
            //分片号
            int sqlNo = Integer.parseInt(fileBlockSqNo);
            RandomAccessFile fr = null;
            try {
                fr = new RandomAccessFile(tempFile, "rw");
                // 开始写入文件
                FileChannel fc = fr.getChannel();
                ByteBuffer bf = ByteBuffer.wrap(chunk.getBytes());
                // 计算写入位置
                long writePosition = (long) sqlNo * sliceSize;
                // 在writePosition位置写入上传的内容
                fc.write(bf, writePosition);
                fc.force(false);
                isWriteFileOk = true;
            } catch (Exception e) {
                isWriteFileOk = false;
                e.printStackTrace();
            } finally {
                if (fr != null) {
                    try {
                        fr.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            if (isWriteFileOk) {
                RandomAccessFile fir = null;
                try {
                    fir = new RandomAccessFile(tempInfoFile, "rw");
                    byte[] fBytes = new byte[4];
                    // 开始写入文件
                    FileChannel fc = fir.getChannel();
                    ByteBuffer bf = ByteBuffer.wrap(fBytes);
                    bf.putInt(0, blockSize);
                    // 计算写入位置
                    long writePosition = sqlNo * 4L;
                    fc.write(bf, writePosition);
                    fc.force(false);
                    isWriteFileOk = true;
                } catch (Exception e) {
                    e.printStackTrace();
                    isWriteFileOk = false;
                } finally {
                    if (fir != null) {
                        try {
                            fir.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        if (isWriteFileOk) {
            return new ResultVO<>(Status.SUCCESS, "分片提交成功");
        }
        return new ResultVO<>(Status.FAILED, "分片提交失败");
    }

    @PostMapping(value = "/chunkFinish")
    public ResultVO<FrameAttachInfoVO> chunkFinish(@RequestBody FrameAttachInfoDTO dto) {
        String tempFileName = getTempFileName(dto.getId());
        String tempFileInfoName = getTempFileInfoName(dto.getId());

        File tempFile = getFile(tempFileName);
        File newFile = null;

        File tempInfoFile = getFile(tempFileInfoName);
        FileInputStream in = null;

        boolean succ = false;
        try {
            if (tempFile.exists()) {
                newFile = renameTempFile(tempFile, dto.getFileName());
                in = new FileInputStream(newFile);

                FrameAttachInfo frameAttachInfo = new FrameAttachInfo();
                frameAttachInfo.setId(Long.parseLong(dto.getId()));
                frameAttachInfo.setFile_name(dto.getFileName());
                frameAttachInfo.setGroup_id(Long.parseLong(dto.getGroupId()));
                frameAttachInfo.setFile_size(newFile.length());
                frameAttachInfo.setFile_type(dto.getFileName().substring(dto.getFileName().lastIndexOf(".")));
                succ = iAttachService.insert(frameAttachInfo, in);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // 删除临时文件
            if (newFile != null) {
                newFile.delete();
            }
            // 删除临时信息文件
            tempInfoFile.delete();
            // 删除临时目录
            File parentFile = new File(sliceUploadPath + getTempFileName(dto.getId()));
            if (parentFile.exists()) {
                parentFile.delete();
            }
        }
        if (succ) {
            FrameAttachInfoVO vo = new FrameAttachInfoVO();
            vo.setId(dto.getId());
            vo.setGroupId(dto.getGroupId());
            return new ResultVO<>(Status.SUCCESS, "文件提交成功", vo);
        }
        return new ResultVO<>(Status.FAILED, "文件提交失败");
    }

    @PostMapping(value = "/getAttachList")
    public ResultVO<List<FrameAttachInfoVO>> getAttachList(@RequestBody FrameAttachInfoDTO dto, HttpServletRequest request) {
        List<FrameAttachInfoVO> frameAttachInfoVOList = new ArrayList<>();
        if (dto.getGroupId() != null) {
            List<FrameAttachInfo> frameAttachInfoList = iAttachService.getAttachListByGroupId(Long.valueOf(dto.getGroupId()));
            frameAttachInfoVOList = frameAttachInfoList.stream().map(entity -> {
                FrameAttachInfoVO vo = IFrameAttachInfoMapper.INSTANCE.toVO(entity);
                vo.setUrl(request.getScheme() + "://" + request.getRemoteAddr() + ":" + request.getServerPort() + request.getContextPath() + "/attach/getContent?id=" + vo.getId());
                return vo;
            }).collect(Collectors.toList());
        }
        return new ResultVO<>(Status.SUCCESS, "查询成功", frameAttachInfoVOList);
    }

    @GetMapping(value = "/getContent")
    public void getContent(@RequestParam("id") String attachId, HttpServletResponse response) {
        FrameAttachInfo frameAttachInfo = iAttachService.getAttachStorage(Long.valueOf(attachId));
        if (frameAttachInfo != null && frameAttachInfo.getFrameAttachStorage() != null) {
            OutputStream out = null;
            InputStream in = frameAttachInfo.getFrameAttachStorage().getFile_content();
            if (in != null) {
                try {
                    response.flushBuffer();
                    // 设置响应头，指定文件名
                    response.setHeader("Content-Disposition", "attachment; filename=" + frameAttachInfo.getFile_name());
                    response.setHeader("Content-Length", frameAttachInfo.getFile_size().toString());

                    response.setContentType("application/octet-stream");

                    out = response.getOutputStream();
                    byte[] buffer = new byte[4096];
                    int ch = 0;
                    while ((ch = in.read(buffer)) != -1) {
                        out.write(buffer, 0, ch);
                    }
                    out.flush();
                } catch (Exception ex) {
                    log.error("下载附件出现异常：" + ex);
                } finally {
                    try {
                        if (out != null) {
                            out.close();
                        }
                        in.close();
                    } catch (IOException e) {
                        log.error("下载附件出现异常：" + e);
                    }
                }
            }
        } else {
            // 处理文件不存在或下载失败的情况
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @PostMapping(value = "/delete")
    public ResultVO<String> delete(@RequestBody FrameAttachInfoDTO dto) {
        if (dto.getId() != null) {
            if (iAttachService.delete(Long.valueOf(dto.getId()))) {
                return new ResultVO<>(Status.SUCCESS, "删除成功");
            }
        }
        return new ResultVO<>(Status.FAILED, "删除失败");
    }

    private String getTempFileName(String attachId) {
        return attachId + ".~Temp";
    }

    private String getTempFileInfoName(String attachId) {
        return attachId + ".uif";
    }

    private File getFile(String fileName) {
        return new File(sliceUploadPath + fileName);
    }

    private File renameTempFile(File tempFile, String fileName) {
        File newFile = new File(sliceUploadPath + fileName);
        tempFile.renameTo(newFile);
        return newFile;
    }
}