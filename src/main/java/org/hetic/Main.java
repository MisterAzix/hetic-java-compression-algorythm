package org.hetic;

import org.hetic.adapters.InFileChunkRepository;
import org.hetic.adapters.InMemoryChunkRepository;
import org.hetic.domain.ChunkingService;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        File file = new File("src/main/resources/file.txt");

        InMemoryChunkRepository inMemoryChunkRepository = new InMemoryChunkRepository();

        ChunkingService inMemoryService = new ChunkingService(inMemoryChunkRepository);
        inMemoryService.processFile(file);

        inMemoryChunkRepository.getStorage().forEach(chunk -> System.out.println(chunk.length));
    }
}