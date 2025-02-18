package org.hetic;

import org.hetic.adapters.strategy.ZstdCompressionStrategy;
import org.hetic.adapters.strategy.RabinChunkingStrategy;
import org.hetic.adapters.repository.InFileCompressionRepository;
import org.hetic.domain.CompressionService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Main {
    public static void main(String[] args) throws IOException {
        String outputPath = "compressed_output";
        Files.createDirectories(new File(outputPath).toPath());
        
        File file = new File("src/main/resources/file.txt");

        
        RabinChunkingStrategy chunkingStrategy = new RabinChunkingStrategy();
        ZstdCompressionStrategy compressionStrategy = new ZstdCompressionStrategy();
        InFileCompressionRepository compressionRepository = new InFileCompressionRepository(outputPath);


        CompressionService compressionService = new CompressionService(
            compressionStrategy,
            compressionRepository,
            chunkingStrategy
        );

        // compressionService.processChunks(file);
        compressionService.processWholeFile(file);
        // System.out.println(compressionService.getCompressionReport());
    }}