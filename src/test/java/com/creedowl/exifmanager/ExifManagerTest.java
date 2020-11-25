package com.creedowl.exifmanager;

import org.apache.commons.imaging.formats.tiff.constants.ExifTagConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class ExifManagerTest {
    // jpeg with metadata
    private final File p1 = new File("src/test/resources/p1.jpg");
    // png
    private final File p2 = new File("src/test/resources/p2.png");
    // jpeg without metadata
    private final File p3 = new File("src/test/resources/p3.jpg");

    private ExifManager manager = null;

    ExifManagerTest() throws Exception {
    }

    @BeforeEach
    void setUp() throws Exception {
        var real_manager = new ExifManager(p1);
        manager = spy(real_manager);
        doNothing().when(manager).removeAllExifMetadata(any());
        when(manager.showAllExifMetadata()).thenReturn(true).thenReturn(false);
    }

    @Test
    void loadImage() throws Exception {
        assertTrue(manager.loadImage(p1));
        assertFalse(manager.loadImage(p2));
    }

    @Test
    void getAllExifMetadata() throws Exception {
        manager.loadImage(p1);
        assertNotNull(manager.getAllExifMetadata());
        manager.loadImage(p3);
        assertNull(manager.getAllExifMetadata());
    }

    @Test
    void showAllExifMetadata() throws Exception {
        manager.loadImage(p1);
        assertTrue(manager.showAllExifMetadata());
        manager.loadImage(p3);
        assertFalse(manager.showAllExifMetadata());
    }

    @Test
    void removeAllExifMetadata() throws Exception {
        final File tempFile = File.createTempFile("test", ".jpg");
        manager.loadImage(p1);
        manager.removeAllExifMetadata(tempFile);
    }

    @Test
    void removeExifTag() throws Exception {
        final File tempFile = File.createTempFile("test", ".jpg");
        manager.loadImage(p1);
        assertTrue(manager.removeExifTag(tempFile, ExifTagConstants.EXIF_TAG_APERTURE_VALUE));
        manager.loadImage(p3);
        assertFalse(manager.removeExifTag(tempFile, ExifTagConstants.EXIF_TAG_APERTURE_VALUE));
    }
}