package com.hww.els.common.util;

//import com.hww.els.ElsWebServiceApplication;
//import com.hww.els.webservice.entity.Article;
//import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//
///**
// * 搜索测试
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = ElsWebServiceApplication.class)
//@WebAppConfiguration
//public class BaseTest {
//
//    @Autowired
//    TransportClientRepository transportClientRepository;
//
//    @Test
//    public void saveDoc() throws Exception{
//        Article article = new Article();
//        article.setDescription("清晨的太阳");
//        article.setTitle("太阳升起");
//        article.setId("2017");
//        article.setHits(5);
//        article.setBeginDateString("2017/12/5");
//        System.out.println(transportClientRepository.saveDoc("blog1","type",article.getId(),article));
//    }
//
//    @Test
//    public void searchFullText(){
//        Article param = new Article();
//        param.setDescription("太阳");
//        ElasticSearchPage<Article> page= new ElasticSearchPage<Article>();
//        page.setPageSize(10);
//        HighlightBuilder highlight = new HighlightBuilder();
//        highlight.field("description").preTags("<span style=\"color:red\">").postTags("</span>");
//        page = transportClientRepository.searchFullText(param,page, highlight, "blog1");
//        for(Article aa : page.getRetList()){
//            System.out.println(aa.getId() +"===="+aa.getDescription()+"===title:=="+aa.getTitle());
//        }
//        System.out.println(page.getTotal());
//    }
//}
