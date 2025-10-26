package com.github.jtorleonstudios.gradle.gen.pack.mc.meta.file.dto;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public record PackMcmetaDTO(
  int packFormat,
  String description
) {



  /**
   * <a href="https://minecraft.fandom.com/wiki/Pack_format">
   *   https://minecraft.fandom.com/wiki/Pack_format
   * </a>
   * <pre><code>
   * {
   *   "pack": {
   *     "description": string,
   *     "pack_format": number
   *   }
   * }
   * </code></pre>
   * @return String
   */
  public String toJson() {
    var sb = new StringBuilder();
    var br = System.lineSeparator();

    sb.append('{').append(br);

    sb.append("  ").append(w("pack")).append(": {").append(br);

    sb.append("    ").append(w("pack_format")).append(": ").append(packFormat).append(',').append(br);

    sb.append("    ").append(w("description")).append(": ").append(w(description)).append(br);

    sb.append("  }").append(br);

    sb.append('}');

    return sb.toString();
  }

  @Contract(pure = true)
  private @NotNull String w(String v) {
    return '"' + v + '"';
  }

}
