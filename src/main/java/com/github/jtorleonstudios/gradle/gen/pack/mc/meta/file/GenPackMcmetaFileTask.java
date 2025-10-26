package com.github.jtorleonstudios.gradle.gen.pack.mc.meta.file;

import com.github.jtorleonstudios.gradle.gen.pack.mc.meta.file.dto.PackMcmetaDTO;
import com.github.jtorleonstudios.gradle.gen.pack.mc.meta.file.exceptions.MissingPackFormatGradleException;
import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;
import org.gradle.api.tasks.TaskAction;
import org.gradle.plugins.ide.idea.model.IdeaModel;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GenPackMcmetaFileTask extends DefaultTask {
  public final static String GEN_PATH_RESOURCE = "generated-packmcmeta-src/main/resources";
  public final static String TASK_NAME = "genPackMcmetaFile";

  public static void register(
    @NotNull Project project
  ) {
    var task = project
      .getTasks()
      .register(
        GenPackMcmetaFileTask.TASK_NAME,
        GenPackMcmetaFileTask.class,
        v -> {
          v.setGroup("genAssets");
          v.setDescription("Generate pack.mcmeta file");
        }
      );

    project
      .getTasks()
      .named("compileJava")
      .configure(v -> v.dependsOn(task));

    // Setup source set
    project
      .getExtensions()
      .getByType(SourceSetContainer.class)
      .named(
        SourceSet.MAIN_SOURCE_SET_NAME,
        sourceSet -> sourceSet
          .getResources()
          .srcDir(project
            .getLayout()
            .getBuildDirectory()
            .dir(GenPackMcmetaFileTask.GEN_PATH_RESOURCE))
      );

    // Setup IDEA
    project
      .getPlugins()
      .withId(
        "idea",
        plugin -> project
          .getExtensions()
          .configure(
            IdeaModel.class,
            idea -> idea
              .getModule()
              .getResourceDirs()
              .add(project
                .getLayout()
                .getBuildDirectory()
                .dir(GenPackMcmetaFileTask.GEN_PATH_RESOURCE)
                .get()
                .getAsFile()
              )
          )
      );
  }

  @TaskAction
  void run() {
    // clean old generation
    this.cleanGeneratedDirectory(GEN_PATH_RESOURCE);

    var resourceDirectory = getResourceDirectory();

    tryCreateDirectoryOrThrow(resourceDirectory);

    var ext = getProject()
      .getExtensions()
      .getByType(GenPackMcmetaFileExt.class);

    var packFormatExt = ext.getPackFormat().getOrNull();

    if (packFormatExt == null) {
      throw new MissingPackFormatGradleException();
    }

    var modIdExt = ext.getModId().get();
    var descriptionExt = ext.getDescription().get();

    if (descriptionExt.trim().isEmpty()) {
      descriptionExt = modIdExt.trim().isEmpty()
        ? "A Minecraft mod resource pack."
        : "Resources for the mod " + modIdExt + ".";
    }

    var dto = new PackMcmetaDTO(
      packFormatExt,
      descriptionExt
    );

    tryWriteFileOrThrow(
      resourceDirectory,
      "pack.mcmeta",
      dto.toJson()
    );
  }

  private @NotNull File getResourceDirectory() {
    return getProject()
      .getLayout()
      .getBuildDirectory()
      .dir(GEN_PATH_RESOURCE)
      .get()
      .getAsFile();
  }

  private static void tryCreateDirectoryOrThrow(
    @NotNull File dir
  ) {
    var resultMkDirs = dir.mkdirs();

    if (!resultMkDirs && !dir.exists()) {
      throw new GradleException(String.format(
        "Failed to generate output directory '%s'.%n" +
        "Possible reasons:%n" +
        " - Insufficient permissions to create directories%n" +
        " - A file with the same name already exists%n" +
        " - Path contains invalid characters%n%n" +
        "Please ensure that the path is writable and does not conflict with existing files.",
        dir.getAbsolutePath()
      ));
    }
  }

  private static void tryWriteFileOrThrow(
    @NotNull File directory,
    @NotNull String fileName,
    @NotNull String content
  ) {
    var file = new File(directory, fileName);
    try (FileWriter writer = new FileWriter(file)) {
      writer.write(content);
    } catch (IOException e) {
      throw new GradleException(e.getMessage(), e);
    }
  }

  private void cleanGeneratedDirectory(String directory) {
    var outputDir = getProject()
      .getLayout()
      .getBuildDirectory()
      .file(directory)
      .get()
      .getAsFile();

    if (outputDir.exists()) {
      getProject().delete(outputDir);
    }
  }

}
