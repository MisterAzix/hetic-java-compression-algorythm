package org.hetic;

import org.hetic.adapters.repository.InMemoryChunkRepository;
import org.hetic.adapters.strategy.RabinChunkingStrategy;
import org.hetic.domain.ChunkingService;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        File file = new File("src/main/resources/file.txt");

        InMemoryChunkRepository inMemoryChunkRepository = new InMemoryChunkRepository();
        RabinChunkingStrategy rabinChunkingStrategy = new RabinChunkingStrategy();

        ChunkingService inMemoryService = new ChunkingService(inMemoryChunkRepository, rabinChunkingStrategy);
        inMemoryService.processFile(file);

        inMemoryChunkRepository.getStorage().forEach(chunk -> System.out.println(chunk.length));
    }
}