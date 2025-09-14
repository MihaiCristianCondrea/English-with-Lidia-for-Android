# Deep Dive into AndroidX Media3: Modernizing Media Playback, Notifications, and Controls


This document summarizes the key points from an exploration of AndroidX Media3, highlighting how it unifies media playback, notifications, and controls.

## What is AndroidX Media3?

Media3 consolidates previous media support libraries (such as ExoPlayer and various session APIs) into a cohesive set of modules. It provides a common `Player` interface used by components like `MediaSession` and `MediaController`, simplifying media app development across the Android ecosystem.

### Key Components

- **Player** – High-level interface for playback operations. Implemented by ExoPlayer and used by sessions and controllers.
- **ExoPlayer** – Default implementation of `Player`, providing robust playback.
- **MediaSession** – Exposes playback externally and receives commands from other apps or system UIs.
- **MediaSessionService** – Hosts a `MediaSession` in a foreground service for background playback.
- **MediaController** – Allows external clients to control a session and also implements `Player` for use with UI widgets.
- **MediaLibraryService** and **MediaBrowser** – Support browsing and discovery of media content.
- **UI components** – `PlayerView` for views or `PlayerSurface`/`media3-ui-compose` for Jetpack Compose.
- **Transformer** – APIs for media editing tasks like trimming and effects.

## Notifications

`MediaSessionService` automatically creates a rich media notification when media items are loaded in the playlist. The notification updates as playback state or metadata changes, and remains visible while the service is running. For Android 13+ (API 33), the system UI derives notification content directly from the `MediaSession`.

- Customization is achieved by updating `MediaItem.MediaMetadata` and configuring media button preferences.
- Playback resumption via hardware media buttons requires declaring a `MediaButtonReceiver` in the manifest and implementing `onPlaybackResumption` in the session callback.

## Controls

`MediaSession` acts as the central hub for playback control from headphones, the system UI, voice assistants, or other apps. UI elements typically interact with a `MediaController`, which forwards commands to the session.

Custom media commands can be exposed using `SessionCommand` objects. Button layouts are defined through an ordered list of `CommandButton` instances.

## Performance and Migration

Media3 brings performance optimizations such as decoder pre-warming and offloading operations from the main thread to reduce ANRs. Transformer APIs prioritize transmuxing for quick edits.

Migrating from standalone ExoPlayer is recommended, as Media3 is now the official home for these libraries.

## In This Repository

This project already uses Media3 ExoPlayer, session, and UI libraries in `app/build.gradle.kts`:

```kotlin
implementation(dependencyNotation = libs.media3.exoplayer)
implementation(dependencyNotation = libs.media3.ui)
implementation(dependencyNotation = libs.media3.session)
```

The `LessonViewModel` class demonstrates basic playback using an `ExoPlayer` instance.

The app uses `MediaSessionService` to create and manage the media notification automatically, so custom `PlayerNotificationManager` logic is no longer required.

