package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.dao.DokumentasiDao;
import com.integrasolusi.pusda.sisda.dao.DokumentasiPhotoDao;
import com.integrasolusi.query.filter.Filter;
import com.integrasolusi.query.filter.QueryOperator;
import com.integrasolusi.query.filter.ValueFilter;
import com.integrasolusi.query.util.OrderDir;
import com.integrasolusi.pusda.sisda.persistence.Dokumentasi;
import com.integrasolusi.pusda.sisda.persistence.DokumentasiPhoto;
import com.integrasolusi.pusda.sisda.repository.BlobDataType;
import com.integrasolusi.pusda.sisda.repository.BlobRepository;
import com.integrasolusi.utils.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

/**
 * Programmer   : pancara
 * Date         : 6/20/11
 * Time         : 4:29 PM
 */
public class DokumentasiServiceImpl implements DokumentasiService {
    private DokumentasiDao dokumentasiDao;
    private DokumentasiPhotoDao dokumentasiPhotoDao;
    private BlobRepository blobRepository;

    private ImageUtils imageUtils;

    public void setDokumentasiDao(DokumentasiDao dokumentasiDao) {
        this.dokumentasiDao = dokumentasiDao;
    }

    public void setDokumentasiPhotoDao(DokumentasiPhotoDao dokumentasiPhotoDao) {
        this.dokumentasiPhotoDao = dokumentasiPhotoDao;
    }

    public void setBlobRepository(BlobRepository blobRepository) {
        this.blobRepository = blobRepository;
    }

    public void setImageUtils(ImageUtils imageUtils) {
        this.imageUtils = imageUtils;
    }

    @Override
    public Dokumentasi findById(Long id) {
        return dokumentasiDao.findById(id);
    }

    @Override
    public Long countAlls() {
        return dokumentasiDao.countAlls();
    }

    @Override
    public Long countPublished() {
        Filter filter = new ValueFilter("published", QueryOperator.EQUALS, true, "published");
        return dokumentasiDao.countByFilter(filter);
    }

    @Override
    public List<Dokumentasi> get(Long start, Long count) {
        return dokumentasiDao.findAlls(start, count, "id", OrderDir.DESC);
    }

    @Override
    public List<Dokumentasi> getPublished(Long start, Long count) {
        Filter filter = new ValueFilter("published", QueryOperator.EQUALS, true, "published");
        return dokumentasiDao.findByFilter(filter, start, count, "id", OrderDir.DESC);
    }


    @Override
    public void saveTitlePicture(Long galleryId, InputStream is) throws IOException {
        blobRepository.store(BlobDataType.DOKUMENTASI_HEADLINE, galleryId, is);
    }

//    @Override
//    public InputStream getTitlePictureStream(Long galleryId) throws IOException {
//        return blobRepository.getTempFile(BlobDataType.DOKUMENTASI_HEADLINE, galleryId);
//    }

    @Override
    public Boolean hasTitlePicture(Long galleryId) {
        return blobRepository.isExist(BlobDataType.DOKUMENTASI_HEADLINE, galleryId);
    }

    @Override
    public void save(Dokumentasi gallery) {
        dokumentasiDao.save(gallery);
    }

    @Override
    public void removeById(Long id) {
        Dokumentasi dokumentasi = dokumentasiDao.findById(id);

        List<DokumentasiPhoto> photoList = dokumentasiPhotoDao.findByFilter(new ValueFilter("dokumentasi", QueryOperator.EQUALS, dokumentasi, "dokumentasi"));
        for (DokumentasiPhoto photo : photoList) {
            dokumentasiPhotoDao.remove(photo);
            blobRepository.remove(BlobDataType.DOKUMENTASI_PHOTO, photo.getId());
        }
        dokumentasiDao.remove(dokumentasi);
        blobRepository.remove(BlobDataType.DOKUMENTASI_HEADLINE, id);
    }

    @Override
    public void savePicture(DokumentasiPhoto picture, InputStream inputStream) throws IOException {
        dokumentasiPhotoDao.save(picture);
        blobRepository.store(BlobDataType.DOKUMENTASI_PHOTO, picture.getId(), inputStream);
    }

    @Override
    public void removePictureById(Long galleryPictureId) {
        dokumentasiPhotoDao.removeById(galleryPictureId);
        blobRepository.remove(BlobDataType.DOKUMENTASI_PHOTO, galleryPictureId);
    }

    @Override
    public Long countByKeyword(String keyword) {
        return dokumentasiDao.countByFilter(createKeywordFilter(keyword));
    }

    private ValueFilter createKeywordFilter(String keyword) {
        return new ValueFilter("title", QueryOperator.LIKE, keyword, "title");
    }

    @Override
    public List<Dokumentasi> findAlls(Long start, Long count) {
        return dokumentasiDao.findAlls(start, count, "id", OrderDir.DESC);
    }

    @Override
    public List<Dokumentasi> findByKeyword(String keyword, Long start, Long count) {
        return dokumentasiDao.findByFilter(createKeywordFilter(keyword), start, count, "id", OrderDir.DESC);
    }

    @Override
    public void removeByIds(Long[] ids) {
        for (Long id : ids) {
            Dokumentasi doc = dokumentasiDao.findById(id);
            removeDokumentasiPhoto(doc);
            dokumentasiDao.remove(doc);
        }
    }

    private void removeDokumentasiPhoto(Dokumentasi doc) {
        List<DokumentasiPhoto> photoList = dokumentasiPhotoDao.findByFilter(new ValueFilter("dokumentasi", QueryOperator.EQUALS, doc, "dokumentasi"));
        for (DokumentasiPhoto photo : photoList) {
            blobRepository.remove(BlobDataType.DOKUMENTASI_PHOTO, photo.getId());
            dokumentasiPhotoDao.remove(photo);
        }
    }

    @Override
    public void publishByIds(Long[] ids) {
        for (Long id : ids) {
            Dokumentasi doc = dokumentasiDao.findById(id);
            doc.setPublished(true);
            dokumentasiDao.save(doc);
        }
    }

    @Override
    public void unpublishByIds(Long[] ids) {
        for (Long id : ids) {
            Dokumentasi doc = dokumentasiDao.findById(id);
            doc.setPublished(false);
            dokumentasiDao.save(doc);
        }
    }

    @Override
    public void getResizedTitlePicture(Long id, Integer width, Integer height, OutputStream outputStream) throws IOException {
        File file = blobRepository.getTempFile(BlobDataType.DOKUMENTASI_HEADLINE, id);
        try {
            InputStream is = new FileInputStream(file);
            BufferedImage image = imageUtils.resizeImage(is, width, height);
            ImageIO.write(image, "jpeg", outputStream);
        } finally {
            file.delete();
        }
    }
}
