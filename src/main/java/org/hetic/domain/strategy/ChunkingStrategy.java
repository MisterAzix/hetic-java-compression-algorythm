package org.hetic.domain.strategy;

import org.hetic.domain.model.Chunk;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface ChunkingStrategy {
    List<Chunk> chunk(InputStream input) throws IOException;
}
