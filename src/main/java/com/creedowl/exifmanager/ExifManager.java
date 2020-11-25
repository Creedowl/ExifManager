package com.creedowl.exifmanager;

import org.apache.commons.imaging.ImageFormats;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.ImageWriteException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.jpeg.exif.ExifRewriter;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfo;

import java.io.*;

public class ExifManager {
    private File image;

    public ExifManager() {
    }

    public ExifManager(File image) throws IOException, ImageReadException {
        this.loadImage(image);
    }

    public boolean loadImage(final File file) throws IOException, ImageReadException {
        if (Imaging.guessFormat(file) != ImageFormats.JPEG) {
            return false;
        }
        this.image = file;
        return true;
    }

    public TiffImageMetadata getAllExifMetadata() throws IOException, ImageReadException {
        var metaData = (JpegImageMetadata) Imaging.getMetadata(this.image);
        if (null == metaData) {
            return null;
        }
        return metaData.getExif();
    }

    public boolean showAllExifMetadata() throws IOException, ImageReadException {
        var exif = this.getAllExifMetadata();
        if (null != exif) {
            System.out.println(this.image.getName());
            System.out.println(exif);
            return true;
        }
        return false;
    }

    public void removeAllExifMetadata(File dst) throws IOException, ImageWriteException, ImageReadException {
        if (null == dst) {
            dst = this.image;
        }
        FileOutputStream fos = new FileOutputStream(dst);
        OutputStream os = new BufferedOutputStream(fos);
        new ExifRewriter().removeExifMetadata(this.image, os);
    }

    public boolean removeExifTag(File dst, TagInfo tagInfo) throws IOException, ImageReadException, ImageWriteException {
        var exif = this.getAllExifMetadata();
        if (null == exif) {
            System.out.println("Exif not found");
            return false;
        }
        var outputSet = exif.getOutputSet();
        if (null == outputSet) {
            System.out.println("MetaData not found");
            return false;
        }
        var exifDir = outputSet.getExifDirectory();
        if (null != exifDir) {
            exifDir.removeField(tagInfo);
        }
        if (null == dst) {
            dst = this.image;
        }
        FileOutputStream fos = new FileOutputStream(dst);
        OutputStream os = new BufferedOutputStream(fos);
        new ExifRewriter().updateExifMetadataLossless(this.image, os, outputSet);
        return true;
    }
}
