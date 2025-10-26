# Gradle Plugin : Generate pack.mcmeta file

This plugin automatically generates a `pack.mcmeta` for your project.

- **License:** MIT
- **Author:** JTorleon Studios Team
- **Public Maven Repository:** https://jtorleon-studios-team-github-io.pages.dev/

## Installation

Add the public Maven repository in your `settings.gradle`:

```groovy
pluginManagement {
  repositories {
    maven {
      url = 'https://jtorleon-studios-team-github-io.pages.dev/maven'
    }
  }
}

```

Then apply the plugin in your `build.gradle`:


```groovy
plugin {
  id 'gradle-gen-packmcmeta-file' version "1.0.0"
}
```

Configuration:

```groovy
setupMixinsFile {
  packFormat = 15                // number - required !

  // Optional fields: 
  description = "My Awesome Mod" // String — optional, overrides default description
  modId = "example"              // String — optional, only used if 'description' is not set
 
}
```

> [!NOTE]
> If a custom `description` is defined, it will be used directly in the generated pack.mcmeta.
> Otherwise, if a `modId` is provided, a generic description will be automatically generated based on it.
>
> For example, if `modId` is set to `libraryferret` and no `description` is specified, the generated 
> description will be: `Resources for the mod libraryferret.`.
> 
> If neither `description` nor `modId` are specified the generated description 
> will default to `A Minecraft mod resource pack.`.
