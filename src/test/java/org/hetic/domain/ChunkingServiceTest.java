package org.hetic.domain;

import org.hetic.adapters.InMemoryChunkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChunkingServiceTest {
    private ChunkingService chunkingService;
    private InMemoryChunkRepository inMemoryChunkRepository;

    @BeforeEach
    void setUp() {
        inMemoryChunkRepository = new InMemoryChunkRepository();
        chunkingService = new ChunkingService(inMemoryChunkRepository);
    }

    @Test
    void should_produce_expected_chunk_sizes() throws IOException {
        // Given
        File file = new File("src/test/resources/test.txt");
        int[] expectedSizes = {16384, 9208, 2938, 15123, 3172, 41};
        byte[] content = new byte[46866];
        Arrays.fill(content, (byte) 1);

        // When
        chunkingService.processFile(file);

        // Then
        List<byte[]> chunks = inMemoryChunkRepository.getStorage();
        assertEquals(expectedSizes.length, chunks.size(), "Number of chunks should match");
        for (int i = 0; i < chunks.size(); i++) {
            assertEquals(expectedSizes[i], chunks.get(i).length,
                    "Chunk " + i + " should have expected size");
        }
    }
}