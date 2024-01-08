package ru.job4j.cache;

import ru.job4j.cache.menu.*;

public class DirCacheFileFactory implements CacheFactory<String, DirFileCache> {

    @Override
    public DirFileCache create(String cachingDir) {
        return new DirFileCache(cachingDir);
    }

    public String askDir(Input in) {
        return in.askStr("Enter file dir (for example 'src/main/java/ru/job4j/cache/files')");
    }

    private class GetAction implements UserAction {
        private final Output out;

        private final Input in;

        public GetAction(Output out, Input in) {
            this.out = out;
            this.in = in;
        }

        @Override
        public String name() {
            return "Get value from cache";
        }

        @Override
        public boolean execute(AbstractCache cache) {
            out.println("=== Get value ====");
            String key = in.askStr("Enter key: ");
            String value = (cache.get(key) != null) ? cache.get(key).toString() : null;

            out.println(String.format("Value is: %s", value));
            return true;
        }
    }

    private class PutAction implements UserAction {
        private final Output out;

        private final Input in;

        public PutAction(Output out, Input in) {
            this.out = out;
            this.in = in;
        }

        @Override
        public String name() {
            return "Put value into cache";
        }

        @Override
        public boolean execute(AbstractCache cache) {
            out.println("=== Put new value ====");
            String key = in.askStr("Enter key: ");
            String value = in.askStr("Enter value: ");
            cache.put(key, value);
            out.println(String.format("Added new value: %s %s", key, value));
            return true;
        }
    }

    private class ExitProgram implements UserAction {
        private final Output out;

        public ExitProgram(Output out) {
            this.out = out;
        }

        @Override
        public String name() {
            return "Exit Program";
        }

        @Override
        public boolean execute(AbstractCache cache) {
            out.println("=== Exit Program ===");
            return false;
        }
    }

    @Override
    public UserAction[] actions(Output out, Input in) {
        return new UserAction[]{
            new GetAction(out, in),
            new PutAction(out, in),
            new ExitProgram(out)
        };
    }
}
