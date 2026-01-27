##############################################
# App - ProGuard / R8 rules
##############################################

# Crashlytics: keep line numbers for readable stacktraces
-keepattributes SourceFile,LineNumberTable

# Kotlin metadata/annotations used by serialization, reflection, Compose tooling, etc.
-keepattributes *Annotation*,InnerClasses,EnclosingMethod

# Kotlinx Serialization (portable template)
-keep @kotlinx.serialization.Serializable class ** { *; }
-keepclassmembers class ** {
    public static ** Companion;
}

-keepclassmembers class **$Companion {
    public kotlinx.serialization.KSerializer serializer(...);
}

-keepclassmembers class **$$serializer { *; }

# Optional noise suppression (safe)
-dontwarn kotlinx.coroutines.**
-dontwarn io.ktor.**
