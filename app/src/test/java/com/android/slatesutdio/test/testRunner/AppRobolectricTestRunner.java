package com.android.slatesutdio.test.testRunner;

import android.app.Application;
import com.android.slatesutdio.test.MockApplication;
import org.junit.runners.model.InitializationError;
import org.robolectric.DefaultTestLifecycle;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.TestLifecycle;
import org.robolectric.annotation.Config;
import org.robolectric.manifest.AndroidManifest;
import org.robolectric.res.FileFsFile;
import org.robolectric.util.Logger;

import java.lang.reflect.Method;


public class AppRobolectricTestRunner extends RobolectricTestRunner {

    public static final int DEFAULT_SDK = 27;
    private String BUILD_OUTPUT = "./app/build/intermediates";

    public AppRobolectricTestRunner(Class t) throws InitializationError {
        super(t);
    }

    @Override
    protected AndroidManifest getAppManifest(final Config config) {
        if (!FileFsFile.from(BUILD_OUTPUT).exists()) {
            BUILD_OUTPUT = "." + BUILD_OUTPUT;
            if (!FileFsFile.from(BUILD_OUTPUT).exists()) {
                throw new RuntimeException("Cannot find build folder");
            }
        }

        final String type = getType(config);
        final String packageName = getPackageName(config);

        final FileFsFile res;
        final FileFsFile assets;
        final FileFsFile manifest;

        // res/merged added in Android Gradle plugin 1.3-beta1
        if (FileFsFile.from(BUILD_OUTPUT, "res", "merged").exists()) {
            res = FileFsFile.from(BUILD_OUTPUT, "res", "merged", type);
        } else if (FileFsFile.from(BUILD_OUTPUT, "res").exists()) {
            res = FileFsFile.from(BUILD_OUTPUT, "res", type);
        } else {
            res = FileFsFile.from(BUILD_OUTPUT, "bundles", type, "res");
        }

        if (FileFsFile.from(BUILD_OUTPUT, "assets").exists()) {
            assets = FileFsFile.from(BUILD_OUTPUT, "assets", type);
        } else {
            assets = FileFsFile.from(BUILD_OUTPUT, "bundles", type, "assets");
        }

        if (FileFsFile.from(BUILD_OUTPUT, "manifests").exists()) {
            manifest = FileFsFile.from(BUILD_OUTPUT, "manifests", "full", type, "AndroidManifest.xml");
        } else {
            manifest = FileFsFile.from(BUILD_OUTPUT, "bundles", type, "AndroidManifest.xml");
        }

        Logger.debug("Robolectric assets directory: " + assets.getPath());
        Logger.debug("   Robolectric res directory: " + res.getPath());
        Logger.debug("   Robolectric manifest path: " + manifest.getPath());
        Logger.debug("    Robolectric package name: " + packageName);

        return new AndroidManifest(manifest, res, assets) {
            @Override
            public String getRClassName() {
                return com.android.slatestudio.test.R.class.getName();
            }
        };
    }

    private static String getType(final Config config) {

        return "debug";
    }

    private static String getPackageName(final Config config) {
        try {
            final String packageName = config.packageName();
            if (packageName != null && !packageName.isEmpty()) {
                return packageName;
            } else {
                return "com.android.slatestudio.test";
            }
        } catch (Throwable e) {
            return null;
        }
    }

    @Override
    protected Class<? extends TestLifecycle> getTestLifecycleClass() {
        return MyTestLifecycle.class;
    }

    public static class MyTestLifecycle extends DefaultTestLifecycle {
        @Override
        public Application createApplication(Method method, AndroidManifest appManifest, Config config) {
            return new MockApplication();
        }
    }

}