package org.hetic.domain;

import org.hetic.domain.repository.ChunkRepository;
import org.rabinfingerprint.fingerprint.RabinFingerprintLongWindowed;
import org.rabinfingerprint.polynomial.Polynomial;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ChunkingService {
    private static final int MIN_CHUNK_SIZE = 2048;    // 2KB minimum
    private static final int TARGET_CHUNK_SIZE = 8192;  // 8KB target
    private static final int MAX_CHUNK_SIZE = 16384;    // 16KB maximum
    private static final int WINDOW_SIZE = 48;          // Rolling window size
    private static final long MASK = (1L << 13) - 1;    // 13-bit mask for boundary detection
    private final ChunkRepository chunkRepository;


    public ChunkingService(ChunkRepository chunkRepository) {
        this.chunkRepository = chunkRepository;
    }

    public void processFile(File file) throws IOException {
        List<byte[]> chunks = chunkFile(file);
        chunkRepository.storeChunks(chunks);
    }

    private List<byte[]> chunkFile(File file) throws IOException {
        List<byte[]> chunks = new ArrayList<>();
        Polynomial polynomial = Polynomial.createFromLong(10923124345206883L);
        RabinFingerprintLongWindowed rabin = new RabinFingerprintLongWindowed(polynomial, WINDOW_SIZE);

        try (InputStream inputStream = new BufferedInputStream(new FileInputStream(file))) {
            ByteArrayOutputStream currentChunk = new ByteArrayOutputStream();
            byte[] buffer = new byte[WINDOW_SIZE];
            int bytesRead;
            int currentSize = 0;

            bytesRead = inputStream.read(buffer);
            if (bytesRead > 0) {
                for (int i = 0; i < bytesRead; i++) {
                    rabin.pushByte(buffer[i]);
                    currentChunk.write(buffer[i]);
                }
                currentSize = bytesRead;
            }

            while ((bytesRead = inputStream.read(buffer)) > 0) {
                for (int i = 0; i < bytesRead; i++) {
                    byte b = buffer[i];
                    rabin.pushByte(b);
                    currentChunk.write(b);
                    currentSize++;

                    if (currentSize >= MIN_CHUNK_SIZE) {
                        if (isChunkBoundary(rabin) || currentSize >= MAX_CHUNK_SIZE) {
                            chunks.add(currentChunk.toByteArray());
                            currentChunk.reset();
                            currentSize = 0;
                        }
                    }
                }
            }

            if (currentSize > 0) {
                chunks.add(currentChunk.toByteArray());
            }
        }

        return chunks;
    }

    private boolean isChunkBoundary(RabinFingerprintLongWindowed rabin) {
        return (rabin.getFingerprintLong() & MASK) == 0;
    }
}
