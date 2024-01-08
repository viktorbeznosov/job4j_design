package ru.job4j.cache.menu;

import ru.job4j.cache.AbstractCache;
import ru.job4j.cache.CacheFactory;
import ru.job4j.cache.DirCacheFileFactory;
import ru.job4j.cache.DirFileCache;

public class Emulator {
    private final Output out;

    private final Input in;

    public Emulator(Output out, Input in) {
        this.out = out;
        this.in = in;
    }

    public void init(AbstractCache cache, UserAction[] actions) {
        boolean run = true;
        while (run) {
            showMenu(actions);
            int select = in.askInt("Select: ");
            if (select < 0 || select >= actions.length) {
                out.println("Wrong input, you can select: 0 .. " + (actions.length - 1));
                continue;
            }
            UserAction action = actions[select];
            run = action.execute(cache);
        }
    }

    private void showMenu(UserAction[] actions) {
        out.println("Menu:");
        for (int index = 0; index < actions.length; index++) {
            out.println(index + ". " + actions[index].name());
        }
    }

    public static void main(String[] args) {
        Output out = new ConsoleOutput();
        Input in = new ConsoleInput();
        Emulator emulator = new Emulator(out, in);

        DirCacheFileFactory cacheFactory = new DirCacheFileFactory();
        String dir = cacheFactory.askDir(in);
        DirFileCache dirFileCache = cacheFactory.create(dir);
        UserAction[] actions = cacheFactory.actions(out, in);
        emulator.init(dirFileCache, actions);
    }
}
