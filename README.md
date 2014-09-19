# immutant-transit

A library that allows you to use
[Transit](https://github.com/cognitect/transit-format) as a read/write
codec with Immutant 2.x messaging and caching.

This is a standalone project, but cannot be used without
Immutant 2.x. Once Transit becomes stable, this will likely move in to
Immutant proper.

## Usage

You'll need to bring in your own version of Transit ("0.8.255" or
later, see [project.clj](project.clj) for the version we test
against).

This won't work with Immutant 2.0.0-alpha1 - you'll need to run an
[incremental build](http://immutant.org/builds/2x/) (#296 or newer,
see [project.clj](project.clj) for the version we test against).

See the [example application](example-app/README.md) for sample usage
with Immutant.

For a Leiningen project:

    [org.immutant/immutant-transit "0.2.0"]

For a maven project:

    <dependency>
      <groupId>org.immutant</groupId>
      <artifactId>immutant-transit</artifactId>
      <version>0.1.0</version>
    </dependency>

## License

Immutant is licensed under the Apache License, v2. See
[LICENSE](LICENSE) for details.
