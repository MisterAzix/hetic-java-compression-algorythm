package org.hetic.domain;

import org.hetic.domain.model.Chunk;
import org.hetic.domain.model.CompressionMetrics;
import org.hetic.domain.repository.CompressionRepository;
import org.hetic.domain.strategy.ChunkingStrategy;

import java.io.*;
import java.nio.file.Files;
import java.util.List;

public class CompressionService {
    private final CompressionRepository wholeFileRepository;
    private final CompressionRepository chunkRepository;
    private final ChunkingStrategy chunkingStrategy;

    public CompressionService(
            CompressionRepository wholeFileRepository,
            CompressionRepository chunkRepository,
            ChunkingStrategy chunkingStrategy) {
        this.wholeFileRepository = wholeFileRepository;
        this.chunkRepository = chunkRepository;
        this.chunkingStrategy = chunkingStrategy;
    }

    public void processFile(File file) throws IOException {
        processWholeFile(file);
        processChunks(file);
    }

    private void processWholeFile(File file) throws IOException {
        byte[] fileContent = Files.readAllBytes(file.toPath());
        wholeFileRepository.compress(fileContent);
    }

    private void processChunks(File file) throws IOException {
        try (InputStream inputStream = new BufferedInputStream(new FileInputStream(file))) {
            List<Chunk> chunks = chunkingStrategy.chunk(inputStream);
            for (Chunk chunk : chunks) {
                chunkRepository.compress(chunk.getContent());
            }
        }
    }

    public String getCompressionReport() {
        CompressionMetrics wholeFileMetrics = wholeFileRepository.getMetrics();
        CompressionMetrics chunkedMetrics = chunkRepository.getMetrics();

        return String.format("""
            Compression Report:
            ----------------
            Whole File:
              Original size: %d bytes
              Compressed size: %d bytes
              Compression ratio: %.2f%%
            
            Chunked:
              Original size: %d bytes
              Compressed size: %d bytes
              Compression ratio: %.2f%%
            """,
            wholeFileMetrics.originalSize(),
            wholeFileMetrics.compressedSize(),
            wholeFileMetrics.getCompressionRatio() * 100,
            chunkedMetrics.originalSize(),
            chunkedMetrics.compressedSize(),
            chunkedMetrics.getCompressionRatio() * 100
        );
    }
}