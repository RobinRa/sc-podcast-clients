# sc-podcast-clients

Defines the podcast clients used by SoundCloud in their creator stats.

Status: development/experimental

Owned by: [SoundCloud Data Team]

Contributing: Pull requests are welcome. The [SoundCloud Data Team] will
review, merge and deploy.

Issues: please report with GitHub Issues

## Usage

This library is published to SoundCloud's internal Maven repository. If you
have access to this repository add this library as a dependency to your build.

    group: com.soundcloud
    artifact: sc-podcast-clients
    version: please see the latest

If you don't have access to SoundCloud's internal Maven repository, you will
need to build the library yourself as described later here.

This library supports two main use-cases.

### 1. Mapping user-agents to podcast client

To map a http request user-agent to a podcast client:


    import com.soundcloud.podcast.client.PodcastClient;

    PodcastClient client = PodcastClient.parse("iTunes/9.1.1");
    System.out.println("code: " + client.getCode());
    System.out.println("name: "+ client.getName());

    //prints
    //code: 12
    //name: iTunes

If no podcast client can be determined from the user-agent then the
`PodcastClient.parse` method returns null.

### 2. Looking up podcast clients from their codes

For the sake of compact storage, and to support name changes all podcast
clients are given a unique positive integer code. To lookup the name of a
client based on it's code:

    import com.soundcloud.podcast.client.PodcastClient;

    PodcastClient client = new PodcastClient(12);
    System.out.println("name: " + client.getName());

    //prints
    //name: iTunes

If the code is unrecognised then a `RuntimeException` is thrown.

## Adding or editing mappings

All the mappings are defined in the [settings.csv]. This file has a line per
podcast client which sets its code, and defines regexes for matching against
user-agent strings. Use this file to add or edit mappings.

To ensure the mappings are defined correctly there is the [examples.csv], which
contains lots of user-agents along with the podcast clients we expected them to
map to. This is checked as part of unit testing. If you make any changes to the
[settings.csv] you may wish to change the [examples.csv] too.

## Building and deploying

You will need Maven to build. To build you can call `make dist`.

To publish the library, first edit the `repository` and `snapshotRepository` as
defined in the `pom.xml`, to reflect your own repository setup. Then call `make
publish`.


[SoundCloud Data Team]: https://github.com/orgs/soundcloud/teams/team-data
[settings.csv]: https://github.com/soundcloud/sc-podcast-clients/blob/master/src/main/resources/settings.csv
[examples.csv]: https://github.com/soundcloud/sc-podcast-clients/blob/master/src/test/resources/examples.csv

