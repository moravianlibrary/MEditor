package cz.mzk.editor.server.DAO;

/**
 * @author: Martin Rumanek
 * @version: 25.2.13
 */
public interface OcrDao {

    /**
     * Cache - ocr (filled mainly from Quartz scheduler)
     * @param uuid
     * @param txtOcr
     * @param altoOcr
     * @throws DatabaseException
     */
    void insertOcr(String uuid, String txtOcr, String altoOcr) throws DatabaseException;
}
