package com.github.jtorleonstudios.gradle.gen.pack.mc.meta.file;

import org.gradle.api.Project;
import org.gradle.api.provider.Property;
import org.jetbrains.annotations.NotNull;

public abstract class GenPackMcmetaFileExt {
  /**
   * <p>
   * The name of this extension when it is added to a Gradle project.
   * </p>
   */
  public final static String NAME = "setupPackMcmeta";

  public static void register(
    @NotNull Project project
  ) {
    project
      .getExtensions()
      .create(
        GenPackMcmetaFileExt.NAME,
        GenPackMcmetaFileExt.class
      );
  }

  public GenPackMcmetaFileExt() {
    this.getDescription().convention("");
    this.getModId().convention("");
  }

  /**
   * OPTIONAL
   *
   * @return the description of the pack
   */
  public abstract Property<String> getDescription();

  /**
   * REQUIRED
   *
   * @return the number used to declare the pack format
   * <a href="https://minecraft.fandom.com/wiki/Pack_format">
   * Pack format
   * </a>
   */
  public abstract Property<Integer> getPackFormat();

  /**
   * OPTIONAL
   *
   * @return the mod identifier used to build the description
   */
  public abstract Property<String> getModId();

}