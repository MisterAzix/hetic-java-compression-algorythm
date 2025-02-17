package org.hetic.domain.repository;

import java.io.IOException;

import org.hetic.domain.model.CompressionMetrics;

public interface CompressionRepository {
    byte[] compress(byte[] data) throws IOException;
    byte[] decompress(byte[] compressedData) throws IOException;
    CompressionMetrics getMetrics();
}