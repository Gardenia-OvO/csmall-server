package cn.hqu.csmall.resource.controller;

import cn.hqu.csmall.commons.ex.ServiceException;
import cn.hqu.csmall.commons.web.JsonResult;
import cn.hqu.csmall.commons.web.ServiceCode;
import cn.hqu.csmall.resource.pojo.vo.UploadResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

/**
 * 处理文件上传的控制器类
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Slf4j
@RestController
@RequestMapping("/resources")
@Api(tags = "文件上传模块")
public class ResourceController {

    @Value("${csmall.upload.host}")
    private String host;
    @Value("${csmall.upload.base-dir}")
    private String uploadBaseDir;
    @Value("${csmall.upload.product-image.max-size}")
    private Integer productImageMaxSize;
    @Value("${csmall.upload.product-image.types}")
    private List<String> productImageValidTypes;

    private String productImageDirName = "product-image/";
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd/");

    public ResourceController() {
        log.debug("创建控制器类对象：ResourceController");
    }

    @PostMapping("/upload/image/product")
    @ApiOperation("上传商品图片")
    public JsonResult<UploadResult> uploadProductImage(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        if (multipartFile == null || multipartFile.isEmpty()) {
            String message = "上传商品图片失败，请选择您要上传的文件！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_UPLOAD_EMPTY, message);
        }

        long size = multipartFile.getSize();
        if (size > productImageMaxSize * 1024 * 1024) {
            String message = "上传商品图片失败，不允许使用超过" + productImageMaxSize + "MB的图片文件！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_UPLOAD_EXCEED_MAX_SIZE, message);
        }

        String contentType = multipartFile.getContentType();
        if (!productImageValidTypes.contains(contentType)) {
            String message = "上传商品图片失败，请使用以下类型的图片文件：" + productImageValidTypes;
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_UPLOAD_INVALID_TYPE, message);
        }

        String dirName = dateFormatter.format(LocalDate.now());
        File productImageDir = new File(uploadBaseDir, productImageDirName);
        File uploadDir = new File(productImageDir, dirName);

        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String newFileName = UUID.randomUUID().toString();
        String originalFilename = multipartFile.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFullFileName = newFileName + suffix;
        File newFile = new File(uploadDir, newFullFileName);

        multipartFile.transferTo(newFile);

        String url = new StringBuilder()
                .append(host)
                .append(productImageDirName)
                .append(dirName)
                .append(newFullFileName)
                .toString();

        UploadResult uploadResult = new UploadResult();
        uploadResult.setUrl(url);
        uploadResult.setFileSize(size);
        uploadResult.setContentType(contentType);
        uploadResult.setFileName(newFullFileName);
        return JsonResult.ok(uploadResult);
    }

}
