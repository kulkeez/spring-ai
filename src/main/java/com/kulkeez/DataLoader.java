package com.kulkeez;

import jakarta.annotation.PostConstruct;

import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * Component that loads the PDF into the PG Vector Store
 * 
 */
@Slf4j
@Component
public class DataLoader {

    private final VectorStore vectorStore;

    private final JdbcClient jdbcClient;

    @Value("classpath:/Test.pdf")
    private Resource pdfResource;

    public DataLoader(VectorStore vectorStore, JdbcClient jdbcClient) {
        this.vectorStore = vectorStore;
        this.jdbcClient = jdbcClient;
    }
    
    @PostConstruct
    public void init() {
        log.debug("init() called.");
        Integer count = jdbcClient.sql("select COUNT(*) from vector_store")
                            .query(Integer.class)
                            .single();

        log.info("No of Records in the PG Vector Store = {}", count);

        if(count == 0) {
            log.info("Loading the PDF into the PG Vector Store...");
            PdfDocumentReaderConfig config
                    = PdfDocumentReaderConfig.builder()
                            .withPagesPerDocument(1)
                            .build();

            PagePdfDocumentReader reader
                    = new PagePdfDocumentReader(pdfResource, config);

            var textSplitter = new TokenTextSplitter();
            vectorStore.accept(textSplitter.apply(reader.get()));

            log.info("PG Vector Store loaded with the PDF supplied.");
        }
    }
}