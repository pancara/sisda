package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.dao.LeafletDao;
import com.integrasolusi.pusda.sisda.persistence.Leaflet;
import com.integrasolusi.pusda.sisda.repository.BlobDataType;
import com.integrasolusi.pusda.sisda.repository.BlobRepository;
import com.integrasolusi.query.filter.CompositeFilter;
import com.integrasolusi.query.filter.QueryOperator;
import com.integrasolusi.query.filter.ValueFilter;
import com.integrasolusi.query.util.OrderDir;
import com.integrasolusi.utils.ImageUtils;
import com.integrasolusi.utils.StreamHelper;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

/**
 * User: pancara
 * Date: 9/3/12
 * Time: 10:45 AM
 */
public class LeafletServiceImpl implements LeafletService {
    private LeafletDao leafletDao;
    private BlobRepository blobRepository;
    private ImageUtils imageUtils;

    public void setLeafletDao(LeafletDao leafletDao) {
        this.leafletDao = leafletDao;
    }

    public void setBlobRepository(BlobRepository blobRepository) {
        this.blobRepository = blobRepository;
    }

    public void setImageUtils(ImageUtils imageUtils) {
        this.imageUtils = imageUtils;
    }

    @Override
    public List<Leaflet> getLatest(Long count) {
        return leafletDao.findByFilter(createPublishedFilter(), 0L, count, "index", OrderDir.ASC);
    }

    @Override
    public Leaflet findById(Long id) {
        return leafletDao.findById(id);
    }

    @Override
    public void getThumbfileBlob(Long id, OutputStream outputStream) throws IOException {
        blobRepository.copyContent(BlobDataType.LEAFLET_THUMB, id, outputStream);
    }

    @Override
    public void getThumbfileBlob(Long id, OutputStream outputStream, Integer width, Integer height) throws Exception {
        File temp = blobRepository.getTempFile(BlobDataType.LEAFLET_THUMB, id);

        InputStream is = new FileInputStream(temp);
        try {
            Leaflet leaflet = leafletDao.findById(id);
            String format = StringUtils.lowerCase(FilenameUtils.getExtension(leaflet.getThumbFilename()));
            BufferedImage image = imageUtils.resizeImage(is, width, height);
            imageUtils.writeImage(image, format, outputStream);
        } finally {
            StreamHelper.closeQuiet(is);
            temp.delete();
        }
    }

    @Override
    public void getDocumentBlob(Long id, OutputStream outputStream) throws IOException {
        blobRepository.copyContent(BlobDataType.LEAFLET, id, outputStream);
    }

    @Override
    public Long countAlls() {
        return leafletDao.countAlls();
    }

    @Override
    public Long countByKeyword(String keyword) {
        CompositeFilter filter = createKeywordFilter(keyword);
        return leafletDao.countByFilter(filter);
    }

    private CompositeFilter createKeywordFilter(String keyword) {
        CompositeFilter filter = new CompositeFilter(QueryOperator.OR);
        String param_value = "%" + keyword + "%";
        filter.add(new ValueFilter("title", QueryOperator.LIKE, param_value, "title"));
        filter.add(new ValueFilter("description", QueryOperator.LIKE, param_value, "description"));
        return filter;
    }

    @Override
    public List<Leaflet> get(Long start, Long count) {
        return leafletDao.findAlls(start, count, "index", OrderDir.DESC);
    }

    @Override
    public List<Leaflet> getByKeyword(String keyword, Long start, Long count) {
        CompositeFilter filter = createKeywordFilter(keyword);
        return leafletDao.findByFilter(filter, start, count, "index", OrderDir.DESC);
    }

    @Override
    public void save(Leaflet leaflet) {
        leafletDao.save(leaflet);
    }

    @Override
    public void save(Leaflet leaflet, InputStream inputStream) throws IOException {
        leafletDao.save(leaflet);
        blobRepository.store(BlobDataType.LEAFLET, leaflet.getId(), inputStream);
    }

    @Override
    public void removeById(Long id) {
        leafletDao.removeById(id);
        blobRepository.remove(BlobDataType.LEAFLET, id);
        blobRepository.remove(BlobDataType.LEAFLET_THUMB, id);
    }

    @Override
    public void removeByIds(Long[] ids) {
        for (Long id : ids) {
            removeById(id);
        }
    }

    @Override
    public void publishByIds(Long[] ids) {
        for (Long id : ids) {
            Leaflet leaflet = leafletDao.findById(id);
            if (leaflet != null) {
                leaflet.setPublished(true);
                leafletDao.save(leaflet);
            }
        }
    }

    @Override
    public void unpublishByIds(Long[] ids) {
        for (Long id : ids) {
            Leaflet leaflet = leafletDao.findById(id);
            if (leaflet != null) {
                leaflet.setPublished(false);
                leafletDao.save(leaflet);
            }
        }
    }

    @Override
    public void removeThumb(Long id) {
        Leaflet leaflet = leafletDao.findById(id);
        if (leaflet != null) {
            leaflet.setThumbFilename(null);
            leafletDao.save(leaflet);
            blobRepository.remove(BlobDataType.LEAFLET_THUMB, id);
        }
    }

    @Override
    public void saveThumb(Leaflet leaflet, InputStream inputStream) throws IOException {
        leafletDao.save(leaflet);
        blobRepository.store(BlobDataType.LEAFLET_THUMB, leaflet.getId(), inputStream);
    }

    @Override
    public Long countPublished() {
        return leafletDao.countByFilter(createPublishedFilter());
    }

    private ValueFilter createPublishedFilter() {
        return new ValueFilter("published", QueryOperator.EQUALS, true, "published");
    }

    @Override
    public Long countPublishedByKeyword(String keyword) {
        return leafletDao.countByFilter(createPublishedAndKeywordFilter(keyword));
    }

    @Override
    public List<Leaflet> getPublished(Long start, Long count) {
        return leafletDao.findByFilter(createPublishedFilter(), start, count, "id", OrderDir.DESC);
    }

    @Override
    public List<Leaflet> getPublishedByKeyword(String keyword, Long start, Long count) {
        return leafletDao.findByFilter(createPublishedAndKeywordFilter(keyword), start, count, "id", OrderDir.DESC);
    }

    @Override
    public Boolean hasPicture(Long id) {
        return blobRepository.isExist(BlobDataType.LEAFLET, id);
    }

    private CompositeFilter createPublishedAndKeywordFilter(String keyword) {
        CompositeFilter filter = new CompositeFilter(QueryOperator.AND);
        filter.add(createPublishedFilter());
        filter.add(createKeywordFilter(keyword));
        return filter;
    }
}
