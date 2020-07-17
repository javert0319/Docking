package com.chia.tracelog.parser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName: LogPattern
 * @Description: 作用描述
 * @Author: CHIA
 * @CreateDate: 2020/7/15
 */
public abstract class LogPattern {
    private final int count;
    private final int length;

    private LogPattern(int count, int length) {
        this.count = count;
        this.length = length;
    }

    public final String apply(StackTraceElement caller) {
        String string = this.doApply(caller);
        return ParserUtils.shorten(string, this.count, this.length);
    }

    protected abstract String doApply(StackTraceElement var1);

    protected boolean isCallerNeeded() {
        return false;
    }

    public static LogPattern compile(String pattern) {
        try {
            return pattern == null ? null : (new LogPattern.Compiler()).compile(pattern);
        } catch (Exception var2) {
            return new LogPattern.PlainLogPattern(0, 0, pattern);
        }
    }

    public static class Compiler {
        private String patternString;
        private int position;
        private List<ConcatenateLogPattern> queue;
        public static final Pattern PERCENT_PATTERN = Pattern.compile("%%");
        public static final Pattern NEWLINE_PATTERN = Pattern.compile("%n");
        public static final Pattern CALLER_PATTERN = Pattern.compile("%([+-]?\\d+)?(\\.([+-]?\\d+))?caller(\\{([+-]?\\d+)?(\\.([+-]?\\d+))?\\})?");
        public static final Pattern DATE_PATTERN = Pattern.compile("%date(\\{(.*?)\\})?");
        public static final Pattern CONCATENATE_PATTERN = Pattern.compile("%([+-]?\\d+)?(\\.([+-]?\\d+))?\\(");
        public static final Pattern DATE_PATTERN_SHORT = Pattern.compile("%d(\\{(.*?)\\})?");
        public static final Pattern CALLER_PATTERN_SHORT = Pattern.compile("%([+-]?\\d+)?(\\.([+-]?\\d+))?c(\\{([+-]?\\d+)?(\\.([+-]?\\d+))?\\})?");
        public static final Pattern THREAD_NAME_PATTERN = Pattern.compile("%([+-]?\\d+)?(\\.([+-]?\\d+))?thread");
        public static final Pattern THREAD_NAME_PATTERN_SHORT = Pattern.compile("%([+-]?\\d+)?(\\.([+-]?\\d+))?t");

        public Compiler() {
        }

        public LogPattern compile(String string) {
            if (string == null) {
                return null;
            } else {
                this.position = 0;
                this.patternString = string;
                this.queue = new ArrayList();
                this.queue.add(new LogPattern.ConcatenateLogPattern(0, 0, new ArrayList()));

                while(string.length() > this.position) {
                    int index = string.indexOf("%", this.position);
                    int bracketIndex = string.indexOf(")", this.position);
                    if (this.queue.size() > 1 && bracketIndex < index) {
                        ((LogPattern.ConcatenateLogPattern)this.queue.get(this.queue.size() - 1)).addPattern(new LogPattern.PlainLogPattern(0, 0, string.substring(this.position, bracketIndex)));
                        ((LogPattern.ConcatenateLogPattern)this.queue.get(this.queue.size() - 2)).addPattern((LogPattern)this.queue.remove(this.queue.size() - 1));
                        this.position = bracketIndex + 1;
                    }

                    if (index == -1) {
                        ((LogPattern.ConcatenateLogPattern)this.queue.get(this.queue.size() - 1)).addPattern(new LogPattern.PlainLogPattern(0, 0, string.substring(this.position)));
                        break;
                    }

                    ((LogPattern.ConcatenateLogPattern)this.queue.get(this.queue.size() - 1)).addPattern(new LogPattern.PlainLogPattern(0, 0, string.substring(this.position, index)));
                    this.position = index;
                    this.parse();
                }

                return (LogPattern)this.queue.get(0);
            }
        }

        private void parse() {
            Matcher matcher;
            if ((matcher = this.findPattern(PERCENT_PATTERN)) != null) {
                ((LogPattern.ConcatenateLogPattern)this.queue.get(this.queue.size() - 1)).addPattern(new LogPattern.PlainLogPattern(0, 0, "%"));
                this.position = matcher.end();
            } else if ((matcher = this.findPattern(NEWLINE_PATTERN)) != null) {
                ((LogPattern.ConcatenateLogPattern)this.queue.get(this.queue.size() - 1)).addPattern(new LogPattern.PlainLogPattern(0, 0, "\n"));
                this.position = matcher.end();
            } else {
                int count;
                int length;
                if ((matcher = this.findPattern(CALLER_PATTERN)) == null && (matcher = this.findPattern(CALLER_PATTERN_SHORT)) == null) {
                    if ((matcher = this.findPattern(DATE_PATTERN)) == null && (matcher = this.findPattern(DATE_PATTERN_SHORT)) == null) {
                        if ((matcher = this.findPattern(THREAD_NAME_PATTERN)) == null && (matcher = this.findPattern(THREAD_NAME_PATTERN_SHORT)) == null) {
                            if ((matcher = this.findPattern(CONCATENATE_PATTERN)) != null) {
                                count = Integer.parseInt(matcher.group(1) == null ? "0" : matcher.group(1));
                                length = Integer.parseInt(matcher.group(3) == null ? "0" : matcher.group(3));
                                this.queue.add(new LogPattern.ConcatenateLogPattern(count, length, new ArrayList()));
                                this.position = matcher.end();
                            } else {
                                throw new IllegalArgumentException();
                            }
                        } else {
                            count = Integer.parseInt(matcher.group(1) == null ? "0" : matcher.group(1));
                            length = Integer.parseInt(matcher.group(3) == null ? "0" : matcher.group(3));
                            ((LogPattern.ConcatenateLogPattern)this.queue.get(this.queue.size() - 1)).addPattern(new LogPattern.ThreadNameLogPattern(count, length));
                            this.position = matcher.end();
                        }
                    } else {
                        String dateFormat = matcher.group(2);
                        ((LogPattern.ConcatenateLogPattern)this.queue.get(this.queue.size() - 1)).addPattern(new LogPattern.DateLogPattern(0, 0, dateFormat));
                        this.position = matcher.end();
                    }
                } else {
                    count = Integer.parseInt(matcher.group(1) == null ? "0" : matcher.group(1));
                    length = Integer.parseInt(matcher.group(3) == null ? "0" : matcher.group(3));
                    int countCaller = Integer.parseInt(matcher.group(5) == null ? "0" : matcher.group(5));
                    int lengthCaller = Integer.parseInt(matcher.group(7) == null ? "0" : matcher.group(7));
                    ((LogPattern.ConcatenateLogPattern)this.queue.get(this.queue.size() - 1)).addPattern(new LogPattern.CallerLogPattern(count, length, countCaller, lengthCaller));
                    this.position = matcher.end();
                }
            }
        }

        private Matcher findPattern(Pattern pattern) {
            Matcher matcher = pattern.matcher(this.patternString);
            return matcher.find(this.position) && matcher.start() == this.position ? matcher : null;
        }
    }

    public static class ThreadNameLogPattern extends LogPattern {
        public ThreadNameLogPattern(int count, int length) {
            super(count, length);
        }

        protected String doApply(StackTraceElement caller) {
            return Thread.currentThread().getName();
        }
    }

    public static class ConcatenateLogPattern extends LogPattern {
        private final List<LogPattern> patternList;

        public ConcatenateLogPattern(int count, int length, List<LogPattern> patternList) {
            super(count, length);
            this.patternList = new ArrayList(patternList);
        }

        public void addPattern(LogPattern pattern) {
            this.patternList.add(pattern);
        }

        protected String doApply(StackTraceElement caller) {
            StringBuilder builder = new StringBuilder();
            Iterator var3 = this.patternList.iterator();

            while(var3.hasNext()) {
                LogPattern pattern = (LogPattern)var3.next();
                builder.append(pattern.apply(caller));
            }

            return builder.toString();
        }

        protected boolean isCallerNeeded() {
            Iterator var1 = this.patternList.iterator();

            LogPattern pattern;
            do {
                if (!var1.hasNext()) {
                    return false;
                }

                pattern = (LogPattern)var1.next();
            } while(!pattern.isCallerNeeded());

            return true;
        }
    }

    public static class CallerLogPattern extends LogPattern {
        private int callerCount;
        private int callerLength;

        public CallerLogPattern(int count, int length, int callerCount, int callerLength) {
            super(count, length);
            this.callerCount = callerCount;
            this.callerLength = callerLength;
        }

        protected String doApply(StackTraceElement caller) {
            if (caller == null) {
                throw new IllegalArgumentException("Caller not found");
            } else {
                String callerString;
                if (caller.getLineNumber() < 0) {
                    callerString = String.format("%s#%s", caller.getClassName(), caller.getMethodName());
                } else {
                    String stackTrace = caller.toString();
                    stackTrace = stackTrace.substring(stackTrace.lastIndexOf(40), stackTrace.length());
                    callerString = String.format("%s.%s%s", caller.getClassName(), caller.getMethodName(), stackTrace);
                }

                try {
                    return ParserUtils.shortenClassName(callerString, this.callerCount, this.callerLength);
                } catch (Exception var4) {
                    return var4.getMessage();
                }
            }
        }

        protected boolean isCallerNeeded() {
            return true;
        }
    }

    public static class DateLogPattern extends LogPattern {
        private final SimpleDateFormat dateFormat;

        public DateLogPattern(int count, int length, String dateFormat) {
            super(count, length);
            if (dateFormat != null) {
                this.dateFormat = new SimpleDateFormat(dateFormat);
            } else {
                this.dateFormat = new SimpleDateFormat("HH:mm:ss.SSS");
            }

        }

        protected String doApply(StackTraceElement caller) {
            return this.dateFormat.format(new Date());
        }
    }

    public static class PlainLogPattern extends LogPattern {
        private final String string;

        public PlainLogPattern(int count, int length, String string) {
            super(count, length);
            this.string = string;
        }

        protected String doApply(StackTraceElement caller) {
            return this.string;
        }
    }
}
