/*
 * Copyright 2007 Philip Jones, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the mydas project, including source code,
 * downloads and documentation, please see:
 *
 * http://code.google.com/p/mydas/
 *
 */

package uk.ac.ebi.mydas.controller;

import uk.ac.ebi.mydas.model.DasFeature;
import uk.ac.ebi.mydas.model.Range;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created Using IntelliJ IDEA.
 * Date: 15-May-2007
 * Time: 14:12:18
 *
 * @author Phil Jones, EMBL-EBI, pjones@ebi.ac.uk
 *
 * This class is used to filter features, according to the details of the request from
 * the user.
 */
public class DasFeatureRequestFilter {

    private Collection<String> typeIds = null;

	private Collection<String> categoryIds = null;

	private Collection<String> featureIds = null;

    /**
     * @deprecated
     */
    private Collection<String> groupIds = null;


    private Integer maxbins=null;
    
    private Range rows=null;
    
	private String advanceQuery=null;
    
    private List<SegmentQuery> requestedSegments;
    
    private boolean paginated=false;
    
    private Integer totalFeatures;
    
    public Integer getTotalFeatures() {
		return totalFeatures;
	}
	public void setTotalFeatures(Integer totalFeatures) {
		this.totalFeatures = totalFeatures;
	}
	public boolean isPaginated(){
    	return paginated;
    }
    public void setPaginated(boolean paginated){
    	this.paginated=paginated;
    }
    public Range getRows() {
		return rows;
	}

	public void setRows(Range rows) {
		this.rows = rows;
	}
    
    public Collection<String> getTypeIds() {
		return typeIds;
	}

    public Collection<String> getCategoryIds() {
		return categoryIds;
	}

    public List<SegmentQuery> getRequestedSegments() {
		return requestedSegments;
	}

	public void setRequestedSegments(List<SegmentQuery> requestedSegments) {
		this.requestedSegments = requestedSegments;
	}

	public String getAdvanceQuery() {
		return advanceQuery;
	}

	public void setAdvanceQuery(String advanceQuery) {
		this.advanceQuery = advanceQuery;
	}

	public Integer getMaxbins() {
		return maxbins;
	}

	public void setMaxbins(Integer maxbins) {
		this.maxbins = maxbins;
	}


	DasFeatureRequestFilter() {
        // Does nothing - Collections are lazy instantiated and populated by add methods.
    }

    void addTypeId (String typeId){
        if (typeId != null){
            if (typeIds == null) typeIds = new ArrayList<String>();
            typeIds.add (typeId);
        }
    }

    void addCategoryId (String categoryId){
        if (categoryId != null){
            if (categoryIds == null) categoryIds = new ArrayList<String>();
            categoryIds.add (categoryId);
        }
    }

    void addFeatureId (String featureId){
        if (featureId != null){
            if (featureIds == null) featureIds = new ArrayList<String>();
            featureIds.add (featureId);
        }
    }

    /**
     * @deprecated
     */
    void addGroupId (String groupId){
        if (groupId != null){
            if (groupIds == null) groupIds = new ArrayList<String>();
            groupIds.add (groupId);
        }
    }

    boolean containsFeatureIds(){
        return featureIds != null && featureIds.size() > 0;
    }

	/**
	 * @deprecated
	 */    
    boolean containsGroupIds(){
        return groupIds != null && groupIds.size() > 0;
    }

    public Collection<String> getFeatureIds(){
        if (featureIds == null)
			return Collections.emptyList();
		else
			return featureIds;
    }

	/**
	 * @deprecated
	 */    
    Collection<String> getGroupIds(){
        if (groupIds == null)
			return Collections.emptyList();
		else
			return groupIds;
    }

    /**
     * Returns true if the tested DasFeature passes all the filters.
     * Method changed to public in order to be reach it by the serializer of the DasFeatureE
     * @param feature being the DasFeature under test.
     * @return a boolean - true if the DasFeature passes the filter, false otherwise
     */
    public boolean featurePasses(DasFeature feature){

        if (!(featureIds == null || (feature.getFeatureId() != null && featureIds.contains(feature.getFeatureId())))){
            return false;
        }

        if (!(typeIds == null || (feature.getType().getId() != null && typeIds.contains(feature.getType().getId())))){
            return false;
        }

        if (!(categoryIds == null || (feature.getType().getCategory() != null && categoryIds.contains(feature.getType().getCategory())))){
            return false;
        }

        //DAS1.6: groups are not part of the specs anymore, then the filter by group is eliminated 
//        if (groupIds != null){
//            if (feature.getGroups() == null){
//                return false;
//            }
//
//            boolean matchesGroupId = false;
//            for (DasGroup dasGroup : feature.getGroups()){
//                if (dasGroup.getGroupId() != null && groupIds.contains(dasGroup.getGroupId())){
//                    matchesGroupId = true;
//                }
//            }
//
//            if (! matchesGroupId)
//                return false;
//        }

        return true;
    }
}
