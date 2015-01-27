package com.soundcloud.podcast.client;

public final class PodcastClient {

    private final int code;
    private final String name;

    private final static Settings settings = Settings.read();

    public PodcastClient(int code) {
      this.code = code;
      this.name = settings.getName(code);

      if (name==null) {
          throw new RuntimeException("Unrecognised podcast client: " + code);
      }
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static PodcastClient parse(String userAgent) {
        for (Settings.Match match : settings.getMatches()) {
            if (match.matches(userAgent)) {
                return new PodcastClient(match.getCode());
            }
        }
        return null;
    }
}
