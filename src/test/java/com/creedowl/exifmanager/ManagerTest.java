package com.creedowl.exifmanager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class ManagerTest {
    private static Manager manager;
    PrintStream console = null;
    ByteArrayOutputStream bytes = null;

    @BeforeAll
    static void beforeAll() {
        manager = new Manager();
    }

    @BeforeEach
    void setUp() {
        bytes = new ByteArrayOutputStream();
        console = System.out;
        System.setOut(new PrintStream(bytes));
    }

    @AfterEach
    void tearDown() {
        System.setOut(console);
    }

    @Test
    void main() {
        Manager.main(new String[]{"-h"});
    }

    @Test
    void parseCli_help() {
        manager.parseCli(new String[]{"-h"});
        assertEquals("usage: Exif Manager\n" +
                " -f,--file <arg>        load image\n" +
                " -h,--help              show help\n" +
                " -o,--operation <arg>   operations:\n" +
                "                        showAll: show all exif info\n" +
                "                        removeAll <destination file>: remove all exif info\n" +
                "                        and save to destination file\n" +
                "                        removeTag <destination file> <Tag>: remove exif\n" +
                "                        tag and save to destination file, tag = APERTURE\n", bytes.toString());
    }

    @Test
    void parseCli_file() {
        manager.parseCli(new String[]{"-f", "src/test/resources/p1.jpg"});
        assertEquals("Load file src/test/resources/p1.jpg successfully\n", bytes.toString());
        bytes.reset();
        manager.parseCli(new String[]{"-f", "naive"});
        assertEquals("No such file\n", bytes.toString());
        bytes.reset();
        manager.parseCli(new String[]{"-f", "src/test/resources/p2.png"});
        assertEquals("Failed to load file src/test/resources/p2.png\n", bytes.toString());
    }

    @Test
    void parseCli_operation() {
        manager.parseCli(new String[]{"-f", "src/test/resources/p1.jpg", "-o", "showAll"});
        assertEquals("Load file src/test/resources/p1.jpg successfully\n" +
                "Exif info:\n" +
                "p1.jpg\n" +
                "\tRoot: \n" +
                "\t\tImageWidth: 4032\n" +
                "\t\tModel: 'MI 8'\n" +
                "\t\tImageLength: 2268\n" +
                "\t\tOrientation: 6\n" +
                "\t\tDateTime: '2020:10:27 19:01:56'\n" +
                "\t\tYCbCrPositioning: 1\n" +
                "\t\tExifOffset: 206\n" +
                "\t\tResolutionUnit: 2\n" +
                "\t\tGPSInfo: 2935\n" +
                "\t\tXResolution: 72\n" +
                "\t\tYResolution: 72\n" +
                "\t\tMake: 'Xiaomi'\n" +
                "\n" +
                "\tExif: \n" +
                "\t\tUnknown Tag (0x9aaa): 27, -35, -61, -58, 67, -78, -12, -85, 67, 70, 22, -106, 105, 87, -122, -31, -55, 22, 35, 22, 125, -43, 68, -113, -98, 17, 97, -13, -42, -99, 67, -128, -53, 105, 99, 118, -51, 91, -38, 35, 26, -57, -26, 54, -7, 57, -2, 29, 12, 106, -126... (2048)\n" +
                "\t\tPhotographicSensitivity: 701\n" +
                "\t\tExposureProgram: 2\n" +
                "\t\tFNumber: 180/100 (1.8)\n" +
                "\t\tExposureTime: 1/24 (0.042)\n" +
                "\t\tUnknown Tag (0x9999): '{\"mirror\":false,\"?sensor_type\":\"rear\",\"Hdr\":\"off\",\"OpMode\":32769,\"AIScene\":0,\"FilterId\":66048,\"ZoomMultiple\":1}'\n" +
                "\t\tSensingMethod: 1\n" +
                "\t\tUnknown Tag (0x8895): 0\n" +
                "\t\tSubSecTimeDigitized: '757204'\n" +
                "\t\tSubSecTimeOriginal: '757204'\n" +
                "\t\tSubSecTime: '757204'\n" +
                "\t\tFocalLength: 4216/1000 (4.216)\n" +
                "\t\tFlash: 16\n" +
                "\t\tLightSource: 0\n" +
                "\t\tMeteringMode: 2\n" +
                "\t\tSceneCaptureType: 0\n" +
                "\t\tInteropOffset: 2905\n" +
                "\t\tFocalLengthIn35mmFormat: 21\n" +
                "\t\tMaxApertureValue: 169/100 (1.69)\n" +
                "\t\tDateTimeDigitized: '2020:10:27 19:01:56'\n" +
                "\t\tExposureCompensation: 0\n" +
                "\t\tExifImageLength: 2268\n" +
                "\t\tWhiteBalance: 0\n" +
                "\t\tDateTimeOriginal: '2020:10:27 19:01:56'\n" +
                "\t\tBrightnessValue: -334/100 (-3.34)\n" +
                "\t\tExifImageWidth: 4032\n" +
                "\t\tExposureMode: 0\n" +
                "\t\tApertureValue: 169/100 (1.69)\n" +
                "\t\tComponentsConfiguration: 1, 2, 3, 0\n" +
                "\t\tColorSpace: 1\n" +
                "\t\tSceneType: 1\n" +
                "\t\tShutterSpeedValue: 4584/1000 (4.584)\n" +
                "\t\tExifVersion: 48, 50, 50, 48\n" +
                "\t\tFlashpixVersion: 48, 49, 48, 48\n" +
                "\n" +
                "\tInteroperability: \n" +
                "\t\tInteroperabilityIndex: 'R98'\n" +
                "\t\tInteroperabilityVersion: 48, 49, 48, 48\n" +
                "\n" +
                "\tGps: \n" +
                "\t\tGPSLatitudeRef: 'N'\n" +
                "\t\tGPSLatitude: 31, 56, 245147/10000 (24.515)\n" +
                "\t\tGPSLongitudeRef: 'E'\n" +
                "\t\tGPSLongitude: 118, 47, 40884/10000 (4.088)\n" +
                "\t\tGPSAltitudeRef: 0\n" +
                "\t\tGPSAltitude: 0\n" +
                "\t\tGPSTimeStamp: 11, 1, 53\n" +
                "\t\tGPSProcessingMethod: 'CELLID'\n" +
                "\t\tGPSDateStamp: '2020:10:27'\n" +
                "\n", bytes.toString());
        bytes.reset();
        manager.parseCli(new String[]{"-f", "src/test/resources/p1.jpg", "-o", "removeAll", "/tmp/test.jpg"});
        assertEquals("Load file src/test/resources/p1.jpg successfully\n" +
                "Remove all Exif info successfully\n", bytes.toString());
        bytes.reset();
        manager.parseCli(new String[]{"-f", "src/test/resources/p1.jpg", "-o", "removeTag", "/tmp/test.jpg"});
        assertEquals("Load file src/test/resources/p1.jpg successfully\n" +
                "Missing tag\n", bytes.toString());
        bytes.reset();
        manager.parseCli(new String[]{"-f", "src/test/resources/p1.jpg", "-o", "removeTag", "/tmp/test.jpg", "APERTURE"});
        assertEquals("Load file src/test/resources/p1.jpg successfully\n" +
                "Remove exif tag successfully\n", bytes.toString());
        bytes.reset();
        manager.parseCli(new String[]{"-f", "src/test/resources/p3.jpg", "-o", "removeTag", "/tmp/test.jpg", "APERTURE"});
        assertEquals("Load file src/test/resources/p3.jpg successfully\n" +
                "Exif not found\n" +
                "Failed to remove exif tag\n", bytes.toString());
    }

    @Test
    void parseCli_default() {
        manager.parseCli(new String[]{"-f", "src/test/resources/p1.jpg", "-o", "naive"});
        assertEquals("Load file src/test/resources/p1.jpg successfully\n" +
                "Missing Operation\n", bytes.toString());
    }
}