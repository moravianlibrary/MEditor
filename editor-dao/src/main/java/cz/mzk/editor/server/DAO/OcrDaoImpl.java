package cz.mzk.editor.server.DAO;

import cz.mzk.editor.client.util.Constants;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author: Martin Rumanek
 * @version: 25.2.13
 */
public class OcrDaoImpl extends AbstractActionDAO implements OcrDao {

    private static final Logger LOGGER = Logger.getLogger(OcrDaoImpl.class.getPackage().toString());
    private static final String INSERT_OCR_ITEM = "INSERT INTO " + Constants.TABLE_OCR
            + " (uuid, ocr_txt, ocr_alto, timestamp) VALUES ((?),(?),(?),(CURRENT_TIMESTAMP)) ";

    @Override
    public void insertOcr(String uuid, String txtOcr, String altoOcr) throws DatabaseException {
        PreparedStatement insertSt = null;
        try {
            insertSt = getConnection().prepareStatement(INSERT_OCR_ITEM);
            insertSt.setString(1, uuid);
            insertSt.setString(2, txtOcr);
            insertSt.setString(3, altoOcr);

            if (insertSt.executeUpdate() == 1) {
                LOGGER.debug("DB has been updated: The OCR: " + uuid
                        + " has been inserted.");
            } else {
                LOGGER.error("DB has not been updated! " + insertSt);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } finally {
            closeConnection();
        }

    }
}
