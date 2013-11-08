package com.integrasolusi.pusda.sisda.service.sda;

import com.integrasolusi.pusda.sisda.dao.sda.BendunganDao;
import com.integrasolusi.query.util.OrderDir;
import com.integrasolusi.pusda.sisda.persistence.sda.Bendungan;
import com.integrasolusi.pusda.sisda.repository.BlobRepository;
import com.integrasolusi.pusda.sisda.repository.BlobDataType;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * User: pancara
 * Date: 7/30/12
 * Time: 6:38 PM
 */
public class BendunganServiceImpl implements BendunganService {
    private BendunganDao bendunganDao;
    private BlobRepository blobRepository;

    public void setBendunganDao(BendunganDao bendunganDao) {
        this.bendunganDao = bendunganDao;
    }

    public void setBlobRepository(BlobRepository blobRepository) {
        this.blobRepository = blobRepository;
    }

    @Override
    public Bendungan findById(Long id) {
        return bendunganDao.findById(id);
    }

    @Override
    public List<Bendungan> findAlls() {
        return bendunganDao.findAlls("id", OrderDir.ASC);
    }

    @Override
    public void getThumbPhoto(Long id, OutputStream outputStream) throws IOException {
        blobRepository.copyContent(BlobDataType.SDA_BENDUNGAN_THUMB, id, outputStream);
    }

    @Override
    public void removeById(Long id) {
        bendunganDao.removeById(id);
        blobRepository.remove(BlobDataType.SDA_BENDUNGAN_THUMB, id);
    }

    @Override
    public void save(Bendungan bendungan) {
        bendunganDao.save(bendungan);
    }

    @Override
    public void save(Bendungan bendungan, InputStream is) throws IOException {
        bendunganDao.save(bendungan);
        blobRepository.store(BlobDataType.SDA_BENDUNGAN_THUMB, bendungan.getId(), is);
    }
}
