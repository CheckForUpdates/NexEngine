package su.nexmedia.engine.utils.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import su.nexmedia.engine.NexEngine;

public class RegexUT {

    public static final Pattern PATTERN_EN = Pattern.compile("[a-zA-Z0-9_]*");
    public static final Pattern PATTERN_RU = Pattern.compile("[a-zA-Zа-яА-Я0-9_]*");

    public static boolean matchesEn(@NotNull String msg) {
        return matches(PATTERN_EN, msg);
    }

    public static boolean matchesEnRu(@NotNull String msg) {
        return matches(PATTERN_RU, msg);
    }

    public static boolean matches(@NotNull Pattern pattern, @NotNull String msg) {
        Matcher matcher = getMatcher(pattern, msg);
        return matcher != null && matcher.matches();
    }

    @Nullable
    public static Matcher getMatcher(@NotNull String patter, @NotNull String msg) {
        Pattern pattern = Pattern.compile(patter);
        return getMatcher(pattern, msg);
    }

    @Nullable
    public static Matcher getMatcher(@NotNull Pattern pattern, @NotNull String msg) {
        Matcher m = pattern.matcher(new TimedCharSequence(msg, 200));
        try {
            if (m.find()) {
                return pattern.matcher(new TimedCharSequence(msg, 200)); // Copy of matcher because of .find();
            }
        }
        catch (RuntimeMatchException ex) {
            NexEngine.get().error("Matcher timeout error! Pattern: '" + pattern.pattern() + "' String: '" + msg + "'");
        }
        return null;
    }
}
