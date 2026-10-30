# Contributing

Bug reports, questions and pull requests are welcome. For bigger changes, open an
issue first so we can agree on the approach.

Issues labeled `good first issue` are a good place to start.

## Building

Use the Maven Wrapper to run the pinned Maven version (3.9.16):

```sh
bash mvnw -B -ntp verify
```

On Windows, run `.\mvnw.cmd -B -ntp verify`. Set `JAVA_HOME` to your JDK.
CI builds and tests on Java 8, 11, 17, 21, and 25; published classes target Java 8.

Lombok is a provided, explicitly configured annotation processor; it is not a
runtime dependency. The lightweight standalone Snowball English stemmer replaces
OpenNLP, avoiding its unused model-loading APIs and their vulnerabilities.
JUnit and OpenCSV (including its BeanUtils dependency) are test-only.

## Releasing

Releases use the Sonatype Central Publisher Portal rather than the retired OSSRH
staging service. Configure a `central` server in your local Maven `settings.xml`
with a Portal user token; never put credentials in the repository. Snapshot
deployments use the Central snapshot repository with the same server ID.
Releases must also be signed with GPG (`-DperformRelease=true` activates signing).
For a non-SNAPSHOT release version, `bash mvnw -DperformRelease=true deploy` signs,
uploads, and automatically publishes the artifacts after Central validation.
Supply GPG credentials through your local agent or the Maven GPG plugin's
supported environment variables, not command-line passphrase arguments.

Run `bash mvnw -B -ntp verify` (or `.\mvnw.cmd -B -ntp verify` on Windows)
before publishing. This builds and tests locally, producing the binary, source,
and Javadoc JARs without signing, committing, tagging, pushing, or deploying.
Publishing remains a separate, explicit step using the instructions above.
