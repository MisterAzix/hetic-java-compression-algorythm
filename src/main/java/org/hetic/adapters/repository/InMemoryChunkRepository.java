package org.hetic.adapters.repository;

import org.hetic.domain.repository.ChunkRepository;

import java.util.ArrayList;
import java.util.List;

public class InMemoryChunkRepository implements ChunkRepository {
    private final List<byte[]> storage = new ArrayList<>();

    @Override
    public void storeChunks(List<byte[]> chunks) {
        storage.addAll(chunks);
    }

    public List<byte[]> getStorage() {
        return storage;
    }
}
