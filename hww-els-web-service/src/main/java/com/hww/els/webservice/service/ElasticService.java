package com.hww.els.webservice.service;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.index.reindex.DeleteByQueryRequestBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hww.els.common.enums.ElasticTypes;

import javax.annotation.PreDestroy;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

@Service
public class ElasticService {

    @Autowired
    private TransportClient client;

    @Value("${elastic.index.name}")
    private String indexName;


    public GetResponse get(ElasticTypes type, String id) {
        if (client == null) {
            return null;
        }
        GetResponse getResponse = client.prepareGet(indexName, type.getTypeName(), id).execute().actionGet();
        return getResponse;
    }
    
    
    public long clearType(String type) {
    	DeleteByQueryRequestBuilder builder=  DeleteByQueryAction.INSTANCE.newRequestBuilder(client)
    		        .source(indexName);
    		builder.request().types(type);
    		 BulkByScrollResponse response = builder.get();                                             
    		long deleted = response.getDeleted();  
    		return deleted;
    			
    }

    public UpdateResponse updateDocument(ElasticTypes type, String id, Map<String, Object> fields) {
        if (client == null) {
            return null;
        }
        try {
            UpdateRequest updateRequest = new UpdateRequest();
            updateRequest.index(indexName);
            updateRequest.type(type.getTypeName());
            updateRequest.id(id);

            XContentBuilder doc = jsonBuilder().startObject();
            if(fields.containsKey("geolocation")) {
            	doc.startObject("geolocation").field("lat", fields.get("latitude")).field("lon", fields.get("longitude")).endObject();
            }
            Iterator<Entry<String, Object>> it = fields.entrySet().iterator();
            while (it.hasNext()) {
                Entry<String, Object> entry = it.next();
                if("geolocation".equals(entry.getKey())) {
                	continue;
                }
                doc.field(entry.getKey(), entry.getValue());
            }
            updateRequest.doc(doc.endObject());
            return client.update(updateRequest).get();
        } catch (Throwable t) {
            t.printStackTrace();
            return null;
        }

    }

    public DeleteResponse deleteDocument(ElasticTypes type, String id) {
        if (client == null) {
            return null;
        }
        return client.prepareDelete(indexName, type.getTypeName(), id).execute().actionGet();
    }
//    client.prepareIndex(index, type).setSource(ret)
    
    public BulkResponse  bulkIndex(ElasticTypes type,List<Map<String, Object>> data) {
    	 if (client == null) {
             return null;
         }
    	 if(data==null||data.isEmpty()) {
    		 return null;
    	 }
    	BulkRequestBuilder bulkRequest = client.prepareBulk();
    	
    	data.stream().forEach(val->{
    		String id=String.valueOf(val.get("_xid"));
    		
    		bulkRequest.add(client.prepareIndex(indexName, type.getTypeName(),id).setSource(val));
        });
    	BulkResponse res=bulkRequest.execute().actionGet();
    	
    	return res;
    }
    /**
     * @param type
     * @param id     主键
     * @param fields
     * @return
     * @throws Throwable
     */
    public IndexResponse indexDocument(ElasticTypes type, String id, Map<String, Object> fields) {
        if (client == null) {
            return null;
        }
        try {
            XContentBuilder builder = jsonBuilder().startObject();
            if(fields.containsKey("geolocation")) {
            	builder.startObject("geolocation").field("lat", fields.get("latitude")).field("lon", fields.get("longitude")).endObject();
            }
            Iterator<Entry<String, Object>> it = fields.entrySet().iterator();
            while (it.hasNext()) {
            	Entry<String, Object> entry = it.next();

            	if("geolocation".equals(entry.getKey())) {
                	continue;
                }
                builder.field(entry.getKey(), entry.getValue());
            }
            builder.endObject();
    		
    		IndexRequest indexRequest = new IndexRequest(indexName, type.getTypeName(), id);
    		
            indexRequest.source(builder);
            return client.index(indexRequest).actionGet();
        } catch (Throwable t) {
            t.printStackTrace();
            return null;
        }
    }
    
    public IndexResponse indexDocument(ElasticTypes type,Map<String, Object> fields) {
        if (client == null) {
            return null;
        }
        try {
            XContentBuilder builder = jsonBuilder().startObject();
            if(fields.containsKey("geolocation")) {
            	builder.startObject("geolocation").field("lat", fields.get("latitude")).field("lon", fields.get("longitude")).endObject();
            }
            Iterator<Entry<String, Object>> it = fields.entrySet().iterator();
            while (it.hasNext()) {
            	Entry<String, Object> entry = it.next();

            	if("geolocation".equals(entry.getKey())) {
                	continue;
                }
                builder.field(entry.getKey(), entry.getValue());
            }
            builder.endObject();
    		
    		IndexRequest indexRequest = new IndexRequest(indexName, type.getTypeName());
    		
            indexRequest.source(builder);
            return client.index(indexRequest).actionGet();
        } catch (Throwable t) {
            t.printStackTrace();
            return null;
        }
    }

    public Client getClient() {
        return client;

    }

    @PreDestroy
    public void closeClient() {
        if (client != null) {
            client.close();
        }
    }


}
