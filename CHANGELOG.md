# Changelog

All notable changes to this project are documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/), and this project adheres to
[Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [1.4.0] - 2026-07-05

### Added

- Antora-based documentation site under `docs/`, with dedicated pages for installation, usage, and each sorting
  algorithm (Quicksort, Heapsort, Shell sort, straight insertion sort, selection).

### Changed

- Expanded `README.md` with more detail and updated CI workflows to build and publish the new documentation site.

## [1.3.2] - 2025-09-18

### Changed

- Migrated artifact publishing from the Nexus staging/OSSRH plugin to the Sonatype Central Publishing Maven
  plugin.
- Updated build plugin versions (Surefire/Failsafe, JaCoCo, SpotBugs, Javadoc, Checkstyle, Maven Site, GPG) and
  bumped the `irurueta-statistics` test dependency to 1.3.4.

## [1.3.1] - 2024-10-19

### Changed

- Bumped the `irurueta-statistics` test dependency to 1.3.2.

## [1.3.0] - 2024-10-14

### Changed

- Raised the minimum/target Java version from 1.7 to Java 17.
- Migrated tests from JUnit 4 to JUnit 5 (`org.junit.jupiter`).
- Refactored source and test code to use local-variable type inference (`var`).
- Updated Checkstyle configuration and Maven plugin versions.

## [1.2.0] - 2023-11-12

### Fixed

- `SystemSorter`'s `sort` methods no longer declare `throws SortingException`, since delegating to
  `java.util.Arrays.sort` cannot actually throw it.

### Changed

- Bumped the `irurueta-statistics` test dependency to 1.2.0 and refreshed tests accordingly.

## [1.1.0] - 2021-12-10

### Changed

- Migrated the CI/CD pipeline from Travis CI to GitHub Actions.

## [1.0.0] - 2021-12-08

Initial release.

### Added

- `Sorter<T>` abstract base class providing a common API to sort `double[]`, `float[]`, `int[]`, `long[]`, and
  object arrays (via `Comparable` or a `Comparator`).
- `sortWithIndices` to retrieve the original positions of sorted elements, so other arrays/collections can be
  reordered consistently.
- `select` and `median` to find the k-th smallest element or the median without fully sorting the array.
- Four selectable algorithms — `StraightInsertionSorter`, `ShellSorter`, `QuicksortSorter`, and `HeapsortSorter` —
  plus `SystemSorter`, which delegates to the JDK's own sort.
- `SortingMethod` enum and `Sorter.create(...)` factory to pick an algorithm statically or dynamically.

[Unreleased]: https://github.com/albertoirurueta/irurueta-sorting/compare/1.4.0...HEAD
[1.4.0]: https://github.com/albertoirurueta/irurueta-sorting/compare/1.3.2...1.4.0
[1.3.2]: https://github.com/albertoirurueta/irurueta-sorting/compare/1.3.1...1.3.2
[1.3.1]: https://github.com/albertoirurueta/irurueta-sorting/compare/1.3.0...1.3.1
[1.3.0]: https://github.com/albertoirurueta/irurueta-sorting/compare/1.2.0...1.3.0
[1.2.0]: https://github.com/albertoirurueta/irurueta-sorting/compare/1.1.0...1.2.0
[1.1.0]: https://github.com/albertoirurueta/irurueta-sorting/compare/1.0.0...1.1.0
[1.0.0]: https://github.com/albertoirurueta/irurueta-sorting/releases/tag/1.0.0
