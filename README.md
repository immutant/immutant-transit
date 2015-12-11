# immutant-transit

A library that allows you to use
[Transit](https://github.com/cognitect/transit-format) as a read/write
codec with Immutant 2.x messaging and caching.

This is a standalone project, but cannot be used without
Immutant 2.0.0 or newer. Once Transit becomes stable, this will likely
move in to Immutant proper.

## Usage

immutant-transit transitively brings in
`com.cognitect/transit-clj` 0.8.285, and may not work with any older
version.

See the [example application](example-app/README.md) for sample usage
with Immutant.

For a Leiningen project:

    [org.immutant/immutant-transit "0.2.3"]

For a maven project:

    <dependency>
      <groupId>org.immutant</groupId>
      <artifactId>immutant-transit</artifactId>
      <version>0.2.3</version>
    </dependency>

## License

Immutant is licensed under the Apache License, v2. See
[LICENSE](LICENSE) for details.
