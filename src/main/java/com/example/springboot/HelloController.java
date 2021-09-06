package com.example.springboot;

import com.google.template.soy.SoyFileSet;
import com.google.template.soy.tofu.SoyTofu;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;

@Controller
public class HelloController {

    private static final String TEMPLATE_NAME = "com.jivesoftware.soy.hello";
    public static final String SOY_LOCATION = "classpath:/static/soy/";

    @GetMapping("/")
    @ResponseBody
    public String index() throws IOException {
        final File soyTemplatesLocation = new FileSystemResourceLoader()
                .getResource(SOY_LOCATION)
                .getFile();
        final SoyFileSet.Builder soyFileSetBuilder = SoyFileSet.builder();
        populateBuilderWithSoyTemplates(soyFileSetBuilder, soyTemplatesLocation);
        final SoyFileSet soyFileSet = soyFileSetBuilder.build();
        final SoyTofu soyTofu = soyFileSet.compileToTofu();

        return soyTofu.newRenderer(TEMPLATE_NAME).render();
    }

    private void populateBuilderWithSoyTemplates(final SoyFileSet.Builder soyFiles,
            final File directory) {
        final File[] files = directory.listFiles();
        if (files != null) {
            for (final File file : files) {
                if (file.isFile()) {
                    if (file.getName().endsWith(".soy")) {
                        soyFiles.add(file);
                    }
                } else if (file.isDirectory()) {
                    populateBuilderWithSoyTemplates(soyFiles, file);
                }
            }
        } else {
            throw new IllegalArgumentException(
                    "Failed to populate SoyFileSet.Builder with soy templates from directory [" + directory + "]. Check that it is" + " indeed a directory " +
                            "and not empty!");
        }
    }
}
