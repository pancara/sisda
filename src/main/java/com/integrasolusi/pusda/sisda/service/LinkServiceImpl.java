package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.dao.LinkDao;
import com.integrasolusi.pusda.sisda.dao.LinkTypeDao;
import com.integrasolusi.query.filter.CompositeFilter;
import com.integrasolusi.query.filter.QueryOperator;
import com.integrasolusi.query.filter.ValueFilter;
import com.integrasolusi.query.util.OrderDir;
import com.integrasolusi.pusda.sisda.persistence.Link;
import com.integrasolusi.pusda.sisda.persistence.LinkType;
import com.integrasolusi.pusda.sisda.repository.BlobDataType;
import com.integrasolusi.pusda.sisda.repository.BlobRepository;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Programmer   : pancara
 * Date         : 6/27/11
 * Time         : 5:46 PM
 */
public class LinkServiceImpl implements LinkService {
    private LinkDao linkDao;
    private LinkTypeDao linkTypeDao;
    private BlobRepository blobRepository;

    public void setLinkDao(LinkDao linkDao) {
        this.linkDao = linkDao;
    }

    public void setBlobRepository(BlobRepository blobRepository) {
        this.blobRepository = blobRepository;
    }

    public void setLinkTypeDao(LinkTypeDao linkTypeDao) {
        this.linkTypeDao = linkTypeDao;
    }

    @Override
    public void save(Link link) {
        linkDao.save(link);
    }

    @Override
    public void save(Link link, java.io.InputStream is) throws IOException {
        linkDao.save(link);
        if (is != null) {
            blobRepository.store(BlobDataType.LINK, link.getId(), is);
        }
    }

    @Override
    public void removeById(Long id) {
        linkDao.removeById(id);
        blobRepository.remove(BlobDataType.LINK, id);
    }

    @Override
    public List<Link> findByTypeAndPublished(Long type) {
        CompositeFilter filter = new CompositeFilter(QueryOperator.AND);
        filter.add(new ValueFilter("published", QueryOperator.EQUALS, true, "published"));
        
        LinkType typeEntity = linkTypeDao.findById(type);
        filter.add(new ValueFilter("type", QueryOperator.EQUALS, typeEntity, "type"));
        
        return linkDao.findByFilter(filter, "index", OrderDir.ASC);
    }

    @Override
    public Long countAlls() {
        return linkDao.countAlls();
    }

    @Override
    public List<Link> get(Long start, Long count) {
        return linkDao.findAlls(start, count);
    }

    @Override
    public void savePicture(Long id, InputStream is) throws IOException {
        blobRepository.store(BlobDataType.LINK, id, is);
    }

    @Override
    public Link findById(Long id) {
        return linkDao.findById(id);
    }

    @Override
    public BufferedImage getPicture(Long id) throws IOException {
        return blobRepository.createImage(BlobDataType.LINK, id);
    }

    @Override
    public LinkType getTypeEntity(Long typeId) {
        return linkTypeDao.findById(typeId);
    }

    @Override
    public List<LinkType> findAvailableTypes() {
        return linkTypeDao.findAlls("id", OrderDir.ASC);
    }

    @Override
    public void getBlob(Long id, OutputStream outputStream) throws IOException {
        blobRepository.copyContent(BlobDataType.LINK, id, outputStream);
    }

    @Override
    public List<Link> findAlls() {
        return linkDao.findAlls("id", OrderDir.ASC);
    }

    @Override
    public void removeByIds(Long[] ids) {
        for (Long id : ids) {
            linkDao.removeById(id);
            blobRepository.remove(BlobDataType.LINK, id);
        }
    }

    @Override
    public void publishByIds(Long[] ids) {
        for (Long id : ids) {
            Link link = linkDao.findById(id);
            link.setPublished(true);
            linkDao.save(link);
        }
    }

    @Override
    public void unpublishByIds(Long[] ids) {
        for (Long id : ids) {
            Link link = linkDao.findById(id);
            link.setPublished(false);
            linkDao.save(link);
        }
    }
}
