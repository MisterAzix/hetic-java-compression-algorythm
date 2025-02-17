package org.hetic;

import org.hetic.adapters.repository.ChunkCompressionRepository;
import org.hetic.adapters.repository.WholeFileCompressionRepository;
import org.hetic.adapters.strategy.RabinChunkingStrategy;
import org.hetic.domain.CompressionService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Main {
    public static void main(String[] args) throws IOException {
        String outputPath = "compressed_output";
        Files.createDirectories(new File(outputPath).toPath());
        
        File file = new File("src/main/resources/file.txt");

        WholeFileCompressionRepository wholeFileRepo = new WholeFileCompressionRepository(outputPath);
        ChunkCompressionRepository chunkRepo = new ChunkCompressionRepository(outputPath);
        RabinChunkingStrategy chunkingStrategy = new RabinChunkingStrategy();

        CompressionService compressionService = new CompressionService(
            wholeFileRepo,
            chunkRepo,
            chunkingStrategy
        );

        compressionService.processFile(file);
        System.out.println(compressionService.getCompressionReport());
    }
}