package com.github.jtorleonstudios.gradle.gen.pack.mc.meta.file.exceptions;

import org.gradle.api.GradleException;

public class MissingPackFormatGradleException extends GradleException {

  public MissingPackFormatGradleException() {
    super("""
          Missing required value: 'packFormat' was not specified in the plugin extension.
          The 'pack_format' determines which Minecraft version the generated pack targets.
          You must define it explicitly, for example:
          ```
          setupPackMcmeta {
              packFormat = 15
          }
          ```
          For more information about valid values, see:
          - https://minecraft.fandom.com/wiki/Pack_format
          - https://misode.github.io/pack-mcmeta/
          """);
  }

}
