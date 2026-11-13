# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project overview

`irurueta-sorting` is a small, dependency-free Java 21 library (Maven, `com.irurueta:irurueta-sorting`) that sorts and
selects elements in `double[]`, `float[]`, `int[]`, `long[]`, and object arrays (via `Comparable`/`Comparator`). All
public source lives under `src/main/java/com/irurueta/sorting`, and every class has a mirroring test class under
`src/test/java/com/irurueta/sorting`.

## Common commands

```bash
mvn test          # run unit tests (Surefire)
mvn package        # build the JAR, run tests, generate JaCoCo coverage
mvn site           # generate Checkstyle/PMD/SpotBugs/Javadoc/coverage reports
```

Run a single test class or method with Surefire's `-Dtest` filter:

```bash
mvn test -Dtest=QuicksortSorterTest
mvn test -Dtest=QuicksortSorterTest#testSortWithComparator
```

Static analysis plugins (Checkstyle, PMD, SpotBugs) are wired into `mvn site`/`mvn verify`, not `mvn test`; run them
explicitly when checking style/quality:

```bash
mvn checkstyle:check
mvn pmd:check
mvn spotbugs:check
```

Antora documentation (source in `docs/modules/ROOT`) is built separately:

```bash
cd docs
npx antora antora-playbook.yml
```

## Architecture

Every algorithm implements the same contract by extending the abstract class `Sorter<T>`
(`src/main/java/com/irurueta/sorting/Sorter.java`), which is the entry point to the whole library:

- Overloaded `sort(...)` / `sortWithIndices(...)` methods exist per primitive array type (`double[]`, `float[]`,
  `int[]`, `long[]`) plus a generic `T[]` + `Comparator<T>` variant. Subclasses only implement the abstract
  `fromIndex`/`toIndex` variants; `Sorter` provides the convenience overloads (whole-array, `Comparable[]`) on top of
  them.
- `sortWithIndices` returns an `int[]` of original positions so callers can reorder companion
  arrays/collections consistently with the sorted values.
- `select(k, array, ...)` and `median(array, ...)` find the k-th smallest element / median via partial partitioning
  (Numerical Recipes' `select` algorithm) without fully sorting — same per-type overload pattern as `sort`.
  `median` on object arrays requires a `ComparatorAndAverager<T>` (see below) to compute the average of the two
  middle elements for even-length arrays.
- `Sorter.create()` / `Sorter.create(SortingMethod)` is the factory entry point; each `SortingMethod` enum value
  maps to one concrete subclass.

Concrete implementations (all under the same package, each `extends Sorter<T>` unless noted):

- `StraightInsertionSorter`, `ShellSorter`, `QuicksortSorter`, `HeapsortSorter` — independent algorithm
  implementations based on *Numerical Recipes, 3rd Edition*.
- `SystemSorter` — **extends `QuicksortSorter`**, not `Sorter` directly. It overrides only the primitive-array/`T[]`
  `sort` methods to delegate to `java.util.Arrays.sort`, but inherits `select`/`median` (and `sortWithIndices`, which
  it does not support) from `QuicksortSorter`.

Supporting types:

- `SortingMethod` — enum selecting the algorithm; used by `Sorter.create(...)`.
- `ComparatorAndAverager<T>` (extends `Comparator<T>`) and `ComparableAndAverageable<T>` (extends `Comparable<T>`) —
  used only by `median()` on object arrays, since computing a median for an even-length array requires averaging two
  middle elements, not just comparing them.
- `SortingException` — checked exception thrown by `sort`/`sortWithIndices` implementations.
- `BuildInfo` — reads `build-info.properties` (generated at build time by the `groovy-maven-plugin` execution in
  `pom.xml`, which stamps group/artifact/version and CI build/commit/branch info into
  `src/main/resources/com/irurueta/sorting/build-info.properties`).

When adding a new sorting algorithm, follow the existing pattern: extend `Sorter<T>`, implement the `fromIndex`/
`toIndex` overloads for each primitive type plus the `T[]`/`Comparator<T>` variant, implement `getMethod()`, add a
`SortingMethod` enum value, and wire it into `Sorter.create(SortingMethod)`.

## Code style

- Checkstyle (`checkstyle.xml`) enforces: 120-char line length, no tabs, full Javadoc on every type/method/field
  (including private fields — `JavadocVariable`), `ImportOrder`, and package-level Javadoc via `JavadocPackage`
  (every package needs a `package-info.java`).
- Existing source files carry an Apache 2.0 license header (`Copyright (C) <year> Alberto Irurueta Carro`) — match
  this when adding new files.
- Tests use JUnit 5 (`org.junit.jupiter`) and commonly drive randomized property-based checks across many iterations
  using `com.irurueta.statistics.UniformRandomizer` (test-scope dependency) rather than fixed fixtures — see
  `QuicksortSorterTest` for the pattern (random array length/bounds, assert sortedness, assert exceptions on invalid
  `fromIndex`/`toIndex`).
