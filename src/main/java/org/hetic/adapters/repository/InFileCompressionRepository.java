package org.hetic.adapters.repository;

import org.hetic.domain.repository.CompressionRepository;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class InFileCompressionRepository implements CompressionRepository {
    private final String outputPath;

    public InFileCompressionRepository(String outputPath) {
        this.outputPath = outputPath;
    }

    @Override
    public void storeCompression(byte[] compressedData) throws IOException {
        String timestamp = String.valueOf(System.currentTimeMillis());
        try (OutputStream outputStream = new FileOutputStream(outputPath + "/compressed" + timestamp + ".bin")) {
            outputStream.write(compressedData);
        }
    }
}