package cn.hqu.csmall.product.service.impl;

import cn.hqu.csmall.commons.ex.ServiceException;
import cn.hqu.csmall.commons.web.ServiceCode;
import cn.hqu.csmall.product.mapper.PictureMapper;
import cn.hqu.csmall.product.pojo.entity.Picture;
import cn.hqu.csmall.product.pojo.param.PictureAddNewParam;
import cn.hqu.csmall.product.pojo.vo.PictureListItemVO;
import cn.hqu.csmall.product.service.IPictureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j @Service
public class PictureServiceImpl implements IPictureService {
    @Autowired private PictureMapper mapper;

    @Override
    public void addNew(PictureAddNewParam param) {
        Picture p = new Picture();
        BeanUtils.copyProperties(param, p);
        p.setIsCover(0);
        p.setGmtCreate(LocalDateTime.now());
        p.setGmtModified(LocalDateTime.now());
        mapper.insert(p);
    }

    @Override
    public List<PictureListItemVO> listByAlbumId(Long albumId) {
        return mapper.listByAlbumId(albumId);
    }

    @Override
    public void delete(Long id) {
        if (mapper.selectById(id) == null)
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, "图片不存在");
        mapper.deleteById(id);
    }
}
