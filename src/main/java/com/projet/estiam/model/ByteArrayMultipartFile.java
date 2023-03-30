package com.projet.estiam.model;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public class ByteArrayMultipartFile implements MultipartFile {

    private final byte[] content;
    private final String name;
    private final String originalFilename;
    private final MediaType contentType;

    public ByteArrayMultipartFile(byte[] content, String name, String originalFilename, MediaType contentType) {
        this.content = content;
        this.name = name;
        this.originalFilename = originalFilename;
        this.contentType = contentType;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getOriginalFilename() {
        return originalFilename;
    }

    @Override
    public String getContentType() {
        return contentType.toString();
    }

    @Override
    public boolean isEmpty() {
        return content.length == 0;
    }

    @Override
    public long getSize() {
        return content.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return content;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayResource(content).getInputStream();
    }

    @Override
    public void transferTo(java.io.File file) throws IOException, IllegalStateException {
        throw new UnsupportedOperationException();
    }

}
