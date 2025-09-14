# Lesson API

The lesson endpoint returns JSON describing a lesson. Each lesson contains an array of `lesson_content` objects. A content item of type `content_player` previously had the following structure:

```json
{
  "content_id": "1",
  "content_type": "content_player",
  "content_audio_url": "https://.../tell_me_about_yourself.mp3"
}
```

Starting with version 5.2.0, an optional `content_thumbnail_url` field may also be provided. This thumbnail is used as artwork for the Media3 notification during audio playback.

```json
{
  "content_id": "1",
  "content_type": "content_player",
  "content_audio_url": "https://.../tell_me_about_yourself.mp3",
  "content_thumbnail_url": "https://example.com/thumb.jpg"
}
```

If omitted, the notification will display without artwork.
