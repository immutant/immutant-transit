# immutant-transit

A library that allows you to use
[Transit](https://github.com/cognitect/transit-format) as a read/write
codec with Immutant 2.x messaging and caching.

This is a standalone project, but cannot be used without
Immutant 2.x. Once Transit becomes stable, this will likely move in to
Immutant proper.

## Usage

immutant-transit transitively brings in
`com.cognitect/transit-clj` 0.8.255, and may not work with any other
version.

This won't work with Immutant 2.0.0-alpha1 - you'll need to run an
[incremental build](http://immutant.org/builds/2x/) (#298 or newer,
see [project.clj](project.clj) for the version we test against).

See the [example application](example-app/README.md) for sample usage
with Immutant.

For a Leiningen project:

    [org.immutant/immutant-transit "0.2.1"]

For a maven project:

    <dependency>
      <groupId>org.immutant</groupId>
      <artifactId>immutant-transit</artifactId>
      <version>0.2.1</version>
    </dependency>

## License

Immutant is licensed under the Apache License, v2. See
[LICENSE](LICENSE) for details.
