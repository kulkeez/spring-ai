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

    @Value("classpath:/JMeter_Manual.pdf")
    private Resource pdfResource;

    /**
     * Constructor based dependency injection
     * 
     * @param vectorStore
     * @param jdbcClient
     */
    public DataLoader(VectorStore vectorStore, JdbcClient jdbcClient) {
        this.vectorStore = vectorStore;
        this.jdbcClient = jdbcClient;
    }
    

    /**
     * 
     * Method to load the PDF into the PG Vector Store
     * 
     */
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
            var vectors = textSplitter.apply(reader.get());

            // Log the dimensions of the vectors
/*             if (vectors != null && !vectors.isEmpty()) {
                log.info("Vector dimensions: {}", vectors.get(0).getDimensions());
            } else {
                log.warn("No vectors generated or vectors list is empty.");
            } */

            vectorStore.accept(vectors);

            log.info("PG Vector Store loaded with the PDF supplied.");
        }
        else {
            log.info("PG Vector Store already loaded with the PDF supplied.");
        }   
    }
}