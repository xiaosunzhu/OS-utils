package net.sunyijun.os.cmd;

/**
 * @author sun.yijun
 */
public class PingExecutor extends Executor {

    @Override
    protected Process executeWindows() {
        return null;
    }

    @Override
    protected Process executeLinux() {
        return null;
    }

    @Override
    protected Process executeOthers() {
        throw new UnsupportedOperationException("Ping command not support other OS yet!");
    }

}
