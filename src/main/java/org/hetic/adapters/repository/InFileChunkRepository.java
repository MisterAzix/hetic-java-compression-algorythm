package org.hetic.adapters.repository;

import org.hetic.domain.repository.ChunkRepository;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class InFileChunkRepository implements ChunkRepository {
    @Override
    public void storeChunks(List<byte[]> chunks) throws IOException {
        for (int i = 0; i < chunks.size(); i++) {
            try (OutputStream outputStream = new FileOutputStream("chunk_" + i + ".bin")) {
                outputStream.write(chunks.get(i));
            }
        }
    }
}
