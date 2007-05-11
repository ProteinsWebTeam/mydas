package uk.ac.ebi.simpledas.datasource;

import uk.ac.ebi.simpledas.exceptions.DataSourceException;
import uk.ac.ebi.simpledas.exceptions.SegmentNotFoundException;
import uk.ac.ebi.simpledas.model.DasFeature;

import java.util.List;

/**
 * Created Using IntelliJ IDEA.
 * Date: 04-May-2007
 * Time: 14:34:22
 *
 * @author Phil Jones, EMBL-EBI, pjones@ebi.ac.uk
 *
 * If your datasource implements only the {@link uk.ac.ebi.simpledas.datasource.AnnotationDataSource} interface,
 * the SimpleDasServlet will handle restricting the features returned to
 * the start / stop coordinates in the request and you will only need to
 * implement the <code>getFeatures (String segmentReference) : List&lt;DasFeature&gt;</code>
 * method.
 *
 * If you also implement this interface, this will allow you to take control
 * of filtering by start / stop coordinates in your AnnotationDataSource.
 */
public interface RangeHandlingAnnotationDataSource extends AnnotationDataSource{

    /**
     * Implement this method to allow you to take control
     * of filtering by start / stop coordinates in your AnnotationDataSource.
     *
     * Useful if your DAS source includes massive segments.
     * @param segmentReference
     * @param start
     * @param stop
     * @return
     * @throws SegmentNotFoundException
     * @throws DataSourceException
     */
    public List<DasFeature> getFeatures(String segmentReference, int start, int stop) throws SegmentNotFoundException, DataSourceException;

}