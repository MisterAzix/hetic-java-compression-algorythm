package org.hetic.domain.repository;

import java.io.IOException;
import java.util.List;

public interface ChunkRepository {
    void storeChunks(List<byte[]> chunks) throws IOException;
}
