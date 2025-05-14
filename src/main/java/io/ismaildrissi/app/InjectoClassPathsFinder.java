package io.ismaildrissi.app;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InjectoClassPathsFinder {
    public Set<String> getClassPaths() throws IOException {
        Set<String> classNames = new HashSet<>();
        String classpath = Main.class.getName();
        classpath = System.getProperty("java.class.path");
        String[] paths = classpath.split(File.pathSeparator);

            for(String path: paths) {
            File file = new File(path);
            if (file.isDirectory()) {
                classNames.addAll(findClassesInDirectory(file, path));
            }
        }

        List<Class<?>> loadedClasses = new ArrayList<>();
            for (String className : classNames) {
            try {
                Class<?> clazz = Class.forName(className, false, ClassLoader.getSystemClassLoader());
                loadedClasses.add(clazz);
            } catch (Throwable t) {
                // Ignore classes that fail to load (dependencies, errors, etc.)
            }
        }

        return classNames;
    }

    public Set<String> findClassesInDirectory(File directory, String basePath) throws IOException {
        Set<String> classNames = new HashSet<>();
        Files.walk(directory.toPath()).filter(path -> path.toString().endsWith(".class")).forEach(path -> {
            String fullPath = path.toFile().getAbsolutePath();
            String className = fullPath
                    .substring(basePath.length() + 1)
                    .replace(File.separatorChar, '.')
                    .replaceAll("\\.class$", "");
            classNames.add(className);
        });
        return classNames;
    }

}
