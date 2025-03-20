# patchwork-book-library

### Instructions:
This project has been structured as a library - there is no main() entrypoint. Expectations have been defined prior to
the code under test by following TDD. Tests were based off the provided "story" list (they're available in the form of
comments in [LibraryTests.kt](lib/src/test/kotlin/com/caiodorn/book/library/LibraryTests.kt), *just for reference*).

Disclaimer: I am not very well-versed in Kotlin, but I tried my best to at least come up with something that is easy to
follow, and hopefully clean. As requested I came up with something very basic that satisfies the aforementioned stories. 
The only thing I couldn't get away with was a build tool, that's the only added "clutter".

To build and run the application, use the *Gradle* tool window by clicking the Gradle icon in the right-hand toolbar,
or run it directly from the terminal:

* Run `./gradlew run` to build and run the application.
* Run `./gradlew build` to only build the application.
* Run `./gradlew check` to run all checks, including tests.
* Run `./gradlew clean` to clean all build outputs.

Note the usage of the Gradle Wrapper (`./gradlew`).
This is the suggested way to use Gradle in production projects.

