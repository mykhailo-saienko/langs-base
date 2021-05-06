package ms.utils;

import static ms.ipp.Iterables.appendList;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Executor {
    private Function<Integer, Integer> waitFor;
    private final Set<Class<? extends Exception>> nonfatals;
    private static final Logger logger = LogManager.getLogger();
    private final Map<Class<? extends Exception>, Consumer<Exception>> fatalHandlers;

    public Executor() {
        nonfatals = new HashSet<>();
        fatalHandlers = new HashMap<>();
    }

    public Executor setNonFatals(Iterable<? extends Class<? extends Exception>> fatals) {
        nonfatals.clear();
        return addNonFatals(fatals);
    }

    public Executor addNonFatals(Iterable<? extends Class<? extends Exception>> fatals) {
        fatals.forEach(nonfatals::add);
        return this;
    }

    public <T extends Exception> Executor setFatalHandler(Class<T> fatal, Consumer<T> handler) {
        if (fatal != null && handler != null) {
            fatalHandlers.put(fatal, e -> handler.accept(fatal.cast(e)));
        }
        return this;
    }

    /**
     * Sets a wait function determining how long (in milliseconds) the executor should wait after an
     * attempt with a given index.
     * 
     * @param waitFor
     */
    public Executor setWaitFor(Function<Integer, Integer> waitFor) {
        this.waitFor = waitFor;
        return this;
    }

    public boolean run(Runnable r, String entity, int attempts) {
        int attempt = 1;
        while (true) {
            try {
                r.run();
                return true;
            } catch (Exception e) {
                if (nonfatals.contains(e.getClass())) {
                    logger.info("Non-fatal " + e.getClass().getSimpleName() + " after attempt #"
                                + attempt + " on entity '" + entity + "': " + e.getMessage());

                    if (attempt < attempts) {
                        int sleep = waitFor == null ? 0 : waitFor.apply(attempt);

                        if (sleep > 0) {
                            logger.info("Retrying in " + sleep + " millis");
                            DateHelper.sleep(sleep);
                        }
                        ++attempt;
                    } else {
                        logger.error("Giving up re-tries on '" + entity + "'");
                        return false;
                    }
                } else {
                    List<StackTraceElement> stackTrace = Arrays.asList(e.getStackTrace());
                    String serialized = appendList(stackTrace, "\t", "", "\n\t", s -> s.toString());
                    logger.error("\n-------------------------------------------------------------------\n"
                                 + "Unexpected {} for attempt #{} on entity '{}':\n{}\nStack trace:\n{}"
                                 + "\n-------------------------------------------------------------------\n",
                                 e.getClass().getSimpleName(),
                                 attempt,
                                 entity,
                                 e.getMessage(),
                                 serialized);
                    Consumer<Exception> handler = fatalHandlers.get(e.getClass());
                    if (handler != null) {
                        logger.trace("Execution special handler for {}", e.getClass());
                        handler.accept(e);
                    }
                    return false;
                }
            }
        }
    }
}