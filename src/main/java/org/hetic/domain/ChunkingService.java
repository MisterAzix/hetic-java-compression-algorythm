package org.hetic.domain;

import org.hetic.domain.model.Chunk;
import org.hetic.domain.repository.ChunkRepository;
import org.hetic.domain.strategy.ChunkingStrategy;

import java.io.*;
import java.util.List;

public class ChunkingService {
    private final ChunkRepository chunkRepository;
    private final ChunkingStrategy chunkingStrategy;


    public ChunkingService(ChunkRepository chunkRepository, ChunkingStrategy chunkingStrategy) {
        this.chunkRepository = chunkRepository;
        this.chunkingStrategy = chunkingStrategy;
    }

    public void processFile(File file) throws IOException {
        try (InputStream inputStream = new BufferedInputStream(new FileInputStream(file))) {
            List<Chunk> chunks = chunkingStrategy.chunk(inputStream);
            chunkRepository.storeChunks(chunks.stream()
                    .map(Chunk::getContent)
                    .toList());
        }
    }
}
