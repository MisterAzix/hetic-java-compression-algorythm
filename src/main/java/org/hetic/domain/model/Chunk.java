package org.hetic.domain.model;

public class Chunk {
    private final byte[] content;
    private final int size;

    private Chunk(byte[] content) {
        this.content = content.clone();
        this.size = content.length;
    }

    public static Chunk from(byte[] content) {
        return new Chunk(content);
    }

    public byte[] getContent() {
        return content.clone();
    }

    public int getSize() {
        return size;
    }
}