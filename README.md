# immutant-transit

A library that allows you to use
[Transit](https://github.com/cognitect/transit-format) as a read/write
codec with Immutant 2.x messaging and caching.

## Usage

You'll need to bring in your own version of Transit ("0.8.255" or
later, see [project.clj](project.clj) for the version we test
against).

This won't work with Immutant 2.0.0-alpha1 - you'll need to run an
[incremental build](http://immutant.org/builds/2x/) (#292 or newer,
see [project.clj](project.clj) for the version we test against).

See the [example application](example-app/README.md) for sample usage
with Immutant.

TODO: coords, sample

## License

Immutant is licensed under the Apache License, v2. See
[LICENSE](LICENSE) for details.
