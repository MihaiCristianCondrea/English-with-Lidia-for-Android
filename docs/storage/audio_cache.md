# Audio cache storage

Lesson audio is cached in `DataStore` preferences and app-private files.

## Preference keys
- `audio.<content_id>.blob`: JSON encoded [`CacheEntry`](../../app/src/main/kotlin/com/d4rk/englishwithlidia/plus/core/data/audio/AudioCacheManager.kt) containing:
  - `url`: last remote URL
  - `urlHash`: SHA-256 of the URL
  - `filePath`: absolute path to cached file or empty if none
  - `lastOpenedMs`: epoch millis when last played
  - `sizeBytes`: size of the cached file
- `audio.cache.eviction_last_run_ms`: timestamp of the last eviction task run

## Storage path
Cached files live under `filesDir/audio_cache/` with names
`<content_id>_<url_hash>.mp3`.
