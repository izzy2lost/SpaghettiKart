package com.izzy.kart;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.*;

public class AssetCopyUtil {

    public static void copyAssetsToExternal(Context context, String assetsFolderPath, String externalFolderPath) throws IOException {
        AssetManager assetManager = context.getAssets();
        String[] assetFiles = assetManager.list(assetsFolderPath);
        if (assetFiles == null) return;

        for (String assetFile : assetFiles) {
            // Always use / in asset path!
            String assetPath = assetsFolderPath.isEmpty() ? assetFile : assetsFolderPath + "/" + assetFile;
            String externalPath = externalFolderPath + File.separator + assetFile;

            String[] children = assetManager.list(assetPath);
            if (children != null && children.length > 0) {
                // It's a directory
                File externalDir = new File(externalPath);
                if (!externalDir.exists()) {
                    externalDir.mkdirs();
                }
                copyAssetsToExternal(context, assetPath, externalPath); // Recurse
            } else {
                // It's a file
                File externalFile = new File(externalPath);
                if (!externalFile.exists()) {
                    InputStream in = null;
                    OutputStream out = null;
                    try {
                        in = assetManager.open(assetPath);
                        out = new FileOutputStream(externalPath);
                        byte[] buffer = new byte[4096];
                        int read;
                        while ((read = in.read(buffer)) != -1) {
                            out.write(buffer, 0, read);
                        }
                        Log.i("AssetCopyUtil", "Copied: " + assetPath + " to " + externalPath);
                    } finally {
                        if (in != null) in.close();
                        if (out != null) out.close();
                    }
                } else {
                    Log.i("AssetCopyUtil", "Already exists, skipping: " + externalPath);
                }
            }
        }
    }

    public static void copyDirectory(File sourceDir, File targetDir) throws IOException {
        if (!targetDir.exists()) {
            targetDir.mkdirs();
        }
        for (File file : sourceDir.listFiles()) {
            File dest = new File(targetDir, file.getName());
            if (file.isDirectory()) {
                copyDirectory(file, dest);
            } else {
                copyFile(file, dest);
            }
        }
    }

    public static void copyFile(File source, File dest) throws IOException {
        try (InputStream in = new FileInputStream(source);
             OutputStream out = new FileOutputStream(dest)) {
            byte[] buf = new byte[64 * 1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        }
    }
}
