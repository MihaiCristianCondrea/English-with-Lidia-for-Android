# Audio cache eviction worker

`AudioCacheEvictionWorker` runs once per day and removes cached
files that have not been opened for 30 days.

The worker iterates over `audio.*.blob` entries in the `audio_cache`
`DataStore`. For each entry older than 30 days it:
1. Deletes the cached file if present.
2. Clears the `filePath` in the stored blob.
3. Updates `audio.cache.eviction_last_run_ms`.

The work is scheduled via `WorkManager` with the unique name
`audio_cache_eviction`.
