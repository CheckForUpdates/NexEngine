package su.nexmedia.engine.utils.regex;

import org.jetbrains.annotations.NotNull;

public class TimedCharSequence implements CharSequence {

    private CharSequence chars;
    private long timeout;
    private long maxTime;

    public TimedCharSequence(@NotNull CharSequence chars, long timeout) {
        this.chars = chars;
        this.timeout = timeout;
        this.maxTime = (System.currentTimeMillis() + timeout);
    }

    @Override
    public char charAt(int index) {
        if (System.currentTimeMillis() > this.maxTime) {
            throw new RuntimeMatchException(this.chars, this.timeout);
        }
        return this.chars.charAt(index);
    }

    @Override
    public int length() {
        return this.chars.length();
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return new TimedCharSequence(this.chars.subSequence(start, end), this.timeout);
    }

    @Override
    public String toString() {
        return this.chars.toString();
    }
}
