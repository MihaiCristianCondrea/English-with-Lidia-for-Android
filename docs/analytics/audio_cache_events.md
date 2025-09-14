# Audio cache telemetry events

| Event | When | Properties |
|-------|------|------------|
| `audio_cache_first_download` | first time an audio file is cached | `content_id`, `size_bytes`, `duration_ms`, `success` |
| `audio_cache_play_local` | playback uses a cached file | `content_id` |
| `audio_cache_play_remote` | playback streams from network | `content_id`, `reason` (`download_failed`, `no_cache`, `low_storage`) |
| `audio_cache_evicted` | cache entry removed after eviction | `content_id`, `age_days`, `size_bytes` |
| `audio_cache_url_changed` | API provided a new URL for same content_id | `content_id` |
