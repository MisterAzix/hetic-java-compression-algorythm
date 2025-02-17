package org.hetic.adapters.strategy;

import org.hetic.domain.repository.CompressionRepository;
import org.hetic.domain.model.CompressionMetrics;
import com.github.luben.zstd.Zstd;



public class ZstdCompressionStrategy implements CompressionRepository {
    private long totalOriginalSize = 0;
    private long totalCompressedSize = 0;

    @Override
    public byte[] compress(byte[] data) {
        totalOriginalSize += data.length;
        byte[] compressed = Zstd.compress(data);
        totalCompressedSize += compressed.length;
        return compressed;
    }

    @Override
    public byte[] decompress(byte[] compressedData) {
        return Zstd.decompress(compressedData, 
            (int) Zstd.decompressedSize(compressedData));
    }

    @Override
    public CompressionMetrics getMetrics() {
        return new CompressionMetrics(totalOriginalSize, totalCompressedSize);
    }
}