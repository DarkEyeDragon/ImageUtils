package me.darkeyedragon.imageutils.client.entity;

import me.darkeyedragon.imageutils.client.util.OutputStreamProgress;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class FileEntity extends org.apache.http.entity.FileEntity {

    private OutputStreamProgress outstream;

    public FileEntity(File file, String contentType) {
        super(file, contentType);
    }

    @Override
    public void writeTo(OutputStream outstream) throws IOException {
        this.outstream = new OutputStreamProgress(outstream);
        super.writeTo(this.outstream);
    }


    public int getProgress() {
        if (outstream == null) {
            return 0;
        }
        long contentLength = getContentLength();
        if (contentLength <= 0) { // Prevent division by zero and negative values
            return 0;
        }
        long writtenLength = outstream.getWrittenLength();
        return (int) (100 * writtenLength / contentLength);
    }
}
