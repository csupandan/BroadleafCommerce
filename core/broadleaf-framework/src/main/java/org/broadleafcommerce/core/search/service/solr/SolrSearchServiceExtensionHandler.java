/*
 * #%L
 * BroadleafCommerce Framework
 * %%
 * Copyright (C) 2009 - 2013 Broadleaf Commerce
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package org.broadleafcommerce.core.search.service.solr;

import org.apache.solr.client.solrj.SolrQuery;
import org.broadleafcommerce.common.extension.ExtensionHandler;
import org.broadleafcommerce.common.extension.ExtensionResultHolder;
import org.broadleafcommerce.common.extension.ExtensionResultStatusType;
import org.broadleafcommerce.core.catalog.domain.Category;
import org.broadleafcommerce.core.search.domain.Field;
import org.broadleafcommerce.core.search.domain.SearchCriteria;
import org.broadleafcommerce.core.search.domain.SearchFacetDTO;
import org.broadleafcommerce.core.search.domain.SearchFacetRange;
import org.broadleafcommerce.core.search.domain.SearchField;
import org.broadleafcommerce.core.search.domain.solr.FieldType;

import java.math.BigDecimal;
import java.util.List;


/**
 * @author Andre Azzolini (apazzolini), bpolster
 */                                      
public interface SolrSearchServiceExtensionHandler extends ExtensionHandler {

    /**
     * Returns a prefix if required for the passed in facet.
     */
    public ExtensionResultStatusType buildPrefixListForSearchableFacet(Field field, List<String> prefixList);

    /**
     * Returns a prefix if required for the passed in searchable field. 
     */
    public ExtensionResultStatusType buildPrefixListForSearchableField(Field field, FieldType searchableFieldType,
            List<String> prefixList);

    /**
     * Builds the search facet ranges for the provided dto.
     * 
     * @param context
     * @param dto
     * @param ranges
     */
    public ExtensionResultStatusType filterSearchFacetRanges(SearchFacetDTO dto, List<SearchFacetRange> ranges);

    /**
     * Provides an extension point to modify the SolrQuery.
     * 
     * @param context
     * @param query
     * @param qualifiedSolrQuery
     * @param facets
     * @param searchCriteria
     * @param defaultSort
     *      * @return
     */
    public ExtensionResultStatusType modifySolrQuery(SolrQuery query, String qualifiedSolrQuery,
            List<SearchFacetDTO> facets, SearchCriteria searchCriteria, String defaultSort);

    /**
     * In certain scenarios, the requested category id might not be the one that should be used in Solr.
     * If this method returns {@link ExtensionResultStatusType#HANDLED}, the value placed in the 0th element
     * in the returnContainer should be used.
     * 
     * @param category
     * @param returnContainer
     * @return the extension result status type
     */
    public ExtensionResultStatusType getCategoryId(Category category, Long[] returnContainer);

    /**
     * Accumulates a field boost value to the given query field if applicable
     *
     * @param searchField the SearchField to gather the field boost value for
     * @param globalBoost the Global boost for this field
     * @return the result of this handler
     */
    public ExtensionResultStatusType accumulateGlobalBoostValue(SearchField searchField, ExtensionResultHolder<BigDecimal> globalBoost);

    /**
     * Appends a boost function to the given query for this if applicable
     *
     *
     * @param query
     * @param searchField
     * @param queryBuilder
     * @return
     */
    public ExtensionResultStatusType appendToBoostQueryIfApplicable(SolrQuery query, SearchField searchField, StringBuilder queryBuilder);
}
