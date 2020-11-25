package com.creedowl.exifmanager;

import org.apache.commons.cli.*;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.ImageWriteException;
import org.apache.commons.imaging.formats.tiff.constants.ExifTagConstants;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfo;

import java.io.File;
import java.io.IOException;

public class Manager {
    private static final Options options = new Options();
    private final String operationDesc = "operations:\n" +
            "showAll: show all exif info\n" +
            "removeAll <destination file>: remove all exif info and save to destination file\n" +
            "removeTag <destination file> <Tag>: remove exif tag and save to destination file, tag = APERTURE";

    public static void main(String[] args) {
        var manager = new Manager();
        manager.parseCli(args);
    }

    public void parseCli(String[] args) {
        var parser = new DefaultParser();
        options.addOption(Option.builder("h").longOpt("help").desc("show help").build());
        options.addOption(Option.builder("f").longOpt("file").desc("load image").hasArg().build());
        options.addOption(Option.builder("o").longOpt("operation").desc(operationDesc).hasArg().build());
        try {
            var cli = parser.parse(options, args);
            var exifManager = new ExifManager();
            String path = "";
            if (cli.hasOption('h')) {
                new HelpFormatter().printHelp("Exif Manager", options);
            }
            if (cli.hasOption('f')) {
                path = cli.getOptionValue('f');
                var file = new File(path);
                if (!file.exists()) {
                    System.out.println("No such file");
                    return;
                }
                if (exifManager.loadImage(file)) {
                    System.out.printf("Load file %s successfully\n", path);
                } else {
                    System.out.printf("Failed to load file %s\n", path);
                    return;
                }
            }
            if (cli.hasOption('o')) {
                var arg = cli.getArgs();
                switch (cli.getOptionValue('o')) {
                    case "showAll":
                        System.out.println("Exif info:");
                        exifManager.showAllExifMetadata();
                        break;
                    case "removeAll":
                        File dst = null;
                        if (arg.length != 0) {
                            dst = new File(arg[0]);
                            if (!dst.exists()) {
                                dst.createNewFile();
                            }
                        }
                        exifManager.removeAllExifMetadata(dst);
                        System.out.println("Remove all Exif info successfully");
                        break;
                    case "removeTag":
                        dst = null;
                        if (arg.length != 0) {
                            dst = new File(arg[0]);
                            if (!dst.exists()) {
                                dst.createNewFile();
                            }
                        }
                        TagInfo tagInfo = null;
                        if (arg.length >= 2 && "APERTURE".equals(arg[1])) {
                            tagInfo = ExifTagConstants.EXIF_TAG_APERTURE_VALUE;
                        } else {
                            System.out.println("Missing tag");
                            return;
                        }
                        if (exifManager.removeExifTag(dst, tagInfo)) {
                            System.out.println("Remove exif tag successfully");
                        } else {
                            System.out.println("Failed to remove exif tag");
                        }
                        break;
                    default:
                        System.out.println("Missing Operation");
                }
            }
        } catch (ParseException e) {
            System.out.println("ERROR: " + e.getMessage());
            new HelpFormatter().printHelp("Exif Manager", options);
        } catch (ImageReadException | IOException | ImageWriteException e) {
            e.printStackTrace();
        }
    }
}
